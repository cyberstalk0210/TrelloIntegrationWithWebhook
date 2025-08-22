package com.mycompany.myapp.checkItemHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.CheckItem;
import com.mycompany.myapp.domain.enumeration.CheckItemState;
import com.mycompany.myapp.repository.CheckItemRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateCheckItemStateOnCard implements ActionHandler {

    private final CheckItemRepository checkItemRepository;
    private static final Logger log = LoggerFactory.getLogger(UpdateCheckItemStateOnCard.class);

    public UpdateCheckItemStateOnCard(CheckItemRepository checkItemRepository) {
        this.checkItemRepository = checkItemRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var Id = payload.path("action").path("data").path("checkItem").get("id").asText();
        var stateRaw = payload.path("action").path("data").path("checkItem").get("state").asText();
        CheckItemState state = CheckItemState.valueOf(stateRaw.toUpperCase());

        Optional<CheckItem> byCheckItemId = checkItemRepository.findByCheckItemId(Id);

        if (byCheckItemId.isPresent()) {
            CheckItem checkItem = byCheckItemId.get();
            checkItem.setState(state);
            checkItemRepository.save(checkItem);
            log.debug("Updated check item state: {}", checkItem.getState());
        }
    }

    @Override
    public String getActionType() {
        return "updateCheckItemStateOnCard";
    }
}
