package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.BoardListAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.BoardList;
import com.mycompany.myapp.repository.BoardListRepository;
import com.mycompany.myapp.service.dto.BoardListDTO;
import com.mycompany.myapp.service.mapper.BoardListMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BoardListResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BoardListResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final String DEFAULT_TRELLO_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRELLO_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/board-lists";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BoardListRepository boardListRepository;

    @Autowired
    private BoardListMapper boardListMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoardListMockMvc;

    private BoardList boardList;

    private BoardList insertedBoardList;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoardList createEntity() {
        return new BoardList().name(DEFAULT_NAME).position(DEFAULT_POSITION).trelloId(DEFAULT_TRELLO_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BoardList createUpdatedEntity() {
        return new BoardList().name(UPDATED_NAME).position(UPDATED_POSITION).trelloId(UPDATED_TRELLO_ID);
    }

    @BeforeEach
    void initTest() {
        boardList = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedBoardList != null) {
            boardListRepository.delete(insertedBoardList);
            insertedBoardList = null;
        }
    }

    @Test
    @Transactional
    void createBoardList() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);
        var returnedBoardListDTO = om.readValue(
            restBoardListMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardListDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BoardListDTO.class
        );

        // Validate the BoardList in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBoardList = boardListMapper.toEntity(returnedBoardListDTO);
        assertBoardListUpdatableFieldsEquals(returnedBoardList, getPersistedBoardList(returnedBoardList));

        insertedBoardList = returnedBoardList;
    }

    @Test
    @Transactional
    void createBoardListWithExistingId() throws Exception {
        // Create the BoardList with an existing ID
        boardList.setId(1L);
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoardListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        boardList.setName(null);

        // Create the BoardList, which fails.
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        restBoardListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardListDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrelloIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        boardList.setTrelloId(null);

        // Create the BoardList, which fails.
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        restBoardListMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardListDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBoardLists() throws Exception {
        // Initialize the database
        insertedBoardList = boardListRepository.saveAndFlush(boardList);

        // Get all the boardListList
        restBoardListMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(boardList.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].trelloId").value(hasItem(DEFAULT_TRELLO_ID)));
    }

    @Test
    @Transactional
    void getBoardList() throws Exception {
        // Initialize the database
        insertedBoardList = boardListRepository.saveAndFlush(boardList);

        // Get the boardList
        restBoardListMockMvc
            .perform(get(ENTITY_API_URL_ID, boardList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(boardList.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.trelloId").value(DEFAULT_TRELLO_ID));
    }

    @Test
    @Transactional
    void getNonExistingBoardList() throws Exception {
        // Get the boardList
        restBoardListMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBoardList() throws Exception {
        // Initialize the database
        insertedBoardList = boardListRepository.saveAndFlush(boardList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boardList
        BoardList updatedBoardList = boardListRepository.findById(boardList.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBoardList are not directly saved in db
        em.detach(updatedBoardList);
        updatedBoardList.name(UPDATED_NAME).position(UPDATED_POSITION).trelloId(UPDATED_TRELLO_ID);
        BoardListDTO boardListDTO = boardListMapper.toDto(updatedBoardList);

        restBoardListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, boardListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(boardListDTO))
            )
            .andExpect(status().isOk());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBoardListToMatchAllProperties(updatedBoardList);
    }

    @Test
    @Transactional
    void putNonExistingBoardList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boardList.setId(longCount.incrementAndGet());

        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoardListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, boardListDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(boardListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBoardList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boardList.setId(longCount.incrementAndGet());

        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardListMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(boardListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBoardList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boardList.setId(longCount.incrementAndGet());

        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardListMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(boardListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBoardListWithPatch() throws Exception {
        // Initialize the database
        insertedBoardList = boardListRepository.saveAndFlush(boardList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boardList using partial update
        BoardList partialUpdatedBoardList = new BoardList();
        partialUpdatedBoardList.setId(boardList.getId());

        partialUpdatedBoardList.position(UPDATED_POSITION);

        restBoardListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoardList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoardList))
            )
            .andExpect(status().isOk());

        // Validate the BoardList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoardListUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedBoardList, boardList),
            getPersistedBoardList(boardList)
        );
    }

    @Test
    @Transactional
    void fullUpdateBoardListWithPatch() throws Exception {
        // Initialize the database
        insertedBoardList = boardListRepository.saveAndFlush(boardList);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the boardList using partial update
        BoardList partialUpdatedBoardList = new BoardList();
        partialUpdatedBoardList.setId(boardList.getId());

        partialUpdatedBoardList.name(UPDATED_NAME).position(UPDATED_POSITION).trelloId(UPDATED_TRELLO_ID);

        restBoardListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBoardList.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBoardList))
            )
            .andExpect(status().isOk());

        // Validate the BoardList in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBoardListUpdatableFieldsEquals(partialUpdatedBoardList, getPersistedBoardList(partialUpdatedBoardList));
    }

    @Test
    @Transactional
    void patchNonExistingBoardList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boardList.setId(longCount.incrementAndGet());

        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoardListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, boardListDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(boardListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBoardList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boardList.setId(longCount.incrementAndGet());

        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardListMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(boardListDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBoardList() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        boardList.setId(longCount.incrementAndGet());

        // Create the BoardList
        BoardListDTO boardListDTO = boardListMapper.toDto(boardList);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBoardListMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(boardListDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BoardList in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBoardList() throws Exception {
        // Initialize the database
        insertedBoardList = boardListRepository.saveAndFlush(boardList);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the boardList
        restBoardListMockMvc
            .perform(delete(ENTITY_API_URL_ID, boardList.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return boardListRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected BoardList getPersistedBoardList(BoardList boardList) {
        return boardListRepository.findById(boardList.getId()).orElseThrow();
    }

    protected void assertPersistedBoardListToMatchAllProperties(BoardList expectedBoardList) {
        assertBoardListAllPropertiesEquals(expectedBoardList, getPersistedBoardList(expectedBoardList));
    }

    protected void assertPersistedBoardListToMatchUpdatableProperties(BoardList expectedBoardList) {
        assertBoardListAllUpdatablePropertiesEquals(expectedBoardList, getPersistedBoardList(expectedBoardList));
    }
}
