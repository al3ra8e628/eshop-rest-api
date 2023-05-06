package com.example.eshop.service.controllers.resources;

import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.money.Money;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public class ItemResponseResource extends RepresentationModel<ItemResponseResource> {

    private Long id;
    private String name;
    private String identifier;
    private ItemCategory responseCategory;
    private Boolean isInStock;
    private Integer rating;
    private ItemUnit unit;
    private Money price;
    private LocalDateTime creationDateTime;


}
