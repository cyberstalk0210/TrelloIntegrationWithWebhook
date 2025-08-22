package com.mycompany.myapp.labelHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Label;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.repository.LabelRepository;
import org.springframework.stereotype.Component;

@Component
public class RemoveLabelFromCard implements ActionHandler {

    private final LabelRepository labelRepository;
    private final CardRepository cardRepository;

    public RemoveLabelFromCard(LabelRepository labelRepository, CardRepository cardRepository) {
        this.labelRepository = labelRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var labelId = payload.path("action").path("data").path("label").path("id").asText();
        var cardId = payload.get("action").get("data").path("card").path("id").asText();

        var label = labelRepository.findByTrelloId(labelId).orElseGet(Label::new);
        cardRepository
            .findByTrelloId(cardId)
            .ifPresent(card -> {
                card.removeLabels(label);
                cardRepository.save(card);
            });
    }

    @Override
    public String getActionType() {
        return "removeLabelFromCard";
    }
}
