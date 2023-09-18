package com.example.eshop.service.inbox;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "inbox")
@Entity(name = "inbox")
public class InboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String messageType;
    @Lob
    private String messagePayload;
    private String status;
    private Integer retries;
    private String failureReason;


    public static InboxEntity of(String messageType, String payload) {
        InboxEntity inboxEntity = new InboxEntity();

        inboxEntity.setStatus("PENDING");
        inboxEntity.setRetries(0);
        inboxEntity.setMessagePayload(payload);
        inboxEntity.setMessageType(messageType);

        return inboxEntity;
    }

}
