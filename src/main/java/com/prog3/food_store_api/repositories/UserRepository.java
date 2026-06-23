package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.models.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
