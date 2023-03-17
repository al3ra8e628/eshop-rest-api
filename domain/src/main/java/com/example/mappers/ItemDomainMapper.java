package com.example.mappers;

import com.example.contract.requests.CreateItemRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.modals.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ItemDomainMapper {

    Item toDomain(CreateItemRequest createItemRequest);

//    @Mapping(target = "name", ignore = true)
//    @Mapping(target = "responseCategory", source = "category")
//    @Mapping(target = "identifier", expression = "java(buildItemIdentifier(item.getId() , item.getName()))")
//    @Mapping(target = "identifier", constant = "ABCD")
    CreateItemResponse toCreateResponse(Item item);

//    default String buildItemIdentifier(Long id, String name) {
//        return id.toString().concat("#").concat(name);
//    }

}
