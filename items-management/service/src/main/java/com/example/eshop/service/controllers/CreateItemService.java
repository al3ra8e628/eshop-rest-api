package com.example.eshop.service.controllers;

import com.example.contract.requests.CreateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.eshop.service.controllers.resources.ItemResponseResource;
import com.example.eshop.service.resourcemappers.ItemResourceMapper;
import com.example.usecases.CreateItemUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class CreateItemService {
    private final CreateItemUseCase createItemUseCase;
    private final ItemResourceMapper itemResourceMapper;

    public CreateItemService(CreateItemUseCase createItemUseCase, ItemResourceMapper itemResourceMapper) {
        this.createItemUseCase = createItemUseCase;
        this.itemResourceMapper = itemResourceMapper;
    }

    @Transactional
    public ItemResponseResource createItem(CreateItemRequest createItemRequest) {
        final CreateItemResponse createItemResponse = createItemUseCase.execute(createItemRequest);
        final ItemResponseResource itemResponseResource = itemResourceMapper.toResource(createItemResponse);
        return itemResponseResource;
    }


}
