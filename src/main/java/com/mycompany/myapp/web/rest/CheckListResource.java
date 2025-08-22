package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.CheckList;
import com.mycompany.myapp.repository.CheckListRepository;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CheckList}.
 */
@RestController
@RequestMapping("/api/check-lists")
@Transactional
public class CheckListResource {

    private static final Logger LOG = LoggerFactory.getLogger(CheckListResource.class);

    private static final String ENTITY_NAME = "checkList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckListRepository checkListRepository;

    public CheckListResource(CheckListRepository checkListRepository) {
        this.checkListRepository = checkListRepository;
    }

    /**
     * {@code POST  /check-lists} : Create a new checkList.
     *
     * @param checkList the checkList to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkList, or with status {@code 400 (Bad Request)} if the checkList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CheckList> createCheckList(@RequestBody CheckList checkList) throws URISyntaxException {
        LOG.debug("REST request to save CheckList : {}", checkList);
        if (checkList.getId() != null) {
            throw new BadRequestAlertException("A new checkList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        checkList = checkListRepository.save(checkList);
        return ResponseEntity.created(new URI("/api/check-lists/" + checkList.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, checkList.getId().toString()))
            .body(checkList);
    }

    /**
     * {@code PUT  /check-lists/:id} : Updates an existing checkList.
     *
     * @param id the id of the checkList to save.
     * @param checkList the checkList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkList,
     * or with status {@code 400 (Bad Request)} if the checkList is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CheckList> updateCheckList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckList checkList
    ) throws URISyntaxException {
        LOG.debug("REST request to update CheckList : {}, {}", id, checkList);
        if (checkList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        checkList = checkListRepository.save(checkList);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkList.getId().toString()))
            .body(checkList);
    }

    /**
     * {@code PATCH  /check-lists/:id} : Partial updates given fields of an existing checkList, field will ignore if it is null
     *
     * @param id the id of the checkList to save.
     * @param checkList the checkList to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkList,
     * or with status {@code 400 (Bad Request)} if the checkList is not valid,
     * or with status {@code 404 (Not Found)} if the checkList is not found,
     * or with status {@code 500 (Internal Server Error)} if the checkList couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CheckList> partialUpdateCheckList(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CheckList checkList
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CheckList partially : {}, {}", id, checkList);
        if (checkList.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, checkList.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!checkListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CheckList> result = checkListRepository
            .findById(checkList.getId())
            .map(existingCheckList -> {
                if (checkList.getCheckListId() != null) {
                    existingCheckList.setCheckListId(checkList.getCheckListId());
                }
                if (checkList.getName() != null) {
                    existingCheckList.setName(checkList.getName());
                }

                return existingCheckList;
            })
            .map(checkListRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkList.getId().toString())
        );
    }

    /**
     * {@code GET  /check-lists} : get all the checkLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkLists in body.
     */
    @GetMapping("")
    public List<CheckList> getAllCheckLists() {
        LOG.debug("REST request to get all CheckLists");
        return checkListRepository.findAll();
    }

    /**
     * {@code GET  /check-lists/:id} : get the "id" checkList.
     *
     * @param id the id of the checkList to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkList, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CheckList> getCheckList(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CheckList : {}", id);
        Optional<CheckList> checkList = checkListRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(checkList);
    }

    /**
     * {@code DELETE  /check-lists/:id} : delete the "id" checkList.
     *
     * @param id the id of the checkList to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckList(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CheckList : {}", id);
        checkListRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
