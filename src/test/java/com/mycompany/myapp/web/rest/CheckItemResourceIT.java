package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CheckItemAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CheckItem;
import com.mycompany.myapp.domain.enumeration.CheckItemState;
import com.mycompany.myapp.repository.CheckItemRepository;
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
 * Integration tests for the {@link CheckItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CheckItemResourceIT {

    private static final String DEFAULT_CHECK_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final CheckItemState DEFAULT_STATE = CheckItemState.INCOMPLETE;
    private static final CheckItemState UPDATED_STATE = CheckItemState.INCOMPLETE;

    private static final String ENTITY_API_URL = "/api/check-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CheckItemRepository checkItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckItemMockMvc;

    private CheckItem checkItem;

    private CheckItem insertedCheckItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckItem createEntity() {
        return new CheckItem().checkItemId(DEFAULT_CHECK_ITEM_ID).name(DEFAULT_NAME).state(DEFAULT_STATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckItem createUpdatedEntity() {
        return new CheckItem().checkItemId(UPDATED_CHECK_ITEM_ID).name(UPDATED_NAME).state(UPDATED_STATE);
    }

    @BeforeEach
    void initTest() {
        checkItem = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCheckItem != null) {
            checkItemRepository.delete(insertedCheckItem);
            insertedCheckItem = null;
        }
    }

    @Test
    @Transactional
    void createCheckItem() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CheckItem
        var returnedCheckItem = om.readValue(
            restCheckItemMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkItem)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CheckItem.class
        );

        // Validate the CheckItem in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertCheckItemUpdatableFieldsEquals(returnedCheckItem, getPersistedCheckItem(returnedCheckItem));

        insertedCheckItem = returnedCheckItem;
    }

    @Test
    @Transactional
    void createCheckItemWithExistingId() throws Exception {
        // Create the CheckItem with an existing ID
        checkItem.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkItem)))
            .andExpect(status().isBadRequest());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCheckItems() throws Exception {
        // Initialize the database
        insertedCheckItem = checkItemRepository.saveAndFlush(checkItem);

        // Get all the checkItemList
        restCheckItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkItemId").value(hasItem(DEFAULT_CHECK_ITEM_ID)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE.toString())));
    }

    @Test
    @Transactional
    void getCheckItem() throws Exception {
        // Initialize the database
        insertedCheckItem = checkItemRepository.saveAndFlush(checkItem);

        // Get the checkItem
        restCheckItemMockMvc
            .perform(get(ENTITY_API_URL_ID, checkItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkItem.getId().intValue()))
            .andExpect(jsonPath("$.checkItemId").value(DEFAULT_CHECK_ITEM_ID))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCheckItem() throws Exception {
        // Get the checkItem
        restCheckItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCheckItem() throws Exception {
        // Initialize the database
        insertedCheckItem = checkItemRepository.saveAndFlush(checkItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the checkItem
        CheckItem updatedCheckItem = checkItemRepository.findById(checkItem.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCheckItem are not directly saved in db
        em.detach(updatedCheckItem);
        updatedCheckItem.checkItemId(UPDATED_CHECK_ITEM_ID).name(UPDATED_NAME).state(UPDATED_STATE);

        restCheckItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCheckItem.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedCheckItem))
            )
            .andExpect(status().isOk());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCheckItemToMatchAllProperties(updatedCheckItem);
    }

    @Test
    @Transactional
    void putNonExistingCheckItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkItem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkItem.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(checkItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(checkItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCheckItemWithPatch() throws Exception {
        // Initialize the database
        insertedCheckItem = checkItemRepository.saveAndFlush(checkItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the checkItem using partial update
        CheckItem partialUpdatedCheckItem = new CheckItem();
        partialUpdatedCheckItem.setId(checkItem.getId());

        partialUpdatedCheckItem.checkItemId(UPDATED_CHECK_ITEM_ID).name(UPDATED_NAME);

        restCheckItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCheckItem))
            )
            .andExpect(status().isOk());

        // Validate the CheckItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCheckItemUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCheckItem, checkItem),
            getPersistedCheckItem(checkItem)
        );
    }

    @Test
    @Transactional
    void fullUpdateCheckItemWithPatch() throws Exception {
        // Initialize the database
        insertedCheckItem = checkItemRepository.saveAndFlush(checkItem);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the checkItem using partial update
        CheckItem partialUpdatedCheckItem = new CheckItem();
        partialUpdatedCheckItem.setId(checkItem.getId());

        partialUpdatedCheckItem.checkItemId(UPDATED_CHECK_ITEM_ID).name(UPDATED_NAME).state(UPDATED_STATE);

        restCheckItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCheckItem))
            )
            .andExpect(status().isOk());

        // Validate the CheckItem in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCheckItemUpdatableFieldsEquals(partialUpdatedCheckItem, getPersistedCheckItem(partialUpdatedCheckItem));
    }

    @Test
    @Transactional
    void patchNonExistingCheckItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkItem.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(checkItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(checkItem))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckItem() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        checkItem.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckItemMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(checkItem)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckItem in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCheckItem() throws Exception {
        // Initialize the database
        insertedCheckItem = checkItemRepository.saveAndFlush(checkItem);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the checkItem
        restCheckItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return checkItemRepository.count();
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

    protected CheckItem getPersistedCheckItem(CheckItem checkItem) {
        return checkItemRepository.findById(checkItem.getId()).orElseThrow();
    }

    protected void assertPersistedCheckItemToMatchAllProperties(CheckItem expectedCheckItem) {
        assertCheckItemAllPropertiesEquals(expectedCheckItem, getPersistedCheckItem(expectedCheckItem));
    }

    protected void assertPersistedCheckItemToMatchUpdatableProperties(CheckItem expectedCheckItem) {
        assertCheckItemAllUpdatablePropertiesEquals(expectedCheckItem, getPersistedCheckItem(expectedCheckItem));
    }
}
