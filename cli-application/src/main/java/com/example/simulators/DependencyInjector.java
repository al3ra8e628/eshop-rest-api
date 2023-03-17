package com.example.simulators;

import com.example.contract.repositories.ItemsRepository;
import com.example.mappers.ItemDomainMapper;
import com.example.mappers.ItemDomainMapperImpl;
import com.example.repositories.InMemoryItemsRepository;
import com.example.usecases.CreateItemUseCase;
import com.example.validators.CreateItemRequestValidator;

import java.util.HashMap;
import java.util.Map;

public class DependencyInjector {
    final static Map<Class<?>, Object> beans = new HashMap<>();

    static {
        String reporsitoryMode = System.getProperty("repository-mode");

        ItemsRepository itemsRepository;

        if (reporsitoryMode.equals("inMemory")) {
            itemsRepository = new InMemoryItemsRepository();
            beans.put(ItemsRepository.class, itemsRepository);
        }

        beans.put(CreateItemRequestValidator.class, new CreateItemRequestValidator());

        beans.put(ItemDomainMapper.class, new ItemDomainMapperImpl());

        beans.put(CreateItemUseCase.class, new CreateItemUseCase(
                (CreateItemRequestValidator) beans.get(CreateItemRequestValidator.class),
                (ItemDomainMapper) beans.get(ItemDomainMapper.class),
                (ItemsRepository) beans.get(ItemsRepository.class)));

        //---------------------------------------->
        ///repository1 , validator , mapper , usercase
    }

    public static <T> T getBean(Class<T> clazz) {
        return (T) beans.get(clazz);
    }

}
