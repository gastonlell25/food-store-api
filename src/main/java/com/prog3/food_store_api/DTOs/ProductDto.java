package com.prog3.food_store_api.DTOs;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String name,
        BigDecimal price,
        String description,
        int stock,
        String image,
        boolean available,
        Long categoryId
) {}
