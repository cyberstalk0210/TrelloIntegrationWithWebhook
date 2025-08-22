package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.CheckItemState;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckItem.
 */
@Entity
@Table(name = "check_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "check_item_id")
    private String checkItemId;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private CheckItemState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "checkItems" }, allowSetters = true)
    private CheckList checkList;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CheckItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckItemId() {
        return this.checkItemId;
    }

    public CheckItem checkItemId(String checkItemId) {
        this.setCheckItemId(checkItemId);
        return this;
    }

    public void setCheckItemId(String checkItemId) {
        this.checkItemId = checkItemId;
    }

    public String getName() {
        return this.name;
    }

    public CheckItem name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CheckItemState getState() {
        return this.state;
    }

    public CheckItem state(CheckItemState state) {
        this.setState(state);
        return this;
    }

    public void setState(CheckItemState state) {
        this.state = state;
    }

    public CheckList getCheckList() {
        return this.checkList;
    }

    public void setCheckList(CheckList checkList) {
        this.checkList = checkList;
    }

    public CheckItem checkList(CheckList checkList) {
        this.setCheckList(checkList);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckItem)) {
            return false;
        }
        return getId() != null && getId().equals(((CheckItem) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckItem{" +
            "id=" + getId() +
            ", checkItemId='" + getCheckItemId() + "'" +
            ", name='" + getName() + "'" +
            ", state='" + getState() + "'" +
            "}";
    }
}
