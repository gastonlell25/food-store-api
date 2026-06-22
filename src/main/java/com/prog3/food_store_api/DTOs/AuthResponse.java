package com.prog3.food_store_api.DTOs;

public record AuthResponse(
        String token,
        UserDto user
) {}
