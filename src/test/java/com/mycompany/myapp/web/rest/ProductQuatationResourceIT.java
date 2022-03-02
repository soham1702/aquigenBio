package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProductQuatation;
import com.mycompany.myapp.domain.QuatationDetails;
import com.mycompany.myapp.domain.SecurityUser;
import com.mycompany.myapp.repository.ProductQuatationRepository;
import com.mycompany.myapp.service.criteria.ProductQuatationCriteria;
import com.mycompany.myapp.service.dto.ProductQuatationDTO;
import com.mycompany.myapp.service.mapper.ProductQuatationMapper;
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
 * Integration tests for the {@link ProductQuatationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductQuatationResourceIT {

    private static final Instant DEFAULT_QUATATIONDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QUATATIONDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_TOTAL_AMT = 1D;
    private static final Double UPDATED_TOTAL_AMT = 2D;
    private static final Double SMALLER_TOTAL_AMT = 1D - 1D;

    private static final Double DEFAULT_GST = 1D;
    private static final Double UPDATED_GST = 2D;
    private static final Double SMALLER_GST = 1D - 1D;

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;
    private static final Double SMALLER_DISCOUNT = 1D - 1D;

    private static final Instant DEFAULT_EXPECTED_DELIVERY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_DELIVERY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DELIVERY_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_ADDRESS = "BBBBBBBBBB";

    private static final Instant DEFAULT_QUO_VALIDITY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QUO_VALIDITY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CLIENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_TERMS_AND_CONDITION = "AAAAAAAAAA";
    private static final String UPDATED_TERMS_AND_CONDITION = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-quatations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductQuatationRepository productQuatationRepository;

    @Autowired
    private ProductQuatationMapper productQuatationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductQuatationMockMvc;

    private ProductQuatation productQuatation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductQuatation createEntity(EntityManager em) {
        ProductQuatation productQuatation = new ProductQuatation()
            .quatationdate(DEFAULT_QUATATIONDATE)
            .totalAmt(DEFAULT_TOTAL_AMT)
            .gst(DEFAULT_GST)
            .discount(DEFAULT_DISCOUNT)
            .expectedDelivery(DEFAULT_EXPECTED_DELIVERY)
            .deliveryAddress(DEFAULT_DELIVERY_ADDRESS)
            .quoValidity(DEFAULT_QUO_VALIDITY)
            .clientName(DEFAULT_CLIENT_NAME)
            .clientMobile(DEFAULT_CLIENT_MOBILE)
            .clientEmail(DEFAULT_CLIENT_EMAIL)
            .termsAndCondition(DEFAULT_TERMS_AND_CONDITION)
            .notes(DEFAULT_NOTES)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return productQuatation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductQuatation createUpdatedEntity(EntityManager em) {
        ProductQuatation productQuatation = new ProductQuatation()
            .quatationdate(UPDATED_QUATATIONDATE)
            .totalAmt(UPDATED_TOTAL_AMT)
            .gst(UPDATED_GST)
            .discount(UPDATED_DISCOUNT)
            .expectedDelivery(UPDATED_EXPECTED_DELIVERY)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .quoValidity(UPDATED_QUO_VALIDITY)
            .clientName(UPDATED_CLIENT_NAME)
            .clientMobile(UPDATED_CLIENT_MOBILE)
            .clientEmail(UPDATED_CLIENT_EMAIL)
            .termsAndCondition(UPDATED_TERMS_AND_CONDITION)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return productQuatation;
    }

    @BeforeEach
    public void initTest() {
        productQuatation = createEntity(em);
    }

    @Test
    @Transactional
    void createProductQuatation() throws Exception {
        int databaseSizeBeforeCreate = productQuatationRepository.findAll().size();
        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);
        restProductQuatationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeCreate + 1);
        ProductQuatation testProductQuatation = productQuatationList.get(productQuatationList.size() - 1);
        assertThat(testProductQuatation.getQuatationdate()).isEqualTo(DEFAULT_QUATATIONDATE);
        assertThat(testProductQuatation.getTotalAmt()).isEqualTo(DEFAULT_TOTAL_AMT);
        assertThat(testProductQuatation.getGst()).isEqualTo(DEFAULT_GST);
        assertThat(testProductQuatation.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testProductQuatation.getExpectedDelivery()).isEqualTo(DEFAULT_EXPECTED_DELIVERY);
        assertThat(testProductQuatation.getDeliveryAddress()).isEqualTo(DEFAULT_DELIVERY_ADDRESS);
        assertThat(testProductQuatation.getQuoValidity()).isEqualTo(DEFAULT_QUO_VALIDITY);
        assertThat(testProductQuatation.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProductQuatation.getClientMobile()).isEqualTo(DEFAULT_CLIENT_MOBILE);
        assertThat(testProductQuatation.getClientEmail()).isEqualTo(DEFAULT_CLIENT_EMAIL);
        assertThat(testProductQuatation.getTermsAndCondition()).isEqualTo(DEFAULT_TERMS_AND_CONDITION);
        assertThat(testProductQuatation.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testProductQuatation.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProductQuatation.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testProductQuatation.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testProductQuatation.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testProductQuatation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductQuatation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createProductQuatationWithExistingId() throws Exception {
        // Create the ProductQuatation with an existing ID
        productQuatation.setId(1L);
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        int databaseSizeBeforeCreate = productQuatationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductQuatationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductQuatations() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList
        restProductQuatationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productQuatation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quatationdate").value(hasItem(DEFAULT_QUATATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmt").value(hasItem(DEFAULT_TOTAL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedDelivery").value(hasItem(DEFAULT_EXPECTED_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS)))
            .andExpect(jsonPath("$.[*].quoValidity").value(hasItem(DEFAULT_QUO_VALIDITY.toString())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].clientMobile").value(hasItem(DEFAULT_CLIENT_MOBILE)))
            .andExpect(jsonPath("$.[*].clientEmail").value(hasItem(DEFAULT_CLIENT_EMAIL)))
            .andExpect(jsonPath("$.[*].termsAndCondition").value(hasItem(DEFAULT_TERMS_AND_CONDITION)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @Test
    @Transactional
    void getProductQuatation() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get the productQuatation
        restProductQuatationMockMvc
            .perform(get(ENTITY_API_URL_ID, productQuatation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productQuatation.getId().intValue()))
            .andExpect(jsonPath("$.quatationdate").value(DEFAULT_QUATATIONDATE.toString()))
            .andExpect(jsonPath("$.totalAmt").value(DEFAULT_TOTAL_AMT.doubleValue()))
            .andExpect(jsonPath("$.gst").value(DEFAULT_GST.doubleValue()))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()))
            .andExpect(jsonPath("$.expectedDelivery").value(DEFAULT_EXPECTED_DELIVERY.toString()))
            .andExpect(jsonPath("$.deliveryAddress").value(DEFAULT_DELIVERY_ADDRESS))
            .andExpect(jsonPath("$.quoValidity").value(DEFAULT_QUO_VALIDITY.toString()))
            .andExpect(jsonPath("$.clientName").value(DEFAULT_CLIENT_NAME))
            .andExpect(jsonPath("$.clientMobile").value(DEFAULT_CLIENT_MOBILE))
            .andExpect(jsonPath("$.clientEmail").value(DEFAULT_CLIENT_EMAIL))
            .andExpect(jsonPath("$.termsAndCondition").value(DEFAULT_TERMS_AND_CONDITION))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getProductQuatationsByIdFiltering() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        Long id = productQuatation.getId();

        defaultProductQuatationShouldBeFound("id.equals=" + id);
        defaultProductQuatationShouldNotBeFound("id.notEquals=" + id);

        defaultProductQuatationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductQuatationShouldNotBeFound("id.greaterThan=" + id);

        defaultProductQuatationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductQuatationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuatationdateIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quatationdate equals to DEFAULT_QUATATIONDATE
        defaultProductQuatationShouldBeFound("quatationdate.equals=" + DEFAULT_QUATATIONDATE);

        // Get all the productQuatationList where quatationdate equals to UPDATED_QUATATIONDATE
        defaultProductQuatationShouldNotBeFound("quatationdate.equals=" + UPDATED_QUATATIONDATE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuatationdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quatationdate not equals to DEFAULT_QUATATIONDATE
        defaultProductQuatationShouldNotBeFound("quatationdate.notEquals=" + DEFAULT_QUATATIONDATE);

        // Get all the productQuatationList where quatationdate not equals to UPDATED_QUATATIONDATE
        defaultProductQuatationShouldBeFound("quatationdate.notEquals=" + UPDATED_QUATATIONDATE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuatationdateIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quatationdate in DEFAULT_QUATATIONDATE or UPDATED_QUATATIONDATE
        defaultProductQuatationShouldBeFound("quatationdate.in=" + DEFAULT_QUATATIONDATE + "," + UPDATED_QUATATIONDATE);

        // Get all the productQuatationList where quatationdate equals to UPDATED_QUATATIONDATE
        defaultProductQuatationShouldNotBeFound("quatationdate.in=" + UPDATED_QUATATIONDATE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuatationdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quatationdate is not null
        defaultProductQuatationShouldBeFound("quatationdate.specified=true");

        // Get all the productQuatationList where quatationdate is null
        defaultProductQuatationShouldNotBeFound("quatationdate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt equals to DEFAULT_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.equals=" + DEFAULT_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt equals to UPDATED_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.equals=" + UPDATED_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt not equals to DEFAULT_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.notEquals=" + DEFAULT_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt not equals to UPDATED_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.notEquals=" + UPDATED_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt in DEFAULT_TOTAL_AMT or UPDATED_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.in=" + DEFAULT_TOTAL_AMT + "," + UPDATED_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt equals to UPDATED_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.in=" + UPDATED_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt is not null
        defaultProductQuatationShouldBeFound("totalAmt.specified=true");

        // Get all the productQuatationList where totalAmt is null
        defaultProductQuatationShouldNotBeFound("totalAmt.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt is greater than or equal to DEFAULT_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.greaterThanOrEqual=" + DEFAULT_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt is greater than or equal to UPDATED_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.greaterThanOrEqual=" + UPDATED_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt is less than or equal to DEFAULT_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.lessThanOrEqual=" + DEFAULT_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt is less than or equal to SMALLER_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.lessThanOrEqual=" + SMALLER_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsLessThanSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt is less than DEFAULT_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.lessThan=" + DEFAULT_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt is less than UPDATED_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.lessThan=" + UPDATED_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTotalAmtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where totalAmt is greater than DEFAULT_TOTAL_AMT
        defaultProductQuatationShouldNotBeFound("totalAmt.greaterThan=" + DEFAULT_TOTAL_AMT);

        // Get all the productQuatationList where totalAmt is greater than SMALLER_TOTAL_AMT
        defaultProductQuatationShouldBeFound("totalAmt.greaterThan=" + SMALLER_TOTAL_AMT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst equals to DEFAULT_GST
        defaultProductQuatationShouldBeFound("gst.equals=" + DEFAULT_GST);

        // Get all the productQuatationList where gst equals to UPDATED_GST
        defaultProductQuatationShouldNotBeFound("gst.equals=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst not equals to DEFAULT_GST
        defaultProductQuatationShouldNotBeFound("gst.notEquals=" + DEFAULT_GST);

        // Get all the productQuatationList where gst not equals to UPDATED_GST
        defaultProductQuatationShouldBeFound("gst.notEquals=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst in DEFAULT_GST or UPDATED_GST
        defaultProductQuatationShouldBeFound("gst.in=" + DEFAULT_GST + "," + UPDATED_GST);

        // Get all the productQuatationList where gst equals to UPDATED_GST
        defaultProductQuatationShouldNotBeFound("gst.in=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst is not null
        defaultProductQuatationShouldBeFound("gst.specified=true");

        // Get all the productQuatationList where gst is null
        defaultProductQuatationShouldNotBeFound("gst.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst is greater than or equal to DEFAULT_GST
        defaultProductQuatationShouldBeFound("gst.greaterThanOrEqual=" + DEFAULT_GST);

        // Get all the productQuatationList where gst is greater than or equal to UPDATED_GST
        defaultProductQuatationShouldNotBeFound("gst.greaterThanOrEqual=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst is less than or equal to DEFAULT_GST
        defaultProductQuatationShouldBeFound("gst.lessThanOrEqual=" + DEFAULT_GST);

        // Get all the productQuatationList where gst is less than or equal to SMALLER_GST
        defaultProductQuatationShouldNotBeFound("gst.lessThanOrEqual=" + SMALLER_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsLessThanSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst is less than DEFAULT_GST
        defaultProductQuatationShouldNotBeFound("gst.lessThan=" + DEFAULT_GST);

        // Get all the productQuatationList where gst is less than UPDATED_GST
        defaultProductQuatationShouldBeFound("gst.lessThan=" + UPDATED_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByGstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where gst is greater than DEFAULT_GST
        defaultProductQuatationShouldNotBeFound("gst.greaterThan=" + DEFAULT_GST);

        // Get all the productQuatationList where gst is greater than SMALLER_GST
        defaultProductQuatationShouldBeFound("gst.greaterThan=" + SMALLER_GST);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount equals to DEFAULT_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the productQuatationList where discount equals to UPDATED_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount not equals to DEFAULT_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the productQuatationList where discount not equals to UPDATED_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the productQuatationList where discount equals to UPDATED_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount is not null
        defaultProductQuatationShouldBeFound("discount.specified=true");

        // Get all the productQuatationList where discount is null
        defaultProductQuatationShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount is greater than or equal to DEFAULT_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.greaterThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the productQuatationList where discount is greater than or equal to UPDATED_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.greaterThanOrEqual=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount is less than or equal to DEFAULT_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.lessThanOrEqual=" + DEFAULT_DISCOUNT);

        // Get all the productQuatationList where discount is less than or equal to SMALLER_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.lessThanOrEqual=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount is less than DEFAULT_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.lessThan=" + DEFAULT_DISCOUNT);

        // Get all the productQuatationList where discount is less than UPDATED_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.lessThan=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where discount is greater than DEFAULT_DISCOUNT
        defaultProductQuatationShouldNotBeFound("discount.greaterThan=" + DEFAULT_DISCOUNT);

        // Get all the productQuatationList where discount is greater than SMALLER_DISCOUNT
        defaultProductQuatationShouldBeFound("discount.greaterThan=" + SMALLER_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByExpectedDeliveryIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where expectedDelivery equals to DEFAULT_EXPECTED_DELIVERY
        defaultProductQuatationShouldBeFound("expectedDelivery.equals=" + DEFAULT_EXPECTED_DELIVERY);

        // Get all the productQuatationList where expectedDelivery equals to UPDATED_EXPECTED_DELIVERY
        defaultProductQuatationShouldNotBeFound("expectedDelivery.equals=" + UPDATED_EXPECTED_DELIVERY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByExpectedDeliveryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where expectedDelivery not equals to DEFAULT_EXPECTED_DELIVERY
        defaultProductQuatationShouldNotBeFound("expectedDelivery.notEquals=" + DEFAULT_EXPECTED_DELIVERY);

        // Get all the productQuatationList where expectedDelivery not equals to UPDATED_EXPECTED_DELIVERY
        defaultProductQuatationShouldBeFound("expectedDelivery.notEquals=" + UPDATED_EXPECTED_DELIVERY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByExpectedDeliveryIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where expectedDelivery in DEFAULT_EXPECTED_DELIVERY or UPDATED_EXPECTED_DELIVERY
        defaultProductQuatationShouldBeFound("expectedDelivery.in=" + DEFAULT_EXPECTED_DELIVERY + "," + UPDATED_EXPECTED_DELIVERY);

        // Get all the productQuatationList where expectedDelivery equals to UPDATED_EXPECTED_DELIVERY
        defaultProductQuatationShouldNotBeFound("expectedDelivery.in=" + UPDATED_EXPECTED_DELIVERY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByExpectedDeliveryIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where expectedDelivery is not null
        defaultProductQuatationShouldBeFound("expectedDelivery.specified=true");

        // Get all the productQuatationList where expectedDelivery is null
        defaultProductQuatationShouldNotBeFound("expectedDelivery.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDeliveryAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where deliveryAddress equals to DEFAULT_DELIVERY_ADDRESS
        defaultProductQuatationShouldBeFound("deliveryAddress.equals=" + DEFAULT_DELIVERY_ADDRESS);

        // Get all the productQuatationList where deliveryAddress equals to UPDATED_DELIVERY_ADDRESS
        defaultProductQuatationShouldNotBeFound("deliveryAddress.equals=" + UPDATED_DELIVERY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDeliveryAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where deliveryAddress not equals to DEFAULT_DELIVERY_ADDRESS
        defaultProductQuatationShouldNotBeFound("deliveryAddress.notEquals=" + DEFAULT_DELIVERY_ADDRESS);

        // Get all the productQuatationList where deliveryAddress not equals to UPDATED_DELIVERY_ADDRESS
        defaultProductQuatationShouldBeFound("deliveryAddress.notEquals=" + UPDATED_DELIVERY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDeliveryAddressIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where deliveryAddress in DEFAULT_DELIVERY_ADDRESS or UPDATED_DELIVERY_ADDRESS
        defaultProductQuatationShouldBeFound("deliveryAddress.in=" + DEFAULT_DELIVERY_ADDRESS + "," + UPDATED_DELIVERY_ADDRESS);

        // Get all the productQuatationList where deliveryAddress equals to UPDATED_DELIVERY_ADDRESS
        defaultProductQuatationShouldNotBeFound("deliveryAddress.in=" + UPDATED_DELIVERY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDeliveryAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where deliveryAddress is not null
        defaultProductQuatationShouldBeFound("deliveryAddress.specified=true");

        // Get all the productQuatationList where deliveryAddress is null
        defaultProductQuatationShouldNotBeFound("deliveryAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDeliveryAddressContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where deliveryAddress contains DEFAULT_DELIVERY_ADDRESS
        defaultProductQuatationShouldBeFound("deliveryAddress.contains=" + DEFAULT_DELIVERY_ADDRESS);

        // Get all the productQuatationList where deliveryAddress contains UPDATED_DELIVERY_ADDRESS
        defaultProductQuatationShouldNotBeFound("deliveryAddress.contains=" + UPDATED_DELIVERY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByDeliveryAddressNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where deliveryAddress does not contain DEFAULT_DELIVERY_ADDRESS
        defaultProductQuatationShouldNotBeFound("deliveryAddress.doesNotContain=" + DEFAULT_DELIVERY_ADDRESS);

        // Get all the productQuatationList where deliveryAddress does not contain UPDATED_DELIVERY_ADDRESS
        defaultProductQuatationShouldBeFound("deliveryAddress.doesNotContain=" + UPDATED_DELIVERY_ADDRESS);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuoValidityIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quoValidity equals to DEFAULT_QUO_VALIDITY
        defaultProductQuatationShouldBeFound("quoValidity.equals=" + DEFAULT_QUO_VALIDITY);

        // Get all the productQuatationList where quoValidity equals to UPDATED_QUO_VALIDITY
        defaultProductQuatationShouldNotBeFound("quoValidity.equals=" + UPDATED_QUO_VALIDITY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuoValidityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quoValidity not equals to DEFAULT_QUO_VALIDITY
        defaultProductQuatationShouldNotBeFound("quoValidity.notEquals=" + DEFAULT_QUO_VALIDITY);

        // Get all the productQuatationList where quoValidity not equals to UPDATED_QUO_VALIDITY
        defaultProductQuatationShouldBeFound("quoValidity.notEquals=" + UPDATED_QUO_VALIDITY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuoValidityIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quoValidity in DEFAULT_QUO_VALIDITY or UPDATED_QUO_VALIDITY
        defaultProductQuatationShouldBeFound("quoValidity.in=" + DEFAULT_QUO_VALIDITY + "," + UPDATED_QUO_VALIDITY);

        // Get all the productQuatationList where quoValidity equals to UPDATED_QUO_VALIDITY
        defaultProductQuatationShouldNotBeFound("quoValidity.in=" + UPDATED_QUO_VALIDITY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuoValidityIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where quoValidity is not null
        defaultProductQuatationShouldBeFound("quoValidity.specified=true");

        // Get all the productQuatationList where quoValidity is null
        defaultProductQuatationShouldNotBeFound("quoValidity.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientName equals to DEFAULT_CLIENT_NAME
        defaultProductQuatationShouldBeFound("clientName.equals=" + DEFAULT_CLIENT_NAME);

        // Get all the productQuatationList where clientName equals to UPDATED_CLIENT_NAME
        defaultProductQuatationShouldNotBeFound("clientName.equals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientName not equals to DEFAULT_CLIENT_NAME
        defaultProductQuatationShouldNotBeFound("clientName.notEquals=" + DEFAULT_CLIENT_NAME);

        // Get all the productQuatationList where clientName not equals to UPDATED_CLIENT_NAME
        defaultProductQuatationShouldBeFound("clientName.notEquals=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientNameIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientName in DEFAULT_CLIENT_NAME or UPDATED_CLIENT_NAME
        defaultProductQuatationShouldBeFound("clientName.in=" + DEFAULT_CLIENT_NAME + "," + UPDATED_CLIENT_NAME);

        // Get all the productQuatationList where clientName equals to UPDATED_CLIENT_NAME
        defaultProductQuatationShouldNotBeFound("clientName.in=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientName is not null
        defaultProductQuatationShouldBeFound("clientName.specified=true");

        // Get all the productQuatationList where clientName is null
        defaultProductQuatationShouldNotBeFound("clientName.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientNameContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientName contains DEFAULT_CLIENT_NAME
        defaultProductQuatationShouldBeFound("clientName.contains=" + DEFAULT_CLIENT_NAME);

        // Get all the productQuatationList where clientName contains UPDATED_CLIENT_NAME
        defaultProductQuatationShouldNotBeFound("clientName.contains=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientNameNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientName does not contain DEFAULT_CLIENT_NAME
        defaultProductQuatationShouldNotBeFound("clientName.doesNotContain=" + DEFAULT_CLIENT_NAME);

        // Get all the productQuatationList where clientName does not contain UPDATED_CLIENT_NAME
        defaultProductQuatationShouldBeFound("clientName.doesNotContain=" + UPDATED_CLIENT_NAME);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientMobile equals to DEFAULT_CLIENT_MOBILE
        defaultProductQuatationShouldBeFound("clientMobile.equals=" + DEFAULT_CLIENT_MOBILE);

        // Get all the productQuatationList where clientMobile equals to UPDATED_CLIENT_MOBILE
        defaultProductQuatationShouldNotBeFound("clientMobile.equals=" + UPDATED_CLIENT_MOBILE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientMobileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientMobile not equals to DEFAULT_CLIENT_MOBILE
        defaultProductQuatationShouldNotBeFound("clientMobile.notEquals=" + DEFAULT_CLIENT_MOBILE);

        // Get all the productQuatationList where clientMobile not equals to UPDATED_CLIENT_MOBILE
        defaultProductQuatationShouldBeFound("clientMobile.notEquals=" + UPDATED_CLIENT_MOBILE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientMobileIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientMobile in DEFAULT_CLIENT_MOBILE or UPDATED_CLIENT_MOBILE
        defaultProductQuatationShouldBeFound("clientMobile.in=" + DEFAULT_CLIENT_MOBILE + "," + UPDATED_CLIENT_MOBILE);

        // Get all the productQuatationList where clientMobile equals to UPDATED_CLIENT_MOBILE
        defaultProductQuatationShouldNotBeFound("clientMobile.in=" + UPDATED_CLIENT_MOBILE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientMobile is not null
        defaultProductQuatationShouldBeFound("clientMobile.specified=true");

        // Get all the productQuatationList where clientMobile is null
        defaultProductQuatationShouldNotBeFound("clientMobile.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientMobileContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientMobile contains DEFAULT_CLIENT_MOBILE
        defaultProductQuatationShouldBeFound("clientMobile.contains=" + DEFAULT_CLIENT_MOBILE);

        // Get all the productQuatationList where clientMobile contains UPDATED_CLIENT_MOBILE
        defaultProductQuatationShouldNotBeFound("clientMobile.contains=" + UPDATED_CLIENT_MOBILE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientMobileNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientMobile does not contain DEFAULT_CLIENT_MOBILE
        defaultProductQuatationShouldNotBeFound("clientMobile.doesNotContain=" + DEFAULT_CLIENT_MOBILE);

        // Get all the productQuatationList where clientMobile does not contain UPDATED_CLIENT_MOBILE
        defaultProductQuatationShouldBeFound("clientMobile.doesNotContain=" + UPDATED_CLIENT_MOBILE);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientEmail equals to DEFAULT_CLIENT_EMAIL
        defaultProductQuatationShouldBeFound("clientEmail.equals=" + DEFAULT_CLIENT_EMAIL);

        // Get all the productQuatationList where clientEmail equals to UPDATED_CLIENT_EMAIL
        defaultProductQuatationShouldNotBeFound("clientEmail.equals=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientEmail not equals to DEFAULT_CLIENT_EMAIL
        defaultProductQuatationShouldNotBeFound("clientEmail.notEquals=" + DEFAULT_CLIENT_EMAIL);

        // Get all the productQuatationList where clientEmail not equals to UPDATED_CLIENT_EMAIL
        defaultProductQuatationShouldBeFound("clientEmail.notEquals=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientEmailIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientEmail in DEFAULT_CLIENT_EMAIL or UPDATED_CLIENT_EMAIL
        defaultProductQuatationShouldBeFound("clientEmail.in=" + DEFAULT_CLIENT_EMAIL + "," + UPDATED_CLIENT_EMAIL);

        // Get all the productQuatationList where clientEmail equals to UPDATED_CLIENT_EMAIL
        defaultProductQuatationShouldNotBeFound("clientEmail.in=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientEmail is not null
        defaultProductQuatationShouldBeFound("clientEmail.specified=true");

        // Get all the productQuatationList where clientEmail is null
        defaultProductQuatationShouldNotBeFound("clientEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientEmailContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientEmail contains DEFAULT_CLIENT_EMAIL
        defaultProductQuatationShouldBeFound("clientEmail.contains=" + DEFAULT_CLIENT_EMAIL);

        // Get all the productQuatationList where clientEmail contains UPDATED_CLIENT_EMAIL
        defaultProductQuatationShouldNotBeFound("clientEmail.contains=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByClientEmailNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where clientEmail does not contain DEFAULT_CLIENT_EMAIL
        defaultProductQuatationShouldNotBeFound("clientEmail.doesNotContain=" + DEFAULT_CLIENT_EMAIL);

        // Get all the productQuatationList where clientEmail does not contain UPDATED_CLIENT_EMAIL
        defaultProductQuatationShouldBeFound("clientEmail.doesNotContain=" + UPDATED_CLIENT_EMAIL);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTermsAndConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where termsAndCondition equals to DEFAULT_TERMS_AND_CONDITION
        defaultProductQuatationShouldBeFound("termsAndCondition.equals=" + DEFAULT_TERMS_AND_CONDITION);

        // Get all the productQuatationList where termsAndCondition equals to UPDATED_TERMS_AND_CONDITION
        defaultProductQuatationShouldNotBeFound("termsAndCondition.equals=" + UPDATED_TERMS_AND_CONDITION);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTermsAndConditionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where termsAndCondition not equals to DEFAULT_TERMS_AND_CONDITION
        defaultProductQuatationShouldNotBeFound("termsAndCondition.notEquals=" + DEFAULT_TERMS_AND_CONDITION);

        // Get all the productQuatationList where termsAndCondition not equals to UPDATED_TERMS_AND_CONDITION
        defaultProductQuatationShouldBeFound("termsAndCondition.notEquals=" + UPDATED_TERMS_AND_CONDITION);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTermsAndConditionIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where termsAndCondition in DEFAULT_TERMS_AND_CONDITION or UPDATED_TERMS_AND_CONDITION
        defaultProductQuatationShouldBeFound("termsAndCondition.in=" + DEFAULT_TERMS_AND_CONDITION + "," + UPDATED_TERMS_AND_CONDITION);

        // Get all the productQuatationList where termsAndCondition equals to UPDATED_TERMS_AND_CONDITION
        defaultProductQuatationShouldNotBeFound("termsAndCondition.in=" + UPDATED_TERMS_AND_CONDITION);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTermsAndConditionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where termsAndCondition is not null
        defaultProductQuatationShouldBeFound("termsAndCondition.specified=true");

        // Get all the productQuatationList where termsAndCondition is null
        defaultProductQuatationShouldNotBeFound("termsAndCondition.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTermsAndConditionContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where termsAndCondition contains DEFAULT_TERMS_AND_CONDITION
        defaultProductQuatationShouldBeFound("termsAndCondition.contains=" + DEFAULT_TERMS_AND_CONDITION);

        // Get all the productQuatationList where termsAndCondition contains UPDATED_TERMS_AND_CONDITION
        defaultProductQuatationShouldNotBeFound("termsAndCondition.contains=" + UPDATED_TERMS_AND_CONDITION);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByTermsAndConditionNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where termsAndCondition does not contain DEFAULT_TERMS_AND_CONDITION
        defaultProductQuatationShouldNotBeFound("termsAndCondition.doesNotContain=" + DEFAULT_TERMS_AND_CONDITION);

        // Get all the productQuatationList where termsAndCondition does not contain UPDATED_TERMS_AND_CONDITION
        defaultProductQuatationShouldBeFound("termsAndCondition.doesNotContain=" + UPDATED_TERMS_AND_CONDITION);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where notes equals to DEFAULT_NOTES
        defaultProductQuatationShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the productQuatationList where notes equals to UPDATED_NOTES
        defaultProductQuatationShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where notes not equals to DEFAULT_NOTES
        defaultProductQuatationShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the productQuatationList where notes not equals to UPDATED_NOTES
        defaultProductQuatationShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultProductQuatationShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the productQuatationList where notes equals to UPDATED_NOTES
        defaultProductQuatationShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where notes is not null
        defaultProductQuatationShouldBeFound("notes.specified=true");

        // Get all the productQuatationList where notes is null
        defaultProductQuatationShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByNotesContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where notes contains DEFAULT_NOTES
        defaultProductQuatationShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the productQuatationList where notes contains UPDATED_NOTES
        defaultProductQuatationShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where notes does not contain DEFAULT_NOTES
        defaultProductQuatationShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the productQuatationList where notes does not contain UPDATED_NOTES
        defaultProductQuatationShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultProductQuatationShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productQuatationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductQuatationShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultProductQuatationShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productQuatationList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultProductQuatationShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultProductQuatationShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the productQuatationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductQuatationShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField1 is not null
        defaultProductQuatationShouldBeFound("freeField1.specified=true");

        // Get all the productQuatationList where freeField1 is null
        defaultProductQuatationShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultProductQuatationShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the productQuatationList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultProductQuatationShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultProductQuatationShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the productQuatationList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultProductQuatationShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultProductQuatationShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productQuatationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductQuatationShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultProductQuatationShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productQuatationList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultProductQuatationShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultProductQuatationShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the productQuatationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductQuatationShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField2 is not null
        defaultProductQuatationShouldBeFound("freeField2.specified=true");

        // Get all the productQuatationList where freeField2 is null
        defaultProductQuatationShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultProductQuatationShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the productQuatationList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultProductQuatationShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultProductQuatationShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the productQuatationList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultProductQuatationShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultProductQuatationShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the productQuatationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultProductQuatationShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultProductQuatationShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the productQuatationList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultProductQuatationShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultProductQuatationShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the productQuatationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultProductQuatationShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField3 is not null
        defaultProductQuatationShouldBeFound("freeField3.specified=true");

        // Get all the productQuatationList where freeField3 is null
        defaultProductQuatationShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultProductQuatationShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the productQuatationList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultProductQuatationShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultProductQuatationShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the productQuatationList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultProductQuatationShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultProductQuatationShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the productQuatationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultProductQuatationShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultProductQuatationShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the productQuatationList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultProductQuatationShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultProductQuatationShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the productQuatationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultProductQuatationShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField4 is not null
        defaultProductQuatationShouldBeFound("freeField4.specified=true");

        // Get all the productQuatationList where freeField4 is null
        defaultProductQuatationShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultProductQuatationShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the productQuatationList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultProductQuatationShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultProductQuatationShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the productQuatationList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultProductQuatationShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProductQuatationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productQuatationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductQuatationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultProductQuatationShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productQuatationList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultProductQuatationShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProductQuatationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the productQuatationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductQuatationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModified is not null
        defaultProductQuatationShouldBeFound("lastModified.specified=true");

        // Get all the productQuatationList where lastModified is null
        defaultProductQuatationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductQuatationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productQuatationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductQuatationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductQuatationShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productQuatationList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductQuatationShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductQuatationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productQuatationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductQuatationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModifiedBy is not null
        defaultProductQuatationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productQuatationList where lastModifiedBy is null
        defaultProductQuatationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductQuatationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productQuatationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductQuatationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        // Get all the productQuatationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductQuatationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productQuatationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductQuatationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductQuatationsBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);
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
        productQuatation.setSecurityUser(securityUser);
        productQuatationRepository.saveAndFlush(productQuatation);
        Long securityUserId = securityUser.getId();

        // Get all the productQuatationList where securityUser equals to securityUserId
        defaultProductQuatationShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the productQuatationList where securityUser equals to (securityUserId + 1)
        defaultProductQuatationShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllProductQuatationsByQuatationDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);
        QuatationDetails quatationDetails;
        if (TestUtil.findAll(em, QuatationDetails.class).isEmpty()) {
            quatationDetails = QuatationDetailsResourceIT.createEntity(em);
            em.persist(quatationDetails);
            em.flush();
        } else {
            quatationDetails = TestUtil.findAll(em, QuatationDetails.class).get(0);
        }
        em.persist(quatationDetails);
        em.flush();
        productQuatation.setQuatationDetails(quatationDetails);
        quatationDetails.setProductQuatation(productQuatation);
        productQuatationRepository.saveAndFlush(productQuatation);
        Long quatationDetailsId = quatationDetails.getId();

        // Get all the productQuatationList where quatationDetails equals to quatationDetailsId
        defaultProductQuatationShouldBeFound("quatationDetailsId.equals=" + quatationDetailsId);

        // Get all the productQuatationList where quatationDetails equals to (quatationDetailsId + 1)
        defaultProductQuatationShouldNotBeFound("quatationDetailsId.equals=" + (quatationDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductQuatationShouldBeFound(String filter) throws Exception {
        restProductQuatationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productQuatation.getId().intValue())))
            .andExpect(jsonPath("$.[*].quatationdate").value(hasItem(DEFAULT_QUATATIONDATE.toString())))
            .andExpect(jsonPath("$.[*].totalAmt").value(hasItem(DEFAULT_TOTAL_AMT.doubleValue())))
            .andExpect(jsonPath("$.[*].gst").value(hasItem(DEFAULT_GST.doubleValue())))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].expectedDelivery").value(hasItem(DEFAULT_EXPECTED_DELIVERY.toString())))
            .andExpect(jsonPath("$.[*].deliveryAddress").value(hasItem(DEFAULT_DELIVERY_ADDRESS)))
            .andExpect(jsonPath("$.[*].quoValidity").value(hasItem(DEFAULT_QUO_VALIDITY.toString())))
            .andExpect(jsonPath("$.[*].clientName").value(hasItem(DEFAULT_CLIENT_NAME)))
            .andExpect(jsonPath("$.[*].clientMobile").value(hasItem(DEFAULT_CLIENT_MOBILE)))
            .andExpect(jsonPath("$.[*].clientEmail").value(hasItem(DEFAULT_CLIENT_EMAIL)))
            .andExpect(jsonPath("$.[*].termsAndCondition").value(hasItem(DEFAULT_TERMS_AND_CONDITION)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restProductQuatationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductQuatationShouldNotBeFound(String filter) throws Exception {
        restProductQuatationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductQuatationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductQuatation() throws Exception {
        // Get the productQuatation
        restProductQuatationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductQuatation() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();

        // Update the productQuatation
        ProductQuatation updatedProductQuatation = productQuatationRepository.findById(productQuatation.getId()).get();
        // Disconnect from session so that the updates on updatedProductQuatation are not directly saved in db
        em.detach(updatedProductQuatation);
        updatedProductQuatation
            .quatationdate(UPDATED_QUATATIONDATE)
            .totalAmt(UPDATED_TOTAL_AMT)
            .gst(UPDATED_GST)
            .discount(UPDATED_DISCOUNT)
            .expectedDelivery(UPDATED_EXPECTED_DELIVERY)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .quoValidity(UPDATED_QUO_VALIDITY)
            .clientName(UPDATED_CLIENT_NAME)
            .clientMobile(UPDATED_CLIENT_MOBILE)
            .clientEmail(UPDATED_CLIENT_EMAIL)
            .termsAndCondition(UPDATED_TERMS_AND_CONDITION)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(updatedProductQuatation);

        restProductQuatationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productQuatationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
        ProductQuatation testProductQuatation = productQuatationList.get(productQuatationList.size() - 1);
        assertThat(testProductQuatation.getQuatationdate()).isEqualTo(UPDATED_QUATATIONDATE);
        assertThat(testProductQuatation.getTotalAmt()).isEqualTo(UPDATED_TOTAL_AMT);
        assertThat(testProductQuatation.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testProductQuatation.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProductQuatation.getExpectedDelivery()).isEqualTo(UPDATED_EXPECTED_DELIVERY);
        assertThat(testProductQuatation.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testProductQuatation.getQuoValidity()).isEqualTo(UPDATED_QUO_VALIDITY);
        assertThat(testProductQuatation.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProductQuatation.getClientMobile()).isEqualTo(UPDATED_CLIENT_MOBILE);
        assertThat(testProductQuatation.getClientEmail()).isEqualTo(UPDATED_CLIENT_EMAIL);
        assertThat(testProductQuatation.getTermsAndCondition()).isEqualTo(UPDATED_TERMS_AND_CONDITION);
        assertThat(testProductQuatation.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testProductQuatation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductQuatation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductQuatation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProductQuatation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProductQuatation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductQuatation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingProductQuatation() throws Exception {
        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();
        productQuatation.setId(count.incrementAndGet());

        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductQuatationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productQuatationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductQuatation() throws Exception {
        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();
        productQuatation.setId(count.incrementAndGet());

        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductQuatationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductQuatation() throws Exception {
        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();
        productQuatation.setId(count.incrementAndGet());

        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductQuatationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductQuatationWithPatch() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();

        // Update the productQuatation using partial update
        ProductQuatation partialUpdatedProductQuatation = new ProductQuatation();
        partialUpdatedProductQuatation.setId(productQuatation.getId());

        partialUpdatedProductQuatation
            .totalAmt(UPDATED_TOTAL_AMT)
            .gst(UPDATED_GST)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .clientMobile(UPDATED_CLIENT_MOBILE)
            .clientEmail(UPDATED_CLIENT_EMAIL)
            .termsAndCondition(UPDATED_TERMS_AND_CONDITION)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restProductQuatationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductQuatation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductQuatation))
            )
            .andExpect(status().isOk());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
        ProductQuatation testProductQuatation = productQuatationList.get(productQuatationList.size() - 1);
        assertThat(testProductQuatation.getQuatationdate()).isEqualTo(DEFAULT_QUATATIONDATE);
        assertThat(testProductQuatation.getTotalAmt()).isEqualTo(UPDATED_TOTAL_AMT);
        assertThat(testProductQuatation.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testProductQuatation.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testProductQuatation.getExpectedDelivery()).isEqualTo(DEFAULT_EXPECTED_DELIVERY);
        assertThat(testProductQuatation.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testProductQuatation.getQuoValidity()).isEqualTo(DEFAULT_QUO_VALIDITY);
        assertThat(testProductQuatation.getClientName()).isEqualTo(DEFAULT_CLIENT_NAME);
        assertThat(testProductQuatation.getClientMobile()).isEqualTo(UPDATED_CLIENT_MOBILE);
        assertThat(testProductQuatation.getClientEmail()).isEqualTo(UPDATED_CLIENT_EMAIL);
        assertThat(testProductQuatation.getTermsAndCondition()).isEqualTo(UPDATED_TERMS_AND_CONDITION);
        assertThat(testProductQuatation.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testProductQuatation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductQuatation.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testProductQuatation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProductQuatation.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testProductQuatation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductQuatation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateProductQuatationWithPatch() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();

        // Update the productQuatation using partial update
        ProductQuatation partialUpdatedProductQuatation = new ProductQuatation();
        partialUpdatedProductQuatation.setId(productQuatation.getId());

        partialUpdatedProductQuatation
            .quatationdate(UPDATED_QUATATIONDATE)
            .totalAmt(UPDATED_TOTAL_AMT)
            .gst(UPDATED_GST)
            .discount(UPDATED_DISCOUNT)
            .expectedDelivery(UPDATED_EXPECTED_DELIVERY)
            .deliveryAddress(UPDATED_DELIVERY_ADDRESS)
            .quoValidity(UPDATED_QUO_VALIDITY)
            .clientName(UPDATED_CLIENT_NAME)
            .clientMobile(UPDATED_CLIENT_MOBILE)
            .clientEmail(UPDATED_CLIENT_EMAIL)
            .termsAndCondition(UPDATED_TERMS_AND_CONDITION)
            .notes(UPDATED_NOTES)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductQuatationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductQuatation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductQuatation))
            )
            .andExpect(status().isOk());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
        ProductQuatation testProductQuatation = productQuatationList.get(productQuatationList.size() - 1);
        assertThat(testProductQuatation.getQuatationdate()).isEqualTo(UPDATED_QUATATIONDATE);
        assertThat(testProductQuatation.getTotalAmt()).isEqualTo(UPDATED_TOTAL_AMT);
        assertThat(testProductQuatation.getGst()).isEqualTo(UPDATED_GST);
        assertThat(testProductQuatation.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProductQuatation.getExpectedDelivery()).isEqualTo(UPDATED_EXPECTED_DELIVERY);
        assertThat(testProductQuatation.getDeliveryAddress()).isEqualTo(UPDATED_DELIVERY_ADDRESS);
        assertThat(testProductQuatation.getQuoValidity()).isEqualTo(UPDATED_QUO_VALIDITY);
        assertThat(testProductQuatation.getClientName()).isEqualTo(UPDATED_CLIENT_NAME);
        assertThat(testProductQuatation.getClientMobile()).isEqualTo(UPDATED_CLIENT_MOBILE);
        assertThat(testProductQuatation.getClientEmail()).isEqualTo(UPDATED_CLIENT_EMAIL);
        assertThat(testProductQuatation.getTermsAndCondition()).isEqualTo(UPDATED_TERMS_AND_CONDITION);
        assertThat(testProductQuatation.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testProductQuatation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductQuatation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductQuatation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProductQuatation.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProductQuatation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductQuatation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingProductQuatation() throws Exception {
        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();
        productQuatation.setId(count.incrementAndGet());

        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductQuatationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productQuatationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductQuatation() throws Exception {
        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();
        productQuatation.setId(count.incrementAndGet());

        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductQuatationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductQuatation() throws Exception {
        int databaseSizeBeforeUpdate = productQuatationRepository.findAll().size();
        productQuatation.setId(count.incrementAndGet());

        // Create the ProductQuatation
        ProductQuatationDTO productQuatationDTO = productQuatationMapper.toDto(productQuatation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductQuatationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productQuatationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductQuatation in the database
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductQuatation() throws Exception {
        // Initialize the database
        productQuatationRepository.saveAndFlush(productQuatation);

        int databaseSizeBeforeDelete = productQuatationRepository.findAll().size();

        // Delete the productQuatation
        restProductQuatationMockMvc
            .perform(delete(ENTITY_API_URL_ID, productQuatation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductQuatation> productQuatationList = productQuatationRepository.findAll();
        assertThat(productQuatationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
