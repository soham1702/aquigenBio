package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ProductQuatationRepository;
import com.mycompany.myapp.service.ProductQuatationQueryService;
import com.mycompany.myapp.service.ProductQuatationService;
import com.mycompany.myapp.service.criteria.ProductQuatationCriteria;
import com.mycompany.myapp.service.dto.ProductQuatationDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.ProductQuatation}.
 */
@RestController
@RequestMapping("/api")
public class ProductQuatationResource {

    private final Logger log = LoggerFactory.getLogger(ProductQuatationResource.class);

    private static final String ENTITY_NAME = "productQuatation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductQuatationService productQuatationService;

    private final ProductQuatationRepository productQuatationRepository;

    private final ProductQuatationQueryService productQuatationQueryService;

    public ProductQuatationResource(
        ProductQuatationService productQuatationService,
        ProductQuatationRepository productQuatationRepository,
        ProductQuatationQueryService productQuatationQueryService
    ) {
        this.productQuatationService = productQuatationService;
        this.productQuatationRepository = productQuatationRepository;
        this.productQuatationQueryService = productQuatationQueryService;
    }

    /**
     * {@code POST  /product-quatations} : Create a new productQuatation.
     *
     * @param productQuatationDTO the productQuatationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productQuatationDTO, or with status {@code 400 (Bad Request)} if the productQuatation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-quatations")
    public ResponseEntity<ProductQuatationDTO> createProductQuatation(@RequestBody ProductQuatationDTO productQuatationDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductQuatation : {}", productQuatationDTO);
        if (productQuatationDTO.getId() != null) {
            throw new BadRequestAlertException("A new productQuatation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductQuatationDTO result = productQuatationService.save(productQuatationDTO);
        return ResponseEntity
            .created(new URI("/api/product-quatations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-quatations/:id} : Updates an existing productQuatation.
     *
     * @param id the id of the productQuatationDTO to save.
     * @param productQuatationDTO the productQuatationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productQuatationDTO,
     * or with status {@code 400 (Bad Request)} if the productQuatationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productQuatationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-quatations/{id}")
    public ResponseEntity<ProductQuatationDTO> updateProductQuatation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductQuatationDTO productQuatationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductQuatation : {}, {}", id, productQuatationDTO);
        if (productQuatationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productQuatationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productQuatationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductQuatationDTO result = productQuatationService.save(productQuatationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productQuatationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-quatations/:id} : Partial updates given fields of an existing productQuatation, field will ignore if it is null
     *
     * @param id the id of the productQuatationDTO to save.
     * @param productQuatationDTO the productQuatationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productQuatationDTO,
     * or with status {@code 400 (Bad Request)} if the productQuatationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productQuatationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productQuatationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-quatations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductQuatationDTO> partialUpdateProductQuatation(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductQuatationDTO productQuatationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductQuatation partially : {}, {}", id, productQuatationDTO);
        if (productQuatationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productQuatationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productQuatationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductQuatationDTO> result = productQuatationService.partialUpdate(productQuatationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productQuatationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-quatations} : get all the productQuatations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productQuatations in body.
     */
    @GetMapping("/product-quatations")
    public ResponseEntity<List<ProductQuatationDTO>> getAllProductQuatations(
        ProductQuatationCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get ProductQuatations by criteria: {}", criteria);
        Page<ProductQuatationDTO> page = productQuatationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /product-quatations/count} : count all the productQuatations.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/product-quatations/count")
    public ResponseEntity<Long> countProductQuatations(ProductQuatationCriteria criteria) {
        log.debug("REST request to count ProductQuatations by criteria: {}", criteria);
        return ResponseEntity.ok().body(productQuatationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /product-quatations/:id} : get the "id" productQuatation.
     *
     * @param id the id of the productQuatationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productQuatationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-quatations/{id}")
    public ResponseEntity<ProductQuatationDTO> getProductQuatation(@PathVariable Long id) {
        log.debug("REST request to get ProductQuatation : {}", id);
        Optional<ProductQuatationDTO> productQuatationDTO = productQuatationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productQuatationDTO);
    }

    /**
     * {@code DELETE  /product-quatations/:id} : delete the "id" productQuatation.
     *
     * @param id the id of the productQuatationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-quatations/{id}")
    public ResponseEntity<Void> deleteProductQuatation(@PathVariable Long id) {
        log.debug("REST request to delete ProductQuatation : {}", id);
        productQuatationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
