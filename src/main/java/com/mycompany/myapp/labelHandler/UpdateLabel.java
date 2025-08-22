package com.mycompany.myapp.labelHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Label;
import com.mycompany.myapp.repository.LabelRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateLabel implements ActionHandler {

    private final LabelRepository labelRepository;

    public UpdateLabel(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var labelName = payload.get("action").get("data").get("label").get("name").asText();
        var labelColor = payload.get("action").get("data").get("label").get("color").asText();
        var labelId = payload.get("action").get("data").get("label").get("id").asText();

        var label = labelRepository.findByTrelloId(labelId).orElseGet(Label::new);

        label.setTrelloId(labelId);
        label.setName(labelName);
        label.setColor(labelColor);

        labelRepository.save(label);
    }

    @Override
    public String getActionType() {
        return "updateLabel";
    }
}
