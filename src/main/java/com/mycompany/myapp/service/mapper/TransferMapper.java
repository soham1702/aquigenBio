package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Transfer;
import com.mycompany.myapp.service.dto.TransferDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transfer} and its DTO {@link TransferDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductInventoryMapper.class })
public interface TransferMapper extends EntityMapper<TransferDTO, Transfer> {
    @Mapping(target = "productInventory", source = "productInventory", qualifiedByName = "id")
    TransferDTO toDto(Transfer s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TransferDTO toDtoId(Transfer transfer);
}
