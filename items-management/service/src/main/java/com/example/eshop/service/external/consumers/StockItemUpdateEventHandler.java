package com.example.eshop.service.external.consumers;

import com.example.contract.requests.UpdateItemInStockStatusRequest;
import com.example.usecases.UpdateItemInStockStatusUseCase;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Slf4j
@Component
public class StockItemUpdateEventHandler {
    private final UpdateItemInStockStatusUseCase updateItemInStockStatusUseCase;
    private final String itemIsInStockStatusValue;

    public StockItemUpdateEventHandler(UpdateItemInStockStatusUseCase updateItemInStockStatusUseCase,
                                       @Value("${eshop.stock.itemInStockStatusValue:ITEM_AVAILABLE}")
                                       String itemIsInStockStatusValue) {
        this.updateItemInStockStatusUseCase = updateItemInStockStatusUseCase;
        this.itemIsInStockStatusValue = itemIsInStockStatusValue;
    }

    @Transactional
    public void handle(UpdateItemStockEvent updateItemStockEvent) {
        LOGGER.info("event consumed {}", updateItemStockEvent);

        updateItemInStockStatusUseCase.execute(
                toUseCaseRequest(updateItemStockEvent)
        );

        LOGGER.info("the event handled successfully...");
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
