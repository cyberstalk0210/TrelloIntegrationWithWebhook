package com.mycompany.myapp.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.BoardList} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BoardListDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Integer position;

    @NotNull
    private String trelloId;

    private BoardDTO board;

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

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTrelloId() {
        return trelloId;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    public BoardDTO getBoard() {
        return board;
    }

    public void setBoard(BoardDTO board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardListDTO)) {
            return false;
        }

        BoardListDTO boardListDTO = (BoardListDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, boardListDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoardListDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", position=" + getPosition() +
            ", trelloId='" + getTrelloId() + "'" +
            ", board=" + getBoard() +
            "}";
    }
}
