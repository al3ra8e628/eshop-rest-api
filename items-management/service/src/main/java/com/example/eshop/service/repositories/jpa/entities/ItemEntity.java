package com.example.eshop.service.repositories.jpa.entities;

import com.example.modals.ItemCategory;
import com.example.modals.ItemUnit;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "items")
@Entity(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "creation_date")
    private LocalDateTime creationDateTime;

    /*
    * another way to map elements as one to many is to use @ElementCollection..
    * Element connection doesn't work with @Entity class instead it's requiring @Embeddable class with no ID.
    * */
    //
    @OneToMany(targetEntity = ItemsPictureEntity.class,
            cascade = CascadeType.ALL,
            orphanRemoval = false)
    private List<ItemsPictureEntity> pictures;

}


