package com.prog3.food_store_api.services;

import com.prog3.food_store_api.DTOs.CategoryCreate;
import com.prog3.food_store_api.DTOs.CategoryDto;
import com.prog3.food_store_api.DTOs.CategoryMapper;
import com.prog3.food_store_api.exceptions.ResourceNotFoundException;
import com.prog3.food_store_api.models.Category;
import com.prog3.food_store_api.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAll_returnsMappedList() {
        Category cat = Category.builder().name("Pizzas").build();
        CategoryDto dto = new CategoryDto(1L, "Pizzas", null);

        when(categoryRepository.findAll()).thenReturn(List.of(cat));
        when(categoryMapper.toDto(cat)).thenReturn(dto);

        List<CategoryDto> result = categoryService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).name()).isEqualTo("Pizzas");
    }

    @Test
    void getAll_returnsEmptyList_whenNoCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of());

        List<CategoryDto> result = categoryService.getAll();

        assertThat(result).isEmpty();
    }

    @Test
    void create_savesAndReturnsDto() {
        CategoryCreate create = new CategoryCreate("Pizzas", "Desc");
        Category entity = Category.builder().name("Pizzas").description("Desc").build();
        CategoryDto dto = new CategoryDto(1L, "Pizzas", "Desc");

        when(categoryMapper.toEntity(create)).thenReturn(entity);
        when(categoryRepository.save(entity)).thenReturn(entity);
        when(categoryMapper.toDto(entity)).thenReturn(dto);

        CategoryDto result = categoryService.create(create);

        assertThat(result.name()).isEqualTo("Pizzas");
        verify(categoryRepository).save(entity);
    }

    @Test
    void update_updatesAndReturnsDto() {
        Category existing = Category.builder().name("Vieja").build();
        CategoryCreate update = new CategoryCreate("Nueva", "Nueva desc");
        CategoryDto dto = new CategoryDto(1L, "Nueva", "Nueva desc");

        when(categoryRepository.findByIdOrThrow(1L)).thenReturn(existing);
        when(categoryRepository.save(existing)).thenReturn(existing);
        when(categoryMapper.toDto(existing)).thenReturn(dto);

        CategoryDto result = categoryService.update(1L, update);

        assertThat(result.name()).isEqualTo("Nueva");
        verify(categoryMapper).updateEntity(update, existing);
    }

    @Test
    void delete_setsDeletedTrue() {
        Category cat = Category.builder().name("Pizzas").build();
        when(categoryRepository.findByIdOrThrow(1L)).thenReturn(cat);

        categoryService.delete(1L);

        assertThat(cat.isDeleted()).isTrue();
        verify(categoryRepository).save(cat);
    }

    @Test
    void findEntityById_throwsWhenNotFound() {
        when(categoryRepository.findByIdOrThrow(99L))
                .thenThrow(new ResourceNotFoundException("Entidad con id 99 no encontrado"));

        assertThatThrownBy(() -> categoryService.findEntityById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");
    }
}
