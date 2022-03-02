package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.SecurityRole;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface SecurityRoleRepositoryWithBagRelationships {
    Optional<SecurityRole> fetchBagRelationships(Optional<SecurityRole> securityRole);

    List<SecurityRole> fetchBagRelationships(List<SecurityRole> securityRoles);

    Page<SecurityRole> fetchBagRelationships(Page<SecurityRole> securityRoles);
}
