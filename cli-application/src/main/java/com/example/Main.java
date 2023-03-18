package com.example;

import com.example.contract.repositories.ItemsRepository;
import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.UpdateItemRequest;
import com.example.exceptions.DomainValidationException;
import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemUseCase;
import org.joda.money.Money;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        final ApplicationContext context = new AnnotationConfigApplicationContext(
                "com.example");

        final CreateItemUseCase createItemUseCase = context.getBean(CreateItemUseCase.class);
        final UpdateItemUseCase updateItemUseCase = context.getBean(UpdateItemUseCase.class);

        final ItemsRepository itemsRepository = context.getBean(ItemsRepository.class);

        //restAPI request body...
        CreateItemRequest firstItem = new CreateItemRequest();
        firstItem.setName("blue t-shirt");
        firstItem.setCategory(ItemCategory.CLOTHS);
        firstItem.setRating(3);
        firstItem.setPrice(Money.parse("USD 10.00"));
        firstItem.setIsInStock(true);
        firstItem.setUnit(ItemUnit.SIZE);

        //restAPI request body...
        CreateItemRequest secondItem = new CreateItemRequest();
        secondItem.setName("laptop dell");
        secondItem.setCategory(ItemCategory.IT);
        secondItem.setRating(5);
        secondItem.setPrice(Money.parse("USD 100.00"));
        secondItem.setIsInStock(true);
        secondItem.setUnit(ItemUnit.PIECE);


        //api exception handler...
        try {
            System.out.println("list existed items");
            System.out.println(itemsRepository.listAll());

            System.out.println("\ncreate two items");
            createItemUseCase.execute(firstItem);
            createItemUseCase.execute(secondItem);


            System.out.println("\nlist all items after creation");
            itemsRepository.listAll().forEach(System.out::println);


            System.out.println("\nupdate second item name and price details!!");
            UpdateItemRequest updateItemRequest = new UpdateItemRequest();
            updateItemRequest.setId(2L);
            updateItemRequest.setName("updated laptop dell");
            updateItemRequest.setCategory(ItemCategory.IT);
            updateItemRequest.setRating(5);
            updateItemRequest.setPrice(Money.parse("USD 150.00"));
            updateItemRequest.setIsInStock(true);
            updateItemRequest.setUnit(ItemUnit.PIECE);
            updateItemUseCase.execute(updateItemRequest);

            System.out.println("\nlist created items after update");
            itemsRepository.listAll().forEach(System.out::println);
        } catch (DomainValidationException e) {
            e.getValidationErrors().forEach(System.out::println);
        }

    }
}