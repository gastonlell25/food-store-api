package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.models.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long> {
    List<Order> findAllByUserIdAndDeletedFalse(Long userId);
}
