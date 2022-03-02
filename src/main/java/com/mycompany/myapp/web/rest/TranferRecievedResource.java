package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TranferRecievedRepository;
import com.mycompany.myapp.service.TranferRecievedQueryService;
import com.mycompany.myapp.service.TranferRecievedService;
import com.mycompany.myapp.service.criteria.TranferRecievedCriteria;
import com.mycompany.myapp.service.dto.TranferRecievedDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TranferRecieved}.
 */
@RestController
@RequestMapping("/api")
public class TranferRecievedResource {

    private final Logger log = LoggerFactory.getLogger(TranferRecievedResource.class);

    private static final String ENTITY_NAME = "tranferRecieved";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranferRecievedService tranferRecievedService;

    private final TranferRecievedRepository tranferRecievedRepository;

    private final TranferRecievedQueryService tranferRecievedQueryService;

    public TranferRecievedResource(
        TranferRecievedService tranferRecievedService,
        TranferRecievedRepository tranferRecievedRepository,
        TranferRecievedQueryService tranferRecievedQueryService
    ) {
        this.tranferRecievedService = tranferRecievedService;
        this.tranferRecievedRepository = tranferRecievedRepository;
        this.tranferRecievedQueryService = tranferRecievedQueryService;
    }

    /**
     * {@code POST  /tranfer-recieveds} : Create a new tranferRecieved.
     *
     * @param tranferRecievedDTO the tranferRecievedDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tranferRecievedDTO, or with status {@code 400 (Bad Request)} if the tranferRecieved has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tranfer-recieveds")
    public ResponseEntity<TranferRecievedDTO> createTranferRecieved(@RequestBody TranferRecievedDTO tranferRecievedDTO)
        throws URISyntaxException {
        log.debug("REST request to save TranferRecieved : {}", tranferRecievedDTO);
        if (tranferRecievedDTO.getId() != null) {
            throw new BadRequestAlertException("A new tranferRecieved cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranferRecievedDTO result = tranferRecievedService.save(tranferRecievedDTO);
        return ResponseEntity
            .created(new URI("/api/tranfer-recieveds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tranfer-recieveds/:id} : Updates an existing tranferRecieved.
     *
     * @param id the id of the tranferRecievedDTO to save.
     * @param tranferRecievedDTO the tranferRecievedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tranferRecievedDTO,
     * or with status {@code 400 (Bad Request)} if the tranferRecievedDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tranferRecievedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tranfer-recieveds/{id}")
    public ResponseEntity<TranferRecievedDTO> updateTranferRecieved(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TranferRecievedDTO tranferRecievedDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TranferRecieved : {}, {}", id, tranferRecievedDTO);
        if (tranferRecievedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tranferRecievedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tranferRecievedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TranferRecievedDTO result = tranferRecievedService.save(tranferRecievedDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tranferRecievedDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tranfer-recieveds/:id} : Partial updates given fields of an existing tranferRecieved, field will ignore if it is null
     *
     * @param id the id of the tranferRecievedDTO to save.
     * @param tranferRecievedDTO the tranferRecievedDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tranferRecievedDTO,
     * or with status {@code 400 (Bad Request)} if the tranferRecievedDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tranferRecievedDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tranferRecievedDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tranfer-recieveds/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TranferRecievedDTO> partialUpdateTranferRecieved(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TranferRecievedDTO tranferRecievedDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TranferRecieved partially : {}, {}", id, tranferRecievedDTO);
        if (tranferRecievedDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tranferRecievedDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tranferRecievedRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TranferRecievedDTO> result = tranferRecievedService.partialUpdate(tranferRecievedDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tranferRecievedDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tranfer-recieveds} : get all the tranferRecieveds.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tranferRecieveds in body.
     */
    @GetMapping("/tranfer-recieveds")
    public ResponseEntity<List<TranferRecievedDTO>> getAllTranferRecieveds(
        TranferRecievedCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TranferRecieveds by criteria: {}", criteria);
        Page<TranferRecievedDTO> page = tranferRecievedQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tranfer-recieveds/count} : count all the tranferRecieveds.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tranfer-recieveds/count")
    public ResponseEntity<Long> countTranferRecieveds(TranferRecievedCriteria criteria) {
        log.debug("REST request to count TranferRecieveds by criteria: {}", criteria);
        return ResponseEntity.ok().body(tranferRecievedQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tranfer-recieveds/:id} : get the "id" tranferRecieved.
     *
     * @param id the id of the tranferRecievedDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tranferRecievedDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tranfer-recieveds/{id}")
    public ResponseEntity<TranferRecievedDTO> getTranferRecieved(@PathVariable Long id) {
        log.debug("REST request to get TranferRecieved : {}", id);
        Optional<TranferRecievedDTO> tranferRecievedDTO = tranferRecievedService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tranferRecievedDTO);
    }

    /**
     * {@code DELETE  /tranfer-recieveds/:id} : delete the "id" tranferRecieved.
     *
     * @param id the id of the tranferRecievedDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tranfer-recieveds/{id}")
    public ResponseEntity<Void> deleteTranferRecieved(@PathVariable Long id) {
        log.debug("REST request to delete TranferRecieved : {}", id);
        tranferRecievedService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
