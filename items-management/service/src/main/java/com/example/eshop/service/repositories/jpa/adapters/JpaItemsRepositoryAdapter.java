package com.example.eshop.service.repositories.jpa.adapters;


import com.example.contract.services.DocumentsService;
import com.example.eshop.contract.UserIdentityProvider;
import com.example.eshop.service.repositories.jpa.JpaItemsRepository;
import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import com.example.eshop.service.repositories.jpa.mappers.ItemEntityMapper;
import com.example.eshop.service.repositories.jpa.specifications.ItemsSpecifications;
import com.example.eshop.service.repositories.lsitingrepositories.ItemsListingRepository;
import com.example.modals.Item;
import com.example.modals.ItemCategory;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("jpa")
public class JpaItemsRepositoryAdapter implements ItemsListingRepository {
    private final JpaItemsRepository jpaItemsRepository;
    private final ItemEntityMapper itemEntityMapper;
    private final DocumentsService externalDocumentsService;

    private final UserIdentityProvider identityProvider;

    public JpaItemsRepositoryAdapter(JpaItemsRepository jpaItemsRepository, ItemEntityMapper itemEntityMapper, DocumentsService externalDocumentsService, UserIdentityProvider identityProvider) {
        this.jpaItemsRepository = jpaItemsRepository;
        this.itemEntityMapper = itemEntityMapper;
        this.externalDocumentsService = externalDocumentsService;
        this.identityProvider = identityProvider;
    }

    @Override
    public Item save(Item item) {
        final ItemEntity itemEntity = itemEntityMapper.toEntity(item);

        itemEntity.setCreatedBy(identityProvider.getCurrentUserIdentity().getUsername());
        itemEntity.setUpdatedBy(itemEntity.getCreatedBy());

        //the jpa save will generate id on save call...
        final ItemEntity savedEntity = jpaItemsRepository.save(itemEntity);

        item.setId(savedEntity.getId());

        item.getPictures().forEach(it -> externalDocumentsService.uploadDocument(item, it));

        return itemEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Item update(Item item) {
        final ItemEntity itemEntity = itemEntityMapper.toEntity(item);

        itemEntity.setUpdatedBy(identityProvider.getCurrentUserIdentity().getUsername());

        //the jpa save will generate id on save call...
        final ItemEntity savedEntity = jpaItemsRepository.save(itemEntity);

        item.setId(savedEntity.getId());

        return itemEntityMapper.toDomain(savedEntity);
    }

    @Override
    public List<Item> listAll(Item filterExample) {
        final ItemEntity itemEntity = itemEntityMapper.toEntity(filterExample);

        final List<ItemEntity> itemEntities = jpaItemsRepository.findAll(Example.of(itemEntity));

        return itemEntities.stream().map(itemEntityMapper::toDomain).collect(Collectors.toList());
    }

    public Page<Item> listAll(ItemsSpecifications specs, Pageable pageable) {
        final Page<ItemEntity> itemEntitiesPage = jpaItemsRepository.findAll(specs, pageable);

        return itemEntitiesPage.map(itemEntityMapper::toDomain);
    }

    @Override
    public Item findById(Long id) {
        return jpaItemsRepository.findById(id).map(itemEntityMapper::toDomain).orElse(null);
    }

    @Override
    public Item deleteById(Long id) {
        Item item = findById(id);
        jpaItemsRepository.deleteById(id);
        return item;
    }

    @Override
    public Optional<Item> findByIdAndInStock(Long id) {
        return jpaItemsRepository.findByIdAndIsInStockTrue(id).map(itemEntityMapper::toDomain);
    }

    @Override
    public List<Item> getAllItemsByCategory(ItemCategory itemCategory) {
        return jpaItemsRepository.customNativeQuery().stream().map(itemEntityMapper::toDomain).collect(Collectors.toList());
    }
}
