package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Categories;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductQuatation;
import com.mycompany.myapp.domain.QuatationDetails;
import com.mycompany.myapp.domain.Unit;
import com.mycompany.myapp.repository.QuatationDetailsRepository;
import com.mycompany.myapp.service.criteria.QuatationDetailsCriteria;
import com.mycompany.myapp.service.dto.QuatationDetailsDTO;
import com.mycompany.myapp.service.mapper.QuatationDetailsMapper;
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
 * Integration tests for the {@link QuatationDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuatationDetailsResourceIT {

    private static final Double DEFAULT_AVAILABEL_STOCK = 1D;
    private static final Double UPDATED_AVAILABEL_STOCK = 2D;
    private static final Double SMALLER_AVAILABEL_STOCK = 1D - 1D;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    private static final Double DEFAULT_RATS_PER_UNIT = 1D;
    private static final Double UPDATED_RATS_PER_UNIT = 2D;
    private static final Double SMALLER_RATS_PER_UNIT = 1D - 1D;

    private static final Double DEFAULT_TOTALPRICE = 1D;
    private static final Double UPDATED_TOTALPRICE = 2D;
    private static final Double SMALLER_TOTALPRICE = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/quatation-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private QuatationDetailsRepository quatationDetailsRepository;

    @Autowired
    private QuatationDetailsMapper quatationDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuatationDetailsMockMvc;

    private QuatationDetails quatationDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuatationDetails createEntity(EntityManager em) {
        QuatationDetails quatationDetails = new QuatationDetails()
            .availabelStock(DEFAULT_AVAILABEL_STOCK)
            .quantity(DEFAULT_QUANTITY)
            .ratsPerUnit(DEFAULT_RATS_PER_UNIT)
            .totalprice(DEFAULT_TOTALPRICE)
            .discount(DEFAULT_DISCOUNT)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return quatationDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuatationDetails createUpdatedEntity(EntityManager em) {
        QuatationDetails quatationDetails = new QuatationDetails()
            .availabelStock(UPDATED_AVAILABEL_STOCK)
            .quantity(UPDATED_QUANTITY)
            .ratsPerUnit(UPDATED_RATS_PER_UNIT)
            .totalprice(UPDATED_TOTALPRICE)
            .discount(UPDATED_DISCOUNT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return quatationDetails;
    }

    @BeforeEach
    public void initTest() {
        quatationDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createQuatationDetails() throws Exception {
        int databaseSizeBeforeCreate = quatationDetailsRepository.findAll().size();
        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);
        restQuatationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        QuatationDetails testQuatationDetails = quatationDetailsList.get(quatationDetailsList.size() - 1);
        assertThat(testQuatationDetails.getAvailabelStock()).isEqualTo(DEFAULT_AVAILABEL_STOCK);
        assertThat(testQuatationDetails.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testQuatationDetails.getRatsPerUnit()).isEqualTo(DEFAULT_RATS_PER_UNIT);
        assertThat(testQuatationDetails.getTotalprice()).isEqualTo(DEFAULT_TOTALPRICE);
        assertThat(testQuatationDetails.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testQuatationDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testQuatationDetails.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testQuatationDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testQuatationDetails.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testQuatationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testQuatationDetails.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createQuatationDetailsWithExistingId() throws Exception {
        // Create the QuatationDetails with an existing ID
        quatationDetails.setId(1L);
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        int databaseSizeBeforeCreate = quatationDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuatationDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllQuatationDetails() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList
        restQuatationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quatationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].availabelStock").value(hasItem(DEFAULT_AVAILABEL_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].ratsPerUnit").value(hasItem(DEFAULT_RATS_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalprice").value(hasItem(DEFAULT_TOTALPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getQuatationDetails() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get the quatationDetails
        restQuatationDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, quatationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(quatationDetails.getId().intValue()))
            .andExpect(jsonPath("$.availabelStock").value(DEFAULT_AVAILABEL_STOCK.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.ratsPerUnit").value(DEFAULT_RATS_PER_UNIT.doubleValue()))
            .andExpect(jsonPath("$.totalprice").value(DEFAULT_TOTALPRICE.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getQuatationDetailsByIdFiltering() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        Long id = quatationDetails.getId();

        defaultQuatationDetailsShouldBeFound("id.equals=" + id);
        defaultQuatationDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultQuatationDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQuatationDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultQuatationDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQuatationDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock equals to DEFAULT_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.equals=" + DEFAULT_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock equals to UPDATED_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.equals=" + UPDATED_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock not equals to DEFAULT_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.notEquals=" + DEFAULT_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock not equals to UPDATED_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.notEquals=" + UPDATED_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock in DEFAULT_AVAILABEL_STOCK or UPDATED_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.in=" + DEFAULT_AVAILABEL_STOCK + "," + UPDATED_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock equals to UPDATED_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.in=" + UPDATED_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock is not null
        defaultQuatationDetailsShouldBeFound("availabelStock.specified=true");

        // Get all the quatationDetailsList where availabelStock is null
        defaultQuatationDetailsShouldNotBeFound("availabelStock.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock is greater than or equal to DEFAULT_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.greaterThanOrEqual=" + DEFAULT_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock is greater than or equal to UPDATED_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.greaterThanOrEqual=" + UPDATED_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock is less than or equal to DEFAULT_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.lessThanOrEqual=" + DEFAULT_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock is less than or equal to SMALLER_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.lessThanOrEqual=" + SMALLER_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsLessThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock is less than DEFAULT_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.lessThan=" + DEFAULT_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock is less than UPDATED_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.lessThan=" + UPDATED_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByAvailabelStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where availabelStock is greater than DEFAULT_AVAILABEL_STOCK
        defaultQuatationDetailsShouldNotBeFound("availabelStock.greaterThan=" + DEFAULT_AVAILABEL_STOCK);

        // Get all the quatationDetailsList where availabelStock is greater than SMALLER_AVAILABEL_STOCK
        defaultQuatationDetailsShouldBeFound("availabelStock.greaterThan=" + SMALLER_AVAILABEL_STOCK);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity equals to DEFAULT_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the quatationDetailsList where quantity equals to UPDATED_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity not equals to DEFAULT_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the quatationDetailsList where quantity not equals to UPDATED_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the quatationDetailsList where quantity equals to UPDATED_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity is not null
        defaultQuatationDetailsShouldBeFound("quantity.specified=true");

        // Get all the quatationDetailsList where quantity is null
        defaultQuatationDetailsShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the quatationDetailsList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the quatationDetailsList where quantity is less than or equal to SMALLER_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity is less than DEFAULT_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the quatationDetailsList where quantity is less than UPDATED_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where quantity is greater than DEFAULT_QUANTITY
        defaultQuatationDetailsShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the quatationDetailsList where quantity is greater than SMALLER_QUANTITY
        defaultQuatationDetailsShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit equals to DEFAULT_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.equals=" + DEFAULT_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit equals to UPDATED_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.equals=" + UPDATED_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit not equals to DEFAULT_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.notEquals=" + DEFAULT_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit not equals to UPDATED_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.notEquals=" + UPDATED_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit in DEFAULT_RATS_PER_UNIT or UPDATED_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.in=" + DEFAULT_RATS_PER_UNIT + "," + UPDATED_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit equals to UPDATED_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.in=" + UPDATED_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit is not null
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.specified=true");

        // Get all the quatationDetailsList where ratsPerUnit is null
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit is greater than or equal to DEFAULT_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.greaterThanOrEqual=" + DEFAULT_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit is greater than or equal to UPDATED_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.greaterThanOrEqual=" + UPDATED_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit is less than or equal to DEFAULT_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.lessThanOrEqual=" + DEFAULT_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit is less than or equal to SMALLER_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.lessThanOrEqual=" + SMALLER_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit is less than DEFAULT_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.lessThan=" + DEFAULT_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit is less than UPDATED_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.lessThan=" + UPDATED_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByRatsPerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where ratsPerUnit is greater than DEFAULT_RATS_PER_UNIT
        defaultQuatationDetailsShouldNotBeFound("ratsPerUnit.greaterThan=" + DEFAULT_RATS_PER_UNIT);

        // Get all the quatationDetailsList where ratsPerUnit is greater than SMALLER_RATS_PER_UNIT
        defaultQuatationDetailsShouldBeFound("ratsPerUnit.greaterThan=" + SMALLER_RATS_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice equals to DEFAULT_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.equals=" + DEFAULT_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice equals to UPDATED_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.equals=" + UPDATED_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice not equals to DEFAULT_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.notEquals=" + DEFAULT_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice not equals to UPDATED_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.notEquals=" + UPDATED_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice in DEFAULT_TOTALPRICE or UPDATED_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.in=" + DEFAULT_TOTALPRICE + "," + UPDATED_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice equals to UPDATED_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.in=" + UPDATED_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice is not null
        defaultQuatationDetailsShouldBeFound("totalprice.specified=true");

        // Get all the quatationDetailsList where totalprice is null
        defaultQuatationDetailsShouldNotBeFound("totalprice.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice is greater than or equal to DEFAULT_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.greaterThanOrEqual=" + DEFAULT_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice is greater than or equal to UPDATED_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.greaterThanOrEqual=" + UPDATED_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice is less than or equal to DEFAULT_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.lessThanOrEqual=" + DEFAULT_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice is less than or equal to SMALLER_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.lessThanOrEqual=" + SMALLER_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice is less than DEFAULT_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.lessThan=" + DEFAULT_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice is less than UPDATED_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.lessThan=" + UPDATED_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByTotalpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where totalprice is greater than DEFAULT_TOTALPRICE
        defaultQuatationDetailsShouldNotBeFound("totalprice.greaterThan=" + DEFAULT_TOTALPRICE);

        // Get all the quatationDetailsList where totalprice is greater than SMALLER_TOTALPRICE
        defaultQuatationDetailsShouldBeFound("totalprice.greaterThan=" + SMALLER_TOTALPRICE);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount equals to DEFAULT_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the quatationDetailsList where discount equals to UPDATED_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount not equals to DEFAULT_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the quatationDetailsList where discount not equals to UPDATED_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the quatationDetailsList where discount equals to UPDATED_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount is not null
        defaultQuatationDetailsShouldBeFound("discount.specified=true");

        // Get all the quatationDetailsList where discount is null
        defaultQuatationDetailsShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the quatationDetailsList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the quatationDetailsList where discount is less than or equal to SMALLER_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount is less than DEFAULT_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the quatationDetailsList where discount is less than UPDATED_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where discount is greater than DEFAULT_DISCOUNT
        defaultQuatationDetailsShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the quatationDetailsList where discount is greater than SMALLER_DISCOUNT
        defaultQuatationDetailsShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultQuatationDetailsShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the quatationDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultQuatationDetailsShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultQuatationDetailsShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the quatationDetailsList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultQuatationDetailsShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultQuatationDetailsShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the quatationDetailsList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultQuatationDetailsShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField1 is not null
        defaultQuatationDetailsShouldBeFound("freeField1.specified=true");

        // Get all the quatationDetailsList where freeField1 is null
        defaultQuatationDetailsShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultQuatationDetailsShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the quatationDetailsList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultQuatationDetailsShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultQuatationDetailsShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the quatationDetailsList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultQuatationDetailsShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultQuatationDetailsShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the quatationDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultQuatationDetailsShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultQuatationDetailsShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the quatationDetailsList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultQuatationDetailsShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultQuatationDetailsShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the quatationDetailsList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultQuatationDetailsShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField2 is not null
        defaultQuatationDetailsShouldBeFound("freeField2.specified=true");

        // Get all the quatationDetailsList where freeField2 is null
        defaultQuatationDetailsShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultQuatationDetailsShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the quatationDetailsList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultQuatationDetailsShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultQuatationDetailsShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the quatationDetailsList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultQuatationDetailsShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultQuatationDetailsShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the quatationDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultQuatationDetailsShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultQuatationDetailsShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the quatationDetailsList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultQuatationDetailsShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultQuatationDetailsShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the quatationDetailsList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultQuatationDetailsShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField3 is not null
        defaultQuatationDetailsShouldBeFound("freeField3.specified=true");

        // Get all the quatationDetailsList where freeField3 is null
        defaultQuatationDetailsShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultQuatationDetailsShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the quatationDetailsList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultQuatationDetailsShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultQuatationDetailsShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the quatationDetailsList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultQuatationDetailsShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultQuatationDetailsShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the quatationDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultQuatationDetailsShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultQuatationDetailsShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the quatationDetailsList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultQuatationDetailsShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultQuatationDetailsShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the quatationDetailsList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultQuatationDetailsShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField4 is not null
        defaultQuatationDetailsShouldBeFound("freeField4.specified=true");

        // Get all the quatationDetailsList where freeField4 is null
        defaultQuatationDetailsShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultQuatationDetailsShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the quatationDetailsList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultQuatationDetailsShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultQuatationDetailsShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the quatationDetailsList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultQuatationDetailsShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultQuatationDetailsShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the quatationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultQuatationDetailsShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultQuatationDetailsShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the quatationDetailsList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultQuatationDetailsShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultQuatationDetailsShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the quatationDetailsList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultQuatationDetailsShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModified is not null
        defaultQuatationDetailsShouldBeFound("lastModified.specified=true");

        // Get all the quatationDetailsList where lastModified is null
        defaultQuatationDetailsShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the quatationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the quatationDetailsList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the quatationDetailsList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModifiedBy is not null
        defaultQuatationDetailsShouldBeFound("lastModifiedBy.specified=true");

        // Get all the quatationDetailsList where lastModifiedBy is null
        defaultQuatationDetailsShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the quatationDetailsList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        // Get all the quatationDetailsList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the quatationDetailsList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultQuatationDetailsShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);
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
        quatationDetails.setProduct(product);
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        Long productId = product.getId();

        // Get all the quatationDetailsList where product equals to productId
        defaultQuatationDetailsShouldBeFound("productId.equals=" + productId);

        // Get all the quatationDetailsList where product equals to (productId + 1)
        defaultQuatationDetailsShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        Unit unit;
        if (TestUtil.findAll(em, Unit.class).isEmpty()) {
            unit = UnitResourceIT.createEntity(em);
            em.persist(unit);
            em.flush();
        } else {
            unit = TestUtil.findAll(em, Unit.class).get(0);
        }
        em.persist(unit);
        em.flush();
        quatationDetails.setUnit(unit);
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        Long unitId = unit.getId();

        // Get all the quatationDetailsList where unit equals to unitId
        defaultQuatationDetailsShouldBeFound("unitId.equals=" + unitId);

        // Get all the quatationDetailsList where unit equals to (unitId + 1)
        defaultQuatationDetailsShouldNotBeFound("unitId.equals=" + (unitId + 1));
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByCategoriesIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        Categories categories;
        if (TestUtil.findAll(em, Categories.class).isEmpty()) {
            categories = CategoriesResourceIT.createEntity(em);
            em.persist(categories);
            em.flush();
        } else {
            categories = TestUtil.findAll(em, Categories.class).get(0);
        }
        em.persist(categories);
        em.flush();
        quatationDetails.setCategories(categories);
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        Long categoriesId = categories.getId();

        // Get all the quatationDetailsList where categories equals to categoriesId
        defaultQuatationDetailsShouldBeFound("categoriesId.equals=" + categoriesId);

        // Get all the quatationDetailsList where categories equals to (categoriesId + 1)
        defaultQuatationDetailsShouldNotBeFound("categoriesId.equals=" + (categoriesId + 1));
    }

    @Test
    @Transactional
    void getAllQuatationDetailsByProductQuatationIsEqualToSomething() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        ProductQuatation productQuatation;
        if (TestUtil.findAll(em, ProductQuatation.class).isEmpty()) {
            productQuatation = ProductQuatationResourceIT.createEntity(em);
            em.persist(productQuatation);
            em.flush();
        } else {
            productQuatation = TestUtil.findAll(em, ProductQuatation.class).get(0);
        }
        em.persist(productQuatation);
        em.flush();
        quatationDetails.setProductQuatation(productQuatation);
        quatationDetailsRepository.saveAndFlush(quatationDetails);
        Long productQuatationId = productQuatation.getId();

        // Get all the quatationDetailsList where productQuatation equals to productQuatationId
        defaultQuatationDetailsShouldBeFound("productQuatationId.equals=" + productQuatationId);

        // Get all the quatationDetailsList where productQuatation equals to (productQuatationId + 1)
        defaultQuatationDetailsShouldNotBeFound("productQuatationId.equals=" + (productQuatationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQuatationDetailsShouldBeFound(String filter) throws Exception {
        restQuatationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quatationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].availabelStock").value(hasItem(DEFAULT_AVAILABEL_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].ratsPerUnit").value(hasItem(DEFAULT_RATS_PER_UNIT.doubleValue())))
            .andExpect(jsonPath("$.[*].totalprice").value(hasItem(DEFAULT_TOTALPRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restQuatationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQuatationDetailsShouldNotBeFound(String filter) throws Exception {
        restQuatationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQuatationDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingQuatationDetails() throws Exception {
        // Get the quatationDetails
        restQuatationDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewQuatationDetails() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();

        // Update the quatationDetails
        QuatationDetails updatedQuatationDetails = quatationDetailsRepository.findById(quatationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedQuatationDetails are not directly saved in db
        em.detach(updatedQuatationDetails);
        updatedQuatationDetails
            .availabelStock(UPDATED_AVAILABEL_STOCK)
            .quantity(UPDATED_QUANTITY)
            .ratsPerUnit(UPDATED_RATS_PER_UNIT)
            .totalprice(UPDATED_TOTALPRICE)
            .discount(UPDATED_DISCOUNT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(updatedQuatationDetails);

        restQuatationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quatationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuatationDetails testQuatationDetails = quatationDetailsList.get(quatationDetailsList.size() - 1);
        assertThat(testQuatationDetails.getAvailabelStock()).isEqualTo(UPDATED_AVAILABEL_STOCK);
        assertThat(testQuatationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuatationDetails.getRatsPerUnit()).isEqualTo(UPDATED_RATS_PER_UNIT);
        assertThat(testQuatationDetails.getTotalprice()).isEqualTo(UPDATED_TOTALPRICE);
        assertThat(testQuatationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testQuatationDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testQuatationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testQuatationDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testQuatationDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testQuatationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuatationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingQuatationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();
        quatationDetails.setId(count.incrementAndGet());

        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuatationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, quatationDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchQuatationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();
        quatationDetails.setId(count.incrementAndGet());

        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuatationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamQuatationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();
        quatationDetails.setId(count.incrementAndGet());

        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuatationDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateQuatationDetailsWithPatch() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();

        // Update the quatationDetails using partial update
        QuatationDetails partialUpdatedQuatationDetails = new QuatationDetails();
        partialUpdatedQuatationDetails.setId(quatationDetails.getId());

        partialUpdatedQuatationDetails
            .quantity(UPDATED_QUANTITY)
            .ratsPerUnit(UPDATED_RATS_PER_UNIT)
            .discount(UPDATED_DISCOUNT)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restQuatationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuatationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuatationDetails))
            )
            .andExpect(status().isOk());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuatationDetails testQuatationDetails = quatationDetailsList.get(quatationDetailsList.size() - 1);
        assertThat(testQuatationDetails.getAvailabelStock()).isEqualTo(DEFAULT_AVAILABEL_STOCK);
        assertThat(testQuatationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuatationDetails.getRatsPerUnit()).isEqualTo(UPDATED_RATS_PER_UNIT);
        assertThat(testQuatationDetails.getTotalprice()).isEqualTo(DEFAULT_TOTALPRICE);
        assertThat(testQuatationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testQuatationDetails.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testQuatationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testQuatationDetails.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testQuatationDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testQuatationDetails.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testQuatationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateQuatationDetailsWithPatch() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();

        // Update the quatationDetails using partial update
        QuatationDetails partialUpdatedQuatationDetails = new QuatationDetails();
        partialUpdatedQuatationDetails.setId(quatationDetails.getId());

        partialUpdatedQuatationDetails
            .availabelStock(UPDATED_AVAILABEL_STOCK)
            .quantity(UPDATED_QUANTITY)
            .ratsPerUnit(UPDATED_RATS_PER_UNIT)
            .totalprice(UPDATED_TOTALPRICE)
            .discount(UPDATED_DISCOUNT)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restQuatationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuatationDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuatationDetails))
            )
            .andExpect(status().isOk());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
        QuatationDetails testQuatationDetails = quatationDetailsList.get(quatationDetailsList.size() - 1);
        assertThat(testQuatationDetails.getAvailabelStock()).isEqualTo(UPDATED_AVAILABEL_STOCK);
        assertThat(testQuatationDetails.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testQuatationDetails.getRatsPerUnit()).isEqualTo(UPDATED_RATS_PER_UNIT);
        assertThat(testQuatationDetails.getTotalprice()).isEqualTo(UPDATED_TOTALPRICE);
        assertThat(testQuatationDetails.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testQuatationDetails.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testQuatationDetails.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testQuatationDetails.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testQuatationDetails.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testQuatationDetails.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testQuatationDetails.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingQuatationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();
        quatationDetails.setId(count.incrementAndGet());

        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuatationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, quatationDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchQuatationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();
        quatationDetails.setId(count.incrementAndGet());

        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuatationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamQuatationDetails() throws Exception {
        int databaseSizeBeforeUpdate = quatationDetailsRepository.findAll().size();
        quatationDetails.setId(count.incrementAndGet());

        // Create the QuatationDetails
        QuatationDetailsDTO quatationDetailsDTO = quatationDetailsMapper.toDto(quatationDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuatationDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(quatationDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the QuatationDetails in the database
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteQuatationDetails() throws Exception {
        // Initialize the database
        quatationDetailsRepository.saveAndFlush(quatationDetails);

        int databaseSizeBeforeDelete = quatationDetailsRepository.findAll().size();

        // Delete the quatationDetails
        restQuatationDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, quatationDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuatationDetails> quatationDetailsList = quatationDetailsRepository.findAll();
        assertThat(quatationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
