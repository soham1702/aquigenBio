package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TransferDetails;
import com.mycompany.myapp.service.dto.TransferDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransferDetails} and its DTO {@link TransferDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransferMapper.class })
public interface TransferDetailsMapper extends EntityMapper<TransferDetailsDTO, TransferDetails> {
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    TransferDetailsDTO toDto(TransferDetails s);
}
