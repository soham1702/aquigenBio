package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RawMaterialOrderRepository;
import com.mycompany.myapp.service.RawMaterialOrderQueryService;
import com.mycompany.myapp.service.RawMaterialOrderService;
import com.mycompany.myapp.service.criteria.RawMaterialOrderCriteria;
import com.mycompany.myapp.service.dto.RawMaterialOrderDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.RawMaterialOrder}.
 */
@RestController
@RequestMapping("/api")
public class RawMaterialOrderResource {

    private final Logger log = LoggerFactory.getLogger(RawMaterialOrderResource.class);

    private static final String ENTITY_NAME = "rawMaterialOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RawMaterialOrderService rawMaterialOrderService;

    private final RawMaterialOrderRepository rawMaterialOrderRepository;

    private final RawMaterialOrderQueryService rawMaterialOrderQueryService;

    public RawMaterialOrderResource(
        RawMaterialOrderService rawMaterialOrderService,
        RawMaterialOrderRepository rawMaterialOrderRepository,
        RawMaterialOrderQueryService rawMaterialOrderQueryService
    ) {
        this.rawMaterialOrderService = rawMaterialOrderService;
        this.rawMaterialOrderRepository = rawMaterialOrderRepository;
        this.rawMaterialOrderQueryService = rawMaterialOrderQueryService;
    }

    /**
     * {@code POST  /raw-material-orders} : Create a new rawMaterialOrder.
     *
     * @param rawMaterialOrderDTO the rawMaterialOrderDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rawMaterialOrderDTO, or with status {@code 400 (Bad Request)} if the rawMaterialOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/raw-material-orders")
    public ResponseEntity<RawMaterialOrderDTO> createRawMaterialOrder(@RequestBody RawMaterialOrderDTO rawMaterialOrderDTO)
        throws URISyntaxException {
        log.debug("REST request to save RawMaterialOrder : {}", rawMaterialOrderDTO);
        if (rawMaterialOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new rawMaterialOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RawMaterialOrderDTO result = rawMaterialOrderService.save(rawMaterialOrderDTO);
        return ResponseEntity
            .created(new URI("/api/raw-material-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /raw-material-orders/:id} : Updates an existing rawMaterialOrder.
     *
     * @param id the id of the rawMaterialOrderDTO to save.
     * @param rawMaterialOrderDTO the rawMaterialOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawMaterialOrderDTO,
     * or with status {@code 400 (Bad Request)} if the rawMaterialOrderDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rawMaterialOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/raw-material-orders/{id}")
    public ResponseEntity<RawMaterialOrderDTO> updateRawMaterialOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawMaterialOrderDTO rawMaterialOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RawMaterialOrder : {}, {}", id, rawMaterialOrderDTO);
        if (rawMaterialOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawMaterialOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawMaterialOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RawMaterialOrderDTO result = rawMaterialOrderService.save(rawMaterialOrderDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawMaterialOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /raw-material-orders/:id} : Partial updates given fields of an existing rawMaterialOrder, field will ignore if it is null
     *
     * @param id the id of the rawMaterialOrderDTO to save.
     * @param rawMaterialOrderDTO the rawMaterialOrderDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rawMaterialOrderDTO,
     * or with status {@code 400 (Bad Request)} if the rawMaterialOrderDTO is not valid,
     * or with status {@code 404 (Not Found)} if the rawMaterialOrderDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the rawMaterialOrderDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/raw-material-orders/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RawMaterialOrderDTO> partialUpdateRawMaterialOrder(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RawMaterialOrderDTO rawMaterialOrderDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RawMaterialOrder partially : {}, {}", id, rawMaterialOrderDTO);
        if (rawMaterialOrderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, rawMaterialOrderDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!rawMaterialOrderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RawMaterialOrderDTO> result = rawMaterialOrderService.partialUpdate(rawMaterialOrderDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rawMaterialOrderDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /raw-material-orders} : get all the rawMaterialOrders.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rawMaterialOrders in body.
     */
    @GetMapping("/raw-material-orders")
    public ResponseEntity<List<RawMaterialOrderDTO>> getAllRawMaterialOrders(
        RawMaterialOrderCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get RawMaterialOrders by criteria: {}", criteria);
        Page<RawMaterialOrderDTO> page = rawMaterialOrderQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /raw-material-orders/count} : count all the rawMaterialOrders.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/raw-material-orders/count")
    public ResponseEntity<Long> countRawMaterialOrders(RawMaterialOrderCriteria criteria) {
        log.debug("REST request to count RawMaterialOrders by criteria: {}", criteria);
        return ResponseEntity.ok().body(rawMaterialOrderQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /raw-material-orders/:id} : get the "id" rawMaterialOrder.
     *
     * @param id the id of the rawMaterialOrderDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rawMaterialOrderDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/raw-material-orders/{id}")
    public ResponseEntity<RawMaterialOrderDTO> getRawMaterialOrder(@PathVariable Long id) {
        log.debug("REST request to get RawMaterialOrder : {}", id);
        Optional<RawMaterialOrderDTO> rawMaterialOrderDTO = rawMaterialOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rawMaterialOrderDTO);
    }

    /**
     * {@code DELETE  /raw-material-orders/:id} : delete the "id" rawMaterialOrder.
     *
     * @param id the id of the rawMaterialOrderDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/raw-material-orders/{id}")
    public ResponseEntity<Void> deleteRawMaterialOrder(@PathVariable Long id) {
        log.debug("REST request to delete RawMaterialOrder : {}", id);
        rawMaterialOrderService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
