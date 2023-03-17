package com.example.usecases;

import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.CreateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.mappers.ItemDomainMapper;
import com.example.modals.Item;
import com.example.validators.CreateItemRequestValidator;

import java.time.LocalDateTime;

/*
 * create item use case execution process
 *
 * accept create item request
 * apply technical validation on request.
 * apply business validation on request.
 * map request to domain modal.
 * add the system calculated values.
 * persist to repository.
 * map to item response.
 * return create item response
 *
 * */
public class CreateItemUseCase {
    private final CreateItemRequestValidator requestValidator;
    private final ItemDomainMapper itemDomainMapper;
    private final ItemsRepository itemsRepository;

    public CreateItemUseCase(CreateItemRequestValidator requestValidator,
                             ItemDomainMapper itemDomainMapper,
                             ItemsRepository itemsRepository) {
        this.requestValidator = requestValidator;
        this.itemDomainMapper = itemDomainMapper;
        this.itemsRepository = itemsRepository;
    }

    public CreateItemResponse execute(CreateItemRequest createItemRequest) {
        validateRequest(createItemRequest);

        final Item item = mapToDomain(createItemRequest);

        initializeSystemValues(item);

        return mapToResponse(
                persistToRepository(item)
        );
    }

    private Item persistToRepository(Item item) {
        return itemsRepository.save(item);
    }

    private CreateItemResponse mapToResponse(Item item) {
        return itemDomainMapper.toCreateResponse(item);
    }

    private Item mapToDomain(CreateItemRequest createItemRequest) {
        return itemDomainMapper.toDomain(createItemRequest);
    }

    private void validateRequest(CreateItemRequest createItemRequest) {
        requestValidator.validate(createItemRequest);
    }

    private void initializeSystemValues(Item item) {
        item.setCreationDateTime(LocalDateTime.now());
    }

}
