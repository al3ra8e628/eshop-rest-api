package com.example.eshop.service.repositories.jpa;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface JpaItemsRepository extends JpaRepository<ItemEntity, Long>,
        PagingAndSortingRepository<ItemEntity, Long>,
        JpaSpecificationExecutor<ItemEntity> {
}
