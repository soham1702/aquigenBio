package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.TranferDetailsApprovalsRepository;
import com.mycompany.myapp.service.TranferDetailsApprovalsQueryService;
import com.mycompany.myapp.service.TranferDetailsApprovalsService;
import com.mycompany.myapp.service.criteria.TranferDetailsApprovalsCriteria;
import com.mycompany.myapp.service.dto.TranferDetailsApprovalsDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.TranferDetailsApprovals}.
 */
@RestController
@RequestMapping("/api")
public class TranferDetailsApprovalsResource {

    private final Logger log = LoggerFactory.getLogger(TranferDetailsApprovalsResource.class);

    private static final String ENTITY_NAME = "tranferDetailsApprovals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TranferDetailsApprovalsService tranferDetailsApprovalsService;

    private final TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository;

    private final TranferDetailsApprovalsQueryService tranferDetailsApprovalsQueryService;

    public TranferDetailsApprovalsResource(
        TranferDetailsApprovalsService tranferDetailsApprovalsService,
        TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository,
        TranferDetailsApprovalsQueryService tranferDetailsApprovalsQueryService
    ) {
        this.tranferDetailsApprovalsService = tranferDetailsApprovalsService;
        this.tranferDetailsApprovalsRepository = tranferDetailsApprovalsRepository;
        this.tranferDetailsApprovalsQueryService = tranferDetailsApprovalsQueryService;
    }

    /**
     * {@code POST  /tranfer-details-approvals} : Create a new tranferDetailsApprovals.
     *
     * @param tranferDetailsApprovalsDTO the tranferDetailsApprovalsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tranferDetailsApprovalsDTO, or with status {@code 400 (Bad Request)} if the tranferDetailsApprovals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tranfer-details-approvals")
    public ResponseEntity<TranferDetailsApprovalsDTO> createTranferDetailsApprovals(
        @RequestBody TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to save TranferDetailsApprovals : {}", tranferDetailsApprovalsDTO);
        if (tranferDetailsApprovalsDTO.getId() != null) {
            throw new BadRequestAlertException("A new tranferDetailsApprovals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TranferDetailsApprovalsDTO result = tranferDetailsApprovalsService.save(tranferDetailsApprovalsDTO);
        return ResponseEntity
            .created(new URI("/api/tranfer-details-approvals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tranfer-details-approvals/:id} : Updates an existing tranferDetailsApprovals.
     *
     * @param id the id of the tranferDetailsApprovalsDTO to save.
     * @param tranferDetailsApprovalsDTO the tranferDetailsApprovalsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tranferDetailsApprovalsDTO,
     * or with status {@code 400 (Bad Request)} if the tranferDetailsApprovalsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tranferDetailsApprovalsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tranfer-details-approvals/{id}")
    public ResponseEntity<TranferDetailsApprovalsDTO> updateTranferDetailsApprovals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TranferDetailsApprovals : {}, {}", id, tranferDetailsApprovalsDTO);
        if (tranferDetailsApprovalsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tranferDetailsApprovalsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tranferDetailsApprovalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TranferDetailsApprovalsDTO result = tranferDetailsApprovalsService.save(tranferDetailsApprovalsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tranferDetailsApprovalsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tranfer-details-approvals/:id} : Partial updates given fields of an existing tranferDetailsApprovals, field will ignore if it is null
     *
     * @param id the id of the tranferDetailsApprovalsDTO to save.
     * @param tranferDetailsApprovalsDTO the tranferDetailsApprovalsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tranferDetailsApprovalsDTO,
     * or with status {@code 400 (Bad Request)} if the tranferDetailsApprovalsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tranferDetailsApprovalsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tranferDetailsApprovalsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tranfer-details-approvals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TranferDetailsApprovalsDTO> partialUpdateTranferDetailsApprovals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TranferDetailsApprovalsDTO tranferDetailsApprovalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TranferDetailsApprovals partially : {}, {}", id, tranferDetailsApprovalsDTO);
        if (tranferDetailsApprovalsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tranferDetailsApprovalsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tranferDetailsApprovalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TranferDetailsApprovalsDTO> result = tranferDetailsApprovalsService.partialUpdate(tranferDetailsApprovalsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tranferDetailsApprovalsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tranfer-details-approvals} : get all the tranferDetailsApprovals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tranferDetailsApprovals in body.
     */
    @GetMapping("/tranfer-details-approvals")
    public ResponseEntity<List<TranferDetailsApprovalsDTO>> getAllTranferDetailsApprovals(
        TranferDetailsApprovalsCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get TranferDetailsApprovals by criteria: {}", criteria);
        Page<TranferDetailsApprovalsDTO> page = tranferDetailsApprovalsQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tranfer-details-approvals/count} : count all the tranferDetailsApprovals.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tranfer-details-approvals/count")
    public ResponseEntity<Long> countTranferDetailsApprovals(TranferDetailsApprovalsCriteria criteria) {
        log.debug("REST request to count TranferDetailsApprovals by criteria: {}", criteria);
        return ResponseEntity.ok().body(tranferDetailsApprovalsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tranfer-details-approvals/:id} : get the "id" tranferDetailsApprovals.
     *
     * @param id the id of the tranferDetailsApprovalsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tranferDetailsApprovalsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tranfer-details-approvals/{id}")
    public ResponseEntity<TranferDetailsApprovalsDTO> getTranferDetailsApprovals(@PathVariable Long id) {
        log.debug("REST request to get TranferDetailsApprovals : {}", id);
        Optional<TranferDetailsApprovalsDTO> tranferDetailsApprovalsDTO = tranferDetailsApprovalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tranferDetailsApprovalsDTO);
    }

    /**
     * {@code DELETE  /tranfer-details-approvals/:id} : delete the "id" tranferDetailsApprovals.
     *
     * @param id the id of the tranferDetailsApprovalsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tranfer-details-approvals/{id}")
    public ResponseEntity<Void> deleteTranferDetailsApprovals(@PathVariable Long id) {
        log.debug("REST request to delete TranferDetailsApprovals : {}", id);
        tranferDetailsApprovalsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
