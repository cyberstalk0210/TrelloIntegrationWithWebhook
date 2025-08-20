package com.mycompany.myapp.organizationActionHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.Board;
import com.mycompany.myapp.domain.Workspace;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.repository.WorkspaceRepository;
import com.mycompany.myapp.service.WorkspaceService;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreateOrganizationHandler implements ActionHandler {

    private static final Logger log = LoggerFactory.getLogger(CreateOrganizationHandler.class);
    private final WorkspaceService workspaceService;

    private final WorkspaceRepository workspaceRepository;

    private final BoardRepository boardRepository;

    public CreateOrganizationHandler(
        WorkspaceService workspaceService,
        WorkspaceRepository workspaceRepository,
        BoardRepository boardRepository
    ) {
        this.workspaceService = workspaceService;
        this.workspaceRepository = workspaceRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        try {
            String trelloId = payload.get("model").get("id").asText();
            String orgName = payload.get("action").get("data").get("organization").get("name").asText();

            String boardId = payload.get("action").get("data").get("board").get("id").asText();
            String boardName = payload.get("action").get("data").get("board").get("name").asText();

            workspaceRepository
                .findByTrelloId(trelloId)
                .ifPresentOrElse(
                    existingWorkspace -> {
                        existingWorkspace.setName(orgName);
                        Board newBoard = new Board();

                        newBoard.setTrelloId(boardId);
                        newBoard.setTitle(boardName);
                        newBoard.setCreatedAt(Instant.now());
                        newBoard.setUpdatedAt(Instant.now());
                        newBoard.setWorkspace(existingWorkspace);

                        boardRepository.save(newBoard);
                        log.debug("New board created inside existing workspace");
                        workspaceRepository.save(existingWorkspace);
                        log.debug("Workspace updated");
                    },
                    () -> {
                        Workspace newWorkspace = new Workspace();
                        newWorkspace.setTrelloId(trelloId);
                        newWorkspace.setName(orgName);
                        workspaceRepository.save(newWorkspace);
                        log.debug("Workspace created");
                    }
                );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getActionType() {
        return "addToOrganizationBoard";
    }
}
