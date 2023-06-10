package com.example.contract.responses;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class DocumentCreateResponse {
    private String reference;
    private String metadata;
    private String contentType;
    private byte[] content;

}
