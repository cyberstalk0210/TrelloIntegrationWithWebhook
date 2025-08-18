package com.mycompany.myapp.actionHandler;

import com.mycompany.myapp.service.WebhookPayload;

public interface ActionHandler {
    void handle(WebhookPayload payload);
}
