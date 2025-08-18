package com.mycompany.myapp.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TrelloWebhook.
 */
@Entity
@Table(name = "trello_webhook")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrelloWebhook implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "trello_webhook_id", nullable = false)
    private String trelloWebhookId;

    @NotNull
    @Column(name = "id_model", nullable = false)
    private String idModel;

    @NotNull
    @Column(name = "callback_url", nullable = false)
    private String callbackUrl;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "last_received_at")
    private Instant lastReceivedAt;

    @Column(name = "secret")
    private String secret;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrelloWebhook id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrelloWebhookId() {
        return this.trelloWebhookId;
    }

    public TrelloWebhook trelloWebhookId(String trelloWebhookId) {
        this.setTrelloWebhookId(trelloWebhookId);
        return this;
    }

    public void setTrelloWebhookId(String trelloWebhookId) {
        this.trelloWebhookId = trelloWebhookId;
    }

    public String getIdModel() {
        return this.idModel;
    }

    public TrelloWebhook idModel(String idModel) {
        this.setIdModel(idModel);
        return this;
    }

    public void setIdModel(String idModel) {
        this.idModel = idModel;
    }

    public String getCallbackUrl() {
        return this.callbackUrl;
    }

    public TrelloWebhook callbackUrl(String callbackUrl) {
        this.setCallbackUrl(callbackUrl);
        return this;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public Boolean getActive() {
        return this.active;
    }

    public TrelloWebhook active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Instant getLastReceivedAt() {
        return this.lastReceivedAt;
    }

    public TrelloWebhook lastReceivedAt(Instant lastReceivedAt) {
        this.setLastReceivedAt(lastReceivedAt);
        return this;
    }

    public void setLastReceivedAt(Instant lastReceivedAt) {
        this.lastReceivedAt = lastReceivedAt;
    }

    public String getSecret() {
        return this.secret;
    }

    public TrelloWebhook secret(String secret) {
        this.setSecret(secret);
        return this;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrelloWebhook)) {
            return false;
        }
        return getId() != null && getId().equals(((TrelloWebhook) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrelloWebhook{" +
            "id=" + getId() +
            ", trelloWebhookId='" + getTrelloWebhookId() + "'" +
            ", idModel='" + getIdModel() + "'" +
            ", callbackUrl='" + getCallbackUrl() + "'" +
            ", active='" + getActive() + "'" +
            ", lastReceivedAt='" + getLastReceivedAt() + "'" +
            ", secret='" + getSecret() + "'" +
            "}";
    }
}
