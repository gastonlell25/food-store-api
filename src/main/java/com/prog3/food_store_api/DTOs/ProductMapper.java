package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.Category;
import com.prog3.food_store_api.models.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product product);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "stock", source = "stock")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "available", source = "available")
    Product toEntity(ProductCreate dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "stock", source = "stock")
    @Mapping(target = "image", source = "image")
    @Mapping(target = "available", source = "available")
    void updateEntity(ProductCreate dto, @MappingTarget Product product);
}
