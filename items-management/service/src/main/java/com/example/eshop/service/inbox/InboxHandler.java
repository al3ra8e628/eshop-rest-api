package com.example.eshop.service.inbox;

import java.util.List;

public interface InboxHandler {
    void handle(String eventType, String message);

    List<String> applicableEvents();

}
