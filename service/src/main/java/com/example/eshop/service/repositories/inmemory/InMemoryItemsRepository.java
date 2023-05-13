package com.example.eshop.service.repositories.inmemory;

import com.example.contract.repositories.ItemsRepository;
import com.example.eshop.service.exceptions.ResourceNotFoundException;
import com.example.modals.Item;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
@Profile("in-memory")
public class InMemoryItemsRepository implements ItemsRepository {

    private Map<Long, Item> itemsTable = new HashMap<>();

    @Override
    public Item save(Item item) {
        if (Objects.isNull(item.getId())) {
            item.setId(itemsTable.keySet().stream().max(Long::compareTo).orElse(0L) + 1);
        }

        itemsTable.put(item.getId(), item);

        return item;
    }

    @Override
    public List<Item> listAll(Item filterExample) {
        return new ArrayList<>(itemsTable.values().stream()
                .filter(it -> Objects.isNull(filterExample.getCategory()) ||
                        Objects.equals(filterExample.getCategory(), it.getCategory()))
                .toList());
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
}
