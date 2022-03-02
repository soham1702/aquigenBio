package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GoodsRecived;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GoodsRecived entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GoodsRecivedRepository extends JpaRepository<GoodsRecived, Long>, JpaSpecificationExecutor<GoodsRecived> {}
