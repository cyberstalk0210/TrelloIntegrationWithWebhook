package com.mycompany.myapp.checkItemHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.repository.CheckItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeleteCheckItem implements ActionHandler {

    private final CheckItemRepository checkItemRepository;
    private static final Logger log = LoggerFactory.getLogger(DeleteCheckItem.class);

    public DeleteCheckItem(CheckItemRepository checkItemRepository) {
        this.checkItemRepository = checkItemRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var Id = payload.path("action").path("data").path("checkItem").get("id").asText();
        checkItemRepository.findByCheckItemId(Id).ifPresent(checkItemRepository::delete);
        log.info("Check Item Deleted Successfully");
    }

    @Override
    public String getActionType() {
        return "deleteCheckItem";
    }
}
