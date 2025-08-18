package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.TrelloWebhook;
import com.mycompany.myapp.repository.TrelloWebhookRepository;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TrelloWebhook}.
 */
@RestController
@RequestMapping("/api/trello-webhooks")
@Transactional
public class TrelloWebhookResource {

    private static final Logger LOG = LoggerFactory.getLogger(TrelloWebhookResource.class);

    private static final String ENTITY_NAME = "trelloWebhook";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrelloWebhookRepository trelloWebhookRepository;

    public TrelloWebhookResource(TrelloWebhookRepository trelloWebhookRepository) {
        this.trelloWebhookRepository = trelloWebhookRepository;
    }

    /**
     * {@code POST  /trello-webhooks} : Create a new trelloWebhook.
     *
     * @param trelloWebhook the trelloWebhook to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trelloWebhook, or with status {@code 400 (Bad Request)} if the trelloWebhook has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TrelloWebhook> createTrelloWebhook(@Valid @RequestBody TrelloWebhook trelloWebhook) throws URISyntaxException {
        LOG.debug("REST request to save TrelloWebhook : {}", trelloWebhook);
        if (trelloWebhook.getId() != null) {
            throw new BadRequestAlertException("A new trelloWebhook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        trelloWebhook = trelloWebhookRepository.save(trelloWebhook);
        return ResponseEntity.created(new URI("/api/trello-webhooks/" + trelloWebhook.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, trelloWebhook.getId().toString()))
            .body(trelloWebhook);
    }

    /**
     * {@code PUT  /trello-webhooks/:id} : Updates an existing trelloWebhook.
     *
     * @param id the id of the trelloWebhook to save.
     * @param trelloWebhook the trelloWebhook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trelloWebhook,
     * or with status {@code 400 (Bad Request)} if the trelloWebhook is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trelloWebhook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrelloWebhook> updateTrelloWebhook(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrelloWebhook trelloWebhook
    ) throws URISyntaxException {
        LOG.debug("REST request to update TrelloWebhook : {}, {}", id, trelloWebhook);
        if (trelloWebhook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trelloWebhook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trelloWebhookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        trelloWebhook = trelloWebhookRepository.save(trelloWebhook);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trelloWebhook.getId().toString()))
            .body(trelloWebhook);
    }

    /**
     * {@code PATCH  /trello-webhooks/:id} : Partial updates given fields of an existing trelloWebhook, field will ignore if it is null
     *
     * @param id the id of the trelloWebhook to save.
     * @param trelloWebhook the trelloWebhook to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trelloWebhook,
     * or with status {@code 400 (Bad Request)} if the trelloWebhook is not valid,
     * or with status {@code 404 (Not Found)} if the trelloWebhook is not found,
     * or with status {@code 500 (Internal Server Error)} if the trelloWebhook couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrelloWebhook> partialUpdateTrelloWebhook(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrelloWebhook trelloWebhook
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update TrelloWebhook partially : {}, {}", id, trelloWebhook);
        if (trelloWebhook.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trelloWebhook.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trelloWebhookRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrelloWebhook> result = trelloWebhookRepository
            .findById(trelloWebhook.getId())
            .map(existingTrelloWebhook -> {
                if (trelloWebhook.getTrelloWebhookId() != null) {
                    existingTrelloWebhook.setTrelloWebhookId(trelloWebhook.getTrelloWebhookId());
                }
                if (trelloWebhook.getIdModel() != null) {
                    existingTrelloWebhook.setIdModel(trelloWebhook.getIdModel());
                }
                if (trelloWebhook.getCallbackUrl() != null) {
                    existingTrelloWebhook.setCallbackUrl(trelloWebhook.getCallbackUrl());
                }
                if (trelloWebhook.getActive() != null) {
                    existingTrelloWebhook.setActive(trelloWebhook.getActive());
                }
                if (trelloWebhook.getLastReceivedAt() != null) {
                    existingTrelloWebhook.setLastReceivedAt(trelloWebhook.getLastReceivedAt());
                }
                if (trelloWebhook.getSecret() != null) {
                    existingTrelloWebhook.setSecret(trelloWebhook.getSecret());
                }

                return existingTrelloWebhook;
            })
            .map(trelloWebhookRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, trelloWebhook.getId().toString())
        );
    }

    /**
     * {@code GET  /trello-webhooks} : get all the trelloWebhooks.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trelloWebhooks in body.
     */
    @GetMapping("")
    public List<TrelloWebhook> getAllTrelloWebhooks() {
        LOG.debug("REST request to get all TrelloWebhooks");
        return trelloWebhookRepository.findAll();
    }

    /**
     * {@code GET  /trello-webhooks/:id} : get the "id" trelloWebhook.
     *
     * @param id the id of the trelloWebhook to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trelloWebhook, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrelloWebhook> getTrelloWebhook(@PathVariable("id") Long id) {
        LOG.debug("REST request to get TrelloWebhook : {}", id);
        Optional<TrelloWebhook> trelloWebhook = trelloWebhookRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(trelloWebhook);
    }

    /**
     * {@code DELETE  /trello-webhooks/:id} : delete the "id" trelloWebhook.
     *
     * @param id the id of the trelloWebhook to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrelloWebhook(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete TrelloWebhook : {}", id);
        trelloWebhookRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
