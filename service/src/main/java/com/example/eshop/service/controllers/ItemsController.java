package com.example.eshop.service.controllers;


import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.UpdateItemRequest;
import com.example.eshop.service.controllers.resources.ItemResponseResource;
import com.example.eshop.service.resourcemappers.ItemResourceMapper;
import com.example.modals.Item;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemUseCase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {
    private final ItemsRepository itemsRepository;
    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final ItemResourceMapper itemResourceMapper;

    public ItemsController(ItemsRepository itemsRepository,
                           CreateItemUseCase createItemUseCase,
                           UpdateItemUseCase updateItemUseCase,
                           ItemResourceMapper itemResourceMapper) {
        this.itemsRepository = itemsRepository;
        this.createItemUseCase = createItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
        this.itemResourceMapper = itemResourceMapper;
    }


    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new eshop item.")
    public ItemResponseResource createItem(@RequestBody CreateItemRequest itemRequest) {
        return addResourceLinks(itemResourceMapper.toResource(
                createItemUseCase.execute(itemRequest)
        ));
    }

    @GetMapping()
    public List<ItemResponseResource> listAllItems(Item filterExample) {
        return itemsRepository.listAll(filterExample)
                .stream()
                .map(itemResourceMapper::toResource)
                .toList();
    }

    @GetMapping("/{id}")
    public ItemResponseResource getItemById(@PathVariable Long id) {
        return this.itemResourceMapper.toResource(
                itemsRepository.findById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ItemResponseResource deleteById(@PathVariable Long id) {
        return this.itemResourceMapper.toResource(
                itemsRepository.deleteById(id)
        );
    }

    @PutMapping("/{id}")
    public ItemResponseResource updateItemById(@PathVariable Long id,
                                               @RequestBody UpdateItemRequest updateItemRequest) {
        updateItemRequest.setId(id);

        return itemResourceMapper.toResource(
                updateItemUseCase.execute(updateItemRequest)
        );
    }

    private ItemResponseResource addResourceLinks(ItemResponseResource resource) {
        final Link selfLink = Link.of("/api/v1/items/" + resource.getId())
                .withType("GET")
                .withSelfRel();

        resource.add(selfLink);

        return resource;
    }

}
