package com.example.stockmanagement;

import com.example.eshop.contract.UserIdentityProvider;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
public class StockItemsController {
    private final EventsPublisher eventsPublisher;
    private final UserIdentityProvider userIdentityProvider;

    public StockItemsController(EventsPublisher eventsPublisher,
                                UserIdentityProvider userIdentityProvider) {
        this.eventsPublisher = eventsPublisher;
        this.userIdentityProvider = userIdentityProvider;
    }

    @SneakyThrows
    @PutMapping("api/v1/stock/items")
    public UpdateItemStockEvent updateStockItemStatus(@RequestBody UpdateStockItemStatusRequest request) {
        final UpdateItemStockEvent updateItemStockEvent = toUpdateEvent(request);

        System.out.println(userIdentityProvider.getCurrentUserIdentity().getUsername());

        eventsPublisher.publishEvent(updateItemStockEvent);

        return updateItemStockEvent;
    }

    private static UpdateItemStockEvent toUpdateEvent(UpdateStockItemStatusRequest request) {
        final UpdateItemStockEvent updateItemStockEvent = new UpdateItemStockEvent();

        updateItemStockEvent.setStockStatus(request.getStockStatus().name());
        updateItemStockEvent.setItemReference(request.getItemReference());
        updateItemStockEvent.setPublishedBy("eshop-user");
        updateItemStockEvent.setPublishedAt(LocalDateTime.now().toString());

        return updateItemStockEvent;
    }


    @Data
    static class UpdateStockItemStatusRequest {
        private String itemReference;
        private StockStatus stockStatus;
    }

    enum StockStatus {
        IN_STOCK, OUT_OF_STOCK
    }


}
