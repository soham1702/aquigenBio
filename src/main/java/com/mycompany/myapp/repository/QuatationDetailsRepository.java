package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.QuatationDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the QuatationDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuatationDetailsRepository extends JpaRepository<QuatationDetails, Long>, JpaSpecificationExecutor<QuatationDetails> {}
