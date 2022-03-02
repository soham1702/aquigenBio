package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.TranferRecieved;
import com.mycompany.myapp.repository.TranferRecievedRepository;
import com.mycompany.myapp.service.criteria.TranferRecievedCriteria;
import com.mycompany.myapp.service.dto.TranferRecievedDTO;
import com.mycompany.myapp.service.mapper.TranferRecievedMapper;
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
 * Service for executing complex queries for {@link TranferRecieved} entities in the database.
 * The main input is a {@link TranferRecievedCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TranferRecievedDTO} or a {@link Page} of {@link TranferRecievedDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TranferRecievedQueryService extends QueryService<TranferRecieved> {

    private final Logger log = LoggerFactory.getLogger(TranferRecievedQueryService.class);

    private final TranferRecievedRepository tranferRecievedRepository;

    private final TranferRecievedMapper tranferRecievedMapper;

    public TranferRecievedQueryService(TranferRecievedRepository tranferRecievedRepository, TranferRecievedMapper tranferRecievedMapper) {
        this.tranferRecievedRepository = tranferRecievedRepository;
        this.tranferRecievedMapper = tranferRecievedMapper;
    }

    /**
     * Return a {@link List} of {@link TranferRecievedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TranferRecievedDTO> findByCriteria(TranferRecievedCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TranferRecieved> specification = createSpecification(criteria);
        return tranferRecievedMapper.toDto(tranferRecievedRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TranferRecievedDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TranferRecievedDTO> findByCriteria(TranferRecievedCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TranferRecieved> specification = createSpecification(criteria);
        return tranferRecievedRepository.findAll(specification, page).map(tranferRecievedMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TranferRecievedCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TranferRecieved> specification = createSpecification(criteria);
        return tranferRecievedRepository.count(specification);
    }

    /**
     * Function to convert {@link TranferRecievedCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TranferRecieved> createSpecification(TranferRecievedCriteria criteria) {
        Specification<TranferRecieved> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TranferRecieved_.id));
            }
            if (criteria.getTransferDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferDate(), TranferRecieved_.transferDate));
            }
            if (criteria.getQtyTransfered() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyTransfered(), TranferRecieved_.qtyTransfered));
            }
            if (criteria.getQtyReceived() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyReceived(), TranferRecieved_.qtyReceived));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), TranferRecieved_.comment));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), TranferRecieved_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), TranferRecieved_.freeField2));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), TranferRecieved_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), TranferRecieved_.lastModifiedBy));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), TranferRecieved_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), TranferRecieved_.isActive));
            }
            if (criteria.getTransferId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTransferId(),
                            root -> root.join(TranferRecieved_.transfer, JoinType.LEFT).get(Transfer_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
