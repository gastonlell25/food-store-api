package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.enums.Role;

public record UserDto(
        Long id,
        String name,
        String lastName,
        String email,
        String phone,
        Role role
) {}
