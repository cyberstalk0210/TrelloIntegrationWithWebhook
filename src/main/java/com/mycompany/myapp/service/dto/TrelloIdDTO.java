package com.mycompany.myapp.service.dto;

public class TrelloIdDTO {

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    private String boardId;
    private String organizationId;
}
