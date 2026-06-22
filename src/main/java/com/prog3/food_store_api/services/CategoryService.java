package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.CategoryCreate;
import com.prog3.food_store_api.DTOs.CategoryDto;
import com.prog3.food_store_api.DTOs.CategoryMapper;
import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.Category;
import com.prog3.food_store_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional
    public CategoryDto create(CategoryCreate dto) {
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(dto)));
    }

    @Transactional
    public CategoryDto update(Long id, CategoryCreate dto) {
        Category category = findEntityById(id);
        categoryMapper.updateEntity(dto, category);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        Category category = findEntityById(id);
        category.setDeleted(true);
        categoryRepository.save(category);
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
