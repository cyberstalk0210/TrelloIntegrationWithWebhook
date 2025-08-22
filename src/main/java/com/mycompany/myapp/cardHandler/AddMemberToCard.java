package com.mycompany.myapp.cardHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.domain.Member;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class AddMemberToCard implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(AddMemberToCard.class);
    private final MemberRepository memberRepository;
    private final CardRepository cardRepository;

    public AddMemberToCard(MemberRepository memberRepository, CardRepository cardRepository) {
        this.memberRepository = memberRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        String name = payload.path("action").path("data").path("member").get("name").asText();
        var id = payload.path("action").path("data").path("member").get("id").asText();
        String cardTrelloId = payload.path("action").path("data").path("card").get("id").asText();
        String trelloId = payload.path("action").path("data").path("member").get("id").asText();

        Member member = memberRepository
            .findByTrelloId(trelloId)
            .orElseGet(() -> {
                Member m = new Member();
                m.setName(name);
                m.setTrelloId(trelloId);
                return memberRepository.save(m);
            });

        Card card = cardRepository
            .findByTrelloId(cardTrelloId)
            .orElseThrow(() -> new IllegalArgumentException("Card not found: " + cardTrelloId));

        card.getMembers().add(member);
        cardRepository.save(card);

        log.debug("Member {} added to Card {}", name, cardTrelloId);
    }

    @Override
    public String getActionType() {
        return "addMemberToCard";
    }
}
