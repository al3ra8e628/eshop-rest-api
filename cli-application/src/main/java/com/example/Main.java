package com.example;

import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.CreateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.exceptions.DomainValidationException;
import com.example.mappers.ItemDomainMapperImpl;
import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import com.example.repositories.InMemoryItemsRepository;
import com.example.simulators.DependencyInjector;
import com.example.usecases.CreateItemUseCase;
import com.example.validators.CreateItemRequestValidator;

public class Main {
    public static void main(String[] args) {
        System.setProperty("repository-mode", "inMemory");

        CreateItemUseCase createItemUseCase = DependencyInjector.getBean(CreateItemUseCase.class);
        InMemoryItemsRepository bean = (InMemoryItemsRepository) DependencyInjector.getBean(ItemsRepository.class);

        //restAPI request body...
        CreateItemRequest createItemRequest = new CreateItemRequest();
        createItemRequest.setName("laptop dell");
        //createItemRequest.setCategory(ItemCategory.IT);
        createItemRequest.setRating(5);
        createItemRequest.setIsInStock(true);
        createItemRequest.setUnit(ItemUnit.PIECE);

        //api exception handler...
        try {
            createItemUseCase.execute(createItemRequest);

            System.out.println(bean.listAll());
        } catch (DomainValidationException e) {
            System.out.println(e.getValidationErrors());
        }
    }
}