package com.mycompany.myapp.attachmentHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Label;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.repository.LabelRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AddLabelToCard implements ActionHandler {

    private final LabelRepository labelRepository;
    private final CardRepository cardRepository;

    public AddLabelToCard(LabelRepository labelRepository, CardRepository cardRepository) {
        this.labelRepository = labelRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    @Transactional
    public void handle(JsonNode payload) {
        var labelName = payload.path("action").path("data").path("label").path("name").asText();
        var labelId = payload.path("action").path("data").path("label").path("id").asText();
        var labelColor = payload.path("action").path("data").path("label").path("color").asText();
        var cardId = payload.get("action").get("data").path("card").path("id").asText();

        var label = labelRepository.findByTrelloId(labelId).orElseGet(Label::new);

        label.setTrelloId(labelId);
        label.setName(labelName);
        label.setColor(labelColor);
        labelRepository.save(label);

        cardRepository
            .findByTrelloId(cardId)
            .ifPresent(card -> {
                card.addLabels(label);
                label.getCards().add(card);
                cardRepository.save(card);
            });
    }

    @Override
    public String getActionType() {
        return "addLabelToCard";
    }
}
