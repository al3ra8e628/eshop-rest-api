package com.example.contract.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class DocumentCreateRequest {
    private String fileName;
    private String contentType;
    private byte[] content;

}
