package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.enums.OrderStatus;
import com.prog3.food_store_api.models.enums.PaymentMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record OrderDto(
        Long id,
        LocalDate date,
        OrderStatus status,
        PaymentMethod paymentMethod,
        BigDecimal total,
        UserDto user,
        List<OrderDetailDto> orderDetails
) {}
