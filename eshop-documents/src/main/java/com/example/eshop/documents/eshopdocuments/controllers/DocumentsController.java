package com.example.eshop.documents.eshopdocuments.controllers;

import com.example.eshop.documents.eshopdocuments.controllers.resources.CreateDocumentRequestResource;
import com.example.eshop.documents.eshopdocuments.controllers.resources.CreateDocumentResponseResource;
import com.example.eshop.documents.eshopdocuments.exceptions.ResourceNotFoundException;
import com.example.eshop.documents.eshopdocuments.repositories.entities.DocumentEntity;
import com.example.eshop.documents.eshopdocuments.repositories.jpa.JPADocumentsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/documents")
public class DocumentsController {
    private final JPADocumentsRepository documentsRepository;
    private final ObjectMapper objectMapper;

    public DocumentsController(JPADocumentsRepository documentsRepository,
                               ObjectMapper objectMapper) {
        this.documentsRepository = documentsRepository;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CreateDocumentResponseResource createDocument(
            @RequestPart("requestBody") String requestBody,
            @RequestPart("document") byte[] document) {
        final CreateDocumentRequestResource requestResource =
                objectMapper.readValue(requestBody,
                        CreateDocumentRequestResource.class);

        final DocumentEntity requestEntity = toDocumentEntity(requestResource, document);

        final DocumentEntity savedEntity = documentsRepository.save(requestEntity);

        return toResponseResource(savedEntity);
    }

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE,
            value = "/{resourceType}/{resourceReference}/{documentReference}")
    public byte[] getDocument(@PathVariable String resourceType,
                              @PathVariable String resourceReference,
                              @PathVariable String documentReference) {
        final Optional<DocumentEntity> document = documentsRepository
                .findFirstByResourceTypeAndResourceReferenceAndDocumentReference(
                        resourceType,
                        resourceReference,
                        documentReference);

        if (document.isEmpty()) {
            throw new ResourceNotFoundException();
        }

        return document.get().getContent();
    }


    private DocumentEntity toDocumentEntity(CreateDocumentRequestResource requestResource, byte[] document) {
        final DocumentEntity documentEntity = new DocumentEntity();

        documentEntity.setContent(document);
        documentEntity.setContentType(requestResource.getContentType());

        documentEntity.setMetaData(requestResource.getMetaData());
        documentEntity.setResourceType(requestResource.getResourceType());
        documentEntity.setResourceReference(requestResource.getResourceReference());
        documentEntity.setDocumentReference(requestResource.getDocumentReference());

        return documentEntity;
    }

    private byte[] readBytes(MultipartFile document) {
        try {
            return document.getBytes();
        } catch (IOException e) {
            return new byte[]{};
        }
    }

    private CreateDocumentResponseResource toResponseResource(DocumentEntity entity) {
        final CreateDocumentResponseResource responseResource = new CreateDocumentResponseResource();

        responseResource.setResourceType(entity.getResourceType());
        responseResource.setId(entity.getId());
        responseResource.setMetaData(entity.getMetaData());
        responseResource.setResourceReference(entity.getResourceReference());
        responseResource.setDocumentReference(entity.getResourceReference());
        responseResource.setContentType(entity.getContentType());

        return responseResource;
    }

}
