package com.mycompany.myapp.web;

import com.mycompany.myapp.ActionHandler.ActionHandler;
import com.mycompany.myapp.ActionHandler.ActionHandlerFactory;
import com.mycompany.myapp.service.TrelloService;
import com.mycompany.myapp.service.WebhookPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trello")
public class TrelloWebhookController {

    private final TrelloService trelloService;
    private final ActionHandlerFactory actionHandlerFactory;

    public TrelloWebhookController(TrelloService trelloService, ActionHandlerFactory actionHandlerFactory) {
        this.trelloService = trelloService;
        this.actionHandlerFactory = actionHandlerFactory;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody WebhookPayload payload) {
        String actionType = payload.getAction().getType();
        ActionHandler handler = actionHandlerFactory.getHandler(actionType);
        handler.handle(payload);

        return ResponseEntity.ok("OK");
    }

    @PostMapping("create-webhook")
    public ResponseEntity<?> postWebhook() {
        return trelloService.response();
    }

    @GetMapping("/webhook")
    public ResponseEntity<?> verifyWebhook() {
        return ResponseEntity.ok("OK");
    }
}
