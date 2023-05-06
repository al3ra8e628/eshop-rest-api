package com.example.eshop.service.resourcemappers;

import com.example.contract.responses.CreateItemResponse;
import com.example.contract.responses.UpdateItemResponse;
import com.example.eshop.service.controllers.resources.ItemResponseResource;
import com.example.modals.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemResourceMapper {

    ItemResponseResource toResource(CreateItemResponse createItemResponse);

    ItemResponseResource toResource(Item item);

    ItemResponseResource toResource(UpdateItemResponse item);

}

