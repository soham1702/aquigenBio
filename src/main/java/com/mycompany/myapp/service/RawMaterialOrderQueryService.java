package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.RawMaterialOrder;
import com.mycompany.myapp.repository.RawMaterialOrderRepository;
import com.mycompany.myapp.service.criteria.RawMaterialOrderCriteria;
import com.mycompany.myapp.service.dto.RawMaterialOrderDTO;
import com.mycompany.myapp.service.mapper.RawMaterialOrderMapper;
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
 * Service for executing complex queries for {@link RawMaterialOrder} entities in the database.
 * The main input is a {@link RawMaterialOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RawMaterialOrderDTO} or a {@link Page} of {@link RawMaterialOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RawMaterialOrderQueryService extends QueryService<RawMaterialOrder> {

    private final Logger log = LoggerFactory.getLogger(RawMaterialOrderQueryService.class);

    private final RawMaterialOrderRepository rawMaterialOrderRepository;

    private final RawMaterialOrderMapper rawMaterialOrderMapper;

    public RawMaterialOrderQueryService(
        RawMaterialOrderRepository rawMaterialOrderRepository,
        RawMaterialOrderMapper rawMaterialOrderMapper
    ) {
        this.rawMaterialOrderRepository = rawMaterialOrderRepository;
        this.rawMaterialOrderMapper = rawMaterialOrderMapper;
    }

    /**
     * Return a {@link List} of {@link RawMaterialOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RawMaterialOrderDTO> findByCriteria(RawMaterialOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RawMaterialOrder> specification = createSpecification(criteria);
        return rawMaterialOrderMapper.toDto(rawMaterialOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link RawMaterialOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RawMaterialOrderDTO> findByCriteria(RawMaterialOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RawMaterialOrder> specification = createSpecification(criteria);
        return rawMaterialOrderRepository.findAll(specification, page).map(rawMaterialOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RawMaterialOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RawMaterialOrder> specification = createSpecification(criteria);
        return rawMaterialOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link RawMaterialOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<RawMaterialOrder> createSpecification(RawMaterialOrderCriteria criteria) {
        Specification<RawMaterialOrder> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), RawMaterialOrder_.id));
            }
            if (criteria.getPricePerUnit() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPricePerUnit(), RawMaterialOrder_.pricePerUnit));
            }
            if (criteria.getQuantityUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuantityUnit(), RawMaterialOrder_.quantityUnit));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), RawMaterialOrder_.quantity));
            }
            if (criteria.getDeliveryDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDeliveryDate(), RawMaterialOrder_.deliveryDate));
            }
            if (criteria.getQuantityCheck() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuantityCheck(), RawMaterialOrder_.quantityCheck));
            }
            if (criteria.getOrderedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrderedOn(), RawMaterialOrder_.orderedOn));
            }
            if (criteria.getOrderStatus() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrderStatus(), RawMaterialOrder_.orderStatus));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), RawMaterialOrder_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), RawMaterialOrder_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), RawMaterialOrder_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), RawMaterialOrder_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), RawMaterialOrder_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), RawMaterialOrder_.freeField4));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseOrderId(),
                            root -> root.join(RawMaterialOrder_.purchaseOrders, JoinType.LEFT).get(PurchaseOrder_.id)
                        )
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductId(),
                            root -> root.join(RawMaterialOrder_.products, JoinType.LEFT).get(Product_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
