package com.example.eshop.service.repositories.jpa.mappers;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import com.example.mappers.MoneyMapper;
import com.example.modals.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemEntityMapper extends MoneyMapper {

    @Mapping(target = "priceAmount", expression = "java(valueFromMoney(item.getPrice()))")
    @Mapping(target = "priceCurrency", expression = "java(currencyFromMoney(item.getPrice()))")
    ItemEntity toEntity(Item item);

    @Mapping(target = "price", expression =
            "java(toMoney(item.getPriceAmount(), item.getPriceCurrency()))")
    Item toDomain(ItemEntity item);


}
