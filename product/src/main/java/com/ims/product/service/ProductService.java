package com.ims.product.service;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ims.product.entity.Product;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	public Product createProduct(Product product) {
		// TODO Auto-generated method stub
		log.info("Creating product");
		Product savedProduct = productRepository.save(product);
		log.info("Product created with ID: {}", savedProduct.getId());
		return savedProduct;
	}

	public Product getProductById(Long id) {
		// TODO Auto-generated method stub
		return  productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		
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
