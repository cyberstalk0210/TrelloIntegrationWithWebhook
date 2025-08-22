package com.mycompany.myapp.checkItemHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.CheckItem;
import com.mycompany.myapp.domain.enumeration.CheckItemState;
import com.mycompany.myapp.repository.CheckItemRepository;
import com.mycompany.myapp.repository.CheckListRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateCheckItem implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(CreateCheckItem.class);
    private final CheckItemRepository checkItemRepository;
    private final CheckListRepository checkListRepository;

    public CreateCheckItem(CheckItemRepository checkItemRepository, CheckListRepository checkListRepository) {
        this.checkItemRepository = checkItemRepository;
        this.checkListRepository = checkListRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var checkItemId = payload.path("action").path("data").path("checkItem").get("id").asText();
        var name = payload.path("action").path("data").path("checkItem").get("name").asText();
        var stateRaw = payload.path("action").path("data").path("checkItem").get("state").asText();
        var checkListId = payload.path("action").path("data").path("checklist").path("id").asText();

        CheckItemState state = CheckItemState.valueOf(stateRaw.toUpperCase());

        Optional<CheckItem> byCheckItemId = checkItemRepository.findByCheckItemId(checkItemId);
        if (byCheckItemId.isEmpty()) {
            CheckItem checkItem = new CheckItem();
            checkItem.setName(name);
            checkItem.setState(state);
            checkItem.setCheckItemId(checkItemId);

            checkListRepository.findByCheckListId(checkListId).ifPresent(checkItem::setCheckList);

            checkItemRepository.save(checkItem);
            log.debug("Created checkItem with id {}", checkItem.getId());
        } else {
            log.debug("checkItemId already exists");
        }
    }

    @Override
    public String getActionType() {
        return "createCheckItem";
    }
}
