package com.example.modals;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.List;


@Data
@ToString
@EqualsAndHashCode(exclude = "pictures")
public class Item {
    private Long id;
    private String name;
    private ItemCategory category;
    private Boolean isInStock;
    private Integer rating;
    private ItemUnit unit;
    private Money price;
    private LocalDateTime creationDateTime;
    private List<Document> pictures;

}
