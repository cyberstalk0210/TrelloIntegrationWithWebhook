package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.BoardVisibility;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Board.
 */
@Entity
@Table(name = "board")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "visibility", nullable = false)
    private BoardVisibility visibility;

    @NotNull
    @Column(name = "trello_id", nullable = false)
    private String trelloId;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cards", "board" }, allowSetters = true)
    private Set<BoardList> lists = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "attachments", "labels", "board", "boardList" }, allowSetters = true)
    private Set<Card> cards = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "boards" }, allowSetters = true)
    private Workspace workspace;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Board id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Board title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Board description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BoardVisibility getVisibility() {
        return this.visibility;
    }

    public Board visibility(BoardVisibility visibility) {
        this.setVisibility(visibility);
        return this;
    }

    public void setVisibility(BoardVisibility visibility) {
        this.visibility = visibility;
    }

    public String getTrelloId() {
        return this.trelloId;
    }

    public Board trelloId(String trelloId) {
        this.setTrelloId(trelloId);
        return this;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Board createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Board createdBy(String createdBy) {
        this.setCreatedBy(createdBy);
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Set<BoardList> getLists() {
        return this.lists;
    }

    public void setLists(Set<BoardList> boardLists) {
        if (this.lists != null) {
            this.lists.forEach(i -> i.setBoard(null));
        }
        if (boardLists != null) {
            boardLists.forEach(i -> i.setBoard(this));
        }
        this.lists = boardLists;
    }

    public Board lists(Set<BoardList> boardLists) {
        this.setLists(boardLists);
        return this;
    }

    public Board addLists(BoardList boardList) {
        this.lists.add(boardList);
        boardList.setBoard(this);
        return this;
    }

    public Board removeLists(BoardList boardList) {
        this.lists.remove(boardList);
        boardList.setBoard(null);
        return this;
    }

    public Set<Card> getCards() {
        return this.cards;
    }

    public void setCards(Set<Card> cards) {
        if (this.cards != null) {
            this.cards.forEach(i -> i.setBoard(null));
        }
        if (cards != null) {
            cards.forEach(i -> i.setBoard(this));
        }
        this.cards = cards;
    }

    public Board cards(Set<Card> cards) {
        this.setCards(cards);
        return this;
    }

    public Board addCards(Card card) {
        this.cards.add(card);
        card.setBoard(this);
        return this;
    }

    public Board removeCards(Card card) {
        this.cards.remove(card);
        card.setBoard(null);
        return this;
    }

    public Workspace getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }

    public Board workspace(Workspace workspace) {
        this.setWorkspace(workspace);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Board)) {
            return false;
        }
        return getId() != null && getId().equals(((Board) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Board{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", visibility='" + getVisibility() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
