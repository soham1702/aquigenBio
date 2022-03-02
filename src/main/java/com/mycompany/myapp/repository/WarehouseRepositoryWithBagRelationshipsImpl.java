package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Warehouse;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WarehouseRepositoryWithBagRelationshipsImpl implements WarehouseRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Warehouse> fetchBagRelationships(Optional<Warehouse> warehouse) {
        return warehouse.map(this::fetchSecurityUsers);
    }

    @Override
    public Page<Warehouse> fetchBagRelationships(Page<Warehouse> warehouses) {
        return new PageImpl<>(fetchBagRelationships(warehouses.getContent()), warehouses.getPageable(), warehouses.getTotalElements());
    }

    @Override
    public List<Warehouse> fetchBagRelationships(List<Warehouse> warehouses) {
        return Optional.of(warehouses).map(this::fetchSecurityUsers).get();
    }

    Warehouse fetchSecurityUsers(Warehouse result) {
        return entityManager
            .createQuery(
                "select warehouse from Warehouse warehouse left join fetch warehouse.securityUsers where warehouse is :warehouse",
                Warehouse.class
            )
            .setParameter("warehouse", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Warehouse> fetchSecurityUsers(List<Warehouse> warehouses) {
        return entityManager
            .createQuery(
                "select distinct warehouse from Warehouse warehouse left join fetch warehouse.securityUsers where warehouse in :warehouses",
                Warehouse.class
            )
            .setParameter("warehouses", warehouses)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
