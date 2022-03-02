package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.ProductInventory;
import com.mycompany.myapp.service.dto.ProductInventoryDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductInventory} and its DTO {@link ProductInventoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProductMapper.class, WarehouseMapper.class, SecurityUserMapper.class })
public interface ProductInventoryMapper extends EntityMapper<ProductInventoryDTO, ProductInventory> {
    @Mapping(target = "products", source = "products", qualifiedByName = "idSet")
    @Mapping(target = "warehouses", source = "warehouses", qualifiedByName = "idSet")
    @Mapping(target = "securityUsers", source = "securityUsers", qualifiedByName = "idSet")
    ProductInventoryDTO toDto(ProductInventory s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProductInventoryDTO toDtoId(ProductInventory productInventory);

    @Mapping(target = "removeProduct", ignore = true)
    @Mapping(target = "removeWarehouse", ignore = true)
    @Mapping(target = "removeSecurityUser", ignore = true)
    ProductInventory toEntity(ProductInventoryDTO productInventoryDTO);
}
