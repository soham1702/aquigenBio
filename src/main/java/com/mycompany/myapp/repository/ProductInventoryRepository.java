package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProductInventory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductInventory entity.
 */
@Repository
public interface ProductInventoryRepository
    extends
        ProductInventoryRepositoryWithBagRelationships, JpaRepository<ProductInventory, Long>, JpaSpecificationExecutor<ProductInventory> {
    default Optional<ProductInventory> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<ProductInventory> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<ProductInventory> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
