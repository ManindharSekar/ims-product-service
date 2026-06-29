package com.ims.product.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.ims.product.entity.Category;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.CategoryReposiory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryReposiory categoryRepository;
	
	public Category createCategory(Category categoryEntity) {
		// TODO Auto-generated method stub
		Category category = categoryRepository.save(categoryEntity);
		return category;
	}

	public Category getCategoryById(Long id) {
		// TODO Auto-generated method stub	
		return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
	}

	public void deleteCategoryById(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);	
	}

	public List getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll();
	}

}
