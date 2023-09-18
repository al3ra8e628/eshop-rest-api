package com.example.eshop.service.external.consumers;

import com.example.eshop.service.external.consumers.StockItemUpdateEventHandler.UpdateItemStockEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Consumer;

@Configuration
public class ConsumersConfig {

    @Bean
    public Consumer<Message<String>> stockUpdate(StockItemUpdateEventHandler handler, ObjectMapper objectMapper) {
        return message -> {
            try {
                final UpdateItemStockEvent updateItemStockEvent = objectMapper.readValue(
                        message.getPayload(), UpdateItemStockEvent.class);


                handler.handle(updateItemStockEvent);
                throw new RuntimeException("asda");

                //inbox design pattern....
                //db table inbox...
                //spring schedulers  job 5 seconds.... handle the pending inboxes.

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        };
    }

}
