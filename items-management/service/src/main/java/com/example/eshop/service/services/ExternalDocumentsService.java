package com.example.eshop.service.services;

import com.example.contract.services.DocumentsService;
import com.example.modals.Document;
import com.example.modals.Item;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;

@Service
@ConditionalOnProperty(value = "eshop.documents-service-mode",
        havingValue = "EXTERNAL")
public class ExternalDocumentsService implements DocumentsService {
    private final RestTemplate restTemplate;
    private final String documentsServiceUrl;

    public ExternalDocumentsService(RestTemplate restTemplate,
                                    @Value("${eshop.documents.service.url}")
                                    String documentsServiceUrl) {
        this.restTemplate = restTemplate;
        this.documentsServiceUrl = documentsServiceUrl;
    }


    public Document uploadDocument(Item item, Document document) {
        final String uploadUrl = documentsServiceUrl + "/api/v1/documents";

        //prepare headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        //prepare request body
        final MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();

        multipartBodyBuilder.part("document", new ByteArrayResource(document.getContent()),
                MediaType.parseMediaType(document.getContentType()));

        multipartBodyBuilder.part("requestBody", prepareRequestBody(item, document));

        final HttpEntity<MultiValueMap<String, HttpEntity<?>>> httpEntity = new HttpEntity<>(
                multipartBodyBuilder.build(),
                headers);

        //do the post call
        final ResponseEntity<UploadDocumentResponse> responseEntity =
                restTemplate.postForEntity(uploadUrl, httpEntity, UploadDocumentResponse.class);


        //handle failures
        handleFailure(responseEntity, HttpStatus.CREATED);


        //return proper response
        final UploadDocumentResponse responseBody = responseEntity.getBody();
        document.setReference(responseBody.getId() + "");


        return document;
    }


    public byte[] getDocument(String documentReference,
                              String resourceType,
                              String resourceReference) {
        //prepare request URL
        final String getDocumentUrl = documentsServiceUrl + "/api/v1/documents/%s/%s/%s"
                .formatted(resourceType, resourceReference, documentReference);


        //prepare headers
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        //do get call
        ResponseEntity<byte[]> responseEntity = restTemplate
                .getForEntity(getDocumentUrl, byte[].class);

        //handle failure
        handleFailure(responseEntity, HttpStatus.OK);

        //return response
        return responseEntity.getBody();
    }


    private static void handleFailure(ResponseEntity<?> responseEntity,
                                      HttpStatus successCode) {
        if (responseEntity.getStatusCode().value() != successCode.value()) {
            throw new RuntimeException("unable to complete the request..");
        }
    }

    private static HashMap<String, String> prepareRequestBody(Item item, Document document) {
        final HashMap<String, String> requestBody = new HashMap<>();
        requestBody.put("resourceReference", item.getId() + "");
        requestBody.put("documentReference", document.getReference() + "");
        requestBody.put("resourceType", "ESHOP_ITEM_PICTURE");
        requestBody.put("contentType", document.getContentType());
        requestBody.put("metaData", "CREATED AT " + LocalDate.now());
        return requestBody;
    }

    @Data
    static class UploadDocumentResponse {
        private Long id;
    }

}
