package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByDeletedFalse();
    Optional<Product> findByIdAndDeletedFalse(Long id);
}
