package com.example.eshop.service.controllers.resources;

import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.joda.money.Money;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(name = "ItemResponseResource", description = "eshop item response details.")
public class ItemResponseResource extends RepresentationModel<ItemResponseResource> {

    private Long id;
    private String name;
    private ItemCategory category;
    private Boolean isInStock;
    private Integer rating;
    private ItemUnit unit;

    @Schema(type = "string",
            example = "USD 100.00",
            description = "the price of the item",
            maxLength = 10,
            minLength = 7
    )
    private Money price;
    private LocalDateTime creationDateTime;


}
