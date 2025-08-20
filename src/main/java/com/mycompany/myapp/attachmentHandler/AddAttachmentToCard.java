package com.mycompany.myapp.attachmentHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import org.springframework.stereotype.Component;

@Component
public class AddAttachmentToCard implements ActionHandler {

    @Override
    public void handle(JsonNode payload) {}

    @Override
    public String getActionType() {
        return "addAttachmentToCard";
    }
}
