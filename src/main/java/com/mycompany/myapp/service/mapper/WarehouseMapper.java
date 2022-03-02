package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Warehouse;
import com.mycompany.myapp.service.dto.WarehouseDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Warehouse} and its DTO {@link WarehouseDTO}.
 */
@Mapper(componentModel = "spring", uses = { SecurityUserMapper.class })
public interface WarehouseMapper extends EntityMapper<WarehouseDTO, Warehouse> {
    @Mapping(target = "securityUsers", source = "securityUsers", qualifiedByName = "idSet")
    WarehouseDTO toDto(Warehouse s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WarehouseDTO toDtoId(Warehouse warehouse);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<WarehouseDTO> toDtoIdSet(Set<Warehouse> warehouse);

    @Mapping(target = "removeSecurityUser", ignore = true)
    Warehouse toEntity(WarehouseDTO warehouseDTO);
}
