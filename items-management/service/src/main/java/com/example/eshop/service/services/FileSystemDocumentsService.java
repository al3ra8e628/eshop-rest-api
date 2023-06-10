package com.example.eshop.service.services;

import com.example.contract.services.DocumentsService;
import com.example.modals.Document;
import com.example.modals.Item;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@ConditionalOnProperty(value = "eshop.documents-service-mode",
        havingValue = "FILE_SYSTEM")
public class FileSystemDocumentsService implements DocumentsService {

    public Document uploadDocument(Item item, Document document) {
        try (FileOutputStream fos = new FileOutputStream(generateUniqueName(
                item.getId() + "", document.getReference()))) {
            fos.write(document.getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return document;
    }

    public byte[] getDocument(String documentReference,
                              String resourceType,
                              String resourceReference) {
        try {
            return Files.readAllBytes(Paths.get(
                    generateUniqueName(resourceReference, documentReference)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateUniqueName(String itemId, String documentReference) {
        return "ESHOP_ITEM_PICTURE_%s_%s".formatted(itemId, documentReference);
    }


}
