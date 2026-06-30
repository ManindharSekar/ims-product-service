package com.ims.product.api;

import java.util.List;

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
import com.ims.product.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Category")
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryApi {
	
	private final CategoryService categoryService;
	
	
	@Operation(summary = "Create Category")
	@PostMapping("/create")
	public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
		return ResponseEntity.ok(createdCategory);
	}
	
	@Operation(summary = "Get Category by Id")
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Long id) {
		CategoryDTO category = categoryService.getCategoryById(id);
		return ResponseEntity.ok(category);
	}
	
	@Operation(summary = "Delete Category by Id")
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteCategoryById(@PathVariable("id") Long id) {
		categoryService.deleteCategoryById(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Update Category by Id")
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryDTO> updateCategory(@PathVariable("id") Long id, @Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO updatedCategory = categoryService.updateCategory(id,categoryDTO);
		return ResponseEntity.ok(updatedCategory);
	}
	
	@Operation(summary = "Get All Categories")
	@GetMapping("/all")
	public ResponseEntity<List<CategoryDTO>> getAllCategories() {
		return ResponseEntity.ok(categoryService.getAllCategories());
	}

}
