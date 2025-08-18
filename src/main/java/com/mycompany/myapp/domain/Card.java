package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Card.
 */
@Entity
@Table(name = "card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Card implements Serializable {

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

    @Column(name = "position")
    private Integer position;

    @Column(name = "due_date")
    private Instant dueDate;

    @Column(name = "is_archived")
    private Boolean isArchived;

    @NotNull
    @Column(name = "trello_id", nullable = false)
    private String trelloId;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "card" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "card")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "card" }, allowSetters = true)
    private Set<Attachment> attachments = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_card__labels", joinColumns = @JoinColumn(name = "card_id"), inverseJoinColumns = @JoinColumn(name = "labels_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "cards" }, allowSetters = true)
    private Set<Label> labels = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "lists", "cards", "workspace" }, allowSetters = true)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "cards", "board" }, allowSetters = true)
    private BoardList boardList;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Card id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Card title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Card description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return this.position;
    }

    public Card position(Integer position) {
        this.setPosition(position);
        return this;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Instant getDueDate() {
        return this.dueDate;
    }

    public Card dueDate(Instant dueDate) {
        this.setDueDate(dueDate);
        return this;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getIsArchived() {
        return this.isArchived;
    }

    public Card isArchived(Boolean isArchived) {
        this.setIsArchived(isArchived);
        return this;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public String getTrelloId() {
        return this.trelloId;
    }

    public Card trelloId(String trelloId) {
        this.setTrelloId(trelloId);
        return this;
    }

    public void setTrelloId(String trelloId) {
        this.trelloId = trelloId;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

    public Card createdAt(Instant createdAt) {
        this.setCreatedAt(createdAt);
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return this.updatedAt;
    }

    public Card updatedAt(Instant updatedAt) {
        this.setUpdatedAt(updatedAt);
        return this;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setCard(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setCard(this));
        }
        this.comments = comments;
    }

    public Card comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Card addComments(Comment comment) {
        this.comments.add(comment);
        comment.setCard(this);
        return this;
    }

    public Card removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setCard(null);
        return this;
    }

    public Set<Attachment> getAttachments() {
        return this.attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        if (this.attachments != null) {
            this.attachments.forEach(i -> i.setCard(null));
        }
        if (attachments != null) {
            attachments.forEach(i -> i.setCard(this));
        }
        this.attachments = attachments;
    }

    public Card attachments(Set<Attachment> attachments) {
        this.setAttachments(attachments);
        return this;
    }

    public Card addAttachments(Attachment attachment) {
        this.attachments.add(attachment);
        attachment.setCard(this);
        return this;
    }

    public Card removeAttachments(Attachment attachment) {
        this.attachments.remove(attachment);
        attachment.setCard(null);
        return this;
    }

    public Set<Label> getLabels() {
        return this.labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public Card labels(Set<Label> labels) {
        this.setLabels(labels);
        return this;
    }

    public Card addLabels(Label label) {
        this.labels.add(label);
        return this;
    }

    public Card removeLabels(Label label) {
        this.labels.remove(label);
        return this;
    }

    public Board getBoard() {
        return this.board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Card board(Board board) {
        this.setBoard(board);
        return this;
    }

    public BoardList getBoardList() {
        return this.boardList;
    }

    public void setBoardList(BoardList boardList) {
        this.boardList = boardList;
    }

    public Card boardList(BoardList boardList) {
        this.setBoardList(boardList);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Card)) {
            return false;
        }
        return getId() != null && getId().equals(((Card) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Card{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", position=" + getPosition() +
            ", dueDate='" + getDueDate() + "'" +
            ", isArchived='" + getIsArchived() + "'" +
            ", trelloId='" + getTrelloId() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            "}";
    }
}
