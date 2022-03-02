package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.TranferDetailsApprovals;
import com.mycompany.myapp.service.dto.TranferDetailsApprovalsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TranferDetailsApprovals} and its DTO {@link TranferDetailsApprovalsDTO}.
 */
@Mapper(componentModel = "spring", uses = { TransferMapper.class })
public interface TranferDetailsApprovalsMapper extends EntityMapper<TranferDetailsApprovalsDTO, TranferDetailsApprovals> {
    @Mapping(target = "transfer", source = "transfer", qualifiedByName = "id")
    TranferDetailsApprovalsDTO toDto(TranferDetailsApprovals s);
}
