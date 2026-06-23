package com.prog3.food_store_api.DTOs;

import com.prog3.food_store_api.models.enums.OrderStatus;
import com.prog3.food_store_api.models.enums.PaymentMethod;

public record OrderUpdate(
        OrderStatus status,
        PaymentMethod paymentMethod
) {}
