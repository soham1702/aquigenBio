package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ConsumptionDetails;
import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.ProductInventory;
import com.mycompany.myapp.domain.ProductTransaction;
import com.mycompany.myapp.domain.PurchaseOrder;
import com.mycompany.myapp.domain.SecurityUser;
import com.mycompany.myapp.domain.Transfer;
import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.repository.ProductInventoryRepository;
import com.mycompany.myapp.service.ProductInventoryService;
import com.mycompany.myapp.service.criteria.ProductInventoryCriteria;
import com.mycompany.myapp.service.dto.ProductInventoryDTO;
import com.mycompany.myapp.service.mapper.ProductInventoryMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductInventoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductInventoryResourceIT {

    private static final Instant DEFAULT_INWARD_OUTWARD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INWARD_OUTWARD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_INWARD_QTY = "AAAAAAAAAA";
    private static final String UPDATED_INWARD_QTY = "BBBBBBBBBB";

    private static final String DEFAULT_OUTWARD_QTY = "AAAAAAAAAA";
    private static final String UPDATED_OUTWARD_QTY = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL_QUANITY = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL_QUANITY = "BBBBBBBBBB";

    private static final Long DEFAULT_PRICE_PER_UNIT = 1L;
    private static final Long UPDATED_PRICE_PER_UNIT = 2L;
    private static final Long SMALLER_PRICE_PER_UNIT = 1L - 1L;

    private static final String DEFAULT_LOT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LOT_NO = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPIRY_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPIRY_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/product-inventories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductInventoryRepository productInventoryRepository;

    @Mock
    private ProductInventoryRepository productInventoryRepositoryMock;

    @Autowired
    private ProductInventoryMapper productInventoryMapper;

    @Mock
    private ProductInventoryService productInventoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductInventoryMockMvc;

    private ProductInventory productInventory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInventory createEntity(EntityManager em) {
        ProductInventory productInventory = new ProductInventory()
            .inwardOutwardDate(DEFAULT_INWARD_OUTWARD_DATE)
            .inwardQty(DEFAULT_INWARD_QTY)
            .outwardQty(DEFAULT_OUTWARD_QTY)
            .totalQuanity(DEFAULT_TOTAL_QUANITY)
            .pricePerUnit(DEFAULT_PRICE_PER_UNIT)
            .lotNo(DEFAULT_LOT_NO)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE);
        return productInventory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInventory createUpdatedEntity(EntityManager em) {
        ProductInventory productInventory = new ProductInventory()
            .inwardOutwardDate(UPDATED_INWARD_OUTWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        return productInventory;
    }

    @BeforeEach
    public void initTest() {
        productInventory = createEntity(em);
    }

    @Test
    @Transactional
    void createProductInventory() throws Exception {
        int databaseSizeBeforeCreate = productInventoryRepository.findAll().size();
        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);
        restProductInventoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInventory testProductInventory = productInventoryList.get(productInventoryList.size() - 1);
        assertThat(testProductInventory.getInwardOutwardDate()).isEqualTo(DEFAULT_INWARD_OUTWARD_DATE);
        assertThat(testProductInventory.getInwardQty()).isEqualTo(DEFAULT_INWARD_QTY);
        assertThat(testProductInventory.getOutwardQty()).isEqualTo(DEFAULT_OUTWARD_QTY);
        assertThat(testProductInventory.getTotalQuanity()).isEqualTo(DEFAULT_TOTAL_QUANITY);
        assertThat(testProductInventory.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testProductInventory.getLotNo()).isEqualTo(DEFAULT_LOT_NO);
        assertThat(testProductInventory.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testProductInventory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProductInventory.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testProductInventory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testProductInventory.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
        assertThat(testProductInventory.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testProductInventory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductInventory.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testProductInventory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void createProductInventoryWithExistingId() throws Exception {
        // Create the ProductInventory with an existing ID
        productInventory.setId(1L);
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        int databaseSizeBeforeCreate = productInventoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInventoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductInventories() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList
        restProductInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inwardOutwardDate").value(hasItem(DEFAULT_INWARD_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].inwardQty").value(hasItem(DEFAULT_INWARD_QTY)))
            .andExpect(jsonPath("$.[*].outwardQty").value(hasItem(DEFAULT_OUTWARD_QTY)))
            .andExpect(jsonPath("$.[*].totalQuanity").value(hasItem(DEFAULT_TOTAL_QUANITY)))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductInventoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(productInventoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductInventoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productInventoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllProductInventoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(productInventoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restProductInventoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(productInventoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getProductInventory() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get the productInventory
        restProductInventoryMockMvc
            .perform(get(ENTITY_API_URL_ID, productInventory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productInventory.getId().intValue()))
            .andExpect(jsonPath("$.inwardOutwardDate").value(DEFAULT_INWARD_OUTWARD_DATE.toString()))
            .andExpect(jsonPath("$.inwardQty").value(DEFAULT_INWARD_QTY))
            .andExpect(jsonPath("$.outwardQty").value(DEFAULT_OUTWARD_QTY))
            .andExpect(jsonPath("$.totalQuanity").value(DEFAULT_TOTAL_QUANITY))
            .andExpect(jsonPath("$.pricePerUnit").value(DEFAULT_PRICE_PER_UNIT.intValue()))
            .andExpect(jsonPath("$.lotNo").value(DEFAULT_LOT_NO))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()));
    }

    @Test
    @Transactional
    void getProductInventoriesByIdFiltering() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        Long id = productInventory.getId();

        defaultProductInventoryShouldBeFound("id.equals=" + id);
        defaultProductInventoryShouldNotBeFound("id.notEquals=" + id);

        defaultProductInventoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductInventoryShouldNotBeFound("id.greaterThan=" + id);

        defaultProductInventoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductInventoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardOutwardDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardOutwardDate equals to DEFAULT_INWARD_OUTWARD_DATE
        defaultProductInventoryShouldBeFound("inwardOutwardDate.equals=" + DEFAULT_INWARD_OUTWARD_DATE);

        // Get all the productInventoryList where inwardOutwardDate equals to UPDATED_INWARD_OUTWARD_DATE
        defaultProductInventoryShouldNotBeFound("inwardOutwardDate.equals=" + UPDATED_INWARD_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardOutwardDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardOutwardDate not equals to DEFAULT_INWARD_OUTWARD_DATE
        defaultProductInventoryShouldNotBeFound("inwardOutwardDate.notEquals=" + DEFAULT_INWARD_OUTWARD_DATE);

        // Get all the productInventoryList where inwardOutwardDate not equals to UPDATED_INWARD_OUTWARD_DATE
        defaultProductInventoryShouldBeFound("inwardOutwardDate.notEquals=" + UPDATED_INWARD_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardOutwardDateIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardOutwardDate in DEFAULT_INWARD_OUTWARD_DATE or UPDATED_INWARD_OUTWARD_DATE
        defaultProductInventoryShouldBeFound("inwardOutwardDate.in=" + DEFAULT_INWARD_OUTWARD_DATE + "," + UPDATED_INWARD_OUTWARD_DATE);

        // Get all the productInventoryList where inwardOutwardDate equals to UPDATED_INWARD_OUTWARD_DATE
        defaultProductInventoryShouldNotBeFound("inwardOutwardDate.in=" + UPDATED_INWARD_OUTWARD_DATE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardOutwardDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardOutwardDate is not null
        defaultProductInventoryShouldBeFound("inwardOutwardDate.specified=true");

        // Get all the productInventoryList where inwardOutwardDate is null
        defaultProductInventoryShouldNotBeFound("inwardOutwardDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardQty equals to DEFAULT_INWARD_QTY
        defaultProductInventoryShouldBeFound("inwardQty.equals=" + DEFAULT_INWARD_QTY);

        // Get all the productInventoryList where inwardQty equals to UPDATED_INWARD_QTY
        defaultProductInventoryShouldNotBeFound("inwardQty.equals=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardQty not equals to DEFAULT_INWARD_QTY
        defaultProductInventoryShouldNotBeFound("inwardQty.notEquals=" + DEFAULT_INWARD_QTY);

        // Get all the productInventoryList where inwardQty not equals to UPDATED_INWARD_QTY
        defaultProductInventoryShouldBeFound("inwardQty.notEquals=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardQtyIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardQty in DEFAULT_INWARD_QTY or UPDATED_INWARD_QTY
        defaultProductInventoryShouldBeFound("inwardQty.in=" + DEFAULT_INWARD_QTY + "," + UPDATED_INWARD_QTY);

        // Get all the productInventoryList where inwardQty equals to UPDATED_INWARD_QTY
        defaultProductInventoryShouldNotBeFound("inwardQty.in=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardQty is not null
        defaultProductInventoryShouldBeFound("inwardQty.specified=true");

        // Get all the productInventoryList where inwardQty is null
        defaultProductInventoryShouldNotBeFound("inwardQty.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardQtyContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardQty contains DEFAULT_INWARD_QTY
        defaultProductInventoryShouldBeFound("inwardQty.contains=" + DEFAULT_INWARD_QTY);

        // Get all the productInventoryList where inwardQty contains UPDATED_INWARD_QTY
        defaultProductInventoryShouldNotBeFound("inwardQty.contains=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByInwardQtyNotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where inwardQty does not contain DEFAULT_INWARD_QTY
        defaultProductInventoryShouldNotBeFound("inwardQty.doesNotContain=" + DEFAULT_INWARD_QTY);

        // Get all the productInventoryList where inwardQty does not contain UPDATED_INWARD_QTY
        defaultProductInventoryShouldBeFound("inwardQty.doesNotContain=" + UPDATED_INWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByOutwardQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where outwardQty equals to DEFAULT_OUTWARD_QTY
        defaultProductInventoryShouldBeFound("outwardQty.equals=" + DEFAULT_OUTWARD_QTY);

        // Get all the productInventoryList where outwardQty equals to UPDATED_OUTWARD_QTY
        defaultProductInventoryShouldNotBeFound("outwardQty.equals=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByOutwardQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where outwardQty not equals to DEFAULT_OUTWARD_QTY
        defaultProductInventoryShouldNotBeFound("outwardQty.notEquals=" + DEFAULT_OUTWARD_QTY);

        // Get all the productInventoryList where outwardQty not equals to UPDATED_OUTWARD_QTY
        defaultProductInventoryShouldBeFound("outwardQty.notEquals=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByOutwardQtyIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where outwardQty in DEFAULT_OUTWARD_QTY or UPDATED_OUTWARD_QTY
        defaultProductInventoryShouldBeFound("outwardQty.in=" + DEFAULT_OUTWARD_QTY + "," + UPDATED_OUTWARD_QTY);

        // Get all the productInventoryList where outwardQty equals to UPDATED_OUTWARD_QTY
        defaultProductInventoryShouldNotBeFound("outwardQty.in=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByOutwardQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where outwardQty is not null
        defaultProductInventoryShouldBeFound("outwardQty.specified=true");

        // Get all the productInventoryList where outwardQty is null
        defaultProductInventoryShouldNotBeFound("outwardQty.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByOutwardQtyContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where outwardQty contains DEFAULT_OUTWARD_QTY
        defaultProductInventoryShouldBeFound("outwardQty.contains=" + DEFAULT_OUTWARD_QTY);

        // Get all the productInventoryList where outwardQty contains UPDATED_OUTWARD_QTY
        defaultProductInventoryShouldNotBeFound("outwardQty.contains=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByOutwardQtyNotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where outwardQty does not contain DEFAULT_OUTWARD_QTY
        defaultProductInventoryShouldNotBeFound("outwardQty.doesNotContain=" + DEFAULT_OUTWARD_QTY);

        // Get all the productInventoryList where outwardQty does not contain UPDATED_OUTWARD_QTY
        defaultProductInventoryShouldBeFound("outwardQty.doesNotContain=" + UPDATED_OUTWARD_QTY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTotalQuanityIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where totalQuanity equals to DEFAULT_TOTAL_QUANITY
        defaultProductInventoryShouldBeFound("totalQuanity.equals=" + DEFAULT_TOTAL_QUANITY);

        // Get all the productInventoryList where totalQuanity equals to UPDATED_TOTAL_QUANITY
        defaultProductInventoryShouldNotBeFound("totalQuanity.equals=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTotalQuanityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where totalQuanity not equals to DEFAULT_TOTAL_QUANITY
        defaultProductInventoryShouldNotBeFound("totalQuanity.notEquals=" + DEFAULT_TOTAL_QUANITY);

        // Get all the productInventoryList where totalQuanity not equals to UPDATED_TOTAL_QUANITY
        defaultProductInventoryShouldBeFound("totalQuanity.notEquals=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTotalQuanityIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where totalQuanity in DEFAULT_TOTAL_QUANITY or UPDATED_TOTAL_QUANITY
        defaultProductInventoryShouldBeFound("totalQuanity.in=" + DEFAULT_TOTAL_QUANITY + "," + UPDATED_TOTAL_QUANITY);

        // Get all the productInventoryList where totalQuanity equals to UPDATED_TOTAL_QUANITY
        defaultProductInventoryShouldNotBeFound("totalQuanity.in=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTotalQuanityIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where totalQuanity is not null
        defaultProductInventoryShouldBeFound("totalQuanity.specified=true");

        // Get all the productInventoryList where totalQuanity is null
        defaultProductInventoryShouldNotBeFound("totalQuanity.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTotalQuanityContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where totalQuanity contains DEFAULT_TOTAL_QUANITY
        defaultProductInventoryShouldBeFound("totalQuanity.contains=" + DEFAULT_TOTAL_QUANITY);

        // Get all the productInventoryList where totalQuanity contains UPDATED_TOTAL_QUANITY
        defaultProductInventoryShouldNotBeFound("totalQuanity.contains=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTotalQuanityNotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where totalQuanity does not contain DEFAULT_TOTAL_QUANITY
        defaultProductInventoryShouldNotBeFound("totalQuanity.doesNotContain=" + DEFAULT_TOTAL_QUANITY);

        // Get all the productInventoryList where totalQuanity does not contain UPDATED_TOTAL_QUANITY
        defaultProductInventoryShouldBeFound("totalQuanity.doesNotContain=" + UPDATED_TOTAL_QUANITY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit equals to DEFAULT_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.equals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.equals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit not equals to DEFAULT_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.notEquals=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit not equals to UPDATED_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.notEquals=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit in DEFAULT_PRICE_PER_UNIT or UPDATED_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.in=" + DEFAULT_PRICE_PER_UNIT + "," + UPDATED_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit equals to UPDATED_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.in=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit is not null
        defaultProductInventoryShouldBeFound("pricePerUnit.specified=true");

        // Get all the productInventoryList where pricePerUnit is null
        defaultProductInventoryShouldNotBeFound("pricePerUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit is greater than or equal to DEFAULT_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.greaterThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit is greater than or equal to UPDATED_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.greaterThanOrEqual=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit is less than or equal to DEFAULT_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.lessThanOrEqual=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit is less than or equal to SMALLER_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.lessThanOrEqual=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsLessThanSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit is less than DEFAULT_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.lessThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit is less than UPDATED_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.lessThan=" + UPDATED_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPricePerUnitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where pricePerUnit is greater than DEFAULT_PRICE_PER_UNIT
        defaultProductInventoryShouldNotBeFound("pricePerUnit.greaterThan=" + DEFAULT_PRICE_PER_UNIT);

        // Get all the productInventoryList where pricePerUnit is greater than SMALLER_PRICE_PER_UNIT
        defaultProductInventoryShouldBeFound("pricePerUnit.greaterThan=" + SMALLER_PRICE_PER_UNIT);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLotNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lotNo equals to DEFAULT_LOT_NO
        defaultProductInventoryShouldBeFound("lotNo.equals=" + DEFAULT_LOT_NO);

        // Get all the productInventoryList where lotNo equals to UPDATED_LOT_NO
        defaultProductInventoryShouldNotBeFound("lotNo.equals=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLotNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lotNo not equals to DEFAULT_LOT_NO
        defaultProductInventoryShouldNotBeFound("lotNo.notEquals=" + DEFAULT_LOT_NO);

        // Get all the productInventoryList where lotNo not equals to UPDATED_LOT_NO
        defaultProductInventoryShouldBeFound("lotNo.notEquals=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLotNoIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lotNo in DEFAULT_LOT_NO or UPDATED_LOT_NO
        defaultProductInventoryShouldBeFound("lotNo.in=" + DEFAULT_LOT_NO + "," + UPDATED_LOT_NO);

        // Get all the productInventoryList where lotNo equals to UPDATED_LOT_NO
        defaultProductInventoryShouldNotBeFound("lotNo.in=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLotNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lotNo is not null
        defaultProductInventoryShouldBeFound("lotNo.specified=true");

        // Get all the productInventoryList where lotNo is null
        defaultProductInventoryShouldNotBeFound("lotNo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLotNoContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lotNo contains DEFAULT_LOT_NO
        defaultProductInventoryShouldBeFound("lotNo.contains=" + DEFAULT_LOT_NO);

        // Get all the productInventoryList where lotNo contains UPDATED_LOT_NO
        defaultProductInventoryShouldNotBeFound("lotNo.contains=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLotNoNotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lotNo does not contain DEFAULT_LOT_NO
        defaultProductInventoryShouldNotBeFound("lotNo.doesNotContain=" + DEFAULT_LOT_NO);

        // Get all the productInventoryList where lotNo does not contain UPDATED_LOT_NO
        defaultProductInventoryShouldBeFound("lotNo.doesNotContain=" + UPDATED_LOT_NO);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultProductInventoryShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the productInventoryList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultProductInventoryShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultProductInventoryShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the productInventoryList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultProductInventoryShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultProductInventoryShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the productInventoryList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultProductInventoryShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where expiryDate is not null
        defaultProductInventoryShouldBeFound("expiryDate.specified=true");

        // Get all the productInventoryList where expiryDate is null
        defaultProductInventoryShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultProductInventoryShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productInventoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductInventoryShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField1 not equals to DEFAULT_FREE_FIELD_1
        defaultProductInventoryShouldNotBeFound("freeField1.notEquals=" + DEFAULT_FREE_FIELD_1);

        // Get all the productInventoryList where freeField1 not equals to UPDATED_FREE_FIELD_1
        defaultProductInventoryShouldBeFound("freeField1.notEquals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultProductInventoryShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the productInventoryList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultProductInventoryShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField1 is not null
        defaultProductInventoryShouldBeFound("freeField1.specified=true");

        // Get all the productInventoryList where freeField1 is null
        defaultProductInventoryShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultProductInventoryShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the productInventoryList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultProductInventoryShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultProductInventoryShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the productInventoryList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultProductInventoryShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultProductInventoryShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productInventoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductInventoryShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField2 not equals to DEFAULT_FREE_FIELD_2
        defaultProductInventoryShouldNotBeFound("freeField2.notEquals=" + DEFAULT_FREE_FIELD_2);

        // Get all the productInventoryList where freeField2 not equals to UPDATED_FREE_FIELD_2
        defaultProductInventoryShouldBeFound("freeField2.notEquals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultProductInventoryShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the productInventoryList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultProductInventoryShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField2 is not null
        defaultProductInventoryShouldBeFound("freeField2.specified=true");

        // Get all the productInventoryList where freeField2 is null
        defaultProductInventoryShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultProductInventoryShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the productInventoryList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultProductInventoryShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultProductInventoryShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the productInventoryList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultProductInventoryShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultProductInventoryShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the productInventoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultProductInventoryShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField3 not equals to DEFAULT_FREE_FIELD_3
        defaultProductInventoryShouldNotBeFound("freeField3.notEquals=" + DEFAULT_FREE_FIELD_3);

        // Get all the productInventoryList where freeField3 not equals to UPDATED_FREE_FIELD_3
        defaultProductInventoryShouldBeFound("freeField3.notEquals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultProductInventoryShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the productInventoryList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultProductInventoryShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField3 is not null
        defaultProductInventoryShouldBeFound("freeField3.specified=true");

        // Get all the productInventoryList where freeField3 is null
        defaultProductInventoryShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultProductInventoryShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the productInventoryList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultProductInventoryShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultProductInventoryShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the productInventoryList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultProductInventoryShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultProductInventoryShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the productInventoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultProductInventoryShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField4 not equals to DEFAULT_FREE_FIELD_4
        defaultProductInventoryShouldNotBeFound("freeField4.notEquals=" + DEFAULT_FREE_FIELD_4);

        // Get all the productInventoryList where freeField4 not equals to UPDATED_FREE_FIELD_4
        defaultProductInventoryShouldBeFound("freeField4.notEquals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultProductInventoryShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the productInventoryList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultProductInventoryShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField4 is not null
        defaultProductInventoryShouldBeFound("freeField4.specified=true");

        // Get all the productInventoryList where freeField4 is null
        defaultProductInventoryShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultProductInventoryShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the productInventoryList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultProductInventoryShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultProductInventoryShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the productInventoryList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultProductInventoryShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultProductInventoryShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductInventoryShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultProductInventoryShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the productInventoryList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultProductInventoryShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultProductInventoryShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the productInventoryList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultProductInventoryShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModified is not null
        defaultProductInventoryShouldBeFound("lastModified.specified=true");

        // Get all the productInventoryList where lastModified is null
        defaultProductInventoryShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductInventoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductInventoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductInventoryShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productInventoryList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductInventoryShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductInventoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productInventoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductInventoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModifiedBy is not null
        defaultProductInventoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productInventoryList where lastModifiedBy is null
        defaultProductInventoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductInventoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productInventoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductInventoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductInventoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productInventoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductInventoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isDeleted equals to DEFAULT_IS_DELETED
        defaultProductInventoryShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the productInventoryList where isDeleted equals to UPDATED_IS_DELETED
        defaultProductInventoryShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultProductInventoryShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the productInventoryList where isDeleted not equals to UPDATED_IS_DELETED
        defaultProductInventoryShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultProductInventoryShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the productInventoryList where isDeleted equals to UPDATED_IS_DELETED
        defaultProductInventoryShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isDeleted is not null
        defaultProductInventoryShouldBeFound("isDeleted.specified=true");

        // Get all the productInventoryList where isDeleted is null
        defaultProductInventoryShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isActive equals to DEFAULT_IS_ACTIVE
        defaultProductInventoryShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the productInventoryList where isActive equals to UPDATED_IS_ACTIVE
        defaultProductInventoryShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultProductInventoryShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the productInventoryList where isActive not equals to UPDATED_IS_ACTIVE
        defaultProductInventoryShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultProductInventoryShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the productInventoryList where isActive equals to UPDATED_IS_ACTIVE
        defaultProductInventoryShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllProductInventoriesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        // Get all the productInventoryList where isActive is not null
        defaultProductInventoryShouldBeFound("isActive.specified=true");

        // Get all the productInventoryList where isActive is null
        defaultProductInventoryShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllProductInventoriesByTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
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
        productInventory.addTransfer(transfer);
        productInventoryRepository.saveAndFlush(productInventory);
        Long transferId = transfer.getId();

        // Get all the productInventoryList where transfer equals to transferId
        defaultProductInventoryShouldBeFound("transferId.equals=" + transferId);

        // Get all the productInventoryList where transfer equals to (transferId + 1)
        defaultProductInventoryShouldNotBeFound("transferId.equals=" + (transferId + 1));
    }

    @Test
    @Transactional
    void getAllProductInventoriesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
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
        productInventory.addPurchaseOrder(purchaseOrder);
        productInventoryRepository.saveAndFlush(productInventory);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the productInventoryList where purchaseOrder equals to purchaseOrderId
        defaultProductInventoryShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the productInventoryList where purchaseOrder equals to (purchaseOrderId + 1)
        defaultProductInventoryShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    @Test
    @Transactional
    void getAllProductInventoriesByConsumptionDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
        ConsumptionDetails consumptionDetails;
        if (TestUtil.findAll(em, ConsumptionDetails.class).isEmpty()) {
            consumptionDetails = ConsumptionDetailsResourceIT.createEntity(em);
            em.persist(consumptionDetails);
            em.flush();
        } else {
            consumptionDetails = TestUtil.findAll(em, ConsumptionDetails.class).get(0);
        }
        em.persist(consumptionDetails);
        em.flush();
        productInventory.addConsumptionDetails(consumptionDetails);
        productInventoryRepository.saveAndFlush(productInventory);
        Long consumptionDetailsId = consumptionDetails.getId();

        // Get all the productInventoryList where consumptionDetails equals to consumptionDetailsId
        defaultProductInventoryShouldBeFound("consumptionDetailsId.equals=" + consumptionDetailsId);

        // Get all the productInventoryList where consumptionDetails equals to (consumptionDetailsId + 1)
        defaultProductInventoryShouldNotBeFound("consumptionDetailsId.equals=" + (consumptionDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllProductInventoriesByProductTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
        ProductTransaction productTransaction;
        if (TestUtil.findAll(em, ProductTransaction.class).isEmpty()) {
            productTransaction = ProductTransactionResourceIT.createEntity(em);
            em.persist(productTransaction);
            em.flush();
        } else {
            productTransaction = TestUtil.findAll(em, ProductTransaction.class).get(0);
        }
        em.persist(productTransaction);
        em.flush();
        productInventory.addProductTransaction(productTransaction);
        productInventoryRepository.saveAndFlush(productInventory);
        Long productTransactionId = productTransaction.getId();

        // Get all the productInventoryList where productTransaction equals to productTransactionId
        defaultProductInventoryShouldBeFound("productTransactionId.equals=" + productTransactionId);

        // Get all the productInventoryList where productTransaction equals to (productTransactionId + 1)
        defaultProductInventoryShouldNotBeFound("productTransactionId.equals=" + (productTransactionId + 1));
    }

    @Test
    @Transactional
    void getAllProductInventoriesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
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
        productInventory.addProduct(product);
        productInventoryRepository.saveAndFlush(productInventory);
        Long productId = product.getId();

        // Get all the productInventoryList where product equals to productId
        defaultProductInventoryShouldBeFound("productId.equals=" + productId);

        // Get all the productInventoryList where product equals to (productId + 1)
        defaultProductInventoryShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductInventoriesByWarehouseIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
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
        productInventory.addWarehouse(warehouse);
        productInventoryRepository.saveAndFlush(productInventory);
        Long warehouseId = warehouse.getId();

        // Get all the productInventoryList where warehouse equals to warehouseId
        defaultProductInventoryShouldBeFound("warehouseId.equals=" + warehouseId);

        // Get all the productInventoryList where warehouse equals to (warehouseId + 1)
        defaultProductInventoryShouldNotBeFound("warehouseId.equals=" + (warehouseId + 1));
    }

    @Test
    @Transactional
    void getAllProductInventoriesBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);
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
        productInventory.addSecurityUser(securityUser);
        productInventoryRepository.saveAndFlush(productInventory);
        Long securityUserId = securityUser.getId();

        // Get all the productInventoryList where securityUser equals to securityUserId
        defaultProductInventoryShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the productInventoryList where securityUser equals to (securityUserId + 1)
        defaultProductInventoryShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductInventoryShouldBeFound(String filter) throws Exception {
        restProductInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInventory.getId().intValue())))
            .andExpect(jsonPath("$.[*].inwardOutwardDate").value(hasItem(DEFAULT_INWARD_OUTWARD_DATE.toString())))
            .andExpect(jsonPath("$.[*].inwardQty").value(hasItem(DEFAULT_INWARD_QTY)))
            .andExpect(jsonPath("$.[*].outwardQty").value(hasItem(DEFAULT_OUTWARD_QTY)))
            .andExpect(jsonPath("$.[*].totalQuanity").value(hasItem(DEFAULT_TOTAL_QUANITY)))
            .andExpect(jsonPath("$.[*].pricePerUnit").value(hasItem(DEFAULT_PRICE_PER_UNIT.intValue())))
            .andExpect(jsonPath("$.[*].lotNo").value(hasItem(DEFAULT_LOT_NO)))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())));

        // Check, that the count call also returns 1
        restProductInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductInventoryShouldNotBeFound(String filter) throws Exception {
        restProductInventoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductInventoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductInventory() throws Exception {
        // Get the productInventory
        restProductInventoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductInventory() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();

        // Update the productInventory
        ProductInventory updatedProductInventory = productInventoryRepository.findById(productInventory.getId()).get();
        // Disconnect from session so that the updates on updatedProductInventory are not directly saved in db
        em.detach(updatedProductInventory);
        updatedProductInventory
            .inwardOutwardDate(UPDATED_INWARD_OUTWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(updatedProductInventory);

        restProductInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
        ProductInventory testProductInventory = productInventoryList.get(productInventoryList.size() - 1);
        assertThat(testProductInventory.getInwardOutwardDate()).isEqualTo(UPDATED_INWARD_OUTWARD_DATE);
        assertThat(testProductInventory.getInwardQty()).isEqualTo(UPDATED_INWARD_QTY);
        assertThat(testProductInventory.getOutwardQty()).isEqualTo(UPDATED_OUTWARD_QTY);
        assertThat(testProductInventory.getTotalQuanity()).isEqualTo(UPDATED_TOTAL_QUANITY);
        assertThat(testProductInventory.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testProductInventory.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testProductInventory.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testProductInventory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductInventory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductInventory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProductInventory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProductInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductInventory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProductInventory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void putNonExistingProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();
        productInventory.setId(count.incrementAndGet());

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInventoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();
        productInventory.setId(count.incrementAndGet());

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();
        productInventory.setId(count.incrementAndGet());

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductInventoryWithPatch() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();

        // Update the productInventory using partial update
        ProductInventory partialUpdatedProductInventory = new ProductInventory();
        partialUpdatedProductInventory.setId(productInventory.getId());

        partialUpdatedProductInventory
            .inwardOutwardDate(UPDATED_INWARD_OUTWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .lotNo(UPDATED_LOT_NO)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInventory))
            )
            .andExpect(status().isOk());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
        ProductInventory testProductInventory = productInventoryList.get(productInventoryList.size() - 1);
        assertThat(testProductInventory.getInwardOutwardDate()).isEqualTo(UPDATED_INWARD_OUTWARD_DATE);
        assertThat(testProductInventory.getInwardQty()).isEqualTo(UPDATED_INWARD_QTY);
        assertThat(testProductInventory.getOutwardQty()).isEqualTo(DEFAULT_OUTWARD_QTY);
        assertThat(testProductInventory.getTotalQuanity()).isEqualTo(UPDATED_TOTAL_QUANITY);
        assertThat(testProductInventory.getPricePerUnit()).isEqualTo(DEFAULT_PRICE_PER_UNIT);
        assertThat(testProductInventory.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testProductInventory.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testProductInventory.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testProductInventory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductInventory.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testProductInventory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProductInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductInventory.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testProductInventory.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
    }

    @Test
    @Transactional
    void fullUpdateProductInventoryWithPatch() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();

        // Update the productInventory using partial update
        ProductInventory partialUpdatedProductInventory = new ProductInventory();
        partialUpdatedProductInventory.setId(productInventory.getId());

        partialUpdatedProductInventory
            .inwardOutwardDate(UPDATED_INWARD_OUTWARD_DATE)
            .inwardQty(UPDATED_INWARD_QTY)
            .outwardQty(UPDATED_OUTWARD_QTY)
            .totalQuanity(UPDATED_TOTAL_QUANITY)
            .pricePerUnit(UPDATED_PRICE_PER_UNIT)
            .lotNo(UPDATED_LOT_NO)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE);

        restProductInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInventory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInventory))
            )
            .andExpect(status().isOk());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
        ProductInventory testProductInventory = productInventoryList.get(productInventoryList.size() - 1);
        assertThat(testProductInventory.getInwardOutwardDate()).isEqualTo(UPDATED_INWARD_OUTWARD_DATE);
        assertThat(testProductInventory.getInwardQty()).isEqualTo(UPDATED_INWARD_QTY);
        assertThat(testProductInventory.getOutwardQty()).isEqualTo(UPDATED_OUTWARD_QTY);
        assertThat(testProductInventory.getTotalQuanity()).isEqualTo(UPDATED_TOTAL_QUANITY);
        assertThat(testProductInventory.getPricePerUnit()).isEqualTo(UPDATED_PRICE_PER_UNIT);
        assertThat(testProductInventory.getLotNo()).isEqualTo(UPDATED_LOT_NO);
        assertThat(testProductInventory.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testProductInventory.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testProductInventory.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testProductInventory.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testProductInventory.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
        assertThat(testProductInventory.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testProductInventory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductInventory.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testProductInventory.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void patchNonExistingProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();
        productInventory.setId(count.incrementAndGet());

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productInventoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();
        productInventory.setId(count.incrementAndGet());

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductInventory() throws Exception {
        int databaseSizeBeforeUpdate = productInventoryRepository.findAll().size();
        productInventory.setId(count.incrementAndGet());

        // Create the ProductInventory
        ProductInventoryDTO productInventoryDTO = productInventoryMapper.toDto(productInventory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInventoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInventoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInventory in the database
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductInventory() throws Exception {
        // Initialize the database
        productInventoryRepository.saveAndFlush(productInventory);

        int databaseSizeBeforeDelete = productInventoryRepository.findAll().size();

        // Delete the productInventory
        restProductInventoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, productInventory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductInventory> productInventoryList = productInventoryRepository.findAll();
        assertThat(productInventoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
