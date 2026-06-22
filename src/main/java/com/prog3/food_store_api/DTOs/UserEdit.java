package com.prog3.food_store_api.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserEdit(
        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        String phone
) {}
