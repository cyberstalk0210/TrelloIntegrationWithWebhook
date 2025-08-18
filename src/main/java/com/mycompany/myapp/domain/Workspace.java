package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Workspace.
 */
@Entity
@Table(name = "workspace")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Workspace implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "trello_id", nullable = false)
    private String trelloId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "workspace")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "lists", "cards", "workspace" }, allowSetters = true)
    private Set<Board> boards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Workspace id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Workspace name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrelloId() {
        return this.trelloId;
    }

    public Workspace trelloId(String trelloId) {
        this.setTrelloId(trelloId);
        return this;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    public Set<Board> getBoards() {
        return this.boards;
    }

    public void setBoards(Set<Board> boards) {
        if (this.boards != null) {
            this.boards.forEach(i -> i.setWorkspace(null));
        }
        if (boards != null) {
            boards.forEach(i -> i.setWorkspace(this));
        }
        this.boards = boards;
    }

    public Workspace boards(Set<Board> boards) {
        this.setBoards(boards);
        return this;
    }

    public Workspace addBoards(Board board) {
        this.boards.add(board);
        board.setWorkspace(this);
        return this;
    }

    public Workspace removeBoards(Board board) {
        this.boards.remove(board);
        board.setWorkspace(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workspace)) {
            return false;
        }
        return getId() != null && getId().equals(((Workspace) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Workspace{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            "}";
    }
}
