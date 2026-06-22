package com.prog3.food_store_api.DTOs;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreate(
        @NotBlank(message = "Name is required")
        String name,

        String description
) {}
