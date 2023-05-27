package com.example.eshop.service.repositories.jpa.entities;

import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table(name = "items")
@Entity(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 30)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ItemCategory category;

    @Column(name = "is_in_stock", length = 4)
    private Boolean isInStock;
    @Column(name = "rating", length = 4)
    private Integer rating;
    @Enumerated(EnumType.STRING)
    @Column(name = "unit")
    private ItemUnit unit;

    @Column(name = "description")
    private String description;
    @Column(name = "price_amount")
    private BigDecimal priceAmount;
    @Column(name = "price_currency", length = 7)
    private String priceCurrency;

    @Column(name = "creation_date")
    private LocalDateTime creationDateTime;

}
