package com.mycompany.myapp.attachmentHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.CheckList;
import com.mycompany.myapp.repository.CheckListRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoveChecklistFromCard implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(RemoveChecklistFromCard.class);
    private final CheckListRepository checkListRepository;

    public RemoveChecklistFromCard(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var id = payload.path("action").path("data").path("checklist").path("id").asText();
        Optional<CheckList> checkListOptional = checkListRepository.findByCheckListId(id);

        checkListOptional.ifPresent(checkListRepository::delete);
        log.debug("CheckList deleted successfully");
    }

    @Override
    public String getActionType() {
        return "removeChecklistFromCard";
    }
}
