package com.example.eshop.service.repositories.jpa.mappers;

import com.example.eshop.service.repositories.jpa.entities.ItemEntity;
import com.example.modals.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemEntityMapper {

    ItemEntity toEntity(Item item);

    Item toDomain(ItemEntity item);

}
