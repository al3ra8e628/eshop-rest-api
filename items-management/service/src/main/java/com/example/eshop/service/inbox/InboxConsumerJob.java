package com.example.eshop.service.inbox;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InboxConsumerJob {
    private final InboxService inboxService;
    private final InboxHandlersRegistry inboxHandlersRegistry;
    private final Integer maxAllowedRetries;
    private final Integer maxFetchSize;

    public InboxConsumerJob(InboxService inboxService,
                            InboxHandlersRegistry inboxHandlersRegistry,
                            @Value("${eshop.inbox.maxAllowedRetries:5}")
                            Integer maxAllowedRetries,
                            @Value("${eshop.inbox.maxFetchSize:20}")
                            Integer maxFetchSize) {
        this.inboxService = inboxService;
        this.inboxHandlersRegistry = inboxHandlersRegistry;
        this.maxAllowedRetries = maxAllowedRetries;
        this.maxFetchSize = maxFetchSize;
    }

    @Scheduled(fixedRate = 5000L)
    public void run() {
        inboxService.findAllReadyForHandling(
                maxAllowedRetries, maxFetchSize
        ).forEach(it -> {
            final String messageType = it.getMessageType();

            try {
                InboxHandler handler = inboxHandlersRegistry.getHandler(messageType);
                handler.handle(messageType, it.getMessagePayload());
                it.setStatus("PROCESSED");
            } catch (Exception e) {
                it.setStatus("FAILED");
                it.setRetries(it.getRetries() + 1);
                it.setFailureReason(e.getMessage());
                LOGGER.error("exception on handling inbox (id:{} ,messageType:{})",
                        it.getId(), it.getMessageType());
            } finally {
                inboxService.save(it);
            }
        });
    }


}
