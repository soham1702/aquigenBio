package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.TranferDetailsApprovals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TranferDetailsApprovals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TranferDetailsApprovalsRepository
    extends JpaRepository<TranferDetailsApprovals, Long>, JpaSpecificationExecutor<TranferDetailsApprovals> {}
