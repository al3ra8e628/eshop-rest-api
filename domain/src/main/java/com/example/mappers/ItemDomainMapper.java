package com.example.mappers;

import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.UpdateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.contract.responses.UpdateItemResponse;
import com.example.modals.Item;
import org.mapstruct.Mapper;

@Mapper
public interface ItemDomainMapper {

    Item toDomain(CreateItemRequest createItemRequest);
    Item toDomain(UpdateItemRequest createItemRequest);

    CreateItemResponse toCreateResponse(Item item);

    UpdateItemResponse toUpdateResponse(Item item);

}
