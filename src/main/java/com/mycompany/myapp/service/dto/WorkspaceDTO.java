package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Workspace} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WorkspaceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String trelloId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrelloId() {
        return trelloId;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkspaceDTO)) {
            return false;
        }

        WorkspaceDTO workspaceDTO = (WorkspaceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, workspaceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkspaceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            "}";
    }
}
