package com.mycompany.myapp.actionHandler;

import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.service.CardService;
import com.mycompany.myapp.service.WebhookPayload;
import com.mycompany.myapp.service.mapper.CardMapper;
import java.util.Optional;

public record CreateCardHandler(CardService cardService, CardRepository repository, CardMapper cardMapper) implements ActionHandler {
    @Override
    public void handle(WebhookPayload payload) {
        try {
            String cardName = payload.getAction().getData().getCard().getName();
            Long idShort = payload.getAction().getData().getCard().getIdShort();
            Optional<Card> card = repository.findById(idShort);

            if (card.isPresent()) {
                card.get().setTitle(cardName);
                cardService.update(cardMapper.toDto(card.get()));
            }

            cardService.save(cardMapper.toDto(card.get()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
