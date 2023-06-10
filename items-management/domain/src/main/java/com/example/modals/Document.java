package com.example.modals;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Document {
    private Long id; //localDb
    private String reference; //document service
    private String contentType; //document service
    private String fileName; //local db
    private byte[] content; //document service.

}
