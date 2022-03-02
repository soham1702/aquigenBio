package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.QuatationDetailsRepository;
import com.mycompany.myapp.service.QuatationDetailsQueryService;
import com.mycompany.myapp.service.QuatationDetailsService;
import com.mycompany.myapp.service.criteria.QuatationDetailsCriteria;
import com.mycompany.myapp.service.dto.QuatationDetailsDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.QuatationDetails}.
 */
@RestController
@RequestMapping("/api")
public class QuatationDetailsResource {

    private final Logger log = LoggerFactory.getLogger(QuatationDetailsResource.class);

    private static final String ENTITY_NAME = "quatationDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuatationDetailsService quatationDetailsService;

    private final QuatationDetailsRepository quatationDetailsRepository;

    private final QuatationDetailsQueryService quatationDetailsQueryService;

    public QuatationDetailsResource(
        QuatationDetailsService quatationDetailsService,
        QuatationDetailsRepository quatationDetailsRepository,
        QuatationDetailsQueryService quatationDetailsQueryService
    ) {
        this.quatationDetailsService = quatationDetailsService;
        this.quatationDetailsRepository = quatationDetailsRepository;
        this.quatationDetailsQueryService = quatationDetailsQueryService;
    }

    /**
     * {@code POST  /quatation-details} : Create a new quatationDetails.
     *
     * @param quatationDetailsDTO the quatationDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new quatationDetailsDTO, or with status {@code 400 (Bad Request)} if the quatationDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/quatation-details")
    public ResponseEntity<QuatationDetailsDTO> createQuatationDetails(@RequestBody QuatationDetailsDTO quatationDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save QuatationDetails : {}", quatationDetailsDTO);
        if (quatationDetailsDTO.getId() != null) {
            throw new BadRequestAlertException("A new quatationDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuatationDetailsDTO result = quatationDetailsService.save(quatationDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/quatation-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /quatation-details/:id} : Updates an existing quatationDetails.
     *
     * @param id the id of the quatationDetailsDTO to save.
     * @param quatationDetailsDTO the quatationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quatationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the quatationDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the quatationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/quatation-details/{id}")
    public ResponseEntity<QuatationDetailsDTO> updateQuatationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuatationDetailsDTO quatationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update QuatationDetails : {}, {}", id, quatationDetailsDTO);
        if (quatationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quatationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quatationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        QuatationDetailsDTO result = quatationDetailsService.save(quatationDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quatationDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /quatation-details/:id} : Partial updates given fields of an existing quatationDetails, field will ignore if it is null
     *
     * @param id the id of the quatationDetailsDTO to save.
     * @param quatationDetailsDTO the quatationDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated quatationDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the quatationDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the quatationDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the quatationDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/quatation-details/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<QuatationDetailsDTO> partialUpdateQuatationDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody QuatationDetailsDTO quatationDetailsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update QuatationDetails partially : {}, {}", id, quatationDetailsDTO);
        if (quatationDetailsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, quatationDetailsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!quatationDetailsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<QuatationDetailsDTO> result = quatationDetailsService.partialUpdate(quatationDetailsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, quatationDetailsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /quatation-details} : get all the quatationDetails.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of quatationDetails in body.
     */
    @GetMapping("/quatation-details")
    public ResponseEntity<List<QuatationDetailsDTO>> getAllQuatationDetails(
        QuatationDetailsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get QuatationDetails by criteria: {}", criteria);
        Page<QuatationDetailsDTO> page = quatationDetailsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /quatation-details/count} : count all the quatationDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/quatation-details/count")
    public ResponseEntity<Long> countQuatationDetails(QuatationDetailsCriteria criteria) {
        log.debug("REST request to count QuatationDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(quatationDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /quatation-details/:id} : get the "id" quatationDetails.
     *
     * @param id the id of the quatationDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quatationDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/quatation-details/{id}")
    public ResponseEntity<QuatationDetailsDTO> getQuatationDetails(@PathVariable Long id) {
        log.debug("REST request to get QuatationDetails : {}", id);
        Optional<QuatationDetailsDTO> quatationDetailsDTO = quatationDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(quatationDetailsDTO);
    }

    /**
     * {@code DELETE  /quatation-details/:id} : delete the "id" quatationDetails.
     *
     * @param id the id of the quatationDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/quatation-details/{id}")
    public ResponseEntity<Void> deleteQuatationDetails(@PathVariable Long id) {
        log.debug("REST request to delete QuatationDetails : {}", id);
        quatationDetailsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
