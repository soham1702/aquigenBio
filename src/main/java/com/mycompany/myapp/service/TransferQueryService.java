package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Transfer;
import com.mycompany.myapp.repository.TransferRepository;
import com.mycompany.myapp.service.criteria.TransferCriteria;
import com.mycompany.myapp.service.dto.TransferDTO;
import com.mycompany.myapp.service.mapper.TransferMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Transfer} entities in the database.
 * The main input is a {@link TransferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransferDTO} or a {@link Page} of {@link TransferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransferQueryService extends QueryService<Transfer> {

    private final Logger log = LoggerFactory.getLogger(TransferQueryService.class);

    private final TransferRepository transferRepository;

    private final TransferMapper transferMapper;

    public TransferQueryService(TransferRepository transferRepository, TransferMapper transferMapper) {
        this.transferRepository = transferRepository;
        this.transferMapper = transferMapper;
    }

    /**
     * Return a {@link List} of {@link TransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransferDTO> findByCriteria(TransferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Transfer> specification = createSpecification(criteria);
        return transferMapper.toDto(transferRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransferDTO> findByCriteria(TransferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Transfer> specification = createSpecification(criteria);
        return transferRepository.findAll(specification, page).map(transferMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Transfer> specification = createSpecification(criteria);
        return transferRepository.count(specification);
    }

    /**
     * Function to convert {@link TransferCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Transfer> createSpecification(TransferCriteria criteria) {
        Specification<Transfer> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Transfer_.id));
            }
            if (criteria.getTranferDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTranferDate(), Transfer_.tranferDate));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), Transfer_.comment));
            }
            if (criteria.getIsApproved() != null) {
                specification = specification.and(buildSpecification(criteria.getIsApproved(), Transfer_.isApproved));
            }
            if (criteria.getIsRecieved() != null) {
                specification = specification.and(buildSpecification(criteria.getIsRecieved(), Transfer_.isRecieved));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Transfer_.status));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Transfer_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Transfer_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Transfer_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Transfer_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(Transfer_.securityUsers, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getTransferDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferDetailsId(),
                            root -> root.join(Transfer_.transferDetails, JoinType.LEFT).get(TransferDetails_.id)
                        )
                    );
            }
            if (criteria.getTranferDetailsApprovalsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTranferDetailsApprovalsId(),
                            root -> root.join(Transfer_.tranferDetailsApprovals, JoinType.LEFT).get(TranferDetailsApprovals_.id)
                        )
                    );
            }
            if (criteria.getTranferRecievedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTranferRecievedId(),
                            root -> root.join(Transfer_.tranferRecieveds, JoinType.LEFT).get(TranferRecieved_.id)
                        )
                    );
            }
            if (criteria.getProductInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductInventoryId(),
                            root -> root.join(Transfer_.productInventory, JoinType.LEFT).get(ProductInventory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
