package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Categories;
import com.mycompany.myapp.service.dto.CategoriesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categories} and its DTO {@link CategoriesDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoriesMapper extends EntityMapper<CategoriesDTO, Categories> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriesDTO toDtoId(Categories categories);
}
