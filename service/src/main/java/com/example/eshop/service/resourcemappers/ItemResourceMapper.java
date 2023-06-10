package com.example.eshop.service.resourcemappers;

import com.example.contract.requests.CreateItemRequest;
import com.example.contract.requests.DocumentCreateRequest;
import com.example.contract.responses.CreateItemResponse;
import com.example.contract.responses.UpdateItemResponse;
import com.example.eshop.service.controllers.resources.CreateItemRequestResource;
import com.example.eshop.service.controllers.resources.DocumentResponseResource;
import com.example.eshop.service.controllers.resources.ItemResponseResource;
import com.example.modals.Document;
import com.example.modals.Item;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public interface ItemResourceMapper {

    ItemResponseResource toResource(CreateItemResponse createItemResponse);

    ItemResponseResource toResource(Item item);

    ItemResponseResource toResource(UpdateItemResponse item);

    CreateItemRequest toCreateItemRequest(CreateItemRequestResource resource);

    DocumentResponseResource toDocumentResponseResource(Document document);

    default DocumentCreateRequest toDocumentCreateRequest(MultipartFile document) {
        final DocumentCreateRequest documentCreateRequest = new DocumentCreateRequest();

        try {
            documentCreateRequest.setContent(document.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        documentCreateRequest.setFileName(document.getOriginalFilename());
        documentCreateRequest.setContentType(document.getContentType());
        return documentCreateRequest;
    }

}

