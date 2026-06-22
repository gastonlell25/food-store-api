package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
