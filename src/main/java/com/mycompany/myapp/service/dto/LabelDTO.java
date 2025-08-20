package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Label} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LabelDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTrelloId() {
        return trelloId;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LabelDTO)) return false;
        LabelDTO labelDTO = (LabelDTO) o;
        if (this.id == null) return false;
        return Objects.equals(this.id, labelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "LabelDTO{" +
            "id=" +
            getId() +
            ", name='" +
            getName() +
            "'" +
            ", color='" +
            getColor() +
            "'" +
            ", trelloId='" +
            getTrelloId() +
            "'" +
            "}"
        );
    }
}
