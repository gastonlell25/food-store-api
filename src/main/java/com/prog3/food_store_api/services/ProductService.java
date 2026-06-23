package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.ProductCreate;
import com.prog3.food_store_api.DTOs.ProductDto;
import com.prog3.food_store_api.DTOs.ProductMapper;
import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.Category;
import com.prog3.food_store_api.models.Product;
import com.prog3.food_store_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        return productRepository.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        return productRepository.findByIdAndDeletedFalse(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Entidad con id " + id + " no encontrado"));
    }

    @Transactional(readOnly = true)
    public List<ProductDto> getByCategory(Long categoryId) {
        categoryService.findEntityById(categoryId);
        return productRepository.findAllByCategoryIdAndDeletedFalse(categoryId).stream()
                .map(productMapper::toDto)
                .toList();
    }

    @Transactional
    public ProductDto create(ProductCreate dto) {
        Category category = categoryService.findEntityById(dto.categoryId());
        Product product = productMapper.toEntity(dto);
        product.setCategory(category);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public ProductDto update(Long id, ProductCreate dto) {
        Product product = findEntityById(id);
        Category category = categoryService.findEntityById(dto.categoryId());
        productMapper.updateEntity(dto, product);
        product.setCategory(category);
        return productMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = findEntityById(id);
        product.setDeleted(true);
        productRepository.save(product);
    }

    public Product findEntityById(Long id) {
        return productRepository.findByIdOrThrow(id);
    }
}
