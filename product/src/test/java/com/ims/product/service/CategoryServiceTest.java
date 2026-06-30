package com.ims.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ims.product.dto.CategoryDTO;
import com.ims.product.entity.Category;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.CategoryReposiory;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

	@Mock
	private CategoryReposiory categoryRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private CategoryService categoryService;

	private Category category;
	private CategoryDTO categoryDTO;

	@BeforeEach
	void setUp() {

		category = new Category();
		category.setId(1L);
		category.setName("Electronics");

		categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Electronics");
	}

	@Test
	void createCategory_ShouldReturnSavedCategory() {

		when(modelMapper.map(categoryDTO, Category.class)).thenReturn(category);

		when(categoryRepository.save(category)).thenReturn(category);

		when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO result = categoryService.createCategory(categoryDTO);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Electronics", result.getName());

		verify(categoryRepository).save(category);
	}

	@Test
	void getCategoryById_ShouldReturnCategory_WhenExists() {

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

		when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO result = categoryService.getCategoryById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());

		verify(categoryRepository).findById(1L);
	}

	@Test
	void getCategoryById_ShouldThrowException_WhenNotFound() {

		when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> categoryService.getCategoryById(1L));

		assertEquals("Category not found with id: 1", exception.getMessage());

		verify(categoryRepository).findById(1L);
	}

	@Test
	void updateCategory_ShouldReturnUpdatedCategory() {

		when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

		doAnswer(invocation -> {
			CategoryDTO dto = invocation.getArgument(0);
			Category entity = invocation.getArgument(1);
			entity.setName(dto.getName());
			return null;
		}).when(modelMapper).map(categoryDTO, category);

		when(categoryRepository.save(category)).thenReturn(category);

		when(modelMapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

		CategoryDTO result = categoryService.updateCategory(1L, categoryDTO);

		assertNotNull(result);
		assertEquals("Electronics", result.getName());

		verify(categoryRepository).save(category);
	}

	@Test
	void deleteCategory_ShouldDeleteCategory() {

		doNothing().when(categoryRepository).deleteById(1L);

		categoryService.deleteCategoryById(1L);

		verify(categoryRepository).deleteById(1L);
	}

	@Test
	void getAllCategories_ShouldReturnCategoryList() {

		Category category2 = new Category();
		category2.setId(2L);
		category2.setName("Furniture");

		when(categoryRepository.findAll()).thenReturn(Arrays.asList(category, category2));

		List<CategoryDTO> result = categoryService.getAllCategories();

		assertEquals(2, result.size());

		verify(categoryRepository).findAll();
	}
}