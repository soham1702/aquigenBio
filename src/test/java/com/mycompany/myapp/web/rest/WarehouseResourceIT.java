package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.ProductInventory;
import com.mycompany.myapp.domain.ProductTransaction;
import com.mycompany.myapp.domain.PurchaseOrder;
import com.mycompany.myapp.domain.SecurityUser;
import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.repository.WarehouseRepository;
import com.mycompany.myapp.service.WarehouseService;
import com.mycompany.myapp.service.criteria.WarehouseCriteria;
import com.mycompany.myapp.service.dto.WarehouseDTO;
import com.mycompany.myapp.service.mapper.WarehouseMapper;
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
 * Integration tests for the {@link WarehouseResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WarehouseResourceIT {

    private static final Long DEFAULT_WAREHOUSE_ID = 1L;
    private static final Long UPDATED_WAREHOUSE_ID = 2L;
    private static final Long SMALLER_WAREHOUSE_ID = 1L - 1L;

    private static final String DEFAULT_WH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PINCODE = 1;
    private static final Integer UPDATED_PINCODE = 2;
    private static final Integer SMALLER_PINCODE = 1 - 1;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE = "AAAAAAAAAA";
    private static final String UPDATED_STATE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_G_ST_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_G_ST_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MANAGER_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_MANAGER_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/warehouses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Mock
    private WarehouseRepository warehouseRepositoryMock;

    @Autowired
    private WarehouseMapper warehouseMapper;

    @Mock
    private WarehouseService warehouseServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWarehouseMockMvc;

    private Warehouse warehouse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warehouse createEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse()
            .warehouseId(DEFAULT_WAREHOUSE_ID)
            .whName(DEFAULT_WH_NAME)
            .address(DEFAULT_ADDRESS)
            .pincode(DEFAULT_PINCODE)
            .city(DEFAULT_CITY)
            .state(DEFAULT_STATE)
            .country(DEFAULT_COUNTRY)
            .gSTDetails(DEFAULT_G_ST_DETAILS)
            .managerName(DEFAULT_MANAGER_NAME)
            .managerEmail(DEFAULT_MANAGER_EMAIL)
            .managerContact(DEFAULT_MANAGER_CONTACT)
            .contact(DEFAULT_CONTACT)
            .isDeleted(DEFAULT_IS_DELETED)
            .isActive(DEFAULT_IS_ACTIVE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        return warehouse;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Warehouse createUpdatedEntity(EntityManager em) {
        Warehouse warehouse = new Warehouse()
            .warehouseId(UPDATED_WAREHOUSE_ID)
            .whName(UPDATED_WH_NAME)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerEmail(UPDATED_MANAGER_EMAIL)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .contact(UPDATED_CONTACT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        return warehouse;
    }

    @BeforeEach
    public void initTest() {
        warehouse = createEntity(em);
    }

    @Test
    @Transactional
    void createWarehouse() throws Exception {
        int databaseSizeBeforeCreate = warehouseRepository.findAll().size();
        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);
        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isCreated());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeCreate + 1);
        Warehouse testWarehouse = warehouseList.get(warehouseList.size() - 1);
        assertThat(testWarehouse.getWarehouseId()).isEqualTo(DEFAULT_WAREHOUSE_ID);
        assertThat(testWarehouse.getWhName()).isEqualTo(DEFAULT_WH_NAME);
        assertThat(testWarehouse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWarehouse.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testWarehouse.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWarehouse.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWarehouse.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWarehouse.getgSTDetails()).isEqualTo(DEFAULT_G_ST_DETAILS);
        assertThat(testWarehouse.getManagerName()).isEqualTo(DEFAULT_MANAGER_NAME);
        assertThat(testWarehouse.getManagerEmail()).isEqualTo(DEFAULT_MANAGER_EMAIL);
        assertThat(testWarehouse.getManagerContact()).isEqualTo(DEFAULT_MANAGER_CONTACT);
        assertThat(testWarehouse.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testWarehouse.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWarehouse.getIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testWarehouse.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWarehouse.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void createWarehouseWithExistingId() throws Exception {
        // Create the Warehouse with an existing ID
        warehouse.setId(1L);
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        int databaseSizeBeforeCreate = warehouseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLastModifiedIsRequired() throws Exception {
        int databaseSizeBeforeTest = warehouseRepository.findAll().size();
        // set the field null
        warehouse.setLastModified(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastModifiedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = warehouseRepository.findAll().size();
        // set the field null
        warehouse.setLastModifiedBy(null);

        // Create the Warehouse, which fails.
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        restWarehouseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isBadRequest());

        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWarehouses() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].warehouseId").value(hasItem(DEFAULT_WAREHOUSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].whName").value(hasItem(DEFAULT_WH_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].gSTDetails").value(hasItem(DEFAULT_G_ST_DETAILS)))
            .andExpect(jsonPath("$.[*].managerName").value(hasItem(DEFAULT_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].managerEmail").value(hasItem(DEFAULT_MANAGER_EMAIL)))
            .andExpect(jsonPath("$.[*].managerContact").value(hasItem(DEFAULT_MANAGER_CONTACT)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWarehousesWithEagerRelationshipsIsEnabled() throws Exception {
        when(warehouseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWarehouseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(warehouseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWarehousesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(warehouseServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWarehouseMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(warehouseServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get the warehouse
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL_ID, warehouse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(warehouse.getId().intValue()))
            .andExpect(jsonPath("$.warehouseId").value(DEFAULT_WAREHOUSE_ID.intValue()))
            .andExpect(jsonPath("$.whName").value(DEFAULT_WH_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.gSTDetails").value(DEFAULT_G_ST_DETAILS))
            .andExpect(jsonPath("$.managerName").value(DEFAULT_MANAGER_NAME))
            .andExpect(jsonPath("$.managerEmail").value(DEFAULT_MANAGER_EMAIL))
            .andExpect(jsonPath("$.managerContact").value(DEFAULT_MANAGER_CONTACT))
            .andExpect(jsonPath("$.contact").value(DEFAULT_CONTACT))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY));
    }

    @Test
    @Transactional
    void getWarehousesByIdFiltering() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        Long id = warehouse.getId();

        defaultWarehouseShouldBeFound("id.equals=" + id);
        defaultWarehouseShouldNotBeFound("id.notEquals=" + id);

        defaultWarehouseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWarehouseShouldNotBeFound("id.greaterThan=" + id);

        defaultWarehouseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWarehouseShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId equals to DEFAULT_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.equals=" + DEFAULT_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId equals to UPDATED_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.equals=" + UPDATED_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId not equals to DEFAULT_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.notEquals=" + DEFAULT_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId not equals to UPDATED_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.notEquals=" + UPDATED_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId in DEFAULT_WAREHOUSE_ID or UPDATED_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.in=" + DEFAULT_WAREHOUSE_ID + "," + UPDATED_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId equals to UPDATED_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.in=" + UPDATED_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId is not null
        defaultWarehouseShouldBeFound("warehouseId.specified=true");

        // Get all the warehouseList where warehouseId is null
        defaultWarehouseShouldNotBeFound("warehouseId.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId is greater than or equal to DEFAULT_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.greaterThanOrEqual=" + DEFAULT_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId is greater than or equal to UPDATED_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.greaterThanOrEqual=" + UPDATED_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId is less than or equal to DEFAULT_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.lessThanOrEqual=" + DEFAULT_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId is less than or equal to SMALLER_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.lessThanOrEqual=" + SMALLER_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsLessThanSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId is less than DEFAULT_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.lessThan=" + DEFAULT_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId is less than UPDATED_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.lessThan=" + UPDATED_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWarehouseIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where warehouseId is greater than DEFAULT_WAREHOUSE_ID
        defaultWarehouseShouldNotBeFound("warehouseId.greaterThan=" + DEFAULT_WAREHOUSE_ID);

        // Get all the warehouseList where warehouseId is greater than SMALLER_WAREHOUSE_ID
        defaultWarehouseShouldBeFound("warehouseId.greaterThan=" + SMALLER_WAREHOUSE_ID);
    }

    @Test
    @Transactional
    void getAllWarehousesByWhNameIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where whName equals to DEFAULT_WH_NAME
        defaultWarehouseShouldBeFound("whName.equals=" + DEFAULT_WH_NAME);

        // Get all the warehouseList where whName equals to UPDATED_WH_NAME
        defaultWarehouseShouldNotBeFound("whName.equals=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByWhNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where whName not equals to DEFAULT_WH_NAME
        defaultWarehouseShouldNotBeFound("whName.notEquals=" + DEFAULT_WH_NAME);

        // Get all the warehouseList where whName not equals to UPDATED_WH_NAME
        defaultWarehouseShouldBeFound("whName.notEquals=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByWhNameIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where whName in DEFAULT_WH_NAME or UPDATED_WH_NAME
        defaultWarehouseShouldBeFound("whName.in=" + DEFAULT_WH_NAME + "," + UPDATED_WH_NAME);

        // Get all the warehouseList where whName equals to UPDATED_WH_NAME
        defaultWarehouseShouldNotBeFound("whName.in=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByWhNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where whName is not null
        defaultWarehouseShouldBeFound("whName.specified=true");

        // Get all the warehouseList where whName is null
        defaultWarehouseShouldNotBeFound("whName.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByWhNameContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where whName contains DEFAULT_WH_NAME
        defaultWarehouseShouldBeFound("whName.contains=" + DEFAULT_WH_NAME);

        // Get all the warehouseList where whName contains UPDATED_WH_NAME
        defaultWarehouseShouldNotBeFound("whName.contains=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByWhNameNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where whName does not contain DEFAULT_WH_NAME
        defaultWarehouseShouldNotBeFound("whName.doesNotContain=" + DEFAULT_WH_NAME);

        // Get all the warehouseList where whName does not contain UPDATED_WH_NAME
        defaultWarehouseShouldBeFound("whName.doesNotContain=" + UPDATED_WH_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where address equals to DEFAULT_ADDRESS
        defaultWarehouseShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the warehouseList where address equals to UPDATED_ADDRESS
        defaultWarehouseShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWarehousesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where address not equals to DEFAULT_ADDRESS
        defaultWarehouseShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the warehouseList where address not equals to UPDATED_ADDRESS
        defaultWarehouseShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWarehousesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultWarehouseShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the warehouseList where address equals to UPDATED_ADDRESS
        defaultWarehouseShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWarehousesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where address is not null
        defaultWarehouseShouldBeFound("address.specified=true");

        // Get all the warehouseList where address is null
        defaultWarehouseShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByAddressContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where address contains DEFAULT_ADDRESS
        defaultWarehouseShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the warehouseList where address contains UPDATED_ADDRESS
        defaultWarehouseShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWarehousesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where address does not contain DEFAULT_ADDRESS
        defaultWarehouseShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the warehouseList where address does not contain UPDATED_ADDRESS
        defaultWarehouseShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode equals to DEFAULT_PINCODE
        defaultWarehouseShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the warehouseList where pincode equals to UPDATED_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode not equals to DEFAULT_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.notEquals=" + DEFAULT_PINCODE);

        // Get all the warehouseList where pincode not equals to UPDATED_PINCODE
        defaultWarehouseShouldBeFound("pincode.notEquals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultWarehouseShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the warehouseList where pincode equals to UPDATED_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode is not null
        defaultWarehouseShouldBeFound("pincode.specified=true");

        // Get all the warehouseList where pincode is null
        defaultWarehouseShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode is greater than or equal to DEFAULT_PINCODE
        defaultWarehouseShouldBeFound("pincode.greaterThanOrEqual=" + DEFAULT_PINCODE);

        // Get all the warehouseList where pincode is greater than or equal to UPDATED_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.greaterThanOrEqual=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode is less than or equal to DEFAULT_PINCODE
        defaultWarehouseShouldBeFound("pincode.lessThanOrEqual=" + DEFAULT_PINCODE);

        // Get all the warehouseList where pincode is less than or equal to SMALLER_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.lessThanOrEqual=" + SMALLER_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsLessThanSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode is less than DEFAULT_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.lessThan=" + DEFAULT_PINCODE);

        // Get all the warehouseList where pincode is less than UPDATED_PINCODE
        defaultWarehouseShouldBeFound("pincode.lessThan=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByPincodeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where pincode is greater than DEFAULT_PINCODE
        defaultWarehouseShouldNotBeFound("pincode.greaterThan=" + DEFAULT_PINCODE);

        // Get all the warehouseList where pincode is greater than SMALLER_PINCODE
        defaultWarehouseShouldBeFound("pincode.greaterThan=" + SMALLER_PINCODE);
    }

    @Test
    @Transactional
    void getAllWarehousesByCityIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where city equals to DEFAULT_CITY
        defaultWarehouseShouldBeFound("city.equals=" + DEFAULT_CITY);

        // Get all the warehouseList where city equals to UPDATED_CITY
        defaultWarehouseShouldNotBeFound("city.equals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where city not equals to DEFAULT_CITY
        defaultWarehouseShouldNotBeFound("city.notEquals=" + DEFAULT_CITY);

        // Get all the warehouseList where city not equals to UPDATED_CITY
        defaultWarehouseShouldBeFound("city.notEquals=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCityIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where city in DEFAULT_CITY or UPDATED_CITY
        defaultWarehouseShouldBeFound("city.in=" + DEFAULT_CITY + "," + UPDATED_CITY);

        // Get all the warehouseList where city equals to UPDATED_CITY
        defaultWarehouseShouldNotBeFound("city.in=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCityIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where city is not null
        defaultWarehouseShouldBeFound("city.specified=true");

        // Get all the warehouseList where city is null
        defaultWarehouseShouldNotBeFound("city.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByCityContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where city contains DEFAULT_CITY
        defaultWarehouseShouldBeFound("city.contains=" + DEFAULT_CITY);

        // Get all the warehouseList where city contains UPDATED_CITY
        defaultWarehouseShouldNotBeFound("city.contains=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCityNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where city does not contain DEFAULT_CITY
        defaultWarehouseShouldNotBeFound("city.doesNotContain=" + DEFAULT_CITY);

        // Get all the warehouseList where city does not contain UPDATED_CITY
        defaultWarehouseShouldBeFound("city.doesNotContain=" + UPDATED_CITY);
    }

    @Test
    @Transactional
    void getAllWarehousesByStateIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where state equals to DEFAULT_STATE
        defaultWarehouseShouldBeFound("state.equals=" + DEFAULT_STATE);

        // Get all the warehouseList where state equals to UPDATED_STATE
        defaultWarehouseShouldNotBeFound("state.equals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWarehousesByStateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where state not equals to DEFAULT_STATE
        defaultWarehouseShouldNotBeFound("state.notEquals=" + DEFAULT_STATE);

        // Get all the warehouseList where state not equals to UPDATED_STATE
        defaultWarehouseShouldBeFound("state.notEquals=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWarehousesByStateIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where state in DEFAULT_STATE or UPDATED_STATE
        defaultWarehouseShouldBeFound("state.in=" + DEFAULT_STATE + "," + UPDATED_STATE);

        // Get all the warehouseList where state equals to UPDATED_STATE
        defaultWarehouseShouldNotBeFound("state.in=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWarehousesByStateIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where state is not null
        defaultWarehouseShouldBeFound("state.specified=true");

        // Get all the warehouseList where state is null
        defaultWarehouseShouldNotBeFound("state.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByStateContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where state contains DEFAULT_STATE
        defaultWarehouseShouldBeFound("state.contains=" + DEFAULT_STATE);

        // Get all the warehouseList where state contains UPDATED_STATE
        defaultWarehouseShouldNotBeFound("state.contains=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWarehousesByStateNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where state does not contain DEFAULT_STATE
        defaultWarehouseShouldNotBeFound("state.doesNotContain=" + DEFAULT_STATE);

        // Get all the warehouseList where state does not contain UPDATED_STATE
        defaultWarehouseShouldBeFound("state.doesNotContain=" + UPDATED_STATE);
    }

    @Test
    @Transactional
    void getAllWarehousesByCountryIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where country equals to DEFAULT_COUNTRY
        defaultWarehouseShouldBeFound("country.equals=" + DEFAULT_COUNTRY);

        // Get all the warehouseList where country equals to UPDATED_COUNTRY
        defaultWarehouseShouldNotBeFound("country.equals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCountryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where country not equals to DEFAULT_COUNTRY
        defaultWarehouseShouldNotBeFound("country.notEquals=" + DEFAULT_COUNTRY);

        // Get all the warehouseList where country not equals to UPDATED_COUNTRY
        defaultWarehouseShouldBeFound("country.notEquals=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCountryIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where country in DEFAULT_COUNTRY or UPDATED_COUNTRY
        defaultWarehouseShouldBeFound("country.in=" + DEFAULT_COUNTRY + "," + UPDATED_COUNTRY);

        // Get all the warehouseList where country equals to UPDATED_COUNTRY
        defaultWarehouseShouldNotBeFound("country.in=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCountryIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where country is not null
        defaultWarehouseShouldBeFound("country.specified=true");

        // Get all the warehouseList where country is null
        defaultWarehouseShouldNotBeFound("country.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByCountryContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where country contains DEFAULT_COUNTRY
        defaultWarehouseShouldBeFound("country.contains=" + DEFAULT_COUNTRY);

        // Get all the warehouseList where country contains UPDATED_COUNTRY
        defaultWarehouseShouldNotBeFound("country.contains=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWarehousesByCountryNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where country does not contain DEFAULT_COUNTRY
        defaultWarehouseShouldNotBeFound("country.doesNotContain=" + DEFAULT_COUNTRY);

        // Get all the warehouseList where country does not contain UPDATED_COUNTRY
        defaultWarehouseShouldBeFound("country.doesNotContain=" + UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    void getAllWarehousesBygSTDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where gSTDetails equals to DEFAULT_G_ST_DETAILS
        defaultWarehouseShouldBeFound("gSTDetails.equals=" + DEFAULT_G_ST_DETAILS);

        // Get all the warehouseList where gSTDetails equals to UPDATED_G_ST_DETAILS
        defaultWarehouseShouldNotBeFound("gSTDetails.equals=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWarehousesBygSTDetailsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where gSTDetails not equals to DEFAULT_G_ST_DETAILS
        defaultWarehouseShouldNotBeFound("gSTDetails.notEquals=" + DEFAULT_G_ST_DETAILS);

        // Get all the warehouseList where gSTDetails not equals to UPDATED_G_ST_DETAILS
        defaultWarehouseShouldBeFound("gSTDetails.notEquals=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWarehousesBygSTDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where gSTDetails in DEFAULT_G_ST_DETAILS or UPDATED_G_ST_DETAILS
        defaultWarehouseShouldBeFound("gSTDetails.in=" + DEFAULT_G_ST_DETAILS + "," + UPDATED_G_ST_DETAILS);

        // Get all the warehouseList where gSTDetails equals to UPDATED_G_ST_DETAILS
        defaultWarehouseShouldNotBeFound("gSTDetails.in=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWarehousesBygSTDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where gSTDetails is not null
        defaultWarehouseShouldBeFound("gSTDetails.specified=true");

        // Get all the warehouseList where gSTDetails is null
        defaultWarehouseShouldNotBeFound("gSTDetails.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesBygSTDetailsContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where gSTDetails contains DEFAULT_G_ST_DETAILS
        defaultWarehouseShouldBeFound("gSTDetails.contains=" + DEFAULT_G_ST_DETAILS);

        // Get all the warehouseList where gSTDetails contains UPDATED_G_ST_DETAILS
        defaultWarehouseShouldNotBeFound("gSTDetails.contains=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWarehousesBygSTDetailsNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where gSTDetails does not contain DEFAULT_G_ST_DETAILS
        defaultWarehouseShouldNotBeFound("gSTDetails.doesNotContain=" + DEFAULT_G_ST_DETAILS);

        // Get all the warehouseList where gSTDetails does not contain UPDATED_G_ST_DETAILS
        defaultWarehouseShouldBeFound("gSTDetails.doesNotContain=" + UPDATED_G_ST_DETAILS);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerName equals to DEFAULT_MANAGER_NAME
        defaultWarehouseShouldBeFound("managerName.equals=" + DEFAULT_MANAGER_NAME);

        // Get all the warehouseList where managerName equals to UPDATED_MANAGER_NAME
        defaultWarehouseShouldNotBeFound("managerName.equals=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerName not equals to DEFAULT_MANAGER_NAME
        defaultWarehouseShouldNotBeFound("managerName.notEquals=" + DEFAULT_MANAGER_NAME);

        // Get all the warehouseList where managerName not equals to UPDATED_MANAGER_NAME
        defaultWarehouseShouldBeFound("managerName.notEquals=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerNameIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerName in DEFAULT_MANAGER_NAME or UPDATED_MANAGER_NAME
        defaultWarehouseShouldBeFound("managerName.in=" + DEFAULT_MANAGER_NAME + "," + UPDATED_MANAGER_NAME);

        // Get all the warehouseList where managerName equals to UPDATED_MANAGER_NAME
        defaultWarehouseShouldNotBeFound("managerName.in=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerName is not null
        defaultWarehouseShouldBeFound("managerName.specified=true");

        // Get all the warehouseList where managerName is null
        defaultWarehouseShouldNotBeFound("managerName.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerNameContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerName contains DEFAULT_MANAGER_NAME
        defaultWarehouseShouldBeFound("managerName.contains=" + DEFAULT_MANAGER_NAME);

        // Get all the warehouseList where managerName contains UPDATED_MANAGER_NAME
        defaultWarehouseShouldNotBeFound("managerName.contains=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerNameNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerName does not contain DEFAULT_MANAGER_NAME
        defaultWarehouseShouldNotBeFound("managerName.doesNotContain=" + DEFAULT_MANAGER_NAME);

        // Get all the warehouseList where managerName does not contain UPDATED_MANAGER_NAME
        defaultWarehouseShouldBeFound("managerName.doesNotContain=" + UPDATED_MANAGER_NAME);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerEmail equals to DEFAULT_MANAGER_EMAIL
        defaultWarehouseShouldBeFound("managerEmail.equals=" + DEFAULT_MANAGER_EMAIL);

        // Get all the warehouseList where managerEmail equals to UPDATED_MANAGER_EMAIL
        defaultWarehouseShouldNotBeFound("managerEmail.equals=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerEmail not equals to DEFAULT_MANAGER_EMAIL
        defaultWarehouseShouldNotBeFound("managerEmail.notEquals=" + DEFAULT_MANAGER_EMAIL);

        // Get all the warehouseList where managerEmail not equals to UPDATED_MANAGER_EMAIL
        defaultWarehouseShouldBeFound("managerEmail.notEquals=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerEmailIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerEmail in DEFAULT_MANAGER_EMAIL or UPDATED_MANAGER_EMAIL
        defaultWarehouseShouldBeFound("managerEmail.in=" + DEFAULT_MANAGER_EMAIL + "," + UPDATED_MANAGER_EMAIL);

        // Get all the warehouseList where managerEmail equals to UPDATED_MANAGER_EMAIL
        defaultWarehouseShouldNotBeFound("managerEmail.in=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerEmail is not null
        defaultWarehouseShouldBeFound("managerEmail.specified=true");

        // Get all the warehouseList where managerEmail is null
        defaultWarehouseShouldNotBeFound("managerEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerEmailContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerEmail contains DEFAULT_MANAGER_EMAIL
        defaultWarehouseShouldBeFound("managerEmail.contains=" + DEFAULT_MANAGER_EMAIL);

        // Get all the warehouseList where managerEmail contains UPDATED_MANAGER_EMAIL
        defaultWarehouseShouldNotBeFound("managerEmail.contains=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerEmailNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerEmail does not contain DEFAULT_MANAGER_EMAIL
        defaultWarehouseShouldNotBeFound("managerEmail.doesNotContain=" + DEFAULT_MANAGER_EMAIL);

        // Get all the warehouseList where managerEmail does not contain UPDATED_MANAGER_EMAIL
        defaultWarehouseShouldBeFound("managerEmail.doesNotContain=" + UPDATED_MANAGER_EMAIL);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerContactIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerContact equals to DEFAULT_MANAGER_CONTACT
        defaultWarehouseShouldBeFound("managerContact.equals=" + DEFAULT_MANAGER_CONTACT);

        // Get all the warehouseList where managerContact equals to UPDATED_MANAGER_CONTACT
        defaultWarehouseShouldNotBeFound("managerContact.equals=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerContact not equals to DEFAULT_MANAGER_CONTACT
        defaultWarehouseShouldNotBeFound("managerContact.notEquals=" + DEFAULT_MANAGER_CONTACT);

        // Get all the warehouseList where managerContact not equals to UPDATED_MANAGER_CONTACT
        defaultWarehouseShouldBeFound("managerContact.notEquals=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerContactIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerContact in DEFAULT_MANAGER_CONTACT or UPDATED_MANAGER_CONTACT
        defaultWarehouseShouldBeFound("managerContact.in=" + DEFAULT_MANAGER_CONTACT + "," + UPDATED_MANAGER_CONTACT);

        // Get all the warehouseList where managerContact equals to UPDATED_MANAGER_CONTACT
        defaultWarehouseShouldNotBeFound("managerContact.in=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerContact is not null
        defaultWarehouseShouldBeFound("managerContact.specified=true");

        // Get all the warehouseList where managerContact is null
        defaultWarehouseShouldNotBeFound("managerContact.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerContactContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerContact contains DEFAULT_MANAGER_CONTACT
        defaultWarehouseShouldBeFound("managerContact.contains=" + DEFAULT_MANAGER_CONTACT);

        // Get all the warehouseList where managerContact contains UPDATED_MANAGER_CONTACT
        defaultWarehouseShouldNotBeFound("managerContact.contains=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByManagerContactNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where managerContact does not contain DEFAULT_MANAGER_CONTACT
        defaultWarehouseShouldNotBeFound("managerContact.doesNotContain=" + DEFAULT_MANAGER_CONTACT);

        // Get all the warehouseList where managerContact does not contain UPDATED_MANAGER_CONTACT
        defaultWarehouseShouldBeFound("managerContact.doesNotContain=" + UPDATED_MANAGER_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByContactIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where contact equals to DEFAULT_CONTACT
        defaultWarehouseShouldBeFound("contact.equals=" + DEFAULT_CONTACT);

        // Get all the warehouseList where contact equals to UPDATED_CONTACT
        defaultWarehouseShouldNotBeFound("contact.equals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByContactIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where contact not equals to DEFAULT_CONTACT
        defaultWarehouseShouldNotBeFound("contact.notEquals=" + DEFAULT_CONTACT);

        // Get all the warehouseList where contact not equals to UPDATED_CONTACT
        defaultWarehouseShouldBeFound("contact.notEquals=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByContactIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where contact in DEFAULT_CONTACT or UPDATED_CONTACT
        defaultWarehouseShouldBeFound("contact.in=" + DEFAULT_CONTACT + "," + UPDATED_CONTACT);

        // Get all the warehouseList where contact equals to UPDATED_CONTACT
        defaultWarehouseShouldNotBeFound("contact.in=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where contact is not null
        defaultWarehouseShouldBeFound("contact.specified=true");

        // Get all the warehouseList where contact is null
        defaultWarehouseShouldNotBeFound("contact.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByContactContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where contact contains DEFAULT_CONTACT
        defaultWarehouseShouldBeFound("contact.contains=" + DEFAULT_CONTACT);

        // Get all the warehouseList where contact contains UPDATED_CONTACT
        defaultWarehouseShouldNotBeFound("contact.contains=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByContactNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where contact does not contain DEFAULT_CONTACT
        defaultWarehouseShouldNotBeFound("contact.doesNotContain=" + DEFAULT_CONTACT);

        // Get all the warehouseList where contact does not contain UPDATED_CONTACT
        defaultWarehouseShouldBeFound("contact.doesNotContain=" + UPDATED_CONTACT);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isDeleted equals to DEFAULT_IS_DELETED
        defaultWarehouseShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the warehouseList where isDeleted equals to UPDATED_IS_DELETED
        defaultWarehouseShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsDeletedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isDeleted not equals to DEFAULT_IS_DELETED
        defaultWarehouseShouldNotBeFound("isDeleted.notEquals=" + DEFAULT_IS_DELETED);

        // Get all the warehouseList where isDeleted not equals to UPDATED_IS_DELETED
        defaultWarehouseShouldBeFound("isDeleted.notEquals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultWarehouseShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the warehouseList where isDeleted equals to UPDATED_IS_DELETED
        defaultWarehouseShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isDeleted is not null
        defaultWarehouseShouldBeFound("isDeleted.specified=true");

        // Get all the warehouseList where isDeleted is null
        defaultWarehouseShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByIsActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isActive equals to DEFAULT_IS_ACTIVE
        defaultWarehouseShouldBeFound("isActive.equals=" + DEFAULT_IS_ACTIVE);

        // Get all the warehouseList where isActive equals to UPDATED_IS_ACTIVE
        defaultWarehouseShouldNotBeFound("isActive.equals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isActive not equals to DEFAULT_IS_ACTIVE
        defaultWarehouseShouldNotBeFound("isActive.notEquals=" + DEFAULT_IS_ACTIVE);

        // Get all the warehouseList where isActive not equals to UPDATED_IS_ACTIVE
        defaultWarehouseShouldBeFound("isActive.notEquals=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsActiveIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isActive in DEFAULT_IS_ACTIVE or UPDATED_IS_ACTIVE
        defaultWarehouseShouldBeFound("isActive.in=" + DEFAULT_IS_ACTIVE + "," + UPDATED_IS_ACTIVE);

        // Get all the warehouseList where isActive equals to UPDATED_IS_ACTIVE
        defaultWarehouseShouldNotBeFound("isActive.in=" + UPDATED_IS_ACTIVE);
    }

    @Test
    @Transactional
    void getAllWarehousesByIsActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where isActive is not null
        defaultWarehouseShouldBeFound("isActive.specified=true");

        // Get all the warehouseList where isActive is null
        defaultWarehouseShouldNotBeFound("isActive.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWarehouseShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the warehouseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWarehouseShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModified not equals to DEFAULT_LAST_MODIFIED
        defaultWarehouseShouldNotBeFound("lastModified.notEquals=" + DEFAULT_LAST_MODIFIED);

        // Get all the warehouseList where lastModified not equals to UPDATED_LAST_MODIFIED
        defaultWarehouseShouldBeFound("lastModified.notEquals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWarehouseShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the warehouseList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWarehouseShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModified is not null
        defaultWarehouseShouldBeFound("lastModified.specified=true");

        // Get all the warehouseList where lastModified is null
        defaultWarehouseShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWarehouseShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the warehouseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWarehouseShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultWarehouseShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the warehouseList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultWarehouseShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWarehouseShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the warehouseList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWarehouseShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModifiedBy is not null
        defaultWarehouseShouldBeFound("lastModifiedBy.specified=true");

        // Get all the warehouseList where lastModifiedBy is null
        defaultWarehouseShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWarehouseShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the warehouseList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWarehouseShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWarehousesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        // Get all the warehouseList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWarehouseShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the warehouseList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWarehouseShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWarehousesByPurchaseOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
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
        warehouse.addPurchaseOrder(purchaseOrder);
        warehouseRepository.saveAndFlush(warehouse);
        Long purchaseOrderId = purchaseOrder.getId();

        // Get all the warehouseList where purchaseOrder equals to purchaseOrderId
        defaultWarehouseShouldBeFound("purchaseOrderId.equals=" + purchaseOrderId);

        // Get all the warehouseList where purchaseOrder equals to (purchaseOrderId + 1)
        defaultWarehouseShouldNotBeFound("purchaseOrderId.equals=" + (purchaseOrderId + 1));
    }

    @Test
    @Transactional
    void getAllWarehousesBySecurityUserIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
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
        warehouse.addSecurityUser(securityUser);
        warehouseRepository.saveAndFlush(warehouse);
        Long securityUserId = securityUser.getId();

        // Get all the warehouseList where securityUser equals to securityUserId
        defaultWarehouseShouldBeFound("securityUserId.equals=" + securityUserId);

        // Get all the warehouseList where securityUser equals to (securityUserId + 1)
        defaultWarehouseShouldNotBeFound("securityUserId.equals=" + (securityUserId + 1));
    }

    @Test
    @Transactional
    void getAllWarehousesByProductTransactionIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
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
        warehouse.setProductTransaction(productTransaction);
        productTransaction.setWarehouse(warehouse);
        warehouseRepository.saveAndFlush(warehouse);
        Long productTransactionId = productTransaction.getId();

        // Get all the warehouseList where productTransaction equals to productTransactionId
        defaultWarehouseShouldBeFound("productTransactionId.equals=" + productTransactionId);

        // Get all the warehouseList where productTransaction equals to (productTransactionId + 1)
        defaultWarehouseShouldNotBeFound("productTransactionId.equals=" + (productTransactionId + 1));
    }

    @Test
    @Transactional
    void getAllWarehousesByProductInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);
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
        warehouse.addProductInventory(productInventory);
        warehouseRepository.saveAndFlush(warehouse);
        Long productInventoryId = productInventory.getId();

        // Get all the warehouseList where productInventory equals to productInventoryId
        defaultWarehouseShouldBeFound("productInventoryId.equals=" + productInventoryId);

        // Get all the warehouseList where productInventory equals to (productInventoryId + 1)
        defaultWarehouseShouldNotBeFound("productInventoryId.equals=" + (productInventoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWarehouseShouldBeFound(String filter) throws Exception {
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(warehouse.getId().intValue())))
            .andExpect(jsonPath("$.[*].warehouseId").value(hasItem(DEFAULT_WAREHOUSE_ID.intValue())))
            .andExpect(jsonPath("$.[*].whName").value(hasItem(DEFAULT_WH_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].state").value(hasItem(DEFAULT_STATE)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].gSTDetails").value(hasItem(DEFAULT_G_ST_DETAILS)))
            .andExpect(jsonPath("$.[*].managerName").value(hasItem(DEFAULT_MANAGER_NAME)))
            .andExpect(jsonPath("$.[*].managerEmail").value(hasItem(DEFAULT_MANAGER_EMAIL)))
            .andExpect(jsonPath("$.[*].managerContact").value(hasItem(DEFAULT_MANAGER_CONTACT)))
            .andExpect(jsonPath("$.[*].contact").value(hasItem(DEFAULT_CONTACT)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)));

        // Check, that the count call also returns 1
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWarehouseShouldNotBeFound(String filter) throws Exception {
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWarehouseMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWarehouse() throws Exception {
        // Get the warehouse
        restWarehouseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();

        // Update the warehouse
        Warehouse updatedWarehouse = warehouseRepository.findById(warehouse.getId()).get();
        // Disconnect from session so that the updates on updatedWarehouse are not directly saved in db
        em.detach(updatedWarehouse);
        updatedWarehouse
            .warehouseId(UPDATED_WAREHOUSE_ID)
            .whName(UPDATED_WH_NAME)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerEmail(UPDATED_MANAGER_EMAIL)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .contact(UPDATED_CONTACT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(updatedWarehouse);

        restWarehouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warehouseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDTO))
            )
            .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
        Warehouse testWarehouse = warehouseList.get(warehouseList.size() - 1);
        assertThat(testWarehouse.getWarehouseId()).isEqualTo(UPDATED_WAREHOUSE_ID);
        assertThat(testWarehouse.getWhName()).isEqualTo(UPDATED_WH_NAME);
        assertThat(testWarehouse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWarehouse.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testWarehouse.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWarehouse.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWarehouse.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWarehouse.getgSTDetails()).isEqualTo(UPDATED_G_ST_DETAILS);
        assertThat(testWarehouse.getManagerName()).isEqualTo(UPDATED_MANAGER_NAME);
        assertThat(testWarehouse.getManagerEmail()).isEqualTo(UPDATED_MANAGER_EMAIL);
        assertThat(testWarehouse.getManagerContact()).isEqualTo(UPDATED_MANAGER_CONTACT);
        assertThat(testWarehouse.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testWarehouse.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWarehouse.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWarehouse.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWarehouse.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void putNonExistingWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();
        warehouse.setId(count.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, warehouseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();
        warehouse.setId(count.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();
        warehouse.setId(count.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(warehouseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWarehouseWithPatch() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();

        // Update the warehouse using partial update
        Warehouse partialUpdatedWarehouse = new Warehouse();
        partialUpdatedWarehouse.setId(warehouse.getId());

        partialUpdatedWarehouse.whName(UPDATED_WH_NAME).isActive(UPDATED_IS_ACTIVE);

        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarehouse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWarehouse))
            )
            .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
        Warehouse testWarehouse = warehouseList.get(warehouseList.size() - 1);
        assertThat(testWarehouse.getWarehouseId()).isEqualTo(DEFAULT_WAREHOUSE_ID);
        assertThat(testWarehouse.getWhName()).isEqualTo(UPDATED_WH_NAME);
        assertThat(testWarehouse.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testWarehouse.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testWarehouse.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testWarehouse.getState()).isEqualTo(DEFAULT_STATE);
        assertThat(testWarehouse.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testWarehouse.getgSTDetails()).isEqualTo(DEFAULT_G_ST_DETAILS);
        assertThat(testWarehouse.getManagerName()).isEqualTo(DEFAULT_MANAGER_NAME);
        assertThat(testWarehouse.getManagerEmail()).isEqualTo(DEFAULT_MANAGER_EMAIL);
        assertThat(testWarehouse.getManagerContact()).isEqualTo(DEFAULT_MANAGER_CONTACT);
        assertThat(testWarehouse.getContact()).isEqualTo(DEFAULT_CONTACT);
        assertThat(testWarehouse.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWarehouse.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWarehouse.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWarehouse.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void fullUpdateWarehouseWithPatch() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();

        // Update the warehouse using partial update
        Warehouse partialUpdatedWarehouse = new Warehouse();
        partialUpdatedWarehouse.setId(warehouse.getId());

        partialUpdatedWarehouse
            .warehouseId(UPDATED_WAREHOUSE_ID)
            .whName(UPDATED_WH_NAME)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .city(UPDATED_CITY)
            .state(UPDATED_STATE)
            .country(UPDATED_COUNTRY)
            .gSTDetails(UPDATED_G_ST_DETAILS)
            .managerName(UPDATED_MANAGER_NAME)
            .managerEmail(UPDATED_MANAGER_EMAIL)
            .managerContact(UPDATED_MANAGER_CONTACT)
            .contact(UPDATED_CONTACT)
            .isDeleted(UPDATED_IS_DELETED)
            .isActive(UPDATED_IS_ACTIVE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWarehouse.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWarehouse))
            )
            .andExpect(status().isOk());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
        Warehouse testWarehouse = warehouseList.get(warehouseList.size() - 1);
        assertThat(testWarehouse.getWarehouseId()).isEqualTo(UPDATED_WAREHOUSE_ID);
        assertThat(testWarehouse.getWhName()).isEqualTo(UPDATED_WH_NAME);
        assertThat(testWarehouse.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testWarehouse.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testWarehouse.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testWarehouse.getState()).isEqualTo(UPDATED_STATE);
        assertThat(testWarehouse.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testWarehouse.getgSTDetails()).isEqualTo(UPDATED_G_ST_DETAILS);
        assertThat(testWarehouse.getManagerName()).isEqualTo(UPDATED_MANAGER_NAME);
        assertThat(testWarehouse.getManagerEmail()).isEqualTo(UPDATED_MANAGER_EMAIL);
        assertThat(testWarehouse.getManagerContact()).isEqualTo(UPDATED_MANAGER_CONTACT);
        assertThat(testWarehouse.getContact()).isEqualTo(UPDATED_CONTACT);
        assertThat(testWarehouse.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWarehouse.getIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testWarehouse.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWarehouse.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();
        warehouse.setId(count.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, warehouseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();
        warehouse.setId(count.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(warehouseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWarehouse() throws Exception {
        int databaseSizeBeforeUpdate = warehouseRepository.findAll().size();
        warehouse.setId(count.incrementAndGet());

        // Create the Warehouse
        WarehouseDTO warehouseDTO = warehouseMapper.toDto(warehouse);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWarehouseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(warehouseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Warehouse in the database
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWarehouse() throws Exception {
        // Initialize the database
        warehouseRepository.saveAndFlush(warehouse);

        int databaseSizeBeforeDelete = warehouseRepository.findAll().size();

        // Delete the warehouse
        restWarehouseMockMvc
            .perform(delete(ENTITY_API_URL_ID, warehouse.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Warehouse> warehouseList = warehouseRepository.findAll();
        assertThat(warehouseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
