package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CheckList.
 */
@Entity
@Table(name = "check_list")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "check_list_id")
    private String checkListId;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "checkList", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "data", "checkList" }, allowSetters = true)
    private Set<CheckItem> checkItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CheckList id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckListId() {
        return this.checkListId;
    }

    public CheckList checkListId(String checkListId) {
        this.setCheckListId(checkListId);
        return this;
    }

    public void setCheckListId(String checkListId) {
        this.checkListId = checkListId;
    }

    public String getName() {
        return this.name;
    }

    public CheckList name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<CheckItem> getCheckItems() {
        return this.checkItems;
    }

    public void setCheckItems(Set<CheckItem> checkItems) {
        if (this.checkItems != null) {
            this.checkItems.forEach(i -> i.setCheckList(null));
        }
        if (checkItems != null) {
            checkItems.forEach(i -> i.setCheckList(this));
        }
        this.checkItems = checkItems;
    }

    public CheckList checkItems(Set<CheckItem> checkItems) {
        this.setCheckItems(checkItems);
        return this;
    }

    public CheckList addCheckItem(CheckItem checkItem) {
        this.checkItems.add(checkItem);
        checkItem.setCheckList(this);
        return this;
    }

    public CheckList removeCheckItem(CheckItem checkItem) {
        this.checkItems.remove(checkItem);
        checkItem.setCheckList(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckList)) {
            return false;
        }
        return getId() != null && getId().equals(((CheckList) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckList{" +
            "id=" + getId() +
            ", checkListId='" + getCheckListId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
