package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.enums.OrderStatus;
import com.prog3.food_store_api.models.enums.PaymentMethod;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderCreate(
        @NotNull(message = "User is required")
        Long userId,

        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,

        @NotNull(message = "Order status is required")
        OrderStatus status,

        @NotEmpty(message = "Order must have at least one detail")
        @Valid
        List<OrderDetailCreate> orderDetails
) {}
