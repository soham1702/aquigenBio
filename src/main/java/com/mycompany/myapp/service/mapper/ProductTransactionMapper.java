package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductTransaction;
import com.mycompany.myapp.service.dto.ProductTransactionDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductTransaction} and its DTO {@link ProductTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = { WarehouseMapper.class, ProductMapper.class, ProductInventoryMapper.class })
public interface ProductTransactionMapper extends EntityMapper<ProductTransactionDTO, ProductTransaction> {
    @Mapping(target = "warehouse", source = "warehouse", qualifiedByName = "id")
    @Mapping(target = "products", source = "products", qualifiedByName = "idSet")
    @Mapping(target = "productInventory", source = "productInventory", qualifiedByName = "id")
    ProductTransactionDTO toDto(ProductTransaction s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductTransactionDTO toDtoId(ProductTransaction productTransaction);

    @Mapping(target = "removeProduct", ignore = true)
    ProductTransaction toEntity(ProductTransactionDTO productTransactionDTO);
}
