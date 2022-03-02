package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.repository.WarehouseRepository;
import com.mycompany.myapp.service.criteria.WarehouseCriteria;
import com.mycompany.myapp.service.dto.WarehouseDTO;
import com.mycompany.myapp.service.mapper.WarehouseMapper;
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
 * Service for executing complex queries for {@link Warehouse} entities in the database.
 * The main input is a {@link WarehouseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WarehouseDTO} or a {@link Page} of {@link WarehouseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WarehouseQueryService extends QueryService<Warehouse> {

    private final Logger log = LoggerFactory.getLogger(WarehouseQueryService.class);

    private final WarehouseRepository warehouseRepository;

    private final WarehouseMapper warehouseMapper;

    public WarehouseQueryService(WarehouseRepository warehouseRepository, WarehouseMapper warehouseMapper) {
        this.warehouseRepository = warehouseRepository;
        this.warehouseMapper = warehouseMapper;
    }

    /**
     * Return a {@link List} of {@link WarehouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WarehouseDTO> findByCriteria(WarehouseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Warehouse> specification = createSpecification(criteria);
        return warehouseMapper.toDto(warehouseRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WarehouseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WarehouseDTO> findByCriteria(WarehouseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Warehouse> specification = createSpecification(criteria);
        return warehouseRepository.findAll(specification, page).map(warehouseMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WarehouseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Warehouse> specification = createSpecification(criteria);
        return warehouseRepository.count(specification);
    }

    /**
     * Function to convert {@link WarehouseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Warehouse> createSpecification(WarehouseCriteria criteria) {
        Specification<Warehouse> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Warehouse_.id));
            }
            if (criteria.getWarehouseId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWarehouseId(), Warehouse_.warehouseId));
            }
            if (criteria.getWhName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWhName(), Warehouse_.whName));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Warehouse_.address));
            }
            if (criteria.getPincode() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPincode(), Warehouse_.pincode));
            }
            if (criteria.getCity() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCity(), Warehouse_.city));
            }
            if (criteria.getState() != null) {
                specification = specification.and(buildStringSpecification(criteria.getState(), Warehouse_.state));
            }
            if (criteria.getCountry() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCountry(), Warehouse_.country));
            }
            if (criteria.getgSTDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getgSTDetails(), Warehouse_.gSTDetails));
            }
            if (criteria.getManagerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerName(), Warehouse_.managerName));
            }
            if (criteria.getManagerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerEmail(), Warehouse_.managerEmail));
            }
            if (criteria.getManagerContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getManagerContact(), Warehouse_.managerContact));
            }
            if (criteria.getContact() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContact(), Warehouse_.contact));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Warehouse_.isDeleted));
            }
            if (criteria.getIsActive() != null) {
                specification = specification.and(buildSpecification(criteria.getIsActive(), Warehouse_.isActive));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Warehouse_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Warehouse_.lastModifiedBy));
            }
            if (criteria.getPurchaseOrderId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getPurchaseOrderId(),
                            root -> root.join(Warehouse_.purchaseOrders, JoinType.LEFT).get(PurchaseOrder_.id)
                        )
                    );
            }
            if (criteria.getSecurityUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSecurityUserId(),
                            root -> root.join(Warehouse_.securityUsers, JoinType.LEFT).get(SecurityUser_.id)
                        )
                    );
            }
            if (criteria.getProductTransactionId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductTransactionId(),
                            root -> root.join(Warehouse_.productTransaction, JoinType.LEFT).get(ProductTransaction_.id)
                        )
                    );
            }
            if (criteria.getProductInventoryId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getProductInventoryId(),
                            root -> root.join(Warehouse_.productInventories, JoinType.LEFT).get(ProductInventory_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
