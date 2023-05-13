package com.example.eshop.service.controllers;


import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.UpdateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.eshop.service.controllers.resources.CreateItemRequestResource;
import com.example.eshop.service.controllers.resources.ItemResponseResource;
import com.example.eshop.service.resourcemappers.ItemResourceMapper;
import com.example.modals.Item;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import lombok.SneakyThrows;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {
    private final ItemsRepository itemsRepository;
    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final ItemResourceMapper itemResourceMapper;
    private final ObjectMapper objectMapper;

    public ItemsController(ItemsRepository itemsRepository,
                           CreateItemUseCase createItemUseCase,
                           UpdateItemUseCase updateItemUseCase,
                           ItemResourceMapper itemResourceMapper,
                           ObjectMapper objectMapper) {
        this.itemsRepository = itemsRepository;
        this.createItemUseCase = createItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
        this.itemResourceMapper = itemResourceMapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "create a new eshop item.",
            description = "create a new eshop item desc.")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponseResource createItem(@RequestBody CreateItemRequestResource itemRequest) {
        return createItem(
                itemResourceMapper.toCreateItemRequest(itemRequest)
        );
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ItemResponseResource createItemWithPictures(@RequestPart("requestBody") String itemRequest,
                                                       @RequestPart("pictures") List<MultipartFile> pictures) {
        CreateItemRequest createItemRequest = itemResourceMapper.toCreateItemRequest(
                objectMapper.readValue(itemRequest, CreateItemRequestResource.class));

        createItemRequest.setPictures(pictures.stream()
                .map(itemResourceMapper::toDocumentCreateRequest)
                .collect(Collectors.toList())
        );

        return createItem(createItemRequest);
    }

    @GetMapping()
    public List<ItemResponseResource> listAllItems(Item filterExample) {
        return itemsRepository.listAll(filterExample)
                .stream()
                .map(itemResourceMapper::toResource)
                .map(this::addResourceLinks)
                .toList();
    }

    @GetMapping("/{id}")
    public ItemResponseResource getItemById(@PathVariable Long id) {
        return this.addResourceLinks(
                this.itemResourceMapper.toResource(
                        itemsRepository.findById(id)
                )
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

        return this.addResourceLinks(itemResourceMapper.toResource(
                updateItemUseCase.execute(updateItemRequest)
        ));
    }

    private ItemResponseResource addResourceLinks(ItemResponseResource resource) {
        final Link selfLink = Link.of("/api/v1/items/" + resource.getId())
                .withType("GET")
                .withSelfRel();

        final Link listPictiresLink = Link.of(selfLink.getHref() + "/pictures")
                .withType("GET")
                .withRel("pictures");

        resource.add(selfLink);
        resource.add(listPictiresLink);

        return resource;
    }

    private ItemResponseResource createItem(CreateItemRequest createItemRequest) {
        final CreateItemResponse createItemResponse = createItemUseCase.execute(createItemRequest);

        final ItemResponseResource itemResponseResource = itemResourceMapper.toResource(createItemResponse);

        return addResourceLinks(itemResponseResource);
    }

}
