package com.ims.product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.ims.product.dto.CategoryDTO;
import com.ims.product.entity.Category;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.CategoryReposiory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	
	private final CategoryReposiory categoryRepository;
	
	private final ModelMapper modelMapper;
	
	public CategoryDTO createCategory(@Valid CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category category = modelMapper.map(categoryDTO, Category.class);
		Category createdCategory = categoryRepository.save(category);
		return modelMapper.map(createdCategory, CategoryDTO.class);
	}

	public CategoryDTO getCategoryById(Long id) {
		// TODO Auto-generated method stub		
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
		return modelMapper.map(category, CategoryDTO.class);
	}

	public void deleteCategoryById(Long id) {
		// TODO Auto-generated method stub
		categoryRepository.deleteById(id);	
	}

	public List<CategoryDTO> getAllCategories() {
		// TODO Auto-generated method stub
		return categoryRepository.findAll().stream().map(category -> modelMapper.map(category, CategoryDTO.class)).toList();
	}

	public CategoryDTO updateCategory(Long id, @Valid CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
		modelMapper.map(categoryDTO, existingCategory);
		existingCategory.setId(id);
		Category save = categoryRepository.save(existingCategory);
		return modelMapper.map(save, CategoryDTO.class);
	}

}
