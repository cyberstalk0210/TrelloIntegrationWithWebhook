package com.mycompany.myapp.cardHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.service.CardService;
import com.mycompany.myapp.service.mapper.CardMapper;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public record UpdateCardHandler(
    CardService cardService,
    CardRepository repository,
    CardMapper cardMapper,
    BoardRepository boardRepository,
    BoardListRepository boardListRepository
)
    implements ActionHandler {
    private static final Logger log = LoggerFactory.getLogger(UpdateCardHandler.class);

    @Override
    public void handle(JsonNode payload) {
        JsonNode cardNode = payload.path("action").path("data").path("card");

        String cardName = cardNode.path("name").asText();
        String trelloId = cardNode.path("id").asText();

        boolean hasDesc = cardNode.has("desc");
        boolean hasStart = cardNode.has("start");
        boolean hasDue = cardNode.has("due");

        Instant newStart = hasStart ? toInstantOrNull(cardNode.get("start")) : null;
        Instant newDue = hasDue ? toInstantOrNull(cardNode.get("due")) : null;
        String desc = hasDesc ? cardNode.get("desc").asText() : "";

        Optional<Card> optionalCard = repository.findByTrelloId(trelloId);

        optionalCard.ifPresent(card -> {
            card.setTitle(cardName);
            card.setTrelloId(trelloId);
            card.setUpdatedAt(Instant.now());

            if (hasDesc && !Objects.equals(card.getDescription(), hasDesc)) {
                card.setDescription(desc);
                log.debug("Description updated for card {} -> {}", cardName, desc);
            }
            if (hasStart && !Objects.equals(card.getStartDate(), newStart)) {
                card.setStartDate(newStart);
                log.debug("StartDate updated for card {} -> {}", cardName, newStart);
            }

            if (hasDue && !Objects.equals(card.getDueDate(), newDue)) {
                card.setDueDate(newDue);
                log.debug("DueDate updated for card {} -> {}", cardName, newDue);
            }

            repository.save(card);
            log.debug("Card Updated: {}", cardName);
        });
    }

    private static Instant toInstantOrNull(JsonNode node) {
        if (node == null || node.isNull()) return null;
        String s = node.asText();
        if (s == null || s.isBlank()) return null;
        return Instant.parse(s);
    }

    @Override
    public String getActionType() {
        return "updateCard";
    }
}
