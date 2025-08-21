package com.mycompany.myapp.attachmentHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Attachment;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.repository.AttachmentRepository;
import com.mycompany.myapp.repository.CardRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AddAttachmentToCard implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(AddAttachmentToCard.class);
    private final CardRepository cardRepository;
    private final AttachmentRepository attachmentRepository;

    public AddAttachmentToCard(CardRepository cardRepository, AttachmentRepository attachmentRepository) {
        this.cardRepository = cardRepository;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var attachmentId = payload.path("action").path("data").path("attachment").path("id").asText();
        var fileName = payload.path("action").path("data").path("attachment").path("name").asText();
        var fileUrl = payload.path("action").path("data").path("attachment").path("url").asText();
        var cardId = payload.path("action").path("data").path("card").path("id").asText();

        if (attachmentRepository.findByAttachmentId(attachmentId).isPresent()) {
            log.debug("Attachment with id {} already exists", attachmentId);
            return;
        }

        Card card = cardRepository
            .findByTrelloId(cardId)
            .orElseThrow(() -> new IllegalArgumentException("Card not found with id: " + cardId));

        Attachment attachment = new Attachment();
        attachment.setAttachmentId(attachmentId);
        attachment.setCard(card);
        attachment.setFileName(fileName);
        attachment.setFileUrl(fileUrl);
        attachment.setUploadedAt(Instant.now());

        attachmentRepository.save(attachment);

        log.debug("Added new attachment with id {}", attachmentId);
    }

    @Override
    public String getActionType() {
        return "addAttachmentToCard";
    }
}
