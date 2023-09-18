package com.example.stockmanagement;

import com.example.stockmanagement.EventsPublisher.Publishable;
import lombok.Data;

@Data
public class UpdateItemStockEvent implements Publishable {
    private String itemReference;
    private String stockStatus;
    private String publishedBy;
    private String publishedAt;

}
