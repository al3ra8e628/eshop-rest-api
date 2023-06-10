package com.example.eshop.service.repositories.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class ItemsPictureEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String reference;
    private String contentType;
    private String fileName;

}
