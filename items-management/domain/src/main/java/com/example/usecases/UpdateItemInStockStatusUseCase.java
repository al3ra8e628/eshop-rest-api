package com.example.usecases;

import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.UpdateItemInStockStatusRequest;
import com.example.exceptions.DomainValidationException;
import com.example.modals.Item;

import java.util.Objects;

public class UpdateItemInStockStatusUseCase {

    private final ItemsRepository itemsRepository;

    public UpdateItemInStockStatusUseCase(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }


    public void execute(UpdateItemInStockStatusRequest request) {
        final Item item = itemsRepository.findById(request.getId());

        validateNotNull(item);

        item.setIsInStock(request.getIsInStock());

        itemsRepository.update(item);
    }

    private void validateNotNull(Item item) {
        if (Objects.isNull(item)) {
            throw DomainValidationException.of("id", "item.with.provided.id.is.not.exist");
        }
    }

}
