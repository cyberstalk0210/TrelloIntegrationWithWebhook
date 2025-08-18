package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.TrelloWebhookAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TrelloWebhook;
import com.mycompany.myapp.repository.TrelloWebhookRepository;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link TrelloWebhookResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrelloWebhookResourceIT {

    private static final String DEFAULT_TRELLO_WEBHOOK_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRELLO_WEBHOOK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ID_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_ID_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_CALLBACK_URL = "AAAAAAAAAA";
    private static final String UPDATED_CALLBACK_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_LAST_RECEIVED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_RECEIVED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/trello-webhooks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TrelloWebhookRepository trelloWebhookRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrelloWebhookMockMvc;

    private TrelloWebhook trelloWebhook;

    private TrelloWebhook insertedTrelloWebhook;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrelloWebhook createEntity() {
        return new TrelloWebhook()
            .trelloWebhookId(DEFAULT_TRELLO_WEBHOOK_ID)
            .idModel(DEFAULT_ID_MODEL)
            .callbackUrl(DEFAULT_CALLBACK_URL)
            .active(DEFAULT_ACTIVE)
            .lastReceivedAt(DEFAULT_LAST_RECEIVED_AT)
            .secret(DEFAULT_SECRET);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrelloWebhook createUpdatedEntity() {
        return new TrelloWebhook()
            .trelloWebhookId(UPDATED_TRELLO_WEBHOOK_ID)
            .idModel(UPDATED_ID_MODEL)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .active(UPDATED_ACTIVE)
            .lastReceivedAt(UPDATED_LAST_RECEIVED_AT)
            .secret(UPDATED_SECRET);
    }

    @BeforeEach
    void initTest() {
        trelloWebhook = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTrelloWebhook != null) {
            trelloWebhookRepository.delete(insertedTrelloWebhook);
            insertedTrelloWebhook = null;
        }
    }

    @Test
    @Transactional
    void createTrelloWebhook() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TrelloWebhook
        var returnedTrelloWebhook = om.readValue(
            restTrelloWebhookMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trelloWebhook)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TrelloWebhook.class
        );

        // Validate the TrelloWebhook in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertTrelloWebhookUpdatableFieldsEquals(returnedTrelloWebhook, getPersistedTrelloWebhook(returnedTrelloWebhook));

        insertedTrelloWebhook = returnedTrelloWebhook;
    }

    @Test
    @Transactional
    void createTrelloWebhookWithExistingId() throws Exception {
        // Create the TrelloWebhook with an existing ID
        trelloWebhook.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrelloWebhookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trelloWebhook)))
            .andExpect(status().isBadRequest());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTrelloWebhookIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trelloWebhook.setTrelloWebhookId(null);

        // Create the TrelloWebhook, which fails.

        restTrelloWebhookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trelloWebhook)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdModelIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trelloWebhook.setIdModel(null);

        // Create the TrelloWebhook, which fails.

        restTrelloWebhookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trelloWebhook)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCallbackUrlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        trelloWebhook.setCallbackUrl(null);

        // Create the TrelloWebhook, which fails.

        restTrelloWebhookMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trelloWebhook)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrelloWebhooks() throws Exception {
        // Initialize the database
        insertedTrelloWebhook = trelloWebhookRepository.saveAndFlush(trelloWebhook);

        // Get all the trelloWebhookList
        restTrelloWebhookMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trelloWebhook.getId().intValue())))
            .andExpect(jsonPath("$.[*].trelloWebhookId").value(hasItem(DEFAULT_TRELLO_WEBHOOK_ID)))
            .andExpect(jsonPath("$.[*].idModel").value(hasItem(DEFAULT_ID_MODEL)))
            .andExpect(jsonPath("$.[*].callbackUrl").value(hasItem(DEFAULT_CALLBACK_URL)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE)))
            .andExpect(jsonPath("$.[*].lastReceivedAt").value(hasItem(DEFAULT_LAST_RECEIVED_AT.toString())))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET)));
    }

    @Test
    @Transactional
    void getTrelloWebhook() throws Exception {
        // Initialize the database
        insertedTrelloWebhook = trelloWebhookRepository.saveAndFlush(trelloWebhook);

        // Get the trelloWebhook
        restTrelloWebhookMockMvc
            .perform(get(ENTITY_API_URL_ID, trelloWebhook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trelloWebhook.getId().intValue()))
            .andExpect(jsonPath("$.trelloWebhookId").value(DEFAULT_TRELLO_WEBHOOK_ID))
            .andExpect(jsonPath("$.idModel").value(DEFAULT_ID_MODEL))
            .andExpect(jsonPath("$.callbackUrl").value(DEFAULT_CALLBACK_URL))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE))
            .andExpect(jsonPath("$.lastReceivedAt").value(DEFAULT_LAST_RECEIVED_AT.toString()))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET));
    }

    @Test
    @Transactional
    void getNonExistingTrelloWebhook() throws Exception {
        // Get the trelloWebhook
        restTrelloWebhookMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrelloWebhook() throws Exception {
        // Initialize the database
        insertedTrelloWebhook = trelloWebhookRepository.saveAndFlush(trelloWebhook);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trelloWebhook
        TrelloWebhook updatedTrelloWebhook = trelloWebhookRepository.findById(trelloWebhook.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTrelloWebhook are not directly saved in db
        em.detach(updatedTrelloWebhook);
        updatedTrelloWebhook
            .trelloWebhookId(UPDATED_TRELLO_WEBHOOK_ID)
            .idModel(UPDATED_ID_MODEL)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .active(UPDATED_ACTIVE)
            .lastReceivedAt(UPDATED_LAST_RECEIVED_AT)
            .secret(UPDATED_SECRET);

        restTrelloWebhookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTrelloWebhook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedTrelloWebhook))
            )
            .andExpect(status().isOk());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTrelloWebhookToMatchAllProperties(updatedTrelloWebhook);
    }

    @Test
    @Transactional
    void putNonExistingTrelloWebhook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trelloWebhook.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrelloWebhookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trelloWebhook.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trelloWebhook))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrelloWebhook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trelloWebhook.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrelloWebhookMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(trelloWebhook))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrelloWebhook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trelloWebhook.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrelloWebhookMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(trelloWebhook)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrelloWebhookWithPatch() throws Exception {
        // Initialize the database
        insertedTrelloWebhook = trelloWebhookRepository.saveAndFlush(trelloWebhook);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trelloWebhook using partial update
        TrelloWebhook partialUpdatedTrelloWebhook = new TrelloWebhook();
        partialUpdatedTrelloWebhook.setId(trelloWebhook.getId());

        partialUpdatedTrelloWebhook.callbackUrl(UPDATED_CALLBACK_URL).lastReceivedAt(UPDATED_LAST_RECEIVED_AT);

        restTrelloWebhookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrelloWebhook.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrelloWebhook))
            )
            .andExpect(status().isOk());

        // Validate the TrelloWebhook in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrelloWebhookUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTrelloWebhook, trelloWebhook),
            getPersistedTrelloWebhook(trelloWebhook)
        );
    }

    @Test
    @Transactional
    void fullUpdateTrelloWebhookWithPatch() throws Exception {
        // Initialize the database
        insertedTrelloWebhook = trelloWebhookRepository.saveAndFlush(trelloWebhook);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the trelloWebhook using partial update
        TrelloWebhook partialUpdatedTrelloWebhook = new TrelloWebhook();
        partialUpdatedTrelloWebhook.setId(trelloWebhook.getId());

        partialUpdatedTrelloWebhook
            .trelloWebhookId(UPDATED_TRELLO_WEBHOOK_ID)
            .idModel(UPDATED_ID_MODEL)
            .callbackUrl(UPDATED_CALLBACK_URL)
            .active(UPDATED_ACTIVE)
            .lastReceivedAt(UPDATED_LAST_RECEIVED_AT)
            .secret(UPDATED_SECRET);

        restTrelloWebhookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrelloWebhook.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTrelloWebhook))
            )
            .andExpect(status().isOk());

        // Validate the TrelloWebhook in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTrelloWebhookUpdatableFieldsEquals(partialUpdatedTrelloWebhook, getPersistedTrelloWebhook(partialUpdatedTrelloWebhook));
    }

    @Test
    @Transactional
    void patchNonExistingTrelloWebhook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trelloWebhook.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrelloWebhookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trelloWebhook.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trelloWebhook))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrelloWebhook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trelloWebhook.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrelloWebhookMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(trelloWebhook))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrelloWebhook() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        trelloWebhook.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrelloWebhookMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(trelloWebhook)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrelloWebhook in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrelloWebhook() throws Exception {
        // Initialize the database
        insertedTrelloWebhook = trelloWebhookRepository.saveAndFlush(trelloWebhook);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the trelloWebhook
        restTrelloWebhookMockMvc
            .perform(delete(ENTITY_API_URL_ID, trelloWebhook.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return trelloWebhookRepository.count();
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

    protected TrelloWebhook getPersistedTrelloWebhook(TrelloWebhook trelloWebhook) {
        return trelloWebhookRepository.findById(trelloWebhook.getId()).orElseThrow();
    }

    protected void assertPersistedTrelloWebhookToMatchAllProperties(TrelloWebhook expectedTrelloWebhook) {
        assertTrelloWebhookAllPropertiesEquals(expectedTrelloWebhook, getPersistedTrelloWebhook(expectedTrelloWebhook));
    }

    protected void assertPersistedTrelloWebhookToMatchUpdatableProperties(TrelloWebhook expectedTrelloWebhook) {
        assertTrelloWebhookAllUpdatablePropertiesEquals(expectedTrelloWebhook, getPersistedTrelloWebhook(expectedTrelloWebhook));
    }
}
