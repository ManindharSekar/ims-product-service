package com.ims.product.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import com.ims.product.entity.Product;
import com.ims.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;

	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return  productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
		
	}

	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
		
	}

	public List getAllProducts() {
		// TODO Auto-generated method stub
		return productRepository.findAll();
	}

}
