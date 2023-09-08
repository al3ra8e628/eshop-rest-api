package com.example.stockmanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SockItemsController {

    private final ObjectMapper objectMapper;

    public SockItemsController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PutMapping("api/v1/stock/items")
    public String updateStockItemStatus(@RequestBody UpdateStockItemStatusRequest request) throws JsonProcessingException {

        final String event = objectMapper.writeValueAsString(request);


        return event;
    }


    @Data
    class UpdateStockItemStatusRequest {
        private String itemReference;
        private StockStatus stockStatus;
    }

    enum StockStatus {
        IN_STOCK, OUT_OF_STOCK
    }


}
