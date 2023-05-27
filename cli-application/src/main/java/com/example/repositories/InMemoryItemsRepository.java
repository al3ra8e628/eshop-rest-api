package com.example.repositories;

import com.example.contract.repositories.ItemsRepository;
import com.example.exceptions.ResourceNotFoundException;
import com.example.modals.Item;
import com.example.modals.ItemCategory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryItemsRepository implements ItemsRepository {

    private Map<Long, Item> itemsTable = new HashMap<>();

    @Override
    public Item save(Item item) {
        if (Objects.isNull(item.getId())) {
            item.setId(itemsTable.keySet().stream().max(Long::compareTo).orElse(0L) + 1);
        }

        return itemsTable.put(item.getId(), item);
    }

    @Override
    public List<Item> listAll(Item filterExample) {
        return new ArrayList<>(itemsTable.values());
    }

    @Override
    public Item findById(Long id) {
        final Item item = itemsTable.get(id);

        if (Objects.isNull(item)) {
            throw new ResourceNotFoundException();
        }

        return item;
    }

    @Override
    public Item deleteById(Long id) {
        Item removed = itemsTable.remove(id);

        if (Objects.isNull(removed)) {
            throw new ResourceNotFoundException();
        }

        return removed;
    }

    @Override
    public Optional<Item> findByIdAndInStock(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Item> getAllItemsByCategory(ItemCategory itemCategory) {
        return null;
    }

}
