package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.GoodsRecived;
import com.mycompany.myapp.domain.ProductInventory;
import com.mycompany.myapp.domain.PurchaseOrder;
import com.mycompany.myapp.domain.PurchaseOrderDetails;
import com.mycompany.myapp.domain.RawMaterialOrder;
import com.mycompany.myapp.domain.SecurityUser;
import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.domain.enumeration.Status;
import com.mycompany.myapp.repository.PurchaseOrderRepository;
import com.mycompany.myapp.service.criteria.PurchaseOrderCriteria;
import com.mycompany.myapp.service.dto.PurchaseOrderDTO;
import com.mycompany.myapp.service.mapper.PurchaseOrderMapper;
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
 * Integration tests for the {@link PurchaseOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PurchaseOrderResourceIT {

    private static final Double DEFAULT_TOTAL_PO_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_PO_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_PO_AMOUNT = 1D - 1D;

    private static final Double DEFAULT_TOTAL_GST_AMOUNT = 1D;
    private static final Double UPDATED_TOTAL_GST_AMOUNT = 2D;
    private static final Double SMALLER_TOTAL_GST_AMOUNT = 1D - 1D;

    private static final Instant DEFAULT_EXPECTED_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PO_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PO_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_ORDER_STATUS = Status.REQUESTED;
    private static final Status UPDATED_ORDER_STATUS = Status.APPROVED;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/purchase-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderMapper purchaseOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPurchaseOrderMockMvc;

    private PurchaseOrder purchaseOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .totalPOAmount(DEFAULT_TOTAL_PO_AMOUNT)
            .totalGSTAmount(DEFAULT_TOTAL_GST_AMOUNT)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .poDate(DEFAULT_PO_DATE)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return purchaseOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchaseOrder createUpdatedEntity(EntityManager em) {
        PurchaseOrder purchaseOrder = new PurchaseOrder()
            .totalPOAmount(UPDATED_TOTAL_PO_AMOUNT)
            .totalGSTAmount(UPDATED_TOTAL_GST_AMOUNT)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .poDate(UPDATED_PO_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return purchaseOrder;
    }

    @BeforeEach
    public void initTest() {
        purchaseOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createPurchaseOrder() throws Exception {
        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();
        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);
        restPurchaseOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getTotalPOAmount()).isEqualTo(DEFAULT_TOTAL_PO_AMOUNT);
        assertThat(testPurchaseOrder.getTotalGSTAmount()).isEqualTo(DEFAULT_TOTAL_GST_AMOUNT);
        assertThat(testPurchaseOrder.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrder.getPoDate()).isEqualTo(DEFAULT_PO_DATE);
        assertThat(testPurchaseOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testPurchaseOrder.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testPurchaseOrder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchaseOrder.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPurchaseOrder.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPurchaseOrder.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPurchaseOrder.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createPurchaseOrderWithExistingId() throws Exception {
        // Create the PurchaseOrder with an existing ID
        purchaseOrder.setId(1L);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        int databaseSizeBeforeCreate = purchaseOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchaseOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrderRepository.findAll().size();
        // set the field null
        purchaseOrder.setLastModified(null);

        // Create the PurchaseOrder, which fails.
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        restPurchaseOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchaseOrderRepository.findAll().size();
        // set the field null
        purchaseOrder.setLastModifiedBy(null);

        // Create the PurchaseOrder, which fails.
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        restPurchaseOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPurchaseOrders() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList
        restPurchaseOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPOAmount").value(hasItem(DEFAULT_TOTAL_PO_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalGSTAmount").value(hasItem(DEFAULT_TOTAL_GST_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get the purchaseOrder
        restPurchaseOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, purchaseOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(purchaseOrder.getId().intValue()))
            .andExpect(jsonPath("$.totalPOAmount").value(DEFAULT_TOTAL_PO_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.totalGSTAmount").value(DEFAULT_TOTAL_GST_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(DEFAULT_EXPECTED_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.poDate").value(DEFAULT_PO_DATE.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getPurchaseOrdersByIdFiltering() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        Long id = purchaseOrder.getId();

        defaultPurchaseOrderShouldBeFound("id.equals=" + id);
        defaultPurchaseOrderShouldNotBeFound("id.notEquals=" + id);

        defaultPurchaseOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPurchaseOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultPurchaseOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPurchaseOrderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount equals to DEFAULT_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.equals=" + DEFAULT_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount equals to UPDATED_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.equals=" + UPDATED_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount not equals to DEFAULT_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.notEquals=" + DEFAULT_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount not equals to UPDATED_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.notEquals=" + UPDATED_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount in DEFAULT_TOTAL_PO_AMOUNT or UPDATED_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.in=" + DEFAULT_TOTAL_PO_AMOUNT + "," + UPDATED_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount equals to UPDATED_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.in=" + UPDATED_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount is not null
        defaultPurchaseOrderShouldBeFound("totalPOAmount.specified=true");

        // Get all the purchaseOrderList where totalPOAmount is null
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount is greater than or equal to DEFAULT_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount is greater than or equal to UPDATED_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.greaterThanOrEqual=" + UPDATED_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount is less than or equal to DEFAULT_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.lessThanOrEqual=" + DEFAULT_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount is less than or equal to SMALLER_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.lessThanOrEqual=" + SMALLER_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount is less than DEFAULT_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.lessThan=" + DEFAULT_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount is less than UPDATED_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.lessThan=" + UPDATED_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalPOAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalPOAmount is greater than DEFAULT_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalPOAmount.greaterThan=" + DEFAULT_TOTAL_PO_AMOUNT);

        // Get all the purchaseOrderList where totalPOAmount is greater than SMALLER_TOTAL_PO_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalPOAmount.greaterThan=" + SMALLER_TOTAL_PO_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount equals to DEFAULT_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.equals=" + DEFAULT_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount equals to UPDATED_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.equals=" + UPDATED_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount not equals to DEFAULT_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.notEquals=" + DEFAULT_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount not equals to UPDATED_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.notEquals=" + UPDATED_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount in DEFAULT_TOTAL_GST_AMOUNT or UPDATED_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.in=" + DEFAULT_TOTAL_GST_AMOUNT + "," + UPDATED_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount equals to UPDATED_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.in=" + UPDATED_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount is not null
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.specified=true");

        // Get all the purchaseOrderList where totalGSTAmount is null
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount is greater than or equal to DEFAULT_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.greaterThanOrEqual=" + DEFAULT_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount is greater than or equal to UPDATED_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.greaterThanOrEqual=" + UPDATED_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount is less than or equal to DEFAULT_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.lessThanOrEqual=" + DEFAULT_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount is less than or equal to SMALLER_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.lessThanOrEqual=" + SMALLER_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount is less than DEFAULT_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.lessThan=" + DEFAULT_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount is less than UPDATED_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.lessThan=" + UPDATED_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByTotalGSTAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where totalGSTAmount is greater than DEFAULT_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldNotBeFound("totalGSTAmount.greaterThan=" + DEFAULT_TOTAL_GST_AMOUNT);

        // Get all the purchaseOrderList where totalGSTAmount is greater than SMALLER_TOTAL_GST_AMOUNT
        defaultPurchaseOrderShouldBeFound("totalGSTAmount.greaterThan=" + SMALLER_TOTAL_GST_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByExpectedDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where expectedDeliveryDate equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrderShouldBeFound("expectedDeliveryDate.equals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the purchaseOrderList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrderShouldNotBeFound("expectedDeliveryDate.equals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByExpectedDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where expectedDeliveryDate not equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrderShouldNotBeFound("expectedDeliveryDate.notEquals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the purchaseOrderList where expectedDeliveryDate not equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrderShouldBeFound("expectedDeliveryDate.notEquals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByExpectedDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where expectedDeliveryDate in DEFAULT_EXPECTED_DELIVERY_DATE or UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrderShouldBeFound(
            "expectedDeliveryDate.in=" + DEFAULT_EXPECTED_DELIVERY_DATE + "," + UPDATED_EXPECTED_DELIVERY_DATE
        );

        // Get all the purchaseOrderList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultPurchaseOrderShouldNotBeFound("expectedDeliveryDate.in=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByExpectedDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where expectedDeliveryDate is not null
        defaultPurchaseOrderShouldBeFound("expectedDeliveryDate.specified=true");

        // Get all the purchaseOrderList where expectedDeliveryDate is null
        defaultPurchaseOrderShouldNotBeFound("expectedDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByPoDateIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where poDate equals to DEFAULT_PO_DATE
        defaultPurchaseOrderShouldBeFound("poDate.equals=" + DEFAULT_PO_DATE);

        // Get all the purchaseOrderList where poDate equals to UPDATED_PO_DATE
        defaultPurchaseOrderShouldNotBeFound("poDate.equals=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByPoDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where poDate not equals to DEFAULT_PO_DATE
        defaultPurchaseOrderShouldNotBeFound("poDate.notEquals=" + DEFAULT_PO_DATE);

        // Get all the purchaseOrderList where poDate not equals to UPDATED_PO_DATE
        defaultPurchaseOrderShouldBeFound("poDate.notEquals=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByPoDateIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where poDate in DEFAULT_PO_DATE or UPDATED_PO_DATE
        defaultPurchaseOrderShouldBeFound("poDate.in=" + DEFAULT_PO_DATE + "," + UPDATED_PO_DATE);

        // Get all the purchaseOrderList where poDate equals to UPDATED_PO_DATE
        defaultPurchaseOrderShouldNotBeFound("poDate.in=" + UPDATED_PO_DATE);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByPoDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where poDate is not null
        defaultPurchaseOrderShouldBeFound("poDate.specified=true");

        // Get all the purchaseOrderList where poDate is null
        defaultPurchaseOrderShouldNotBeFound("poDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByOrderStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderStatus equals to DEFAULT_ORDER_STATUS
        defaultPurchaseOrderShouldBeFound("orderStatus.equals=" + DEFAULT_ORDER_STATUS);

        // Get all the purchaseOrderList where orderStatus equals to UPDATED_ORDER_STATUS
        defaultPurchaseOrderShouldNotBeFound("orderStatus.equals=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByOrderStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderStatus not equals to DEFAULT_ORDER_STATUS
        defaultPurchaseOrderShouldNotBeFound("orderStatus.notEquals=" + DEFAULT_ORDER_STATUS);

        // Get all the purchaseOrderList where orderStatus not equals to UPDATED_ORDER_STATUS
        defaultPurchaseOrderShouldBeFound("orderStatus.notEquals=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByOrderStatusIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderStatus in DEFAULT_ORDER_STATUS or UPDATED_ORDER_STATUS
        defaultPurchaseOrderShouldBeFound("orderStatus.in=" + DEFAULT_ORDER_STATUS + "," + UPDATED_ORDER_STATUS);

        // Get all the purchaseOrderList where orderStatus equals to UPDATED_ORDER_STATUS
        defaultPurchaseOrderShouldNotBeFound("orderStatus.in=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByOrderStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where orderStatus is not null
        defaultPurchaseOrderShouldBeFound("orderStatus.specified=true");

        // Get all the purchaseOrderList where orderStatus is null
        defaultPurchaseOrderShouldNotBeFound("orderStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultPurchaseOrderShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the purchaseOrderList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPurchaseOrderShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultPurchaseOrderShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the purchaseOrderList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultPurchaseOrderShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultPurchaseOrderShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the purchaseOrderList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultPurchaseOrderShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModified is not null
        defaultPurchaseOrderShouldBeFound("lastModified.specified=true");

        // Get all the purchaseOrderList where lastModified is null
        defaultPurchaseOrderShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseOrderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseOrderList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the purchaseOrderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModifiedBy is not null
        defaultPurchaseOrderShouldBeFound("lastModifiedBy.specified=true");

        // Get all the purchaseOrderList where lastModifiedBy is null
        defaultPurchaseOrderShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseOrderList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the purchaseOrderList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultPurchaseOrderShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultPurchaseOrderShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseOrderList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultPurchaseOrderShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultPurchaseOrderShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseOrderList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultPurchaseOrderShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultPurchaseOrderShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the purchaseOrderList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultPurchaseOrderShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField1 is not null
        defaultPurchaseOrderShouldBeFound("freeField1.specified=true");

        // Get all the purchaseOrderList where freeField1 is null
        defaultPurchaseOrderShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultPurchaseOrderShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseOrderList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultPurchaseOrderShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultPurchaseOrderShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the purchaseOrderList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultPurchaseOrderShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultPurchaseOrderShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseOrderList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultPurchaseOrderShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultPurchaseOrderShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseOrderList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultPurchaseOrderShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultPurchaseOrderShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the purchaseOrderList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultPurchaseOrderShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField2 is not null
        defaultPurchaseOrderShouldBeFound("freeField2.specified=true");

        // Get all the purchaseOrderList where freeField2 is null
        defaultPurchaseOrderShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultPurchaseOrderShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseOrderList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultPurchaseOrderShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultPurchaseOrderShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the purchaseOrderList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultPurchaseOrderShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultPurchaseOrderShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the purchaseOrderList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultPurchaseOrderShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultPurchaseOrderShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the purchaseOrderList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultPurchaseOrderShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultPurchaseOrderShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the purchaseOrderList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultPurchaseOrderShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField3 is not null
        defaultPurchaseOrderShouldBeFound("freeField3.specified=true");

        // Get all the purchaseOrderList where freeField3 is null
        defaultPurchaseOrderShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultPurchaseOrderShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the purchaseOrderList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultPurchaseOrderShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultPurchaseOrderShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the purchaseOrderList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultPurchaseOrderShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultPurchaseOrderShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the purchaseOrderList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultPurchaseOrderShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultPurchaseOrderShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the purchaseOrderList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultPurchaseOrderShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultPurchaseOrderShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the purchaseOrderList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultPurchaseOrderShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField4 is not null
        defaultPurchaseOrderShouldBeFound("freeField4.specified=true");

        // Get all the purchaseOrderList where freeField4 is null
        defaultPurchaseOrderShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultPurchaseOrderShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the purchaseOrderList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultPurchaseOrderShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        // Get all the purchaseOrderList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultPurchaseOrderShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the purchaseOrderList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultPurchaseOrderShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByPurchaseOrderDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        PurchaseOrderDetails purchaseOrderDetails;
        if (TestUtil.findAll(em, PurchaseOrderDetails.class).isEmpty()) {
            purchaseOrderDetails = PurchaseOrderDetailsResourceIT.createEntity(em);
            em.persist(purchaseOrderDetails);
            em.flush();
        } else {
            purchaseOrderDetails = TestUtil.findAll(em, PurchaseOrderDetails.class).get(0);
        }
        em.persist(purchaseOrderDetails);
        em.flush();
        purchaseOrder.addPurchaseOrderDetails(purchaseOrderDetails);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long purchaseOrderDetailsId = purchaseOrderDetails.getId();

        // Get all the purchaseOrderList where purchaseOrderDetails equals to purchaseOrderDetailsId
        defaultPurchaseOrderShouldBeFound("purchaseOrderDetailsId.equals=" + purchaseOrderDetailsId);

        // Get all the purchaseOrderList where purchaseOrderDetails equals to (purchaseOrderDetailsId + 1)
        defaultPurchaseOrderShouldNotBeFound("purchaseOrderDetailsId.equals=" + (purchaseOrderDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByGoodsRecivedIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        GoodsRecived goodsRecived;
        if (TestUtil.findAll(em, GoodsRecived.class).isEmpty()) {
            goodsRecived = GoodsRecivedResourceIT.createEntity(em);
            em.persist(goodsRecived);
            em.flush();
        } else {
            goodsRecived = TestUtil.findAll(em, GoodsRecived.class).get(0);
        }
        em.persist(goodsRecived);
        em.flush();
        purchaseOrder.addGoodsRecived(goodsRecived);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long goodsRecivedId = goodsRecived.getId();

        // Get all the purchaseOrderList where goodsRecived equals to goodsRecivedId
        defaultPurchaseOrderShouldBeFound("goodsRecivedId.equals=" + goodsRecivedId);

        // Get all the purchaseOrderList where goodsRecived equals to (goodsRecivedId + 1)
        defaultPurchaseOrderShouldNotBeFound("goodsRecivedId.equals=" + (goodsRecivedId + 1));
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByProductInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        ProductInventory productInventory;
        if (TestUtil.findAll(em, ProductInventory.class).isEmpty()) {
            productInventory = ProductInventoryResourceIT.createEntity(em);
            em.persist(productInventory);
            em.flush();
        } else {
            productInventory = TestUtil.findAll(em, ProductInventory.class).get(0);
        }
        em.persist(productInventory);
        em.flush();
        purchaseOrder.setProductInventory(productInventory);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long productInventoryId = productInventory.getId();

        // Get all the purchaseOrderList where productInventory equals to productInventoryId
        defaultPurchaseOrderShouldBeFound("productInventoryId.equals=" + productInventoryId);

        // Get all the purchaseOrderList where productInventory equals to (productInventoryId + 1)
        defaultPurchaseOrderShouldNotBeFound("productInventoryId.equals=" + (productInventoryId + 1));
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Warehouse warehouse;
        if (TestUtil.findAll(em, Warehouse.class).isEmpty()) {
            warehouse = WarehouseResourceIT.createEntity(em);
            em.persist(warehouse);
            em.flush();
        } else {
            warehouse = TestUtil.findAll(em, Warehouse.class).get(0);
        }
        em.persist(warehouse);
        em.flush();
        purchaseOrder.setWarehouse(warehouse);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long warehouseId = warehouse.getId();

        // Get all the purchaseOrderList where warehouse equals to warehouseId
        defaultPurchaseOrderShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the purchaseOrderList where warehouse equals to (warehouseId + 1)
        defaultPurchaseOrderShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        SecurityUser securityUser;
        if (TestUtil.findAll(em, SecurityUser.class).isEmpty()) {
            securityUser = SecurityUserResourceIT.createEntity(em);
            em.persist(securityUser);
            em.flush();
        } else {
            securityUser = TestUtil.findAll(em, SecurityUser.class).get(0);
        }
        em.persist(securityUser);
        em.flush();
        purchaseOrder.setSecurityUser(securityUser);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long securityUserId = securityUser.getId();

        // Get all the purchaseOrderList where securityUser equals to securityUserId
        defaultPurchaseOrderShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the purchaseOrderList where securityUser equals to (securityUserId + 1)
        defaultPurchaseOrderShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllPurchaseOrdersByRawMaterialOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        RawMaterialOrder rawMaterialOrder;
        if (TestUtil.findAll(em, RawMaterialOrder.class).isEmpty()) {
            rawMaterialOrder = RawMaterialOrderResourceIT.createEntity(em);
            em.persist(rawMaterialOrder);
            em.flush();
        } else {
            rawMaterialOrder = TestUtil.findAll(em, RawMaterialOrder.class).get(0);
        }
        em.persist(rawMaterialOrder);
        em.flush();
        purchaseOrder.setRawMaterialOrder(rawMaterialOrder);
        purchaseOrderRepository.saveAndFlush(purchaseOrder);
        Long rawMaterialOrderId = rawMaterialOrder.getId();

        // Get all the purchaseOrderList where rawMaterialOrder equals to rawMaterialOrderId
        defaultPurchaseOrderShouldBeFound("rawMaterialOrderId.equals=" + rawMaterialOrderId);

        // Get all the purchaseOrderList where rawMaterialOrder equals to (rawMaterialOrderId + 1)
        defaultPurchaseOrderShouldNotBeFound("rawMaterialOrderId.equals=" + (rawMaterialOrderId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPurchaseOrderShouldBeFound(String filter) throws Exception {
        restPurchaseOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchaseOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalPOAmount").value(hasItem(DEFAULT_TOTAL_PO_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalGSTAmount").value(hasItem(DEFAULT_TOTAL_GST_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(DEFAULT_EXPECTED_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].poDate").value(hasItem(DEFAULT_PO_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restPurchaseOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPurchaseOrderShouldNotBeFound(String filter) throws Exception {
        restPurchaseOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPurchaseOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPurchaseOrder() throws Exception {
        // Get the purchaseOrder
        restPurchaseOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder
        PurchaseOrder updatedPurchaseOrder = purchaseOrderRepository.findById(purchaseOrder.getId()).get();
        // Disconnect from session so that the updates on updatedPurchaseOrder are not directly saved in db
        em.detach(updatedPurchaseOrder);
        updatedPurchaseOrder
            .totalPOAmount(UPDATED_TOTAL_PO_AMOUNT)
            .totalGSTAmount(UPDATED_TOTAL_GST_AMOUNT)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .poDate(UPDATED_PO_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(updatedPurchaseOrder);

        restPurchaseOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getTotalPOAmount()).isEqualTo(UPDATED_TOTAL_PO_AMOUNT);
        assertThat(testPurchaseOrder.getTotalGSTAmount()).isEqualTo(UPDATED_TOTAL_GST_AMOUNT);
        assertThat(testPurchaseOrder.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrder.getPoDate()).isEqualTo(UPDATED_PO_DATE);
        assertThat(testPurchaseOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testPurchaseOrder.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPurchaseOrder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchaseOrder.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPurchaseOrder.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPurchaseOrder.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPurchaseOrder.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();
        purchaseOrder.setId(count.incrementAndGet());

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, purchaseOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();
        purchaseOrder.setId(count.incrementAndGet());

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();
        purchaseOrder.setId(count.incrementAndGet());

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePurchaseOrderWithPatch() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder using partial update
        PurchaseOrder partialUpdatedPurchaseOrder = new PurchaseOrder();
        partialUpdatedPurchaseOrder.setId(purchaseOrder.getId());

        partialUpdatedPurchaseOrder
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restPurchaseOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseOrder))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getTotalPOAmount()).isEqualTo(DEFAULT_TOTAL_PO_AMOUNT);
        assertThat(testPurchaseOrder.getTotalGSTAmount()).isEqualTo(DEFAULT_TOTAL_GST_AMOUNT);
        assertThat(testPurchaseOrder.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrder.getPoDate()).isEqualTo(DEFAULT_PO_DATE);
        assertThat(testPurchaseOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testPurchaseOrder.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPurchaseOrder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPurchaseOrder.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPurchaseOrder.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPurchaseOrder.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPurchaseOrder.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdatePurchaseOrderWithPatch() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();

        // Update the purchaseOrder using partial update
        PurchaseOrder partialUpdatedPurchaseOrder = new PurchaseOrder();
        partialUpdatedPurchaseOrder.setId(purchaseOrder.getId());

        partialUpdatedPurchaseOrder
            .totalPOAmount(UPDATED_TOTAL_PO_AMOUNT)
            .totalGSTAmount(UPDATED_TOTAL_GST_AMOUNT)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .poDate(UPDATED_PO_DATE)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restPurchaseOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPurchaseOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPurchaseOrder))
            )
            .andExpect(status().isOk());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
        PurchaseOrder testPurchaseOrder = purchaseOrderList.get(purchaseOrderList.size() - 1);
        assertThat(testPurchaseOrder.getTotalPOAmount()).isEqualTo(UPDATED_TOTAL_PO_AMOUNT);
        assertThat(testPurchaseOrder.getTotalGSTAmount()).isEqualTo(UPDATED_TOTAL_GST_AMOUNT);
        assertThat(testPurchaseOrder.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testPurchaseOrder.getPoDate()).isEqualTo(UPDATED_PO_DATE);
        assertThat(testPurchaseOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testPurchaseOrder.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testPurchaseOrder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPurchaseOrder.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPurchaseOrder.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPurchaseOrder.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPurchaseOrder.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();
        purchaseOrder.setId(count.incrementAndGet());

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, purchaseOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();
        purchaseOrder.setId(count.incrementAndGet());

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPurchaseOrder() throws Exception {
        int databaseSizeBeforeUpdate = purchaseOrderRepository.findAll().size();
        purchaseOrder.setId(count.incrementAndGet());

        // Create the PurchaseOrder
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderMapper.toDto(purchaseOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPurchaseOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(purchaseOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PurchaseOrder in the database
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePurchaseOrder() throws Exception {
        // Initialize the database
        purchaseOrderRepository.saveAndFlush(purchaseOrder);

        int databaseSizeBeforeDelete = purchaseOrderRepository.findAll().size();

        // Delete the purchaseOrder
        restPurchaseOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, purchaseOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        assertThat(purchaseOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
