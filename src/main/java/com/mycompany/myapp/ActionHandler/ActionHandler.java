package com.mycompany.myapp.ActionHandler;

import com.mycompany.myapp.service.WebhookPayload;

public interface ActionHandler {
    void handle(WebhookPayload payload);
}
