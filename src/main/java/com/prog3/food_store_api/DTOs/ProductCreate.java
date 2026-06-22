package com.prog3.food_store_api.DTOs;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductCreate(
        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Price is required")
        @DecimalMin(value = "0.01", message = "Price must be greater than zero")
        BigDecimal price,

        String description,

        @Min(value = 0, message = "Stock cannot be negative")
        int stock,

        String image,

        boolean available,

        @NotNull(message = "Category is required")
        Long categoryId
) {}
