package com.example.eshop.service.controllers.resources;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class DocumentResponseResource extends RepresentationModel<ItemResponseResource> {
    private String reference;
    private String fileName;

}
