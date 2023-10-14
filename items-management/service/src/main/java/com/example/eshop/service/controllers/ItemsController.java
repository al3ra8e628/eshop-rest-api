package com.example.eshop.service.controllers;


import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.UpdateItemRequest;
import com.example.eshop.contract.UserIdentityProvider;
import com.example.eshop.models.UserIdentity;
import com.example.eshop.service.controllers.resources.CreateItemRequestResource;
import com.example.eshop.service.controllers.resources.ItemResponseResource;
import com.example.eshop.service.exceptions.ResourceNotFoundException;
import com.example.eshop.service.repositories.jpa.specifications.ItemsSpecifications;
import com.example.eshop.service.repositories.lsitingrepositories.ItemsListingRepository;
import com.example.eshop.service.resourcemappers.ItemResourceMapper;
import com.example.modals.Item;
import com.example.modals.ItemCategory;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/items")
public class ItemsController {
    private final ItemsListingRepository itemsRepository;
    private final CreateItemUseCase createItemUseCase;
    private final UpdateItemUseCase updateItemUseCase;
    private final ItemResourceMapper itemResourceMapper;

    private final CreateItemService createItemService;
    private final PagedResourcesAssembler pagedResourcesAssembler;
    private final ObjectMapper objectMapper;
    private final UserIdentityProvider userIdentityProvider;

    public ItemsController(ItemsListingRepository itemsRepository,
                           CreateItemUseCase createItemUseCase,
                           UpdateItemUseCase updateItemUseCase,
                           ItemResourceMapper itemResourceMapper,
                           CreateItemService createItemService,
                           PagedResourcesAssembler pagedResourcesAssembler,
                           ObjectMapper objectMapper,
                           UserIdentityProvider userIdentityProvider) {
        this.itemsRepository = itemsRepository;
        this.createItemUseCase = createItemUseCase;
        this.updateItemUseCase = updateItemUseCase;
        this.itemResourceMapper = itemResourceMapper;
        this.createItemService = createItemService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.objectMapper = objectMapper;
        this.userIdentityProvider = userIdentityProvider;
    }

//    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "create a new eshop item.",
//            description = "create a new eshop item desc.")
//    @ResponseStatus(HttpStatus.CREATED)
//    @RolesAllowed("item-management-creator")
//    public ItemResponseResource createItem(@RequestBody CreateItemRequestResource itemRequest) {
//        return createItemService.createItem(
//                itemResourceMapper.toCreateItemRequest(itemRequest)
//        );
//    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    @RolesAllowed("item-management-creator")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ItemResponseResource createItemWithPictures(@RequestPart("requestBody") String itemRequest,
                                                       @RequestPart("pictures") List<MultipartFile> pictures) {
        CreateItemRequest createItemRequest = itemResourceMapper.toCreateItemRequest(
                objectMapper.readValue(itemRequest, CreateItemRequestResource.class));

        createItemRequest.setPictures(pictures.stream()
                .map(itemResourceMapper::toDocumentCreateRequest)
                .collect(Collectors.toList())
        );

        return createItemService.createItem(createItemRequest);
    }

    @GetMapping()
    @RolesAllowed("item-management-viewer")
    public PagedModel<ItemResponseResource> listAllItems(ItemsSpecifications specs,
                                                         Pageable pageable) {
        UserIdentity currentUser = this.userIdentityProvider.getCurrentUserIdentity();

        LOGGER.info("this call triggered by username:{} , fullName:{} , email:{}",
                currentUser.getUsername(),
                currentUser.getFullName(),
                currentUser.getEmail());

        final Page<ItemResponseResource> responsePage = itemsRepository
                .listAll(specs, pageable)
                .map(itemResourceMapper::toResource)
                .map(this::addResourceLinks);

        return pagedResourcesAssembler.toModel(responsePage);
    }

    @GetMapping("/{id}")
    public ItemResponseResource getItemById(@PathVariable Long id) {
        return this.addResourceLinks(
                this.itemResourceMapper.toResource(
                        itemsRepository.findById(id)
                )
        );
    }

    @GetMapping("/stock/{id}")
    public ItemResponseResource getInStockItemById(@PathVariable Long id) {
        return this.addResourceLinks(
                this.itemResourceMapper.toResource(
                        itemsRepository.findByIdAndInStock(id)
                                .orElseThrow(ResourceNotFoundException::new)
                )
        );
    }

    @GetMapping("/all-by-category/{category}")
    public List<Item> getAllByCategory(@PathVariable ItemCategory category) {
        return itemsRepository.getAllItemsByCategory(category);
    }


    @DeleteMapping("/{id}")
    public ItemResponseResource deleteById(@PathVariable Long id) {
        return this.itemResourceMapper.toResource(
                itemsRepository.deleteById(id)
        );
    }

    @PutMapping("/{id}")
    @RolesAllowed("item-management-modifier")
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


}
