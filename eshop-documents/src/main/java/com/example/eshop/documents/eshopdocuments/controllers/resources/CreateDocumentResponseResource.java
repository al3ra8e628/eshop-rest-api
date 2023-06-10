package com.example.eshop.documents.eshopdocuments.controllers.resources;

import lombok.Data;

@Data
public class CreateDocumentResponseResource {
    private Long id;
    private String resourceReference;
    private String resourceType;
    private String contentType;
    private String metaData;

}
