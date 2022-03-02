package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.PurchaseOrderDetails;
import com.mycompany.myapp.service.dto.PurchaseOrderDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PurchaseOrderDetails} and its DTO {@link PurchaseOrderDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { PurchaseOrderMapper.class, ProductMapper.class, UnitMapper.class })
public interface PurchaseOrderDetailsMapper extends EntityMapper<PurchaseOrderDetailsDTO, PurchaseOrderDetails> {
    @Mapping(target = "purchaseOrder", source = "purchaseOrder", qualifiedByName = "id")
    @Mapping(target = "product", source = "product", qualifiedByName = "id")
    @Mapping(target = "unit", source = "unit", qualifiedByName = "id")
    PurchaseOrderDetailsDTO toDto(PurchaseOrderDetails s);
}
