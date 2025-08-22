package com.mycompany.myapp.boardActionHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.repository.BoardListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UpdateBoardList implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(UpdateBoardList.class);
    private final BoardListRepository boardListRepository;

    public UpdateBoardList(BoardListRepository bookListRepository) {
        this.boardListRepository = bookListRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        String boardListId = payload.path("action").path("data").path("list").get("id").asText();
        var name = payload.path("action").path("data").path("list").get("name").asText();

        boardListRepository
            .findByTrelloId(boardListId)
            .ifPresent(boardList -> {
                boardList.setName(name);
                boardListRepository.save(boardList);
                log.debug("Successfully updated board list {}", boardList);
            });
    }

    @Override
    public String getActionType() {
        return "updateList";
    }
}
