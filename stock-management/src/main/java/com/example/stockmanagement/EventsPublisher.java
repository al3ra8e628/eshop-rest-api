package com.example.stockmanagement;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventsPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final String destinationQueue;

    public EventsPublisher(RabbitTemplate rabbitTemplate,
                           ObjectMapper objectMapper,
                           @Value("${eshop.stock.events.update_status_queue}") String destinationQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.destinationQueue = destinationQueue;
    }


    public <T extends Publishable> boolean publishEvent(T publishable) {
        try {
            final String event = objectMapper.writeValueAsString(publishable);

            rabbitTemplate.convertAndSend(destinationQueue, "#", event);

            LOGGER.info("new event published: {}", event);
            return true;
        } catch (Exception e) {
            LOGGER.error("Exception thrown while publish update stock item event: {}", publishable, e);
            return false;
        }
    }


    interface Publishable {
        String getPublishedAt();

        String getPublishedBy();

    }


}
