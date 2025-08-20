package com.mycompany.myapp.actionHandler;

import com.fasterxml.jackson.databind.JsonNode;

public interface ActionHandler {
    void handle(JsonNode payload);
    String getActionType();
}
