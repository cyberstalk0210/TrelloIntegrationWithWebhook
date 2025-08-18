package com.mycompany.myapp.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Card} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CardDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private String description;

    private Integer position;

    private Instant dueDate;

    private Boolean isArchived;

    @NotNull
    private String trelloId;

    private Instant createdAt;

    private Instant updatedAt;

    private Set<LabelDTO> labels = new HashSet<>();

    private BoardDTO board;

    private BoardListDTO boardList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getTrelloId() {
        return trelloId;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<LabelDTO> getLabels() {
        return labels;
    }

    public void setLabels(Set<LabelDTO> labels) {
        this.labels = labels;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    public BoardListDTO getBoardList() {
        return boardList;
    }

    public void setBoardList(BoardListDTO boardList) {
        this.boardList = boardList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CardDTO)) {
            return false;
        }

        CardDTO cardDTO = (CardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CardDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", position=" + getPosition() +
            ", dueDate='" + getDueDate() + "'" +
            ", isArchived='" + getIsArchived() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", labels=" + getLabels() +
            ", board=" + getBoard() +
            ", boardList=" + getBoardList() +
            "}";
    }
}
