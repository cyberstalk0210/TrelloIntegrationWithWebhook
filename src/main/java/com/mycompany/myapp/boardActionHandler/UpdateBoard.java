package com.mycompany.myapp.boardActionHandler;

import com.fasterxml.jackson.databind.JsonNode;
import com.mycompany.myapp.actionHandler.ActionHandler;
import com.mycompany.myapp.domain.enumeration.BoardVisibility;
import com.mycompany.myapp.repository.BoardRepository;
import com.mycompany.myapp.repository.WorkspaceRepository;
import org.springframework.stereotype.Component;

@Component
public class UpdateBoard implements ActionHandler {

    private final BoardRepository boardRepository;
    private final WorkspaceRepository workspaceRepository;

    public UpdateBoard(BoardRepository boardRepository, WorkspaceRepository workspaceRepository) {
        this.boardRepository = boardRepository;
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public void handle(JsonNode payload) {
        String boardName = payload.path("model").path("name").asText(null);
        String boardId = payload.path("action").path("data").path("board").path("id").asText(null);
        String workspaceId = payload.path("model").path("idOrganization").asText(null);
        String memberCreatorUsername = payload.path("memberCreator").path("id").asText();

        if (boardId == null) return;

        boardRepository
            .findByTrelloId(boardId)
            .ifPresent(board -> {
                if (workspaceId != null) workspaceRepository.findByTrelloId(workspaceId).ifPresent(board::setWorkspace);

                if (boardName != null) board.setTitle(boardName);

                board.setUpdatedBy(memberCreatorUsername);

                String visibility = payload.path("model").path("prefs").path("permissionLevel").asText(null);
                if (visibility != null) board.setVisibility(BoardVisibility.valueOf(visibility.toUpperCase()));

                boardRepository.save(board);
            });
    }

    @Override
    public String getActionType() {
        return "updateBoard";
    }
}
