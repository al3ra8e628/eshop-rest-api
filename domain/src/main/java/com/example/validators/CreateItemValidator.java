package com.example.validators;

import com.example.contract.requests.CreateItemRequest;
import com.example.modals.ItemCategory;

public class CreateItemValidator extends ItemRequestValidator<CreateItemRequest> {
    @Override
    protected int getRating(CreateItemRequest itemRequest) {
        return itemRequest.getRating();
    }

    @Override
    protected ItemCategory getCategory(CreateItemRequest itemRequest) {
        return itemRequest.getCategory();
    }
}
