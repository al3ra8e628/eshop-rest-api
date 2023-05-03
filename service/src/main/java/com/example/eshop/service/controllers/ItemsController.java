package com.example.eshop.service.controllers;


import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.UpdateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.contract.responses.UpdateItemResponse;
import com.example.modals.Item;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {
    private final ItemsRepository itemsRepository;
    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;

    public ItemsController(ItemsRepository itemsRepository,
                           CreateItemUseCase createItemUseCase,
                           UpdateItemUseCase updateItemUseCase) {
        this.itemsRepository = itemsRepository;
        this.createItemUseCase = createItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CreateItemResponse createItem(@RequestBody CreateItemRequest itemRequest) {
        return createItemUseCase.execute(itemRequest);
    }

    @GetMapping()
    public List<Item> listAllItems(Item filterExample) {
        return itemsRepository.listAll(filterExample);
    }

    @GetMapping("/{id}")
    public Item getItemById(@PathVariable Long id) {
        return itemsRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public Item deleteById(@PathVariable Long id) {
        return itemsRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public UpdateItemResponse updateItemById(@PathVariable Long id,
                                             @RequestBody UpdateItemRequest updateItemRequest) {
        updateItemRequest.setId(id);

        return updateItemUseCase.execute(updateItemRequest);
    }

}
