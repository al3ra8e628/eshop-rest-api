package com.example.eshop.service.controllers.resources;

import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.joda.money.Money;

@Data
@Schema(name = "CreateItemRequestResource", description = "eshop item create request resource.")
public class CreateItemRequestResource {

    private String name;
    private ItemCategory category;
    private Boolean isInStock;
    private Integer rating;
    private ItemUnit unit;
    private String description;

    @Schema(type = "string",
            example = "USD 100.00",
            description = "the price of the item",
            maxLength = 10,
            minLength = 7
    )
    private Money price;

}
