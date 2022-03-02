package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Warehouse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Warehouse entity.
 */
@Repository
public interface WarehouseRepository
    extends WarehouseRepositoryWithBagRelationships, JpaRepository<Warehouse, Long>, JpaSpecificationExecutor<Warehouse> {
    default Optional<Warehouse> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Warehouse> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Warehouse> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
