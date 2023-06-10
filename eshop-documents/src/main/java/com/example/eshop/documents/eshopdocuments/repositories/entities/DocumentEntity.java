package com.example.eshop.documents.eshopdocuments.repositories.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "documents")
public class DocumentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String resourceReference;
    private String documentReference;
    private String resourceType;
    @Lob
    private byte[] content;
    @Lob
    private String metaData;
    private String contentType;

}
