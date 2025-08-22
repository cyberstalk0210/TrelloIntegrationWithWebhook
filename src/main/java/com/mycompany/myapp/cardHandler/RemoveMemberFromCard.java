package com.mycompany.myapp.cardHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.repository.MemberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoveMemberFromCard implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(RemoveMemberFromCard.class);
    private final MemberRepository memberRepository;

    public RemoveMemberFromCard(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var memberId = payload.path("action").path("data").path("member").path("id").asText();
        memberRepository.findByTrelloId(memberId).ifPresent(memberRepository::delete);
        log.debug("Member successfully removed from card");
    }

    @Override
    public String getActionType() {
        return "removeMemberFromCard";
    }
}
