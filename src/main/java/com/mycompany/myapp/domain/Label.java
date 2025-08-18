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
 * A Label.
 */
@Entity
@Table(name = "label")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "trello_id")
    private String trelloId;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "labels")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "comments", "attachments", "labels", "board", "boardList" }, allowSetters = true)
    private Set<Card> cards = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Label id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Label name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public Label color(String color) {
        this.setColor(color);
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTrelloId() {
        return this.trelloId;
    }

    public Label trelloId(String trelloId) {
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
            this.cards.forEach(i -> i.removeLabels(this));
        }
        if (cards != null) {
            cards.forEach(i -> i.addLabels(this));
        }
        this.cards = cards;
    }

    public Label cards(Set<Card> cards) {
        this.setCards(cards);
        return this;
    }

    public Label addCards(Card card) {
        this.cards.add(card);
        card.getLabels().add(this);
        return this;
    }

    public Label removeCards(Card card) {
        this.cards.remove(card);
        card.getLabels().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Label)) {
            return false;
        }
        return getId() != null && getId().equals(((Label) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Label{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", color='" + getColor() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            "}";
    }
}
