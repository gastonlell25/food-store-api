package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.models.Product;

import java.util.List;

public interface ProductRepository extends BaseRepository<Product, Long> {
    List<Product> findAllByCategoryIdAndDeletedFalse(Long categoryId);
}
