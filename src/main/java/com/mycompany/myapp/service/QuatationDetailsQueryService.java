package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.QuatationDetails;
import com.mycompany.myapp.repository.QuatationDetailsRepository;
import com.mycompany.myapp.service.criteria.QuatationDetailsCriteria;
import com.mycompany.myapp.service.dto.QuatationDetailsDTO;
import com.mycompany.myapp.service.mapper.QuatationDetailsMapper;
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
 * Service for executing complex queries for {@link QuatationDetails} entities in the database.
 * The main input is a {@link QuatationDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link QuatationDetailsDTO} or a {@link Page} of {@link QuatationDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QuatationDetailsQueryService extends QueryService<QuatationDetails> {

    private final Logger log = LoggerFactory.getLogger(QuatationDetailsQueryService.class);

    private final QuatationDetailsRepository quatationDetailsRepository;

    private final QuatationDetailsMapper quatationDetailsMapper;

    public QuatationDetailsQueryService(
        QuatationDetailsRepository quatationDetailsRepository,
        QuatationDetailsMapper quatationDetailsMapper
    ) {
        this.quatationDetailsRepository = quatationDetailsRepository;
        this.quatationDetailsMapper = quatationDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link QuatationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<QuatationDetailsDTO> findByCriteria(QuatationDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<QuatationDetails> specification = createSpecification(criteria);
        return quatationDetailsMapper.toDto(quatationDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link QuatationDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<QuatationDetailsDTO> findByCriteria(QuatationDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<QuatationDetails> specification = createSpecification(criteria);
        return quatationDetailsRepository.findAll(specification, page).map(quatationDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QuatationDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<QuatationDetails> specification = createSpecification(criteria);
        return quatationDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link QuatationDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<QuatationDetails> createSpecification(QuatationDetailsCriteria criteria) {
        Specification<QuatationDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), QuatationDetails_.id));
            }
            if (criteria.getAvailabelStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAvailabelStock(), QuatationDetails_.availabelStock));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), QuatationDetails_.quantity));
            }
            if (criteria.getRatsPerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRatsPerUnit(), QuatationDetails_.ratsPerUnit));
            }
            if (criteria.getTotalprice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalprice(), QuatationDetails_.totalprice));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), QuatationDetails_.discount));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), QuatationDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), QuatationDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), QuatationDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), QuatationDetails_.freeField4));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), QuatationDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), QuatationDetails_.lastModifiedBy));
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(QuatationDetails_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUnitId(), root -> root.join(QuatationDetails_.unit, JoinType.LEFT).get(Unit_.id))
                    );
            }
            if (criteria.getCategoriesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCategoriesId(),
                            root -> root.join(QuatationDetails_.categories, JoinType.LEFT).get(Categories_.id)
                        )
                    );
            }
            if (criteria.getProductQuatationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductQuatationId(),
                            root -> root.join(QuatationDetails_.productQuatation, JoinType.LEFT).get(ProductQuatation_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
