package com.example.eshop.documents.eshopdocuments.controllers.resources;

import lombok.Data;

@Data
public class CreateDocumentRequestResource {

    private String resourceReference;
    private String contentType;
    private String resourceType;
    private String metaData;


}



