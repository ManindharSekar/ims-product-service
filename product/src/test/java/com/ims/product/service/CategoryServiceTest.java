package com.ims.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.ims.product.entity.Category;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.CategoryReposiory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryReposiory categoryRepository;

	@InjectMocks
	private CategoryService categoryService;

	private Category category;

	@BeforeEach
	void setUp() {
		category = new Category();
		category.setId(1L);
		category.setName("Electronics");
	}

	@Test
	void createCategory_ShouldReturnSavedCategory() {

		// Arrange
		when(categoryRepository.save(category)).thenReturn(category);

		// Act
		Category savedCategory = categoryService.createCategory(category);

		// Assert
		assertNotNull(savedCategory);
		assertEquals(1L, savedCategory.getId());
		assertEquals("Electronics", savedCategory.getName());

		verify(categoryRepository, times(1)).save(category);
	}

	@Test
	void getCategoryById_ShouldReturnCategory_WhenCategoryExists() {

		// Arrange
		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

		// Act
		Category result = categoryService.getCategoryById(1L);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Electronics", result.getName());

		verify(categoryRepository).findById(1L);
	}

	@Test
	void getCategoryById_ShouldThrowException_WhenCategoryNotFound() {

		// Arrange
		when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> categoryService.getCategoryById(1L));

		assertEquals("Category not found with id: 1", exception.getMessage());

		verify(categoryRepository).findById(1L);
	}

	@Test
	void deleteCategoryById_ShouldDeleteCategory() {

		// Arrange
		doNothing().when(categoryRepository).deleteById(1L);

		// Act
		categoryService.deleteCategoryById(1L);

		// Assert
		verify(categoryRepository, times(1)).deleteById(1L);
	}

	@Test
	void getAllCategories_ShouldReturnCategoryList() {

		// Arrange
		Category category2 = new Category();
		category2.setId(2L);
		category2.setName("Furniture");

		List<Category> categories = Arrays.asList(category, category2);

		when(categoryRepository.findAll()).thenReturn(categories);

		// Act
		List<Category> result = categoryService.getAllCategories();

		// Assert
		assertEquals(2, result.size());
		assertEquals("Electronics", result.get(0).getName());
		assertEquals("Furniture", result.get(1).getName());

		verify(categoryRepository).findAll();
	}
}