package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.TranferDetailsApprovals;
import com.mycompany.myapp.domain.Transfer;
import com.mycompany.myapp.repository.TranferDetailsApprovalsRepository;
import com.mycompany.myapp.service.criteria.TranferDetailsApprovalsCriteria;
import com.mycompany.myapp.service.dto.TranferDetailsApprovalsDTO;
import com.mycompany.myapp.service.mapper.TranferDetailsApprovalsMapper;
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
 * Integration tests for the {@link TranferDetailsApprovalsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TranferDetailsApprovalsResourceIT {

    private static final Instant DEFAULT_APPROVAL_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_APPROVAL_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_QTY_REQUESTED = 1D;
    private static final Double UPDATED_QTY_REQUESTED = 2D;
    private static final Double SMALLER_QTY_REQUESTED = 1D - 1D;

    private static final Double DEFAULT_QTY_APPROVED = 1D;
    private static final Double UPDATED_QTY_APPROVED = 2D;
    private static final Double SMALLER_QTY_APPROVED = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/tranfer-details-approvals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository;

    @Autowired
    private TranferDetailsApprovalsMapper tranferDetailsApprovalsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTranferDetailsApprovalsMockMvc;

    private TranferDetailsApprovals tranferDetailsApprovals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranferDetailsApprovals createEntity(EntityManager em) {
        TranferDetailsApprovals tranferDetailsApprovals = new TranferDetailsApprovals()
            .approvalDate(DEFAULT_APPROVAL_DATE)
            .qtyRequested(DEFAULT_QTY_REQUESTED)
            .qtyApproved(DEFAULT_QTY_APPROVED)
            .comment(DEFAULT_COMMENT)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return tranferDetailsApprovals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TranferDetailsApprovals createUpdatedEntity(EntityManager em) {
        TranferDetailsApprovals tranferDetailsApprovals = new TranferDetailsApprovals()
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return tranferDetailsApprovals;
    }

    @BeforeEach
    public void initTest() {
        tranferDetailsApprovals = createEntity(em);
    }

    @Test
    @Transactional
    void createTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeCreate = tranferDetailsApprovalsRepository.findAll().size();
        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);
        restTranferDetailsApprovalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeCreate + 1);
        TranferDetailsApprovals testTranferDetailsApprovals = tranferDetailsApprovalsList.get(tranferDetailsApprovalsList.size() - 1);
        assertThat(testTranferDetailsApprovals.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testTranferDetailsApprovals.getQtyRequested()).isEqualTo(DEFAULT_QTY_REQUESTED);
        assertThat(testTranferDetailsApprovals.getQtyApproved()).isEqualTo(DEFAULT_QTY_APPROVED);
        assertThat(testTranferDetailsApprovals.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTranferDetailsApprovals.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTranferDetailsApprovals.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testTranferDetailsApprovals.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testTranferDetailsApprovals.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testTranferDetailsApprovals.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTranferDetailsApprovals.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createTranferDetailsApprovalsWithExistingId() throws Exception {
        // Create the TranferDetailsApprovals with an existing ID
        tranferDetailsApprovals.setId(1L);
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        int databaseSizeBeforeCreate = tranferDetailsApprovalsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTranferDetailsApprovalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovals() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList
        restTranferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tranferDetailsApprovals.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyRequested").value(hasItem(DEFAULT_QTY_REQUESTED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyApproved").value(hasItem(DEFAULT_QTY_APPROVED.doubleValue())))
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
    void getTranferDetailsApprovals() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get the tranferDetailsApprovals
        restTranferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL_ID, tranferDetailsApprovals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tranferDetailsApprovals.getId().intValue()))
            .andExpect(jsonPath("$.approvalDate").value(DEFAULT_APPROVAL_DATE.toString()))
            .andExpect(jsonPath("$.qtyRequested").value(DEFAULT_QTY_REQUESTED.doubleValue()))
            .andExpect(jsonPath("$.qtyApproved").value(DEFAULT_QTY_APPROVED.doubleValue()))
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
    void getTranferDetailsApprovalsByIdFiltering() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        Long id = tranferDetailsApprovals.getId();

        defaultTranferDetailsApprovalsShouldBeFound("id.equals=" + id);
        defaultTranferDetailsApprovalsShouldNotBeFound("id.notEquals=" + id);

        defaultTranferDetailsApprovalsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTranferDetailsApprovalsShouldNotBeFound("id.greaterThan=" + id);

        defaultTranferDetailsApprovalsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTranferDetailsApprovalsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByApprovalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where approvalDate equals to DEFAULT_APPROVAL_DATE
        defaultTranferDetailsApprovalsShouldBeFound("approvalDate.equals=" + DEFAULT_APPROVAL_DATE);

        // Get all the tranferDetailsApprovalsList where approvalDate equals to UPDATED_APPROVAL_DATE
        defaultTranferDetailsApprovalsShouldNotBeFound("approvalDate.equals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByApprovalDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where approvalDate not equals to DEFAULT_APPROVAL_DATE
        defaultTranferDetailsApprovalsShouldNotBeFound("approvalDate.notEquals=" + DEFAULT_APPROVAL_DATE);

        // Get all the tranferDetailsApprovalsList where approvalDate not equals to UPDATED_APPROVAL_DATE
        defaultTranferDetailsApprovalsShouldBeFound("approvalDate.notEquals=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByApprovalDateIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where approvalDate in DEFAULT_APPROVAL_DATE or UPDATED_APPROVAL_DATE
        defaultTranferDetailsApprovalsShouldBeFound("approvalDate.in=" + DEFAULT_APPROVAL_DATE + "," + UPDATED_APPROVAL_DATE);

        // Get all the tranferDetailsApprovalsList where approvalDate equals to UPDATED_APPROVAL_DATE
        defaultTranferDetailsApprovalsShouldNotBeFound("approvalDate.in=" + UPDATED_APPROVAL_DATE);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByApprovalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where approvalDate is not null
        defaultTranferDetailsApprovalsShouldBeFound("approvalDate.specified=true");

        // Get all the tranferDetailsApprovalsList where approvalDate is null
        defaultTranferDetailsApprovalsShouldNotBeFound("approvalDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested equals to DEFAULT_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.equals=" + DEFAULT_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested equals to UPDATED_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.equals=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested not equals to DEFAULT_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.notEquals=" + DEFAULT_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested not equals to UPDATED_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.notEquals=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested in DEFAULT_QTY_REQUESTED or UPDATED_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.in=" + DEFAULT_QTY_REQUESTED + "," + UPDATED_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested equals to UPDATED_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.in=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested is not null
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.specified=true");

        // Get all the tranferDetailsApprovalsList where qtyRequested is null
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested is greater than or equal to DEFAULT_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.greaterThanOrEqual=" + DEFAULT_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested is greater than or equal to UPDATED_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.greaterThanOrEqual=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested is less than or equal to DEFAULT_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.lessThanOrEqual=" + DEFAULT_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested is less than or equal to SMALLER_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.lessThanOrEqual=" + SMALLER_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsLessThanSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested is less than DEFAULT_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.lessThan=" + DEFAULT_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested is less than UPDATED_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.lessThan=" + UPDATED_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyRequestedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyRequested is greater than DEFAULT_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyRequested.greaterThan=" + DEFAULT_QTY_REQUESTED);

        // Get all the tranferDetailsApprovalsList where qtyRequested is greater than SMALLER_QTY_REQUESTED
        defaultTranferDetailsApprovalsShouldBeFound("qtyRequested.greaterThan=" + SMALLER_QTY_REQUESTED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved equals to DEFAULT_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.equals=" + DEFAULT_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved equals to UPDATED_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.equals=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved not equals to DEFAULT_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.notEquals=" + DEFAULT_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved not equals to UPDATED_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.notEquals=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved in DEFAULT_QTY_APPROVED or UPDATED_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.in=" + DEFAULT_QTY_APPROVED + "," + UPDATED_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved equals to UPDATED_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.in=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved is not null
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.specified=true");

        // Get all the tranferDetailsApprovalsList where qtyApproved is null
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved is greater than or equal to DEFAULT_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.greaterThanOrEqual=" + DEFAULT_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved is greater than or equal to UPDATED_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.greaterThanOrEqual=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved is less than or equal to DEFAULT_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.lessThanOrEqual=" + DEFAULT_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved is less than or equal to SMALLER_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.lessThanOrEqual=" + SMALLER_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsLessThanSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved is less than DEFAULT_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.lessThan=" + DEFAULT_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved is less than UPDATED_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.lessThan=" + UPDATED_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByQtyApprovedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where qtyApproved is greater than DEFAULT_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldNotBeFound("qtyApproved.greaterThan=" + DEFAULT_QTY_APPROVED);

        // Get all the tranferDetailsApprovalsList where qtyApproved is greater than SMALLER_QTY_APPROVED
        defaultTranferDetailsApprovalsShouldBeFound("qtyApproved.greaterThan=" + SMALLER_QTY_APPROVED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where comment equals to DEFAULT_COMMENT
        defaultTranferDetailsApprovalsShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the tranferDetailsApprovalsList where comment equals to UPDATED_COMMENT
        defaultTranferDetailsApprovalsShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByCommentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where comment not equals to DEFAULT_COMMENT
        defaultTranferDetailsApprovalsShouldNotBeFound("comment.notEquals=" + DEFAULT_COMMENT);

        // Get all the tranferDetailsApprovalsList where comment not equals to UPDATED_COMMENT
        defaultTranferDetailsApprovalsShouldBeFound("comment.notEquals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultTranferDetailsApprovalsShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the tranferDetailsApprovalsList where comment equals to UPDATED_COMMENT
        defaultTranferDetailsApprovalsShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where comment is not null
        defaultTranferDetailsApprovalsShouldBeFound("comment.specified=true");

        // Get all the tranferDetailsApprovalsList where comment is null
        defaultTranferDetailsApprovalsShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByCommentContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where comment contains DEFAULT_COMMENT
        defaultTranferDetailsApprovalsShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the tranferDetailsApprovalsList where comment contains UPDATED_COMMENT
        defaultTranferDetailsApprovalsShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where comment does not contain DEFAULT_COMMENT
        defaultTranferDetailsApprovalsShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the tranferDetailsApprovalsList where comment does not contain UPDATED_COMMENT
        defaultTranferDetailsApprovalsShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferDetailsApprovalsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferDetailsApprovalsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the tranferDetailsApprovalsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField1 is not null
        defaultTranferDetailsApprovalsShouldBeFound("freeField1.specified=true");

        // Get all the tranferDetailsApprovalsList where freeField1 is null
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferDetailsApprovalsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the tranferDetailsApprovalsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultTranferDetailsApprovalsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferDetailsApprovalsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferDetailsApprovalsList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the tranferDetailsApprovalsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField2 is not null
        defaultTranferDetailsApprovalsShouldBeFound("freeField2.specified=true");

        // Get all the tranferDetailsApprovalsList where freeField2 is null
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferDetailsApprovalsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the tranferDetailsApprovalsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultTranferDetailsApprovalsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultTranferDetailsApprovalsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tranferDetailsApprovalsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the tranferDetailsApprovalsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultTranferDetailsApprovalsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultTranferDetailsApprovalsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the tranferDetailsApprovalsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModified is not null
        defaultTranferDetailsApprovalsShouldBeFound("lastModified.specified=true");

        // Get all the tranferDetailsApprovalsList where lastModified is null
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy is not null
        defaultTranferDetailsApprovalsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the tranferDetailsApprovalsList where lastModifiedBy is null
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the tranferDetailsApprovalsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultTranferDetailsApprovalsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isDeleted equals to DEFAULT_IS_DELETED
        defaultTranferDetailsApprovalsShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the tranferDetailsApprovalsList where isDeleted equals to UPDATED_IS_DELETED
        defaultTranferDetailsApprovalsShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultTranferDetailsApprovalsShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the tranferDetailsApprovalsList where isDeleted not equals to UPDATED_IS_DELETED
        defaultTranferDetailsApprovalsShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultTranferDetailsApprovalsShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the tranferDetailsApprovalsList where isDeleted equals to UPDATED_IS_DELETED
        defaultTranferDetailsApprovalsShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isDeleted is not null
        defaultTranferDetailsApprovalsShouldBeFound("isDeleted.specified=true");

        // Get all the tranferDetailsApprovalsList where isDeleted is null
        defaultTranferDetailsApprovalsShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isActive equals to DEFAULT_IS_ACTIVE
        defaultTranferDetailsApprovalsShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the tranferDetailsApprovalsList where isActive equals to UPDATED_IS_ACTIVE
        defaultTranferDetailsApprovalsShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultTranferDetailsApprovalsShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the tranferDetailsApprovalsList where isActive not equals to UPDATED_IS_ACTIVE
        defaultTranferDetailsApprovalsShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultTranferDetailsApprovalsShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the tranferDetailsApprovalsList where isActive equals to UPDATED_IS_ACTIVE
        defaultTranferDetailsApprovalsShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        // Get all the tranferDetailsApprovalsList where isActive is not null
        defaultTranferDetailsApprovalsShouldBeFound("isActive.specified=true");

        // Get all the tranferDetailsApprovalsList where isActive is null
        defaultTranferDetailsApprovalsShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllTranferDetailsApprovalsByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);
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
        tranferDetailsApprovals.setTransfer(transfer);
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);
        Long transferId = transfer.getId();

        // Get all the tranferDetailsApprovalsList where transfer equals to transferId
        defaultTranferDetailsApprovalsShouldBeFound("transferId.equals=" + transferId);

        // Get all the tranferDetailsApprovalsList where transfer equals to (transferId + 1)
        defaultTranferDetailsApprovalsShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTranferDetailsApprovalsShouldBeFound(String filter) throws Exception {
        restTranferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tranferDetailsApprovals.getId().intValue())))
            .andExpect(jsonPath("$.[*].approvalDate").value(hasItem(DEFAULT_APPROVAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].qtyRequested").value(hasItem(DEFAULT_QTY_REQUESTED.doubleValue())))
            .andExpect(jsonPath("$.[*].qtyApproved").value(hasItem(DEFAULT_QTY_APPROVED.doubleValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restTranferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTranferDetailsApprovalsShouldNotBeFound(String filter) throws Exception {
        restTranferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTranferDetailsApprovalsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTranferDetailsApprovals() throws Exception {
        // Get the tranferDetailsApprovals
        restTranferDetailsApprovalsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTranferDetailsApprovals() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();

        // Update the tranferDetailsApprovals
        TranferDetailsApprovals updatedTranferDetailsApprovals = tranferDetailsApprovalsRepository
            .findById(tranferDetailsApprovals.getId())
            .get();
        // Disconnect from session so that the updates on updatedTranferDetailsApprovals are not directly saved in db
        em.detach(updatedTranferDetailsApprovals);
        updatedTranferDetailsApprovals
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(updatedTranferDetailsApprovals);

        restTranferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tranferDetailsApprovalsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isOk());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
        TranferDetailsApprovals testTranferDetailsApprovals = tranferDetailsApprovalsList.get(tranferDetailsApprovalsList.size() - 1);
        assertThat(testTranferDetailsApprovals.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTranferDetailsApprovals.getQtyRequested()).isEqualTo(UPDATED_QTY_REQUESTED);
        assertThat(testTranferDetailsApprovals.getQtyApproved()).isEqualTo(UPDATED_QTY_APPROVED);
        assertThat(testTranferDetailsApprovals.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTranferDetailsApprovals.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTranferDetailsApprovals.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTranferDetailsApprovals.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTranferDetailsApprovals.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTranferDetailsApprovals.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTranferDetailsApprovals.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();
        tranferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tranferDetailsApprovalsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();
        tranferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();
        tranferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferDetailsApprovalsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTranferDetailsApprovalsWithPatch() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();

        // Update the tranferDetailsApprovals using partial update
        TranferDetailsApprovals partialUpdatedTranferDetailsApprovals = new TranferDetailsApprovals();
        partialUpdatedTranferDetailsApprovals.setId(tranferDetailsApprovals.getId());

        partialUpdatedTranferDetailsApprovals
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED);

        restTranferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranferDetailsApprovals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranferDetailsApprovals))
            )
            .andExpect(status().isOk());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
        TranferDetailsApprovals testTranferDetailsApprovals = tranferDetailsApprovalsList.get(tranferDetailsApprovalsList.size() - 1);
        assertThat(testTranferDetailsApprovals.getApprovalDate()).isEqualTo(DEFAULT_APPROVAL_DATE);
        assertThat(testTranferDetailsApprovals.getQtyRequested()).isEqualTo(DEFAULT_QTY_REQUESTED);
        assertThat(testTranferDetailsApprovals.getQtyApproved()).isEqualTo(DEFAULT_QTY_APPROVED);
        assertThat(testTranferDetailsApprovals.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testTranferDetailsApprovals.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testTranferDetailsApprovals.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTranferDetailsApprovals.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTranferDetailsApprovals.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTranferDetailsApprovals.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTranferDetailsApprovals.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateTranferDetailsApprovalsWithPatch() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();

        // Update the tranferDetailsApprovals using partial update
        TranferDetailsApprovals partialUpdatedTranferDetailsApprovals = new TranferDetailsApprovals();
        partialUpdatedTranferDetailsApprovals.setId(tranferDetailsApprovals.getId());

        partialUpdatedTranferDetailsApprovals
            .approvalDate(UPDATED_APPROVAL_DATE)
            .qtyRequested(UPDATED_QTY_REQUESTED)
            .qtyApproved(UPDATED_QTY_APPROVED)
            .comment(UPDATED_COMMENT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restTranferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTranferDetailsApprovals.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTranferDetailsApprovals))
            )
            .andExpect(status().isOk());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
        TranferDetailsApprovals testTranferDetailsApprovals = tranferDetailsApprovalsList.get(tranferDetailsApprovalsList.size() - 1);
        assertThat(testTranferDetailsApprovals.getApprovalDate()).isEqualTo(UPDATED_APPROVAL_DATE);
        assertThat(testTranferDetailsApprovals.getQtyRequested()).isEqualTo(UPDATED_QTY_REQUESTED);
        assertThat(testTranferDetailsApprovals.getQtyApproved()).isEqualTo(UPDATED_QTY_APPROVED);
        assertThat(testTranferDetailsApprovals.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testTranferDetailsApprovals.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testTranferDetailsApprovals.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testTranferDetailsApprovals.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testTranferDetailsApprovals.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testTranferDetailsApprovals.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTranferDetailsApprovals.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();
        tranferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTranferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tranferDetailsApprovalsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();
        tranferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTranferDetailsApprovals() throws Exception {
        int databaseSizeBeforeUpdate = tranferDetailsApprovalsRepository.findAll().size();
        tranferDetailsApprovals.setId(count.incrementAndGet());

        // Create the TranferDetailsApprovals
        TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO = tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTranferDetailsApprovalsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tranferDetailsApprovalsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TranferDetailsApprovals in the database
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTranferDetailsApprovals() throws Exception {
        // Initialize the database
        tranferDetailsApprovalsRepository.saveAndFlush(tranferDetailsApprovals);

        int databaseSizeBeforeDelete = tranferDetailsApprovalsRepository.findAll().size();

        // Delete the tranferDetailsApprovals
        restTranferDetailsApprovalsMockMvc
            .perform(delete(ENTITY_API_URL_ID, tranferDetailsApprovals.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TranferDetailsApprovals> tranferDetailsApprovalsList = tranferDetailsApprovalsRepository.findAll();
        assertThat(tranferDetailsApprovalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
