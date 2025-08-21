package com.mycompany.myapp.attachmentHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.repository.AttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeleteAttachmentFromCard implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(DeleteAttachmentFromCard.class);
    private final AttachmentRepository attachmentRepository;

    public DeleteAttachmentFromCard(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        var attachmentId = payload.path("action").path("data").path("attachment").path("id").asText();
        attachmentRepository.findByAttachmentId(attachmentId).ifPresent(attachmentRepository::delete);
        log.debug("Attachment with id {} has been deleted", attachmentId);
    }

    @Override
    public String getActionType() {
        return "deleteAttachmentFromCard";
    }
}
