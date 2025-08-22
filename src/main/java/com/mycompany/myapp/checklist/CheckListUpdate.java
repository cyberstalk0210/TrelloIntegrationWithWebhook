package com.mycompany.myapp.checklist;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.CheckList;
import com.mycompany.myapp.repository.CheckListRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CheckListUpdate implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(CheckListUpdate.class);
    private final CheckListRepository checkListRepository;

    public CheckListUpdate(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var name = payload.get("action").get("data").get("checklist").get("name").asText();
        var id = payload.path("action").path("data").path("checklist").path("id").asText();
        Optional<CheckList> checkListOptional = checkListRepository.findByCheckListId(id);

        if (checkListOptional.isPresent()) {
            CheckList checkList = checkListOptional.get();
            checkList.setName(name);
            checkList.setCheckListId(id);
            checkListRepository.save(checkList);
            log.debug("CheckList updated successfully");
        }
    }

    @Override
    public String getActionType() {
        return "updateChecklist";
    }
}
