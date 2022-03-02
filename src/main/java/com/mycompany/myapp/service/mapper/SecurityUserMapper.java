package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.SecurityUser;
import com.mycompany.myapp.service.dto.SecurityUserDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SecurityUser} and its DTO {@link SecurityUserDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = {
        ProductTransactionMapper.class,
        SecurityPermissionMapper.class,
        SecurityRoleMapper.class,
        TransferMapper.class,
        ConsumptionDetailsMapper.class,
    }
)
public interface SecurityUserMapper extends EntityMapper<SecurityUserDTO, SecurityUser> {
    @Mapping(target = "productTransaction", source = "productTransaction", qualifiedByName = "id")
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "nameSet")
    @Mapping(target = "securityRoles", source = "securityRoles", qualifiedByName = "nameSet")
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    @Mapping(target = "consumptionDetails", source = "consumptionDetails", qualifiedByName = "id")
    SecurityUserDTO toDto(SecurityUser s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SecurityUserDTO toDtoId(SecurityUser securityUser);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<SecurityUserDTO> toDtoIdSet(Set<SecurityUser> securityUser);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    SecurityUser toEntity(SecurityUserDTO securityUserDTO);

    @Named("login")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    SecurityUserDTO toDtoLogin(SecurityUser securityUser);
}
