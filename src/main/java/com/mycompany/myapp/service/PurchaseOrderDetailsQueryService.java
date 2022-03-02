package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PurchaseOrderDetails;
import com.mycompany.myapp.repository.PurchaseOrderDetailsRepository;
import com.mycompany.myapp.service.criteria.PurchaseOrderDetailsCriteria;
import com.mycompany.myapp.service.dto.PurchaseOrderDetailsDTO;
import com.mycompany.myapp.service.mapper.PurchaseOrderDetailsMapper;
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
 * Service for executing complex queries for {@link PurchaseOrderDetails} entities in the database.
 * The main input is a {@link PurchaseOrderDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrderDetailsDTO} or a {@link Page} of {@link PurchaseOrderDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderDetailsQueryService extends QueryService<PurchaseOrderDetails> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderDetailsQueryService.class);

    private final PurchaseOrderDetailsRepository purchaseOrderDetailsRepository;

    private final PurchaseOrderDetailsMapper purchaseOrderDetailsMapper;

    public PurchaseOrderDetailsQueryService(
        PurchaseOrderDetailsRepository purchaseOrderDetailsRepository,
        PurchaseOrderDetailsMapper purchaseOrderDetailsMapper
    ) {
        this.purchaseOrderDetailsRepository = purchaseOrderDetailsRepository;
        this.purchaseOrderDetailsMapper = purchaseOrderDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrderDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrderDetailsDTO> findByCriteria(PurchaseOrderDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrderDetails> specification = createSpecification(criteria);
        return purchaseOrderDetailsMapper.toDto(purchaseOrderDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrderDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderDetailsDTO> findByCriteria(PurchaseOrderDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrderDetails> specification = createSpecification(criteria);
        return purchaseOrderDetailsRepository.findAll(specification, page).map(purchaseOrderDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrderDetails> specification = createSpecification(criteria);
        return purchaseOrderDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseOrderDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseOrderDetails> createSpecification(PurchaseOrderDetailsCriteria criteria) {
        Specification<PurchaseOrderDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurchaseOrderDetails_.id));
            }
            if (criteria.getQtyordered() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQtyordered(), PurchaseOrderDetails_.qtyordered));
            }
            if (criteria.getGstTaxPercentage() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getGstTaxPercentage(), PurchaseOrderDetails_.gstTaxPercentage));
            }
            if (criteria.getPricePerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerUnit(), PurchaseOrderDetails_.pricePerUnit));
            }
            if (criteria.getTotalPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPrice(), PurchaseOrderDetails_.totalPrice));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PurchaseOrderDetails_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PurchaseOrderDetails_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), PurchaseOrderDetails_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), PurchaseOrderDetails_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), PurchaseOrderDetails_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), PurchaseOrderDetails_.freeField4));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseOrderId(),
                            root -> root.join(PurchaseOrderDetails_.purchaseOrder, JoinType.LEFT).get(PurchaseOrder_.id)
                        )
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(PurchaseOrderDetails_.product, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
            if (criteria.getUnitId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getUnitId(), root -> root.join(PurchaseOrderDetails_.unit, JoinType.LEFT).get(Unit_.id))
                    );
            }
        }
        return specification;
    }
}
