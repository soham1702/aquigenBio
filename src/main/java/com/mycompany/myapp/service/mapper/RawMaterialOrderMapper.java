package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.RawMaterialOrder;
import com.mycompany.myapp.service.dto.RawMaterialOrderDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link RawMaterialOrder} and its DTO {@link RawMaterialOrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RawMaterialOrderMapper extends EntityMapper<RawMaterialOrderDTO, RawMaterialOrder> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RawMaterialOrderDTO toDtoId(RawMaterialOrder rawMaterialOrder);

    @Named("idSet")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    Set<RawMaterialOrderDTO> toDtoIdSet(Set<RawMaterialOrder> rawMaterialOrder);
}
