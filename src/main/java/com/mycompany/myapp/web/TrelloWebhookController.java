package com.mycompany.myapp.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.actionHandler.ActionHandlerFactory;
import com.mycompany.myapp.service.TrelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trello")
public class TrelloWebhookController {

    private static final Logger log = LoggerFactory.getLogger(TrelloWebhookController.class);
    private final TrelloService trelloService;
    private final ActionHandlerFactory actionHandlerFactory;

    public TrelloWebhookController(TrelloService trelloService, ActionHandlerFactory actionHandlerFactory) {
        this.trelloService = trelloService;
        this.actionHandlerFactory = actionHandlerFactory;
    }

    @PostMapping("/webhook")
    public void handleWebhook(@RequestBody JsonNode payload) {
        log.debug("Webhook kelib tushdi: {}", payload.toPrettyString());
        String actionType = payload.get("action").get("type").asText();
        ActionHandler handler = actionHandlerFactory.getHandler(actionType);
        handler.handle(payload);
    }

    @PostMapping("create-webhook")
    public void postWebhook() {
        trelloService.createWebhookForBoard();
    }

    @RequestMapping(value = "/webhook", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ResponseEntity<String> verifyWebhook() {
        return ResponseEntity.ok("Webhook is active");
    }
}
