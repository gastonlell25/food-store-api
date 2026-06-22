package com.prog3.food_store_api.DTOs;

import java.math.BigDecimal;

public record OrderDetailDto(
        Long id,
        int quantity,
        BigDecimal subtotal,
        ProductDto product
) {}
