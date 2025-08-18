package com.mycompany.myapp.actionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class ActionHandlerFactory {

    private final Map<String, ActionHandler> handlers = new HashMap<>();

    public ActionHandlerFactory(List<ActionHandler> handlerList) {
        for (ActionHandler handler : handlerList) {
            handlers.put(handler.getClass().getSimpleName(), handler);
        }
    }

    public ActionHandler getHandler(String actionType) {
        return handlers.getOrDefault(actionType, payload -> {
            System.out.println("Handler topilmadi: " + actionType);
        });
    }
}
