package com.example.modals;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Document {
    private String reference;
    private String metadata;
    private String contentType;
    private byte[] content;

}
