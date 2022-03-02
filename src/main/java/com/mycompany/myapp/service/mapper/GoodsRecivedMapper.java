package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.GoodsRecived;
import com.mycompany.myapp.service.dto.GoodsRecivedDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoodsRecived} and its DTO {@link GoodsRecivedDTO}.
 */
@Mapper(componentModel = "spring", uses = { PurchaseOrderMapper.class })
public interface GoodsRecivedMapper extends EntityMapper<GoodsRecivedDTO, GoodsRecived> {
    @Mapping(target = "purchaseOrder", source = "purchaseOrder", qualifiedByName = "id")
    GoodsRecivedDTO toDto(GoodsRecived s);
}
