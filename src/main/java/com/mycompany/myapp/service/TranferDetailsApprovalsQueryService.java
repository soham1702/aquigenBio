package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.TranferDetailsApprovals;
import com.mycompany.myapp.repository.TranferDetailsApprovalsRepository;
import com.mycompany.myapp.service.criteria.TranferDetailsApprovalsCriteria;
import com.mycompany.myapp.service.dto.TranferDetailsApprovalsDTO;
import com.mycompany.myapp.service.mapper.TranferDetailsApprovalsMapper;
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
 * Service for executing complex queries for {@link TranferDetailsApprovals} entities in the database.
 * The main input is a {@link TranferDetailsApprovalsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TranferDetailsApprovalsDTO} or a {@link Page} of {@link TranferDetailsApprovalsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TranferDetailsApprovalsQueryService extends QueryService<TranferDetailsApprovals> {

    private final Logger log = LoggerFactory.getLogger(TranferDetailsApprovalsQueryService.class);

    private final TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository;

    private final TranferDetailsApprovalsMapper tranferDetailsApprovalsMapper;

    public TranferDetailsApprovalsQueryService(
        TranferDetailsApprovalsRepository tranferDetailsApprovalsRepository,
        TranferDetailsApprovalsMapper tranferDetailsApprovalsMapper
    ) {
        this.tranferDetailsApprovalsRepository = tranferDetailsApprovalsRepository;
        this.tranferDetailsApprovalsMapper = tranferDetailsApprovalsMapper;
    }

    /**
     * Return a {@link List} of {@link TranferDetailsApprovalsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TranferDetailsApprovalsDTO> findByCriteria(TranferDetailsApprovalsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TranferDetailsApprovals> specification = createSpecification(criteria);
        return tranferDetailsApprovalsMapper.toDto(tranferDetailsApprovalsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TranferDetailsApprovalsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TranferDetailsApprovalsDTO> findByCriteria(TranferDetailsApprovalsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TranferDetailsApprovals> specification = createSpecification(criteria);
        return tranferDetailsApprovalsRepository.findAll(specification, page).map(tranferDetailsApprovalsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TranferDetailsApprovalsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TranferDetailsApprovals> specification = createSpecification(criteria);
        return tranferDetailsApprovalsRepository.count(specification);
    }

    /**
     * Function to convert {@link TranferDetailsApprovalsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TranferDetailsApprovals> createSpecification(TranferDetailsApprovalsCriteria criteria) {
        Specification<TranferDetailsApprovals> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TranferDetailsApprovals_.id));
            }
            if (criteria.getApprovalDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getApprovalDate(), TranferDetailsApprovals_.approvalDate));
            }
            if (criteria.getQtyRequested() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getQtyRequested(), TranferDetailsApprovals_.qtyRequested));
            }
            if (criteria.getQtyApproved() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyApproved(), TranferDetailsApprovals_.qtyApproved));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), TranferDetailsApprovals_.comment));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), TranferDetailsApprovals_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), TranferDetailsApprovals_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getLastModified(), TranferDetailsApprovals_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TranferDetailsApprovals_.lastModifiedBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), TranferDetailsApprovals_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), TranferDetailsApprovals_.isActive));
            }
            if (criteria.getTransferId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferId(),
                            root -> root.join(TranferDetailsApprovals_.transfer, JoinType.LEFT).get(Transfer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
