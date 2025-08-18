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
 * A BoardList.
 */
@Entity
@Table(name = "board_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BoardList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position")
    private Integer position;

    @NotNull
    @Column(name = "trello_id", nullable = false)
    private String trelloId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "boardList")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "attachments", "labels", "board", "boardList" }, allowSetters = true)
    private Set<Card> cards = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "lists", "cards", "workspace" }, allowSetters = true)
    private Board board;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BoardList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public BoardList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPosition() {
        return this.position;
    }

    public BoardList position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getTrelloId() {
        return this.trelloId;
    }

    public BoardList trelloId(String trelloId) {
        this.setTrelloId(trelloId);
        return this;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    public Set<Card> getCards() {
        return this.cards;
    }

    public void setCards(Set<Card> cards) {
        if (this.cards != null) {
            this.cards.forEach(i -> i.setBoardList(null));
        }
        if (cards != null) {
            cards.forEach(i -> i.setBoardList(this));
        }
        this.cards = cards;
    }

    public BoardList cards(Set<Card> cards) {
        this.setCards(cards);
        return this;
    }

    public BoardList addCards(Card card) {
        this.cards.add(card);
        card.setBoardList(this);
        return this;
    }

    public BoardList removeCards(Card card) {
        this.cards.remove(card);
        card.setBoardList(null);
        return this;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public BoardList board(Board board) {
        this.setBoard(board);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardList)) {
            return false;
        }
        return getId() != null && getId().equals(((BoardList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BoardList{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", position=" + getPosition() +
            ", trelloId='" + getTrelloId() + "'" +
            "}";
    }
}
