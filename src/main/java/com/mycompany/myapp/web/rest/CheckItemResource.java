package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CheckItem;
import com.mycompany.myapp.repository.CheckItemRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CheckItem}.
 */
@RestController
@RequestMapping("/api/check-items")
@Transactional
public class CheckItemResource {

    private static final Logger LOG = LoggerFactory.getLogger(CheckItemResource.class);

    private static final String ENTITY_NAME = "checkItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckItemRepository checkItemRepository;

    public CheckItemResource(CheckItemRepository checkItemRepository) {
        this.checkItemRepository = checkItemRepository;
    }

    /**
     * {@code POST  /check-items} : Create a new checkItem.
     *
     * @param checkItem the checkItem to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkItem, or with status {@code 400 (Bad Request)} if the checkItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CheckItem> createCheckItem(@RequestBody CheckItem checkItem) throws URISyntaxException {
        LOG.debug("REST request to save CheckItem : {}", checkItem);
        if (checkItem.getId() != null) {
            throw new BadRequestAlertException("A new checkItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkItem = checkItemRepository.save(checkItem);
        return ResponseEntity.created(new URI("/api/check-items/" + checkItem.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, checkItem.getId().toString()))
            .body(checkItem);
    }

    /**
     * {@code PUT  /check-items/:id} : Updates an existing checkItem.
     *
     * @param id the id of the checkItem to save.
     * @param checkItem the checkItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkItem,
     * or with status {@code 400 (Bad Request)} if the checkItem is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CheckItem> updateCheckItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckItem checkItem
    ) throws URISyntaxException {
        LOG.debug("REST request to update CheckItem : {}, {}", id, checkItem);
        if (checkItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        checkItem = checkItemRepository.save(checkItem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkItem.getId().toString()))
            .body(checkItem);
    }

    /**
     * {@code PATCH  /check-items/:id} : Partial updates given fields of an existing checkItem, field will ignore if it is null
     *
     * @param id the id of the checkItem to save.
     * @param checkItem the checkItem to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkItem,
     * or with status {@code 400 (Bad Request)} if the checkItem is not valid,
     * or with status {@code 404 (Not Found)} if the checkItem is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkItem couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CheckItem> partialUpdateCheckItem(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckItem checkItem
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CheckItem partially : {}, {}", id, checkItem);
        if (checkItem.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkItem.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckItem> result = checkItemRepository
            .findById(checkItem.getId())
            .map(existingCheckItem -> {
                if (checkItem.getCheckItemId() != null) {
                    existingCheckItem.setCheckItemId(checkItem.getCheckItemId());
                }
                if (checkItem.getName() != null) {
                    existingCheckItem.setName(checkItem.getName());
                }
                if (checkItem.getState() != null) {
                    existingCheckItem.setState(checkItem.getState());
                }

                return existingCheckItem;
            })
            .map(checkItemRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkItem.getId().toString())
        );
    }

    /**
     * {@code GET  /check-items} : get all the checkItems.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkItems in body.
     */
    @GetMapping("")
    public List<CheckItem> getAllCheckItems() {
        LOG.debug("REST request to get all CheckItems");
        return checkItemRepository.findAll();
    }

    /**
     * {@code GET  /check-items/:id} : get the "id" checkItem.
     *
     * @param id the id of the checkItem to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkItem, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CheckItem> getCheckItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CheckItem : {}", id);
        Optional<CheckItem> checkItem = checkItemRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(checkItem);
    }

    /**
     * {@code DELETE  /check-items/:id} : delete the "id" checkItem.
     *
     * @param id the id of the checkItem to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckItem(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CheckItem : {}", id);
        checkItemRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
