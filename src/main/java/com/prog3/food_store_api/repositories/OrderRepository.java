package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
