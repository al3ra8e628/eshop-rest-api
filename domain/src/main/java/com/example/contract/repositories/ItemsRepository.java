package com.example.contract.repositories;

import com.example.modals.Item;

import java.util.List;

public interface ItemsRepository {
    Item save(Item item);

    List<Item> listAll();

    Item findById(Long id);
}
