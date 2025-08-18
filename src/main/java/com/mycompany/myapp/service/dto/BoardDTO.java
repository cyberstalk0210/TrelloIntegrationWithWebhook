package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.enumeration.BoardVisibility;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Board} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BoardDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;

    @Lob
    private String description;

    @NotNull
    private BoardVisibility visibility;

    @NotNull
    private String trelloId;

    @NotNull
    private Instant createdAt;

    private String createdBy;

    private WorkspaceDTO workspace;

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

    public BoardVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(BoardVisibility visibility) {
        this.visibility = visibility;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public WorkspaceDTO getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceDTO workspace) {
        this.workspace = workspace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardDTO)) {
            return false;
        }

        BoardDTO boardDTO = (BoardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, boardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoardDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", visibility='" + getVisibility() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", workspace=" + getWorkspace() +
            "}";
    }
}
