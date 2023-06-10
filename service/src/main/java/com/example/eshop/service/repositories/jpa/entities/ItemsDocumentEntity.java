package com.example.eshop.service.repositories.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "item_documents")
public class ItemsDocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String reference;
    private String fileName;

    @ManyToOne(targetEntity = ItemEntity.class)
    private ItemEntity itemEntity;

}
