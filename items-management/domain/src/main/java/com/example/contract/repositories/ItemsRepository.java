package com.example.contract.repositories;

import com.example.modals.Item;
import com.example.modals.ItemCategory;

import java.util.List;
import java.util.Optional;

public interface ItemsRepository {
    Item save(Item item);

    Item update(Item item);

    List<Item> listAll(Item filterExample);

    Item findById(Long id);

    Item deleteById(Long id);

    Optional<Item> findByIdAndInStock(Long id);

    List<Item> getAllItemsByCategory(ItemCategory itemCategory);

}
