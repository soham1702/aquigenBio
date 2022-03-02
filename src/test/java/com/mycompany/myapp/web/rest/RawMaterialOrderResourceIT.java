package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.PurchaseOrder;
import com.mycompany.myapp.domain.RawMaterialOrder;
import com.mycompany.myapp.repository.RawMaterialOrderRepository;
import com.mycompany.myapp.service.criteria.RawMaterialOrderCriteria;
import com.mycompany.myapp.service.dto.RawMaterialOrderDTO;
import com.mycompany.myapp.service.mapper.RawMaterialOrderMapper;
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
 * Integration tests for the {@link RawMaterialOrderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RawMaterialOrderResourceIT {

    private static final Double DEFAULT_PRICE_PER_UNIT = 1D;
    private static final Double UPDATED_PRICE_PER_UNIT = 2D;
    private static final Double SMALLER_PRICE_PER_UNIT = 1D - 1D;

    private static final String DEFAULT_QUANTITY_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY_UNIT = "BBBBBBBBBB";

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    private static final Instant DEFAULT_DELIVERY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELIVERY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_QUANTITY_CHECK = "AAAAAAAAAA";
    private static final String UPDATED_QUANTITY_CHECK = "BBBBBBBBBB";

    private static final Instant DEFAULT_ORDERED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDERED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/raw-material-orders";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RawMaterialOrderRepository rawMaterialOrderRepository;

    @Autowired
    private RawMaterialOrderMapper rawMaterialOrderMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRawMaterialOrderMockMvc;

    private RawMaterialOrder rawMaterialOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterialOrder createEntity(EntityManager em) {
        RawMaterialOrder rawMaterialOrder = new RawMaterialOrder()
            .pricePerUnit(DEFAULT_PRICE_PER_UNIT)
            .quantityUnit(DEFAULT_QUANTITY_UNIT)
            .quantity(DEFAULT_QUANTITY)
            .deliveryDate(DEFAULT_DELIVERY_DATE)
            .quantityCheck(DEFAULT_QUANTITY_CHECK)
            .orderedOn(DEFAULT_ORDERED_ON)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return rawMaterialOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RawMaterialOrder createUpdatedEntity(EntityManager em) {
        RawMaterialOrder rawMaterialOrder = new RawMaterialOrder()
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .quantityUnit(UPDATED_QUANTITY_UNIT)
            .quantity(UPDATED_QUANTITY)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .quantityCheck(UPDATED_QUANTITY_CHECK)
            .orderedOn(UPDATED_ORDERED_ON)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return rawMaterialOrder;
    }

    @BeforeEach
    public void initTest() {
        rawMaterialOrder = createEntity(em);
    }

    @Test
    @Transactional
    void createRawMaterialOrder() throws Exception {
        int databaseSizeBeforeCreate = rawMaterialOrderRepository.findAll().size();
        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);
        restRawMaterialOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeCreate + 1);
        RawMaterialOrder testRawMaterialOrder = rawMaterialOrderList.get(rawMaterialOrderList.size() - 1);
        assertThat(testRawMaterialOrder.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testRawMaterialOrder.getQuantityUnit()).isEqualTo(DEFAULT_QUANTITY_UNIT);
        assertThat(testRawMaterialOrder.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testRawMaterialOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testRawMaterialOrder.getQuantityCheck()).isEqualTo(DEFAULT_QUANTITY_CHECK);
        assertThat(testRawMaterialOrder.getOrderedOn()).isEqualTo(DEFAULT_ORDERED_ON);
        assertThat(testRawMaterialOrder.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testRawMaterialOrder.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRawMaterialOrder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRawMaterialOrder.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testRawMaterialOrder.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRawMaterialOrder.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testRawMaterialOrder.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createRawMaterialOrderWithExistingId() throws Exception {
        // Create the RawMaterialOrder with an existing ID
        rawMaterialOrder.setId(1L);
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        int databaseSizeBeforeCreate = rawMaterialOrderRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRawMaterialOrderMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrders() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList
        restRawMaterialOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterialOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityUnit").value(hasItem(DEFAULT_QUANTITY_UNIT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantityCheck").value(hasItem(DEFAULT_QUANTITY_CHECK)))
            .andExpect(jsonPath("$.[*].orderedOn").value(hasItem(DEFAULT_ORDERED_ON.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getRawMaterialOrder() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get the rawMaterialOrder
        restRawMaterialOrderMockMvc
            .perform(get(ENTITY_API_URL_ID, rawMaterialOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(rawMaterialOrder.getId().intValue()))
            .andExpect(jsonPath("$.pricePerUnit").value(DEFAULT_PRICE_PER_UNIT.doubleValue()))
            .andExpect(jsonPath("$.quantityUnit").value(DEFAULT_QUANTITY_UNIT))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.deliveryDate").value(DEFAULT_DELIVERY_DATE.toString()))
            .andExpect(jsonPath("$.quantityCheck").value(DEFAULT_QUANTITY_CHECK))
            .andExpect(jsonPath("$.orderedOn").value(DEFAULT_ORDERED_ON.toString()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getRawMaterialOrdersByIdFiltering() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        Long id = rawMaterialOrder.getId();

        defaultRawMaterialOrderShouldBeFound("id.equals=" + id);
        defaultRawMaterialOrderShouldNotBeFound("id.notEquals=" + id);

        defaultRawMaterialOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRawMaterialOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultRawMaterialOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRawMaterialOrderShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit equals to DEFAULT_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.equals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.equals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit not equals to DEFAULT_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.notEquals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit not equals to UPDATED_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.notEquals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit in DEFAULT_PRICE_PER_UNIT or UPDATED_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.in=" + DEFAULT_PRICE_PER_UNIT + "," + UPDATED_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.in=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit is not null
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.specified=true");

        // Get all the rawMaterialOrderList where pricePerUnit is null
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit is greater than or equal to DEFAULT_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.greaterThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit is greater than or equal to UPDATED_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.greaterThanOrEqual=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit is less than or equal to DEFAULT_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.lessThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit is less than or equal to SMALLER_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.lessThanOrEqual=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit is less than DEFAULT_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.lessThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit is less than UPDATED_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.lessThan=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPricePerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where pricePerUnit is greater than DEFAULT_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldNotBeFound("pricePerUnit.greaterThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the rawMaterialOrderList where pricePerUnit is greater than SMALLER_PRICE_PER_UNIT
        defaultRawMaterialOrderShouldBeFound("pricePerUnit.greaterThan=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityUnit equals to DEFAULT_QUANTITY_UNIT
        defaultRawMaterialOrderShouldBeFound("quantityUnit.equals=" + DEFAULT_QUANTITY_UNIT);

        // Get all the rawMaterialOrderList where quantityUnit equals to UPDATED_QUANTITY_UNIT
        defaultRawMaterialOrderShouldNotBeFound("quantityUnit.equals=" + UPDATED_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityUnit not equals to DEFAULT_QUANTITY_UNIT
        defaultRawMaterialOrderShouldNotBeFound("quantityUnit.notEquals=" + DEFAULT_QUANTITY_UNIT);

        // Get all the rawMaterialOrderList where quantityUnit not equals to UPDATED_QUANTITY_UNIT
        defaultRawMaterialOrderShouldBeFound("quantityUnit.notEquals=" + UPDATED_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityUnitIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityUnit in DEFAULT_QUANTITY_UNIT or UPDATED_QUANTITY_UNIT
        defaultRawMaterialOrderShouldBeFound("quantityUnit.in=" + DEFAULT_QUANTITY_UNIT + "," + UPDATED_QUANTITY_UNIT);

        // Get all the rawMaterialOrderList where quantityUnit equals to UPDATED_QUANTITY_UNIT
        defaultRawMaterialOrderShouldNotBeFound("quantityUnit.in=" + UPDATED_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityUnit is not null
        defaultRawMaterialOrderShouldBeFound("quantityUnit.specified=true");

        // Get all the rawMaterialOrderList where quantityUnit is null
        defaultRawMaterialOrderShouldNotBeFound("quantityUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityUnitContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityUnit contains DEFAULT_QUANTITY_UNIT
        defaultRawMaterialOrderShouldBeFound("quantityUnit.contains=" + DEFAULT_QUANTITY_UNIT);

        // Get all the rawMaterialOrderList where quantityUnit contains UPDATED_QUANTITY_UNIT
        defaultRawMaterialOrderShouldNotBeFound("quantityUnit.contains=" + UPDATED_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityUnitNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityUnit does not contain DEFAULT_QUANTITY_UNIT
        defaultRawMaterialOrderShouldNotBeFound("quantityUnit.doesNotContain=" + DEFAULT_QUANTITY_UNIT);

        // Get all the rawMaterialOrderList where quantityUnit does not contain UPDATED_QUANTITY_UNIT
        defaultRawMaterialOrderShouldBeFound("quantityUnit.doesNotContain=" + UPDATED_QUANTITY_UNIT);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity equals to DEFAULT_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the rawMaterialOrderList where quantity equals to UPDATED_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity not equals to DEFAULT_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the rawMaterialOrderList where quantity not equals to UPDATED_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the rawMaterialOrderList where quantity equals to UPDATED_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity is not null
        defaultRawMaterialOrderShouldBeFound("quantity.specified=true");

        // Get all the rawMaterialOrderList where quantity is null
        defaultRawMaterialOrderShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the rawMaterialOrderList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the rawMaterialOrderList where quantity is less than or equal to SMALLER_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity is less than DEFAULT_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the rawMaterialOrderList where quantity is less than UPDATED_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantity is greater than DEFAULT_QUANTITY
        defaultRawMaterialOrderShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the rawMaterialOrderList where quantity is greater than SMALLER_QUANTITY
        defaultRawMaterialOrderShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where deliveryDate equals to DEFAULT_DELIVERY_DATE
        defaultRawMaterialOrderShouldBeFound("deliveryDate.equals=" + DEFAULT_DELIVERY_DATE);

        // Get all the rawMaterialOrderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultRawMaterialOrderShouldNotBeFound("deliveryDate.equals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where deliveryDate not equals to DEFAULT_DELIVERY_DATE
        defaultRawMaterialOrderShouldNotBeFound("deliveryDate.notEquals=" + DEFAULT_DELIVERY_DATE);

        // Get all the rawMaterialOrderList where deliveryDate not equals to UPDATED_DELIVERY_DATE
        defaultRawMaterialOrderShouldBeFound("deliveryDate.notEquals=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where deliveryDate in DEFAULT_DELIVERY_DATE or UPDATED_DELIVERY_DATE
        defaultRawMaterialOrderShouldBeFound("deliveryDate.in=" + DEFAULT_DELIVERY_DATE + "," + UPDATED_DELIVERY_DATE);

        // Get all the rawMaterialOrderList where deliveryDate equals to UPDATED_DELIVERY_DATE
        defaultRawMaterialOrderShouldNotBeFound("deliveryDate.in=" + UPDATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where deliveryDate is not null
        defaultRawMaterialOrderShouldBeFound("deliveryDate.specified=true");

        // Get all the rawMaterialOrderList where deliveryDate is null
        defaultRawMaterialOrderShouldNotBeFound("deliveryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityCheckIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityCheck equals to DEFAULT_QUANTITY_CHECK
        defaultRawMaterialOrderShouldBeFound("quantityCheck.equals=" + DEFAULT_QUANTITY_CHECK);

        // Get all the rawMaterialOrderList where quantityCheck equals to UPDATED_QUANTITY_CHECK
        defaultRawMaterialOrderShouldNotBeFound("quantityCheck.equals=" + UPDATED_QUANTITY_CHECK);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityCheckIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityCheck not equals to DEFAULT_QUANTITY_CHECK
        defaultRawMaterialOrderShouldNotBeFound("quantityCheck.notEquals=" + DEFAULT_QUANTITY_CHECK);

        // Get all the rawMaterialOrderList where quantityCheck not equals to UPDATED_QUANTITY_CHECK
        defaultRawMaterialOrderShouldBeFound("quantityCheck.notEquals=" + UPDATED_QUANTITY_CHECK);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityCheckIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityCheck in DEFAULT_QUANTITY_CHECK or UPDATED_QUANTITY_CHECK
        defaultRawMaterialOrderShouldBeFound("quantityCheck.in=" + DEFAULT_QUANTITY_CHECK + "," + UPDATED_QUANTITY_CHECK);

        // Get all the rawMaterialOrderList where quantityCheck equals to UPDATED_QUANTITY_CHECK
        defaultRawMaterialOrderShouldNotBeFound("quantityCheck.in=" + UPDATED_QUANTITY_CHECK);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityCheckIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityCheck is not null
        defaultRawMaterialOrderShouldBeFound("quantityCheck.specified=true");

        // Get all the rawMaterialOrderList where quantityCheck is null
        defaultRawMaterialOrderShouldNotBeFound("quantityCheck.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityCheckContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityCheck contains DEFAULT_QUANTITY_CHECK
        defaultRawMaterialOrderShouldBeFound("quantityCheck.contains=" + DEFAULT_QUANTITY_CHECK);

        // Get all the rawMaterialOrderList where quantityCheck contains UPDATED_QUANTITY_CHECK
        defaultRawMaterialOrderShouldNotBeFound("quantityCheck.contains=" + UPDATED_QUANTITY_CHECK);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByQuantityCheckNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where quantityCheck does not contain DEFAULT_QUANTITY_CHECK
        defaultRawMaterialOrderShouldNotBeFound("quantityCheck.doesNotContain=" + DEFAULT_QUANTITY_CHECK);

        // Get all the rawMaterialOrderList where quantityCheck does not contain UPDATED_QUANTITY_CHECK
        defaultRawMaterialOrderShouldBeFound("quantityCheck.doesNotContain=" + UPDATED_QUANTITY_CHECK);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderedOn equals to DEFAULT_ORDERED_ON
        defaultRawMaterialOrderShouldBeFound("orderedOn.equals=" + DEFAULT_ORDERED_ON);

        // Get all the rawMaterialOrderList where orderedOn equals to UPDATED_ORDERED_ON
        defaultRawMaterialOrderShouldNotBeFound("orderedOn.equals=" + UPDATED_ORDERED_ON);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderedOn not equals to DEFAULT_ORDERED_ON
        defaultRawMaterialOrderShouldNotBeFound("orderedOn.notEquals=" + DEFAULT_ORDERED_ON);

        // Get all the rawMaterialOrderList where orderedOn not equals to UPDATED_ORDERED_ON
        defaultRawMaterialOrderShouldBeFound("orderedOn.notEquals=" + UPDATED_ORDERED_ON);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderedOnIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderedOn in DEFAULT_ORDERED_ON or UPDATED_ORDERED_ON
        defaultRawMaterialOrderShouldBeFound("orderedOn.in=" + DEFAULT_ORDERED_ON + "," + UPDATED_ORDERED_ON);

        // Get all the rawMaterialOrderList where orderedOn equals to UPDATED_ORDERED_ON
        defaultRawMaterialOrderShouldNotBeFound("orderedOn.in=" + UPDATED_ORDERED_ON);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderedOn is not null
        defaultRawMaterialOrderShouldBeFound("orderedOn.specified=true");

        // Get all the rawMaterialOrderList where orderedOn is null
        defaultRawMaterialOrderShouldNotBeFound("orderedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderStatus equals to DEFAULT_ORDER_STATUS
        defaultRawMaterialOrderShouldBeFound("orderStatus.equals=" + DEFAULT_ORDER_STATUS);

        // Get all the rawMaterialOrderList where orderStatus equals to UPDATED_ORDER_STATUS
        defaultRawMaterialOrderShouldNotBeFound("orderStatus.equals=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderStatus not equals to DEFAULT_ORDER_STATUS
        defaultRawMaterialOrderShouldNotBeFound("orderStatus.notEquals=" + DEFAULT_ORDER_STATUS);

        // Get all the rawMaterialOrderList where orderStatus not equals to UPDATED_ORDER_STATUS
        defaultRawMaterialOrderShouldBeFound("orderStatus.notEquals=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderStatusIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderStatus in DEFAULT_ORDER_STATUS or UPDATED_ORDER_STATUS
        defaultRawMaterialOrderShouldBeFound("orderStatus.in=" + DEFAULT_ORDER_STATUS + "," + UPDATED_ORDER_STATUS);

        // Get all the rawMaterialOrderList where orderStatus equals to UPDATED_ORDER_STATUS
        defaultRawMaterialOrderShouldNotBeFound("orderStatus.in=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderStatus is not null
        defaultRawMaterialOrderShouldBeFound("orderStatus.specified=true");

        // Get all the rawMaterialOrderList where orderStatus is null
        defaultRawMaterialOrderShouldNotBeFound("orderStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderStatusContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderStatus contains DEFAULT_ORDER_STATUS
        defaultRawMaterialOrderShouldBeFound("orderStatus.contains=" + DEFAULT_ORDER_STATUS);

        // Get all the rawMaterialOrderList where orderStatus contains UPDATED_ORDER_STATUS
        defaultRawMaterialOrderShouldNotBeFound("orderStatus.contains=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByOrderStatusNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where orderStatus does not contain DEFAULT_ORDER_STATUS
        defaultRawMaterialOrderShouldNotBeFound("orderStatus.doesNotContain=" + DEFAULT_ORDER_STATUS);

        // Get all the rawMaterialOrderList where orderStatus does not contain UPDATED_ORDER_STATUS
        defaultRawMaterialOrderShouldBeFound("orderStatus.doesNotContain=" + UPDATED_ORDER_STATUS);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultRawMaterialOrderShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the rawMaterialOrderList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRawMaterialOrderShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultRawMaterialOrderShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the rawMaterialOrderList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultRawMaterialOrderShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultRawMaterialOrderShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the rawMaterialOrderList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultRawMaterialOrderShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModified is not null
        defaultRawMaterialOrderShouldBeFound("lastModified.specified=true");

        // Get all the rawMaterialOrderList where lastModified is null
        defaultRawMaterialOrderShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialOrderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialOrderList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the rawMaterialOrderList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModifiedBy is not null
        defaultRawMaterialOrderShouldBeFound("lastModifiedBy.specified=true");

        // Get all the rawMaterialOrderList where lastModifiedBy is null
        defaultRawMaterialOrderShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialOrderList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the rawMaterialOrderList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultRawMaterialOrderShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultRawMaterialOrderShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialOrderList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRawMaterialOrderShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultRawMaterialOrderShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialOrderList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultRawMaterialOrderShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultRawMaterialOrderShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the rawMaterialOrderList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultRawMaterialOrderShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField1 is not null
        defaultRawMaterialOrderShouldBeFound("freeField1.specified=true");

        // Get all the rawMaterialOrderList where freeField1 is null
        defaultRawMaterialOrderShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultRawMaterialOrderShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialOrderList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultRawMaterialOrderShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultRawMaterialOrderShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the rawMaterialOrderList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultRawMaterialOrderShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultRawMaterialOrderShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialOrderList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRawMaterialOrderShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultRawMaterialOrderShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialOrderList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultRawMaterialOrderShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultRawMaterialOrderShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the rawMaterialOrderList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultRawMaterialOrderShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField2 is not null
        defaultRawMaterialOrderShouldBeFound("freeField2.specified=true");

        // Get all the rawMaterialOrderList where freeField2 is null
        defaultRawMaterialOrderShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultRawMaterialOrderShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialOrderList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultRawMaterialOrderShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultRawMaterialOrderShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the rawMaterialOrderList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultRawMaterialOrderShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultRawMaterialOrderShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialOrderList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRawMaterialOrderShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultRawMaterialOrderShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialOrderList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultRawMaterialOrderShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultRawMaterialOrderShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the rawMaterialOrderList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultRawMaterialOrderShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField3 is not null
        defaultRawMaterialOrderShouldBeFound("freeField3.specified=true");

        // Get all the rawMaterialOrderList where freeField3 is null
        defaultRawMaterialOrderShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultRawMaterialOrderShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialOrderList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultRawMaterialOrderShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultRawMaterialOrderShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the rawMaterialOrderList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultRawMaterialOrderShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultRawMaterialOrderShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialOrderList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRawMaterialOrderShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultRawMaterialOrderShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialOrderList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultRawMaterialOrderShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultRawMaterialOrderShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the rawMaterialOrderList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultRawMaterialOrderShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField4 is not null
        defaultRawMaterialOrderShouldBeFound("freeField4.specified=true");

        // Get all the rawMaterialOrderList where freeField4 is null
        defaultRawMaterialOrderShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultRawMaterialOrderShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialOrderList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultRawMaterialOrderShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        // Get all the rawMaterialOrderList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultRawMaterialOrderShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the rawMaterialOrderList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultRawMaterialOrderShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);
        PurchaseOrder purchaseOrder;
        if (TestUtil.findAll(em, PurchaseOrder.class).isEmpty()) {
            purchaseOrder = PurchaseOrderResourceIT.createEntity(em);
            em.persist(purchaseOrder);
            em.flush();
        } else {
            purchaseOrder = TestUtil.findAll(em, PurchaseOrder.class).get(0);
        }
        em.persist(purchaseOrder);
        em.flush();
        rawMaterialOrder.addPurchaseOrder(purchaseOrder);
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the rawMaterialOrderList where purchaseOrder equals to purchaseOrderId
        defaultRawMaterialOrderShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the rawMaterialOrderList where purchaseOrder equals to (purchaseOrderId + 1)
        defaultRawMaterialOrderShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    @Test
    @Transactional
    void getAllRawMaterialOrdersByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        rawMaterialOrder.addProduct(product);
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);
        Long productId = product.getId();

        // Get all the rawMaterialOrderList where product equals to productId
        defaultRawMaterialOrderShouldBeFound("productId.equals=" + productId);

        // Get all the rawMaterialOrderList where product equals to (productId + 1)
        defaultRawMaterialOrderShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRawMaterialOrderShouldBeFound(String filter) throws Exception {
        restRawMaterialOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rawMaterialOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].quantityUnit").value(hasItem(DEFAULT_QUANTITY_UNIT)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].deliveryDate").value(hasItem(DEFAULT_DELIVERY_DATE.toString())))
            .andExpect(jsonPath("$.[*].quantityCheck").value(hasItem(DEFAULT_QUANTITY_CHECK)))
            .andExpect(jsonPath("$.[*].orderedOn").value(hasItem(DEFAULT_ORDERED_ON.toString())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restRawMaterialOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRawMaterialOrderShouldNotBeFound(String filter) throws Exception {
        restRawMaterialOrderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRawMaterialOrderMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingRawMaterialOrder() throws Exception {
        // Get the rawMaterialOrder
        restRawMaterialOrderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewRawMaterialOrder() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();

        // Update the rawMaterialOrder
        RawMaterialOrder updatedRawMaterialOrder = rawMaterialOrderRepository.findById(rawMaterialOrder.getId()).get();
        // Disconnect from session so that the updates on updatedRawMaterialOrder are not directly saved in db
        em.detach(updatedRawMaterialOrder);
        updatedRawMaterialOrder
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .quantityUnit(UPDATED_QUANTITY_UNIT)
            .quantity(UPDATED_QUANTITY)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .quantityCheck(UPDATED_QUANTITY_CHECK)
            .orderedOn(UPDATED_ORDERED_ON)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(updatedRawMaterialOrder);

        restRawMaterialOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterialOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialOrder testRawMaterialOrder = rawMaterialOrderList.get(rawMaterialOrderList.size() - 1);
        assertThat(testRawMaterialOrder.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testRawMaterialOrder.getQuantityUnit()).isEqualTo(UPDATED_QUANTITY_UNIT);
        assertThat(testRawMaterialOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRawMaterialOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testRawMaterialOrder.getQuantityCheck()).isEqualTo(UPDATED_QUANTITY_CHECK);
        assertThat(testRawMaterialOrder.getOrderedOn()).isEqualTo(UPDATED_ORDERED_ON);
        assertThat(testRawMaterialOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testRawMaterialOrder.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRawMaterialOrder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRawMaterialOrder.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRawMaterialOrder.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRawMaterialOrder.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRawMaterialOrder.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingRawMaterialOrder() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();
        rawMaterialOrder.setId(count.incrementAndGet());

        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, rawMaterialOrderDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRawMaterialOrder() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();
        rawMaterialOrder.setId(count.incrementAndGet());

        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialOrderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRawMaterialOrder() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();
        rawMaterialOrder.setId(count.incrementAndGet());

        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialOrderMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRawMaterialOrderWithPatch() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();

        // Update the rawMaterialOrder using partial update
        RawMaterialOrder partialUpdatedRawMaterialOrder = new RawMaterialOrder();
        partialUpdatedRawMaterialOrder.setId(rawMaterialOrder.getId());

        partialUpdatedRawMaterialOrder
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .quantity(UPDATED_QUANTITY)
            .orderedOn(UPDATED_ORDERED_ON)
            .orderStatus(UPDATED_ORDER_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restRawMaterialOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterialOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterialOrder))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialOrder testRawMaterialOrder = rawMaterialOrderList.get(rawMaterialOrderList.size() - 1);
        assertThat(testRawMaterialOrder.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testRawMaterialOrder.getQuantityUnit()).isEqualTo(DEFAULT_QUANTITY_UNIT);
        assertThat(testRawMaterialOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRawMaterialOrder.getDeliveryDate()).isEqualTo(DEFAULT_DELIVERY_DATE);
        assertThat(testRawMaterialOrder.getQuantityCheck()).isEqualTo(DEFAULT_QUANTITY_CHECK);
        assertThat(testRawMaterialOrder.getOrderedOn()).isEqualTo(UPDATED_ORDERED_ON);
        assertThat(testRawMaterialOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testRawMaterialOrder.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testRawMaterialOrder.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testRawMaterialOrder.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRawMaterialOrder.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testRawMaterialOrder.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRawMaterialOrder.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateRawMaterialOrderWithPatch() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();

        // Update the rawMaterialOrder using partial update
        RawMaterialOrder partialUpdatedRawMaterialOrder = new RawMaterialOrder();
        partialUpdatedRawMaterialOrder.setId(rawMaterialOrder.getId());

        partialUpdatedRawMaterialOrder
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .quantityUnit(UPDATED_QUANTITY_UNIT)
            .quantity(UPDATED_QUANTITY)
            .deliveryDate(UPDATED_DELIVERY_DATE)
            .quantityCheck(UPDATED_QUANTITY_CHECK)
            .orderedOn(UPDATED_ORDERED_ON)
            .orderStatus(UPDATED_ORDER_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restRawMaterialOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRawMaterialOrder.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRawMaterialOrder))
            )
            .andExpect(status().isOk());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
        RawMaterialOrder testRawMaterialOrder = rawMaterialOrderList.get(rawMaterialOrderList.size() - 1);
        assertThat(testRawMaterialOrder.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testRawMaterialOrder.getQuantityUnit()).isEqualTo(UPDATED_QUANTITY_UNIT);
        assertThat(testRawMaterialOrder.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testRawMaterialOrder.getDeliveryDate()).isEqualTo(UPDATED_DELIVERY_DATE);
        assertThat(testRawMaterialOrder.getQuantityCheck()).isEqualTo(UPDATED_QUANTITY_CHECK);
        assertThat(testRawMaterialOrder.getOrderedOn()).isEqualTo(UPDATED_ORDERED_ON);
        assertThat(testRawMaterialOrder.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testRawMaterialOrder.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testRawMaterialOrder.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testRawMaterialOrder.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testRawMaterialOrder.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testRawMaterialOrder.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testRawMaterialOrder.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingRawMaterialOrder() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();
        rawMaterialOrder.setId(count.incrementAndGet());

        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRawMaterialOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, rawMaterialOrderDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRawMaterialOrder() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();
        rawMaterialOrder.setId(count.incrementAndGet());

        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialOrderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRawMaterialOrder() throws Exception {
        int databaseSizeBeforeUpdate = rawMaterialOrderRepository.findAll().size();
        rawMaterialOrder.setId(count.incrementAndGet());

        // Create the RawMaterialOrder
        RawMaterialOrderDTO rawMaterialOrderDTO = rawMaterialOrderMapper.toDto(rawMaterialOrder);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRawMaterialOrderMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(rawMaterialOrderDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RawMaterialOrder in the database
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRawMaterialOrder() throws Exception {
        // Initialize the database
        rawMaterialOrderRepository.saveAndFlush(rawMaterialOrder);

        int databaseSizeBeforeDelete = rawMaterialOrderRepository.findAll().size();

        // Delete the rawMaterialOrder
        restRawMaterialOrderMockMvc
            .perform(delete(ENTITY_API_URL_ID, rawMaterialOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RawMaterialOrder> rawMaterialOrderList = rawMaterialOrderRepository.findAll();
        assertThat(rawMaterialOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
