package com.ims.product.api;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.product.dto.CategoryDTO;
import com.ims.product.entity.Category;
import com.ims.product.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryApi {
	
	private final CategoryService categoryService;
	
	private final ModelMapper modelMapper;
	
	@PostMapping("/create")
	public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		Category categoryEntity = modelMapper.map(categoryDTO, Category.class);
		Category createdCategory = categoryService.createCategory(categoryEntity);
		CategoryDTO createdCategoryDTO = modelMapper.map(createdCategory, CategoryDTO.class);
		return createdCategoryDTO;
	}
	
	@GetMapping("/{id}")
	public CategoryDTO getCategoryById(@PathVariable("id") Long id) {
		Category category = categoryService.getCategoryById(id);
		CategoryDTO dto = modelMapper.map(category, CategoryDTO.class);
		return dto;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/update/{id}")
	public CategoryDTO updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
		Category existingCategory = categoryService.getCategoryById(id);
		modelMapper.map(categoryDTO, existingCategory);
		existingCategory.setId(id);
		Category updatedCategory = categoryService.createCategory(existingCategory);
		CategoryDTO updatedCategoryDTO = modelMapper.map(updatedCategory, CategoryDTO.class);
		return updatedCategoryDTO;
	}
	@GetMapping("/all")
	public ResponseEntity<?> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

}
