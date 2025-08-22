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
public class UpdateCheckItem implements ActionHandler {

    private final CheckItemRepository checkItemRepository;
    private static final Logger log = LoggerFactory.getLogger(UpdateCheckItem.class);

    public UpdateCheckItem(CheckItemRepository checkItemRepository) {
        this.checkItemRepository = checkItemRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var Id = payload.path("action").path("data").path("checkItem").get("id").asText();
        var name = payload.path("action").path("data").path("checkItem").get("name").asText();
        var stateRaw = payload.path("action").path("data").path("checkItem").get("state").asText();

        CheckItemState state = CheckItemState.valueOf(stateRaw.toUpperCase());

        Optional<CheckItem> byCheckItemId = checkItemRepository.findByCheckItemId(Id);

        if (byCheckItemId.isPresent()) {
            CheckItem checkItem = byCheckItemId.get();
            checkItem.setName(name);
            checkItem.setState(state);
            checkItem.setCheckItemId(Id);
            checkItemRepository.save(checkItem);
        } else log.error("CheckItem not found");
    }

    @Override
    public String getActionType() {
        return "updateCheckItem";
    }
}
