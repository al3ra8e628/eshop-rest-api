package com.example.usecases;

import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.UpdateItemRequest;
import com.example.contract.responses.UpdateItemResponse;
import com.example.mappers.ItemDomainMapper;
import com.example.modals.Item;
import com.example.validators.ItemRequestValidator;

public class UpdateItemUseCase {

    private final ItemRequestValidator<UpdateItemRequest> itemRequestValidator;
    private final ItemsRepository itemsRepository;
    private final ItemDomainMapper itemDomainMapper;

    public UpdateItemUseCase(ItemRequestValidator<UpdateItemRequest> itemRequestValidator, ItemsRepository itemsRepository, ItemDomainMapper itemDomainMapper) {
        this.itemRequestValidator = itemRequestValidator;
        this.itemsRepository = itemsRepository;
        this.itemDomainMapper = itemDomainMapper;
    }

    public UpdateItemResponse execute(UpdateItemRequest updateItemRequest) {
        final Item original = itemsRepository.findById(updateItemRequest.getId()); //ItemNotFoundException::: 404

        itemRequestValidator.validate(updateItemRequest);

        final Item updatedItem = applyUpdates(original, updateItemRequest);

        itemsRepository.save(updatedItem);

        return itemDomainMapper.toUpdateResponse(updatedItem);
    }

    private Item applyUpdates(Item original, UpdateItemRequest updateItemRequest) {
        final Item updates = itemDomainMapper.toDomain(updateItemRequest);

        updates.setId(original.getId());
        updates.setCreationDateTime(original.getCreationDateTime());

        return updates;
    }


}

