package com.example.contract.services;

import com.example.modals.Document;
import com.example.modals.Item;

public interface DocumentsService {

    Document uploadDocument(Item item, Document document);

    byte[] getDocument(String documentReference,
                       String resourceType,
                       String resourceReference);


}
