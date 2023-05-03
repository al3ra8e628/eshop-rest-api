package com.example.eshop.service.configs;

import com.example.contract.repositories.ItemsRepository;
import com.example.mappers.ItemDomainMapper;
import com.example.mappers.ItemDomainMapperImpl;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemUseCase;
import com.example.validators.CreateItemValidator;
import com.example.validators.UpdateItemValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppBeansConfig {
    @Bean
    CreateItemUseCase createItemUseCase(CreateItemValidator createItemValidator,
                                        ItemDomainMapper itemDomainMapper,
                                        ItemsRepository itemsRepository) {
        return new CreateItemUseCase(createItemValidator, itemDomainMapper, itemsRepository);
    }

    @Bean
    UpdateItemUseCase updateItemUseCase(UpdateItemValidator createItemValidator,
                                        ItemDomainMapper itemDomainMapper,
                                        ItemsRepository itemsRepository) {
        return new UpdateItemUseCase(createItemValidator, itemsRepository, itemDomainMapper);
    }


    @Bean
    ItemDomainMapper itemDomainMapper() {
        return new ItemDomainMapperImpl();
    }

    @Bean
    CreateItemValidator createItemValidator() {
        return new CreateItemValidator();
    }

    @Bean
    UpdateItemValidator updateItemValidator() {
        return new UpdateItemValidator();
    }


}
