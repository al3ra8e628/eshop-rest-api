package com.example.eshop.service.configs;

import com.example.contract.repositories.ItemsRepository;
import com.example.eshop.service.configs.serializationconfig.MoneyDeserializer;
import com.example.eshop.service.configs.serializationconfig.MoneySerializer;
import com.example.mappers.ItemDomainMapper;
import com.example.mappers.ItemDomainMapperImpl;
import com.example.usecases.CreateItemUseCase;
import com.example.usecases.UpdateItemInStockStatusUseCase;
import com.example.usecases.UpdateItemUseCase;
import com.example.validators.CreateItemValidator;
import com.example.validators.UpdateItemValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.joda.money.Money;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppBeansConfig {
    @Bean
    CreateItemUseCase createItemUseCase(CreateItemValidator createItemValidator, ItemDomainMapper itemDomainMapper, ItemsRepository itemsRepository) {
        return new CreateItemUseCase(createItemValidator, itemDomainMapper, itemsRepository);
    }

    @Bean
    UpdateItemUseCase updateItemUseCase(UpdateItemValidator createItemValidator, ItemDomainMapper itemDomainMapper, ItemsRepository itemsRepository) {
        return new UpdateItemUseCase(createItemValidator, itemsRepository, itemDomainMapper);
    }

    @Bean
    UpdateItemInStockStatusUseCase updateItemInStockStatusUseCase(ItemsRepository itemsRepository) {
        return new UpdateItemInStockStatusUseCase(itemsRepository);
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

    @Bean
    ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();

        final SimpleModule moneySerializationModule = new SimpleModule("moneySerializationModule");

        moneySerializationModule.addSerializer(Money.class, new MoneySerializer());
        moneySerializationModule.addDeserializer(Money.class, new MoneyDeserializer());

        objectMapper.registerModule(moneySerializationModule);
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        return objectMapper;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
