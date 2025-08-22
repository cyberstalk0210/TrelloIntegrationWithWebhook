package com.mycompany.myapp.cardHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.service.CardService;
import com.mycompany.myapp.service.mapper.CardMapper;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public record CreateCardHandler(
    CardService cardService,
    CardRepository repository,
    CardMapper cardMapper,
    BoardRepository boardRepository,
    BoardListRepository boardListRepository
)
    implements ActionHandler {
    private static final Logger log = LoggerFactory.getLogger(CreateCardHandler.class);

    @Override
    public void handle(JsonNode payload) {
        String cardName = payload.get("action").get("data").get("card").get("name").asText();
        String trelloId = payload.get("action").get("data").get("card").get("id").asText();
        String boardId = payload.get("action").get("data").get("board").get("id").asText();
        String listId = payload.get("action").get("data").get("list").get("id").asText();

        Optional<Card> optionalCard = repository.findByTrelloId(trelloId);

        if (optionalCard.isEmpty()) {
            Card newCard = new Card();
            newCard.setCreatedAt(Instant.now());
            newCard.setUpdatedAt(Instant.now());
            newCard.setTitle(cardName);
            newCard.setTrelloId(trelloId);
            newCard.setDescription("");
            newCard.setPosition(null);
            newCard.setDueDate(null);
            newCard.setArchived(false);

            Optional<Board> boardIdOptionalBoard = boardRepository.findByTrelloId(boardId);

            newCard.setBoard(boardIdOptionalBoard.orElse(null));

            Optional<BoardList> boardList = boardListRepository.findByTrelloId(listId);

            newCard.setBoardList(boardList.orElse(null));

            repository.save(newCard);
            log.debug("New Card Saved");
        }
    }

    @Override
    public String getActionType() {
        return "createCard";
    }
}
