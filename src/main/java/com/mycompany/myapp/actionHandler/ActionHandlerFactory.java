package com.mycompany.myapp.actionHandler;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ActionHandlerFactory {

    private final Map<String, ActionHandler> handlers = new HashMap<>();

    public ActionHandlerFactory(List<ActionHandler> handlerList) {
        for (ActionHandler handler : handlerList) {
            handlers.put(handler.getActionType(), handler);
        }
    }

    public ActionHandler getHandler(String actionType) {
        return handlers.getOrDefault(
            actionType,
            new ActionHandler() {
                @Override
                public String getActionType() {
                    return actionType;
                }

                @Override
                public void handle(JsonNode payload) {
                    System.out.println("Handler topilmadi: " + actionType);
                }
            }
        );
    }
}
