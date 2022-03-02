package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.PurchaseOrder;
import com.mycompany.myapp.repository.PurchaseOrderRepository;
import com.mycompany.myapp.service.criteria.PurchaseOrderCriteria;
import com.mycompany.myapp.service.dto.PurchaseOrderDTO;
import com.mycompany.myapp.service.mapper.PurchaseOrderMapper;
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
 * Service for executing complex queries for {@link PurchaseOrder} entities in the database.
 * The main input is a {@link PurchaseOrderCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PurchaseOrderDTO} or a {@link Page} of {@link PurchaseOrderDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PurchaseOrderQueryService extends QueryService<PurchaseOrder> {

    private final Logger log = LoggerFactory.getLogger(PurchaseOrderQueryService.class);

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderMapper purchaseOrderMapper;

    public PurchaseOrderQueryService(PurchaseOrderRepository purchaseOrderRepository, PurchaseOrderMapper purchaseOrderMapper) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseOrderMapper = purchaseOrderMapper;
    }

    /**
     * Return a {@link List} of {@link PurchaseOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PurchaseOrderDTO> findByCriteria(PurchaseOrderCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderMapper.toDto(purchaseOrderRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PurchaseOrderDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PurchaseOrderDTO> findByCriteria(PurchaseOrderCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.findAll(specification, page).map(purchaseOrderMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PurchaseOrderCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PurchaseOrder> specification = createSpecification(criteria);
        return purchaseOrderRepository.count(specification);
    }

    /**
     * Function to convert {@link PurchaseOrderCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PurchaseOrder> createSpecification(PurchaseOrderCriteria criteria) {
        Specification<PurchaseOrder> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PurchaseOrder_.id));
            }
            if (criteria.getTotalPOAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalPOAmount(), PurchaseOrder_.totalPOAmount));
            }
            if (criteria.getTotalGSTAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotalGSTAmount(), PurchaseOrder_.totalGSTAmount));
            }
            if (criteria.getExpectedDeliveryDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getExpectedDeliveryDate(), PurchaseOrder_.expectedDeliveryDate));
            }
            if (criteria.getPoDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPoDate(), PurchaseOrder_.poDate));
            }
            if (criteria.getOrderStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getOrderStatus(), PurchaseOrder_.orderStatus));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), PurchaseOrder_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), PurchaseOrder_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), PurchaseOrder_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), PurchaseOrder_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), PurchaseOrder_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), PurchaseOrder_.freeField4));
            }
            if (criteria.getPurchaseOrderDetailsId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseOrderDetailsId(),
                            root -> root.join(PurchaseOrder_.purchaseOrderDetails, JoinType.LEFT).get(PurchaseOrderDetails_.id)
                        )
                    );
            }
            if (criteria.getGoodsRecivedId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getGoodsRecivedId(),
                            root -> root.join(PurchaseOrder_.goodsReciveds, JoinType.LEFT).get(GoodsRecived_.id)
                        )
                    );
            }
            if (criteria.getProductInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductInventoryId(),
                            root -> root.join(PurchaseOrder_.productInventory, JoinType.LEFT).get(ProductInventory_.id)
                        )
                    );
            }
            if (criteria.getWarehouseId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getWarehouseId(),
                            root -> root.join(PurchaseOrder_.warehouse, JoinType.LEFT).get(Warehouse_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(PurchaseOrder_.securityUser, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getRawMaterialOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getRawMaterialOrderId(),
                            root -> root.join(PurchaseOrder_.rawMaterialOrder, JoinType.LEFT).get(RawMaterialOrder_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
