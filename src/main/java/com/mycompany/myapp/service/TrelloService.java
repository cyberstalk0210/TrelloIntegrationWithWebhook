package com.mycompany.myapp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.domain.enumeration.BoardVisibility;
import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.service.dto.TrelloIdDTO;
import com.mycompany.myapp.service.dto.TrelloWebhookDTO;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TrelloService {

    private static final Logger log = LoggerFactory.getLogger(TrelloService.class);

    private final RestTemplate restTemplate;
    private final String key;
    private final String token;

    private static final String BASE_WEBHOOK_URL =
        "https://api.trello.com/1/webhooks?callbackURL={callbackURL}&idModel={idModel}&key={key}&token={token}";

    private static final String BOARD_URL = "https://api.trello.com/1/members/me/boards?key={key}&token={token}";

    private final String callbackUrl;
    private final BoardListRepository boardListRepository;
    private final CardRepository cardRepository;

    private final TrelloIdDTO trelloIdDTO = new TrelloIdDTO();
    private final BoardRepository boardRepository;

    public TrelloService(
        RestTemplate restTemplate,
        @Value("${trello.api.key}") String key,
        @Value("${trello.api.token}") String token,
        BoardListRepository boardListRepository,
        CardRepository cardRepository,
        BoardRepository boardRepository
    ) {
        this.restTemplate = restTemplate;
        this.key = key;
        this.token = token;
        this.callbackUrl = "https://ab3f6ac4cbde.ngrok-free.app/api/trello/webhook";
        this.boardListRepository = boardListRepository;
        this.cardRepository = cardRepository;
        this.boardRepository = boardRepository;
    }

    public void getMyWorkspace() {
        JsonNode boards = restTemplate.getForObject(BOARD_URL, JsonNode.class, key, token);

        if (boards != null && boards.isArray() && !boards.isEmpty()) {
            JsonNode firstBoard = boards.get(0);
            trelloIdDTO.setBoardId(firstBoard.path("id").asText());
            trelloIdDTO.setOrganizationId(firstBoard.path("idOrganization").asText());
            log.info("Board Id: {}", trelloIdDTO.getBoardId());
            log.info("Organization Id: {}", trelloIdDTO.getOrganizationId());
        }
    }

    @PostConstruct
    @Transactional
    public void importAllListsAndCards() {
        getMyWorkspace();
        String boardId = trelloIdDTO.getBoardId();

        if (boardId == null) {
            log.error("Board ID not found, import aborted!");
            return;
        }

        JsonNode boards = restTemplate.getForObject(
            "https://api.trello.com/1/boards/{id}?key={key}&token={token}",
            JsonNode.class,
            boardId,
            key,
            token
        );

        if (boards == null) {
            log.error("Trello board data's doesn't import");
            return;
        }
        String id = boards.get("id").asText();
        String name = boards.get("name").asText();
        String boardDesc = boards.get("desc").asText();
        String memberCreator = boards.has("idMemberCreator") ? boards.get("idMemberCreator").asText() : null;
        String permissionLevel = boards.get("prefs").get("permissionLevel").asText();

        BoardVisibility visibility =
            switch (permissionLevel) {
                case "org" -> BoardVisibility.WORKSPACE;
                case "public" -> BoardVisibility.PUBLIC;
                default -> BoardVisibility.PRIVATE;
            };

        // bu createdAt ni trelloId dan generatsiya qilib oladi

        long timestamp = Long.parseLong(id.substring(0, 8), 16);
        Instant createdAt = Instant.ofEpochSecond(timestamp);

        Optional<Board> existingBoard = boardRepository.findByTrelloId(id);
        Board board = existingBoard.orElseGet(Board::new);

        board.setTitle(name);
        board.setDescription(boardDesc);
        board.setTrelloId(id);
        board.setCreatedBy(memberCreator);
        board.setVisibility(visibility);

        if (board.getId() == null) {
            board.setCreatedAt(createdAt);
            board.setCreatedBy(memberCreator);
        } else {
            board.setUpdatedAt(Instant.now());
            board.setUpdatedBy(memberCreator);
        }
        boardRepository.save(board);

        JsonNode lists = restTemplate.getForObject(
            "https://api.trello.com/1/boards/{id}/lists?key={key}&token={token}",
            JsonNode.class,
            boardId,
            key,
            token
        );

        if (lists != null && lists.isArray()) {
            for (JsonNode listNode : lists) {
                String listId = listNode.get("id").asText();
                String listName = listNode.get("name").asText();

                Optional<BoardList> existingList = boardListRepository.findByTrelloId(listId);

                BoardList listEntity = existingList.orElseGet(BoardList::new);
                listEntity.setTrelloId(listId);
                listEntity.setName(listName);
                listEntity.setBoard(board);

                boardListRepository.save(listEntity);

                JsonNode cards = restTemplate.getForObject(
                    "https://api.trello.com/1/lists/{id}/cards?key={key}&token={token}",
                    JsonNode.class,
                    listId,
                    key,
                    token
                );

                if (cards != null && cards.isArray()) {
                    for (JsonNode cardNode : cards) {
                        String cardId = cardNode.get("id").asText();
                        String cardName = cardNode.get("name").asText();
                        String desc = cardNode.has("desc") ? cardNode.get("desc").asText() : "";

                        Optional<Card> existingCard = cardRepository.findByTrelloId(cardId);

                        Card cardEntity = existingCard.orElseGet(Card::new);
                        cardEntity.setTrelloId(cardId);
                        cardEntity.setTitle(cardName);
                        cardEntity.setDescription(desc);

                        if (cardEntity.getId() == null) cardEntity.setCreatedAt(createdAt);
                        else cardEntity.setUpdatedAt(Instant.now());

                        cardEntity.setBoardList(listEntity);
                        cardEntity.setBoard(board);

                        cardRepository.save(cardEntity);
                    }
                }
            }
        }
        log.info("All list and cards is imported.");
    }

    public void createWebhookForBoard() {
        getMyWorkspace();
        if (trelloIdDTO.getBoardId() != null) {
            restTemplate.postForEntity(BASE_WEBHOOK_URL, null, TrelloWebhookDTO.class, callbackUrl, trelloIdDTO.getBoardId(), key, token);
            log.info("Webhook created for the board.");
            return;
        }
        throw new RuntimeException("Board Id is doesn't existing");
    }
}
