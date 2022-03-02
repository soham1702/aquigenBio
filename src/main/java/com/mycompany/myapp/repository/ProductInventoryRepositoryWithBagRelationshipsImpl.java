package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductInventory;
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
public class ProductInventoryRepositoryWithBagRelationshipsImpl implements ProductInventoryRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<ProductInventory> fetchBagRelationships(Optional<ProductInventory> productInventory) {
        return productInventory.map(this::fetchProducts).map(this::fetchWarehouses).map(this::fetchSecurityUsers);
    }

    @Override
    public Page<ProductInventory> fetchBagRelationships(Page<ProductInventory> productInventories) {
        return new PageImpl<>(
            fetchBagRelationships(productInventories.getContent()),
            productInventories.getPageable(),
            productInventories.getTotalElements()
        );
    }

    @Override
    public List<ProductInventory> fetchBagRelationships(List<ProductInventory> productInventories) {
        return Optional.of(productInventories).map(this::fetchProducts).map(this::fetchWarehouses).map(this::fetchSecurityUsers).get();
    }

    ProductInventory fetchProducts(ProductInventory result) {
        return entityManager
            .createQuery(
                "select productInventory from ProductInventory productInventory left join fetch productInventory.products where productInventory is :productInventory",
                ProductInventory.class
            )
            .setParameter("productInventory", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ProductInventory> fetchProducts(List<ProductInventory> productInventories) {
        return entityManager
            .createQuery(
                "select distinct productInventory from ProductInventory productInventory left join fetch productInventory.products where productInventory in :productInventories",
                ProductInventory.class
            )
            .setParameter("productInventories", productInventories)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    ProductInventory fetchWarehouses(ProductInventory result) {
        return entityManager
            .createQuery(
                "select productInventory from ProductInventory productInventory left join fetch productInventory.warehouses where productInventory is :productInventory",
                ProductInventory.class
            )
            .setParameter("productInventory", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ProductInventory> fetchWarehouses(List<ProductInventory> productInventories) {
        return entityManager
            .createQuery(
                "select distinct productInventory from ProductInventory productInventory left join fetch productInventory.warehouses where productInventory in :productInventories",
                ProductInventory.class
            )
            .setParameter("productInventories", productInventories)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    ProductInventory fetchSecurityUsers(ProductInventory result) {
        return entityManager
            .createQuery(
                "select productInventory from ProductInventory productInventory left join fetch productInventory.securityUsers where productInventory is :productInventory",
                ProductInventory.class
            )
            .setParameter("productInventory", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<ProductInventory> fetchSecurityUsers(List<ProductInventory> productInventories) {
        return entityManager
            .createQuery(
                "select distinct productInventory from ProductInventory productInventory left join fetch productInventory.securityUsers where productInventory in :productInventories",
                ProductInventory.class
            )
            .setParameter("productInventories", productInventories)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
