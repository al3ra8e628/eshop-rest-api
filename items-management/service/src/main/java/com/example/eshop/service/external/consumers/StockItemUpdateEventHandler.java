package com.example.eshop.service.external.consumers;

import com.example.contract.requests.UpdateItemInStockStatusRequest;
import com.example.eshop.service.inbox.InboxHandler;
import com.example.usecases.UpdateItemInStockStatusUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;


@Slf4j
@Component
public class StockItemUpdateEventHandler implements InboxHandler {
    private final UpdateItemInStockStatusUseCase updateItemInStockStatusUseCase;
    private final ObjectMapper objectMapper;
    private final String itemIsInStockStatusValue;

    public StockItemUpdateEventHandler(UpdateItemInStockStatusUseCase updateItemInStockStatusUseCase,
                                       ObjectMapper objectMapper,
                                       @Value("${eshop.stock.itemInStockStatusValue:ITEM_AVAILABLE}")
                                       String itemIsInStockStatusValue) {
        this.updateItemInStockStatusUseCase = updateItemInStockStatusUseCase;
        this.objectMapper = objectMapper;
        this.itemIsInStockStatusValue = itemIsInStockStatusValue;
    }

    @Override
    @SneakyThrows
    @Transactional
    public void handle(String eventType, String message) {
        UpdateItemStockEvent updateItemStockEvent = objectMapper.readValue(
                message, UpdateItemStockEvent.class);

        LOGGER.info("event consumed {}", updateItemStockEvent);

        updateItemInStockStatusUseCase.execute(
                toUseCaseRequest(updateItemStockEvent)
        );

        LOGGER.info("the event handled successfully...");
    }

    @Override
    public List<String> applicableEvents() {
        return List.of("STOCK_ITEM_STATUS_UPDATED");
    }

    private UpdateItemInStockStatusRequest toUseCaseRequest(UpdateItemStockEvent updateItemStockEvent) {
        final UpdateItemInStockStatusRequest request = new UpdateItemInStockStatusRequest();
        request.setId(Long.parseLong(updateItemStockEvent.itemReference));

        request.setIsInStock(Objects.equals(
                updateItemStockEvent.getStockStatus(), itemIsInStockStatusValue));

        return request;
    }


    @Data
    public static class UpdateItemStockEvent {
        private String itemReference;
        private String stockStatus;
        private String publishedBy;
        private String publishedAt;

    }

}
