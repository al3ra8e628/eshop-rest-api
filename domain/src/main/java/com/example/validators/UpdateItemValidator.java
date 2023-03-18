package com.example.validators;

import com.example.contract.requests.UpdateItemRequest;
import com.example.modals.ItemCategory;

public class UpdateItemValidator extends ItemRequestValidator<UpdateItemRequest> {

    @Override
    protected int getRating(UpdateItemRequest itemRequest) {
        return itemRequest.getRating();
    }

    @Override
    protected ItemCategory getCategory(UpdateItemRequest itemRequest) {
        return itemRequest.getCategory();
    }

}
