package com.mycompany.myapp.cardHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.repository.CheckItemRepository;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ConvertToCardFromCheckItem implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(ConvertToCardFromCheckItem.class);
    private final CardRepository cardRepository;
    private final BoardRepository boardRepository;
    private final BoardListRepository boardListRepository;
    private final CheckItemRepository checkItemRepository;

    public ConvertToCardFromCheckItem(
        CardRepository cardRepository,
        BoardRepository boardRepository,
        BoardListRepository boardListRepository,
        CheckItemRepository checkItemRepository
    ) {
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
        this.boardListRepository = boardListRepository;
        this.checkItemRepository = checkItemRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var cardId = payload.get("action").get("data").get("card").get("id").asText();
        String checkItemId = payload.get("action").get("data").get("cardSource").get("id").asText();
        var checkItemName = payload.get("action").get("data").get("checkItem").get("name").asText();
        String boardId = payload.get("action").get("data").get("board").get("id").asText();
        String listId = payload.get("action").get("data").get("list").get("id").asText();

        Optional<Card> optionalCard = cardRepository.findByTrelloId(cardId);

        if (optionalCard.isEmpty()) {
            Card newCard = new Card();
            newCard.setCreatedAt(Instant.now());
            newCard.setUpdatedAt(Instant.now());
            newCard.setTitle(checkItemName);
            newCard.setTrelloId(cardId);
            newCard.setPosition(null);
            newCard.setDueDate(null);
            newCard.setArchived(false);

            Optional<Board> boardIdOptionalBoard = boardRepository.findByTrelloId(boardId);
            Optional<BoardList> boardList = boardListRepository.findByTrelloId(listId);

            newCard.setBoard(boardIdOptionalBoard.orElse(null));
            newCard.setBoardList(boardList.orElse(null));
            cardRepository.save(newCard);

            checkItemRepository.findByCheckItemId(checkItemId).ifPresent(checkItemRepository::delete);

            log.debug("New Card Saved and CheckItem deleted");
        }
    }

    @Override
    public String getActionType() {
        return "convertToCardFromCheckItem";
    }
}
