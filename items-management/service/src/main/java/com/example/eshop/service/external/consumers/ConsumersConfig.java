package com.example.eshop.service.external.consumers;

import com.example.eshop.service.inbox.InboxEntity;
import com.example.eshop.service.inbox.InboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

import java.util.function.Consumer;

@Slf4j
@Configuration
public class ConsumersConfig {

    @Bean
    public Consumer<Message<String>> stockUpdate(InboxService inboxService) {
        return message -> {
            try {
                inboxService.save(InboxEntity.of(
                        "STOCK_ITEM_STATUS_UPDATED", message.getPayload()));
                LOGGER.info("message saved to inbox to be processed, message {}" , message);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}
