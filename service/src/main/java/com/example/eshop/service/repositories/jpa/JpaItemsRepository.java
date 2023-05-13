package com.example.eshop.service.repositories.jpa;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemsRepository extends JpaRepository<ItemEntity, Long> {


}
