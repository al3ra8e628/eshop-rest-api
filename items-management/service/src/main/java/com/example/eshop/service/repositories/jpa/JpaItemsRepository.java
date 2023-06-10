package com.example.eshop.service.repositories.jpa;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import com.example.modals.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaItemsRepository extends JpaRepository<ItemEntity, Long>,
        PagingAndSortingRepository<ItemEntity, Long>,
        JpaSpecificationExecutor<ItemEntity> {

    Optional<ItemEntity> findByIdAndIsInStockTrue(Long id);

    @Query("select itemObject from items itemObject " +
            "where (itemObject.category = :category and " +
            "itemObject.isInStock = true) and itemObject.description != null")
    List<ItemEntity> customNamedQuery(@Param("category") ItemCategory category);

    @Query(value = "SELECT * FROM ITEMS", nativeQuery = true)
    List<ItemEntity> customNativeQuery();

    List<ItemEntity> findAllByCategory(ItemCategory category);

}
