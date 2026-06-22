package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto toDto(Category category);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    Category toEntity(CategoryCreate dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    void updateEntity(CategoryCreate dto, @MappingTarget Category category);
}
