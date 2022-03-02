package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.ProductQuatation;
import com.mycompany.myapp.repository.ProductQuatationRepository;
import com.mycompany.myapp.service.criteria.ProductQuatationCriteria;
import com.mycompany.myapp.service.dto.ProductQuatationDTO;
import com.mycompany.myapp.service.mapper.ProductQuatationMapper;
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
 * Service for executing complex queries for {@link ProductQuatation} entities in the database.
 * The main input is a {@link ProductQuatationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProductQuatationDTO} or a {@link Page} of {@link ProductQuatationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProductQuatationQueryService extends QueryService<ProductQuatation> {

    private final Logger log = LoggerFactory.getLogger(ProductQuatationQueryService.class);

    private final ProductQuatationRepository productQuatationRepository;

    private final ProductQuatationMapper productQuatationMapper;

    public ProductQuatationQueryService(
        ProductQuatationRepository productQuatationRepository,
        ProductQuatationMapper productQuatationMapper
    ) {
        this.productQuatationRepository = productQuatationRepository;
        this.productQuatationMapper = productQuatationMapper;
    }

    /**
     * Return a {@link List} of {@link ProductQuatationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProductQuatationDTO> findByCriteria(ProductQuatationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProductQuatation> specification = createSpecification(criteria);
        return productQuatationMapper.toDto(productQuatationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ProductQuatationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProductQuatationDTO> findByCriteria(ProductQuatationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProductQuatation> specification = createSpecification(criteria);
        return productQuatationRepository.findAll(specification, page).map(productQuatationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProductQuatationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProductQuatation> specification = createSpecification(criteria);
        return productQuatationRepository.count(specification);
    }

    /**
     * Function to convert {@link ProductQuatationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ProductQuatation> createSpecification(ProductQuatationCriteria criteria) {
        Specification<ProductQuatation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ProductQuatation_.id));
            }
            if (criteria.getQuatationdate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuatationdate(), ProductQuatation_.quatationdate));
            }
            if (criteria.getTotalAmt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalAmt(), ProductQuatation_.totalAmt));
            }
            if (criteria.getGst() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGst(), ProductQuatation_.gst));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), ProductQuatation_.discount));
            }
            if (criteria.getExpectedDelivery() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExpectedDelivery(), ProductQuatation_.expectedDelivery));
            }
            if (criteria.getDeliveryAddress() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getDeliveryAddress(), ProductQuatation_.deliveryAddress));
            }
            if (criteria.getQuoValidity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuoValidity(), ProductQuatation_.quoValidity));
            }
            if (criteria.getClientName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientName(), ProductQuatation_.clientName));
            }
            if (criteria.getClientMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientMobile(), ProductQuatation_.clientMobile));
            }
            if (criteria.getClientEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getClientEmail(), ProductQuatation_.clientEmail));
            }
            if (criteria.getTermsAndCondition() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getTermsAndCondition(), ProductQuatation_.termsAndCondition));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), ProductQuatation_.notes));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ProductQuatation_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ProductQuatation_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), ProductQuatation_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), ProductQuatation_.freeField4));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ProductQuatation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ProductQuatation_.lastModifiedBy));
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(ProductQuatation_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getQuatationDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getQuatationDetailsId(),
                            root -> root.join(ProductQuatation_.quatationDetails, JoinType.LEFT).get(QuatationDetails_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
