package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.service.BoardListService;
import com.mycompany.myapp.service.dto.BoardListDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.BoardList}.
 */
@RestController
@RequestMapping("/api/board-lists")
public class BoardListResource {

    private static final Logger LOG = LoggerFactory.getLogger(BoardListResource.class);

    private static final String ENTITY_NAME = "boardList";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoardListService boardListService;

    private final BoardListRepository boardListRepository;

    public BoardListResource(BoardListService boardListService, BoardListRepository boardListRepository) {
        this.boardListService = boardListService;
        this.boardListRepository = boardListRepository;
    }

    /**
     * {@code POST  /board-lists} : Create a new boardList.
     *
     * @param boardListDTO the boardListDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boardListDTO, or with status {@code 400 (Bad Request)} if the boardList has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<BoardListDTO> createBoardList(@Valid @RequestBody BoardListDTO boardListDTO) throws URISyntaxException {
        LOG.debug("REST request to save BoardList : {}", boardListDTO);
        if (boardListDTO.getId() != null) {
            throw new BadRequestAlertException("A new boardList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        boardListDTO = boardListService.save(boardListDTO);
        return ResponseEntity.created(new URI("/api/board-lists/" + boardListDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, boardListDTO.getId().toString()))
            .body(boardListDTO);
    }

    /**
     * {@code PUT  /board-lists/:id} : Updates an existing boardList.
     *
     * @param id the id of the boardListDTO to save.
     * @param boardListDTO the boardListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boardListDTO,
     * or with status {@code 400 (Bad Request)} if the boardListDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boardListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BoardListDTO> updateBoardList(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody BoardListDTO boardListDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update BoardList : {}, {}", id, boardListDTO);
        if (boardListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boardListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boardListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        boardListDTO = boardListService.update(boardListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boardListDTO.getId().toString()))
            .body(boardListDTO);
    }

    /**
     * {@code PATCH  /board-lists/:id} : Partial updates given fields of an existing boardList, field will ignore if it is null
     *
     * @param id the id of the boardListDTO to save.
     * @param boardListDTO the boardListDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boardListDTO,
     * or with status {@code 400 (Bad Request)} if the boardListDTO is not valid,
     * or with status {@code 404 (Not Found)} if the boardListDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the boardListDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<BoardListDTO> partialUpdateBoardList(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody BoardListDTO boardListDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update BoardList partially : {}, {}", id, boardListDTO);
        if (boardListDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, boardListDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!boardListRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<BoardListDTO> result = boardListService.partialUpdate(boardListDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boardListDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /board-lists} : get all the boardLists.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boardLists in body.
     */
    @GetMapping("")
    public List<BoardListDTO> getAllBoardLists() {
        LOG.debug("REST request to get all BoardLists");
        return boardListService.findAll();
    }

    /**
     * {@code GET  /board-lists/:id} : get the "id" boardList.
     *
     * @param id the id of the boardListDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boardListDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BoardListDTO> getBoardList(@PathVariable("id") Long id) {
        LOG.debug("REST request to get BoardList : {}", id);
        Optional<BoardListDTO> boardListDTO = boardListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boardListDTO);
    }

    /**
     * {@code DELETE  /board-lists/:id} : delete the "id" boardList.
     *
     * @param id the id of the boardListDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardList(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete BoardList : {}", id);
        boardListService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
