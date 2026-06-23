package com.prog3.food_store_api.repositories;

import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.Base;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseRepository<E extends Base, ID> extends JpaRepository<E, ID> {

    List<E> findAllByDeletedFalse();

    Optional<E> findByIdAndDeletedFalse(ID id);

    default E findByIdOrThrow(ID id) {
        return findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Entidad con id " + id + " no encontrado"));
    }
}
