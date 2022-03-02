package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TranferRecieved;
import com.mycompany.myapp.domain.Transfer;
import com.mycompany.myapp.repository.TranferRecievedRepository;
import com.mycompany.myapp.service.criteria.TranferRecievedCriteria;
import com.mycompany.myapp.service.dto.TranferRecievedDTO;
import com.mycompany.myapp.service.mapper.TranferRecievedMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TranferRecievedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TranferRecievedResourceIT {

    private static final Instant DEFAULT_TRANSFER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRANSFER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_TRANSFERED = 1D;
    private static final Double UPDATED_QTY_TRANSFERED = 2D;
    private static final Double SMALLER_QTY_TRANSFERED = 1D - 1D;

    private static final Double DEFAULT_QTY_RECEIVED = 1D;
    private static final Double UPDATED_QTY_RECEIVED = 2D;
    private static final Double SMALLER_QTY_RECEIVED = 1D - 1D;

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/tranfer-recieveds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TranferRecievedRepository tranferRecievedRepository;

    @Autowired
    private TranferRecievedMapper tranferRecievedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTranferRecievedMockMvc;

    private TranferRecieved tranferRecieved;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranferRecieved createEntity(EntityManager em) {
        TranferRecieved tranferRecieved = new TranferRecieved()
            .transferDate(DEFAULT_TRANSFER_DATE)
            .qtyTransfered(DEFAULT_QTY_TRANSFERED)
            .qtyReceived(DEFAULT_QTY_RECEIVED)
            .comment(DEFAULT_COMMENT)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return tranferRecieved;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranferRecieved createUpdatedEntity(EntityManager em) {
        TranferRecieved tranferRecieved = new TranferRecieved()
            .transferDate(UPDATED_TRANSFER_DATE)
            .qtyTransfered(UPDATED_QTY_TRANSFERED)
            .qtyReceived(UPDATED_QTY_RECEIVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return tranferRecieved;
    }

    @BeforeEach
    public void initTest() {
        tranferRecieved = createEntity(em);
    }

    @Test
    @Transactional
    void createTranferRecieved() throws Exception {
        int databaseSizeBeforeCreate = tranferRecievedRepository.findAll().size();
        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);
        restTranferRecievedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeCreate + 1);
        TranferRecieved testTranferRecieved = tranferRecievedList.get(tranferRecievedList.size() - 1);
        assertThat(testTranferRecieved.getTransferDate()).isEqualTo(DEFAULT_TRANSFER_DATE);
        assertThat(testTranferRecieved.getQtyTransfered()).isEqualTo(DEFAULT_QTY_TRANSFERED);
        assertThat(testTranferRecieved.getQtyReceived()).isEqualTo(DEFAULT_QTY_RECEIVED);
        assertThat(testTranferRecieved.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTranferRecieved.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTranferRecieved.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testTranferRecieved.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTranferRecieved.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTranferRecieved.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTranferRecieved.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createTranferRecievedWithExistingId() throws Exception {
        // Create the TranferRecieved with an existing ID
        tranferRecieved.setId(1L);
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        int databaseSizeBeforeCreate = tranferRecievedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranferRecievedMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTranferRecieveds() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList
        restTranferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tranferRecieved.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyTransfered").value(hasItem(DEFAULT_QTY_TRANSFERED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyReceived").value(hasItem(DEFAULT_QTY_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @Test
    @Transactional
    void getTranferRecieved() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get the tranferRecieved
        restTranferRecievedMockMvc
            .perform(get(ENTITY_API_URL_ID, tranferRecieved.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tranferRecieved.getId().intValue()))
            .andExpect(jsonPath("$.transferDate").value(DEFAULT_TRANSFER_DATE.toString()))
            .andExpect(jsonPath("$.qtyTransfered").value(DEFAULT_QTY_TRANSFERED.doubleValue()))
            .andExpect(jsonPath("$.qtyReceived").value(DEFAULT_QTY_RECEIVED.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getTranferRecievedsByIdFiltering() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        Long id = tranferRecieved.getId();

        defaultTranferRecievedShouldBeFound("id.equals=" + id);
        defaultTranferRecievedShouldNotBeFound("id.notEquals=" + id);

        defaultTranferRecievedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTranferRecievedShouldNotBeFound("id.greaterThan=" + id);

        defaultTranferRecievedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTranferRecievedShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByTransferDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where transferDate equals to DEFAULT_TRANSFER_DATE
        defaultTranferRecievedShouldBeFound("transferDate.equals=" + DEFAULT_TRANSFER_DATE);

        // Get all the tranferRecievedList where transferDate equals to UPDATED_TRANSFER_DATE
        defaultTranferRecievedShouldNotBeFound("transferDate.equals=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByTransferDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where transferDate not equals to DEFAULT_TRANSFER_DATE
        defaultTranferRecievedShouldNotBeFound("transferDate.notEquals=" + DEFAULT_TRANSFER_DATE);

        // Get all the tranferRecievedList where transferDate not equals to UPDATED_TRANSFER_DATE
        defaultTranferRecievedShouldBeFound("transferDate.notEquals=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByTransferDateIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where transferDate in DEFAULT_TRANSFER_DATE or UPDATED_TRANSFER_DATE
        defaultTranferRecievedShouldBeFound("transferDate.in=" + DEFAULT_TRANSFER_DATE + "," + UPDATED_TRANSFER_DATE);

        // Get all the tranferRecievedList where transferDate equals to UPDATED_TRANSFER_DATE
        defaultTranferRecievedShouldNotBeFound("transferDate.in=" + UPDATED_TRANSFER_DATE);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByTransferDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where transferDate is not null
        defaultTranferRecievedShouldBeFound("transferDate.specified=true");

        // Get all the tranferRecievedList where transferDate is null
        defaultTranferRecievedShouldNotBeFound("transferDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered equals to DEFAULT_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.equals=" + DEFAULT_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered equals to UPDATED_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.equals=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered not equals to DEFAULT_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.notEquals=" + DEFAULT_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered not equals to UPDATED_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.notEquals=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered in DEFAULT_QTY_TRANSFERED or UPDATED_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.in=" + DEFAULT_QTY_TRANSFERED + "," + UPDATED_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered equals to UPDATED_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.in=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered is not null
        defaultTranferRecievedShouldBeFound("qtyTransfered.specified=true");

        // Get all the tranferRecievedList where qtyTransfered is null
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered is greater than or equal to DEFAULT_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.greaterThanOrEqual=" + DEFAULT_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered is greater than or equal to UPDATED_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.greaterThanOrEqual=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered is less than or equal to DEFAULT_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.lessThanOrEqual=" + DEFAULT_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered is less than or equal to SMALLER_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.lessThanOrEqual=" + SMALLER_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsLessThanSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered is less than DEFAULT_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.lessThan=" + DEFAULT_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered is less than UPDATED_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.lessThan=" + UPDATED_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyTransferedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyTransfered is greater than DEFAULT_QTY_TRANSFERED
        defaultTranferRecievedShouldNotBeFound("qtyTransfered.greaterThan=" + DEFAULT_QTY_TRANSFERED);

        // Get all the tranferRecievedList where qtyTransfered is greater than SMALLER_QTY_TRANSFERED
        defaultTranferRecievedShouldBeFound("qtyTransfered.greaterThan=" + SMALLER_QTY_TRANSFERED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived equals to DEFAULT_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.equals=" + DEFAULT_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived equals to UPDATED_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.equals=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived not equals to DEFAULT_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.notEquals=" + DEFAULT_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived not equals to UPDATED_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.notEquals=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived in DEFAULT_QTY_RECEIVED or UPDATED_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.in=" + DEFAULT_QTY_RECEIVED + "," + UPDATED_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived equals to UPDATED_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.in=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived is not null
        defaultTranferRecievedShouldBeFound("qtyReceived.specified=true");

        // Get all the tranferRecievedList where qtyReceived is null
        defaultTranferRecievedShouldNotBeFound("qtyReceived.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived is greater than or equal to DEFAULT_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.greaterThanOrEqual=" + DEFAULT_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived is greater than or equal to UPDATED_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.greaterThanOrEqual=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived is less than or equal to DEFAULT_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.lessThanOrEqual=" + DEFAULT_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived is less than or equal to SMALLER_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.lessThanOrEqual=" + SMALLER_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsLessThanSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived is less than DEFAULT_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.lessThan=" + DEFAULT_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived is less than UPDATED_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.lessThan=" + UPDATED_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByQtyReceivedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where qtyReceived is greater than DEFAULT_QTY_RECEIVED
        defaultTranferRecievedShouldNotBeFound("qtyReceived.greaterThan=" + DEFAULT_QTY_RECEIVED);

        // Get all the tranferRecievedList where qtyReceived is greater than SMALLER_QTY_RECEIVED
        defaultTranferRecievedShouldBeFound("qtyReceived.greaterThan=" + SMALLER_QTY_RECEIVED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where comment equals to DEFAULT_COMMENT
        defaultTranferRecievedShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the tranferRecievedList where comment equals to UPDATED_COMMENT
        defaultTranferRecievedShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where comment not equals to DEFAULT_COMMENT
        defaultTranferRecievedShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the tranferRecievedList where comment not equals to UPDATED_COMMENT
        defaultTranferRecievedShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTranferRecievedShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the tranferRecievedList where comment equals to UPDATED_COMMENT
        defaultTranferRecievedShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where comment is not null
        defaultTranferRecievedShouldBeFound("comment.specified=true");

        // Get all the tranferRecievedList where comment is null
        defaultTranferRecievedShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByCommentContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where comment contains DEFAULT_COMMENT
        defaultTranferRecievedShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the tranferRecievedList where comment contains UPDATED_COMMENT
        defaultTranferRecievedShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where comment does not contain DEFAULT_COMMENT
        defaultTranferRecievedShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the tranferRecievedList where comment does not contain UPDATED_COMMENT
        defaultTranferRecievedShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTranferRecievedShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferRecievedList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTranferRecievedShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTranferRecievedShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferRecievedList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTranferRecievedShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTranferRecievedShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the tranferRecievedList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTranferRecievedShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField1 is not null
        defaultTranferRecievedShouldBeFound("freeField1.specified=true");

        // Get all the tranferRecievedList where freeField1 is null
        defaultTranferRecievedShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTranferRecievedShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferRecievedList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTranferRecievedShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTranferRecievedShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferRecievedList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTranferRecievedShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultTranferRecievedShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferRecievedList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTranferRecievedShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultTranferRecievedShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferRecievedList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultTranferRecievedShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultTranferRecievedShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the tranferRecievedList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTranferRecievedShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField2 is not null
        defaultTranferRecievedShouldBeFound("freeField2.specified=true");

        // Get all the tranferRecievedList where freeField2 is null
        defaultTranferRecievedShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultTranferRecievedShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferRecievedList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultTranferRecievedShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultTranferRecievedShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferRecievedList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultTranferRecievedShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTranferRecievedShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tranferRecievedList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTranferRecievedShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTranferRecievedShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tranferRecievedList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTranferRecievedShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTranferRecievedShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the tranferRecievedList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTranferRecievedShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModified is not null
        defaultTranferRecievedShouldBeFound("lastModified.specified=true");

        // Get all the tranferRecievedList where lastModified is null
        defaultTranferRecievedShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTranferRecievedShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferRecievedList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTranferRecievedShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTranferRecievedShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferRecievedList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTranferRecievedShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTranferRecievedShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the tranferRecievedList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTranferRecievedShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModifiedBy is not null
        defaultTranferRecievedShouldBeFound("lastModifiedBy.specified=true");

        // Get all the tranferRecievedList where lastModifiedBy is null
        defaultTranferRecievedShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTranferRecievedShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferRecievedList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTranferRecievedShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTranferRecievedShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferRecievedList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTranferRecievedShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isDeleted equals to DEFAULT_IS_DELETED
        defaultTranferRecievedShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the tranferRecievedList where isDeleted equals to UPDATED_IS_DELETED
        defaultTranferRecievedShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultTranferRecievedShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the tranferRecievedList where isDeleted not equals to UPDATED_IS_DELETED
        defaultTranferRecievedShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultTranferRecievedShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the tranferRecievedList where isDeleted equals to UPDATED_IS_DELETED
        defaultTranferRecievedShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isDeleted is not null
        defaultTranferRecievedShouldBeFound("isDeleted.specified=true");

        // Get all the tranferRecievedList where isDeleted is null
        defaultTranferRecievedShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isActive equals to DEFAULT_IS_ACTIVE
        defaultTranferRecievedShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the tranferRecievedList where isActive equals to UPDATED_IS_ACTIVE
        defaultTranferRecievedShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultTranferRecievedShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the tranferRecievedList where isActive not equals to UPDATED_IS_ACTIVE
        defaultTranferRecievedShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultTranferRecievedShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the tranferRecievedList where isActive equals to UPDATED_IS_ACTIVE
        defaultTranferRecievedShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        // Get all the tranferRecievedList where isActive is not null
        defaultTranferRecievedShouldBeFound("isActive.specified=true");

        // Get all the tranferRecievedList where isActive is null
        defaultTranferRecievedShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferRecievedsByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);
        Transfer transfer;
        if (TestUtil.findAll(em, Transfer.class).isEmpty()) {
            transfer = TransferResourceIT.createEntity(em);
            em.persist(transfer);
            em.flush();
        } else {
            transfer = TestUtil.findAll(em, Transfer.class).get(0);
        }
        em.persist(transfer);
        em.flush();
        tranferRecieved.setTransfer(transfer);
        tranferRecievedRepository.saveAndFlush(tranferRecieved);
        Long transferId = transfer.getId();

        // Get all the tranferRecievedList where transfer equals to transferId
        defaultTranferRecievedShouldBeFound("transferId.equals=" + transferId);

        // Get all the tranferRecievedList where transfer equals to (transferId + 1)
        defaultTranferRecievedShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTranferRecievedShouldBeFound(String filter) throws Exception {
        restTranferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tranferRecieved.getId().intValue())))
            .andExpect(jsonPath("$.[*].transferDate").value(hasItem(DEFAULT_TRANSFER_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyTransfered").value(hasItem(DEFAULT_QTY_TRANSFERED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyReceived").value(hasItem(DEFAULT_QTY_RECEIVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restTranferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTranferRecievedShouldNotBeFound(String filter) throws Exception {
        restTranferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTranferRecievedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTranferRecieved() throws Exception {
        // Get the tranferRecieved
        restTranferRecievedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTranferRecieved() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();

        // Update the tranferRecieved
        TranferRecieved updatedTranferRecieved = tranferRecievedRepository.findById(tranferRecieved.getId()).get();
        // Disconnect from session so that the updates on updatedTranferRecieved are not directly saved in db
        em.detach(updatedTranferRecieved);
        updatedTranferRecieved
            .transferDate(UPDATED_TRANSFER_DATE)
            .qtyTransfered(UPDATED_QTY_TRANSFERED)
            .qtyReceived(UPDATED_QTY_RECEIVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(updatedTranferRecieved);

        restTranferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tranferRecievedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isOk());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
        TranferRecieved testTranferRecieved = tranferRecievedList.get(tranferRecievedList.size() - 1);
        assertThat(testTranferRecieved.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTranferRecieved.getQtyTransfered()).isEqualTo(UPDATED_QTY_TRANSFERED);
        assertThat(testTranferRecieved.getQtyReceived()).isEqualTo(UPDATED_QTY_RECEIVED);
        assertThat(testTranferRecieved.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTranferRecieved.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTranferRecieved.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTranferRecieved.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTranferRecieved.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTranferRecieved.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTranferRecieved.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTranferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();
        tranferRecieved.setId(count.incrementAndGet());

        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tranferRecievedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTranferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();
        tranferRecieved.setId(count.incrementAndGet());

        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTranferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();
        tranferRecieved.setId(count.incrementAndGet());

        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferRecievedMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTranferRecievedWithPatch() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();

        // Update the tranferRecieved using partial update
        TranferRecieved partialUpdatedTranferRecieved = new TranferRecieved();
        partialUpdatedTranferRecieved.setId(tranferRecieved.getId());

        partialUpdatedTranferRecieved.lastModifiedBy(UPDATED_LAST_MODIFIED_BY).isDeleted(UPDATED_IS_DELETED).isActive(UPDATED_IS_ACTIVE);

        restTranferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranferRecieved.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranferRecieved))
            )
            .andExpect(status().isOk());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
        TranferRecieved testTranferRecieved = tranferRecievedList.get(tranferRecievedList.size() - 1);
        assertThat(testTranferRecieved.getTransferDate()).isEqualTo(DEFAULT_TRANSFER_DATE);
        assertThat(testTranferRecieved.getQtyTransfered()).isEqualTo(DEFAULT_QTY_TRANSFERED);
        assertThat(testTranferRecieved.getQtyReceived()).isEqualTo(DEFAULT_QTY_RECEIVED);
        assertThat(testTranferRecieved.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTranferRecieved.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTranferRecieved.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testTranferRecieved.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTranferRecieved.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTranferRecieved.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTranferRecieved.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTranferRecievedWithPatch() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();

        // Update the tranferRecieved using partial update
        TranferRecieved partialUpdatedTranferRecieved = new TranferRecieved();
        partialUpdatedTranferRecieved.setId(tranferRecieved.getId());

        partialUpdatedTranferRecieved
            .transferDate(UPDATED_TRANSFER_DATE)
            .qtyTransfered(UPDATED_QTY_TRANSFERED)
            .qtyReceived(UPDATED_QTY_RECEIVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restTranferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranferRecieved.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranferRecieved))
            )
            .andExpect(status().isOk());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
        TranferRecieved testTranferRecieved = tranferRecievedList.get(tranferRecievedList.size() - 1);
        assertThat(testTranferRecieved.getTransferDate()).isEqualTo(UPDATED_TRANSFER_DATE);
        assertThat(testTranferRecieved.getQtyTransfered()).isEqualTo(UPDATED_QTY_TRANSFERED);
        assertThat(testTranferRecieved.getQtyReceived()).isEqualTo(UPDATED_QTY_RECEIVED);
        assertThat(testTranferRecieved.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTranferRecieved.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTranferRecieved.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTranferRecieved.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTranferRecieved.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTranferRecieved.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTranferRecieved.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTranferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();
        tranferRecieved.setId(count.incrementAndGet());

        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tranferRecievedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTranferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();
        tranferRecieved.setId(count.incrementAndGet());

        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTranferRecieved() throws Exception {
        int databaseSizeBeforeUpdate = tranferRecievedRepository.findAll().size();
        tranferRecieved.setId(count.incrementAndGet());

        // Create the TranferRecieved
        TranferRecievedDTO tranferRecievedDTO = tranferRecievedMapper.toDto(tranferRecieved);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferRecievedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranferRecievedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TranferRecieved in the database
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTranferRecieved() throws Exception {
        // Initialize the database
        tranferRecievedRepository.saveAndFlush(tranferRecieved);

        int databaseSizeBeforeDelete = tranferRecievedRepository.findAll().size();

        // Delete the tranferRecieved
        restTranferRecievedMockMvc
            .perform(delete(ENTITY_API_URL_ID, tranferRecieved.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TranferRecieved> tranferRecievedList = tranferRecievedRepository.findAll();
        assertThat(tranferRecievedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
