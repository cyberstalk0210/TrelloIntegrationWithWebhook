package com.mycompany.myapp.cardHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.service.CardService;
import org.springframework.stereotype.Component;

@Component
public class DeleteCardHandler implements ActionHandler {

    private final CardService cardService;
    private final CardRepository cardRepository;

    public DeleteCardHandler(CardService cardService, CardRepository cardRepository) {
        this.cardService = cardService;
        this.cardRepository = cardRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        Long idShort = payload.get("action").asLong();
        cardRepository.findById(idShort).ifPresent(card -> cardService.delete(idShort));
    }

    @Override
    public String getActionType() {
        return "";
    }
}
