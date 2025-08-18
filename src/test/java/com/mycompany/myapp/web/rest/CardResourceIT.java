package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CardAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Card;
import com.mycompany.myapp.repository.CardRepository;
import com.mycompany.myapp.service.CardService;
import com.mycompany.myapp.service.dto.CardDTO;
import com.mycompany.myapp.service.mapper.CardMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CardResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CardResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSITION = 1;
    private static final Integer UPDATED_POSITION = 2;

    private static final Instant DEFAULT_DUE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DUE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_ARCHIVED = false;
    private static final Boolean UPDATED_IS_ARCHIVED = true;

    private static final String DEFAULT_TRELLO_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRELLO_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/cards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CardRepository cardRepository;

    @Mock
    private CardRepository cardRepositoryMock;

    @Autowired
    private CardMapper cardMapper;

    @Mock
    private CardService cardServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardMockMvc;

    private Card card;

    private Card insertedCard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createEntity() {
        return new Card()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .position(DEFAULT_POSITION)
            .dueDate(DEFAULT_DUE_DATE)
            .isArchived(DEFAULT_IS_ARCHIVED)
            .trelloId(DEFAULT_TRELLO_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Card createUpdatedEntity() {
        return new Card()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .dueDate(UPDATED_DUE_DATE)
            .isArchived(UPDATED_IS_ARCHIVED)
            .trelloId(UPDATED_TRELLO_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
    }

    @BeforeEach
    void initTest() {
        card = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCard != null) {
            cardRepository.delete(insertedCard);
            insertedCard = null;
        }
    }

    @Test
    @Transactional
    void createCard() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);
        var returnedCardDTO = om.readValue(
            restCardMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CardDTO.class
        );

        // Validate the Card in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCard = cardMapper.toEntity(returnedCardDTO);
        assertCardUpdatableFieldsEquals(returnedCard, getPersistedCard(returnedCard));

        insertedCard = returnedCard;
    }

    @Test
    @Transactional
    void createCardWithExistingId() throws Exception {
        // Create the Card with an existing ID
        card.setId(1L);
        CardDTO cardDTO = cardMapper.toDto(card);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTitleIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        card.setTitle(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.toDto(card);

        restCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTrelloIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        card.setTrelloId(null);

        // Create the Card, which fails.
        CardDTO cardDTO = cardMapper.toDto(card);

        restCardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCards() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        // Get all the cardList
        restCardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(card.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION)))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(DEFAULT_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].isArchived").value(hasItem(DEFAULT_IS_ARCHIVED)))
            .andExpect(jsonPath("$.[*].trelloId").value(hasItem(DEFAULT_TRELLO_ID)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCardsWithEagerRelationshipsIsEnabled() throws Exception {
        when(cardServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCardMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(cardServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCardsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(cardServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCardMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(cardRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCard() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        // Get the card
        restCardMockMvc
            .perform(get(ENTITY_API_URL_ID, card.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(card.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION))
            .andExpect(jsonPath("$.dueDate").value(DEFAULT_DUE_DATE.toString()))
            .andExpect(jsonPath("$.isArchived").value(DEFAULT_IS_ARCHIVED))
            .andExpect(jsonPath("$.trelloId").value(DEFAULT_TRELLO_ID))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCard() throws Exception {
        // Get the card
        restCardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCard() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the card
        Card updatedCard = cardRepository.findById(card.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCard are not directly saved in db
        em.detach(updatedCard);
        updatedCard
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .dueDate(UPDATED_DUE_DATE)
            .isArchived(UPDATED_IS_ARCHIVED)
            .trelloId(UPDATED_TRELLO_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        CardDTO cardDTO = cardMapper.toDto(updatedCard);

        restCardMockMvc
            .perform(put(ENTITY_API_URL_ID, cardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isOk());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCardToMatchAllProperties(updatedCard);
    }

    @Test
    @Transactional
    void putNonExistingCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(longCount.incrementAndGet());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(put(ENTITY_API_URL_ID, cardDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(longCount.incrementAndGet());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(longCount.incrementAndGet());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCardWithPatch() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the card using partial update
        Card partialUpdatedCard = new Card();
        partialUpdatedCard.setId(card.getId());

        partialUpdatedCard
            .description(UPDATED_DESCRIPTION)
            .dueDate(UPDATED_DUE_DATE)
            .trelloId(UPDATED_TRELLO_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCardUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCard, card), getPersistedCard(card));
    }

    @Test
    @Transactional
    void fullUpdateCardWithPatch() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the card using partial update
        Card partialUpdatedCard = new Card();
        partialUpdatedCard.setId(card.getId());

        partialUpdatedCard
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .position(UPDATED_POSITION)
            .dueDate(UPDATED_DUE_DATE)
            .isArchived(UPDATED_IS_ARCHIVED)
            .trelloId(UPDATED_TRELLO_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);

        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCard.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCard))
            )
            .andExpect(status().isOk());

        // Validate the Card in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCardUpdatableFieldsEquals(partialUpdatedCard, getPersistedCard(partialUpdatedCard));
    }

    @Test
    @Transactional
    void patchNonExistingCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(longCount.incrementAndGet());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cardDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(longCount.incrementAndGet());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCard() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        card.setId(longCount.incrementAndGet());

        // Create the Card
        CardDTO cardDTO = cardMapper.toDto(card);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Card in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCard() throws Exception {
        // Initialize the database
        insertedCard = cardRepository.saveAndFlush(card);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the card
        restCardMockMvc
            .perform(delete(ENTITY_API_URL_ID, card.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cardRepository.count();
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

    protected Card getPersistedCard(Card card) {
        return cardRepository.findById(card.getId()).orElseThrow();
    }

    protected void assertPersistedCardToMatchAllProperties(Card expectedCard) {
        assertCardAllPropertiesEquals(expectedCard, getPersistedCard(expectedCard));
    }

    protected void assertPersistedCardToMatchUpdatableProperties(Card expectedCard) {
        assertCardAllUpdatablePropertiesEquals(expectedCard, getPersistedCard(expectedCard));
    }
}
