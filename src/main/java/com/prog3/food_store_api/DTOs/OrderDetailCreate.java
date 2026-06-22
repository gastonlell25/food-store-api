package com.prog3.food_store_api.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderDetailCreate(
        @NotNull(message = "Product is required")
        Long productId,

        @Min(value = 1, message = "Quantity must be at least 1")
        int quantity
) {}
