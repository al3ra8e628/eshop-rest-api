package com.example.eshop.service.inbox;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class InboxHandlersRegistry {

    private List<InboxHandler> inboxHandlers;

    public InboxHandlersRegistry(List<InboxHandler> inboxHandlers) {
        this.inboxHandlers = inboxHandlers;
    }

    public InboxHandler getHandler(String eventType) {
        final Optional<InboxHandler> inboxHandler = inboxHandlers.stream().filter(it -> {
            final List<String> applicableEvents = it.applicableEvents();
            return applicableEvents.contains(eventType);
        }).findFirst();

        return inboxHandler.orElse(new InboxHandler() {
            @Override
            public void handle(String eventType, String message) {
                LOGGER.info("event: {} , has no implemented handlers", eventType);
            }

            @Override
            public List<String> applicableEvents() {
                return List.of(eventType);
            }
        });
    }

}
