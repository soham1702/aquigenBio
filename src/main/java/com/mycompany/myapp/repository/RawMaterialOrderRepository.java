package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.RawMaterialOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RawMaterialOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RawMaterialOrderRepository extends JpaRepository<RawMaterialOrder, Long>, JpaSpecificationExecutor<RawMaterialOrder> {}
