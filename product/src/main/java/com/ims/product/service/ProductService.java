package com.ims.product.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ims.product.dto.ProductDTO;
import com.ims.product.entity.Product;
import com.ims.product.exception.DuplicateResourceException;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
	
	private final ProductRepository productRepository;
	
	private final ModelMapper modelMapper;
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	public ProductDTO createProduct(@Valid ProductDTO productDTO) {
		// TODO Auto-generated method stub
		log.info("Creating product with SKU {}", productDTO.getSku());
		if(productRepository.existsBySku(productDTO.getSku())){
		    throw new DuplicateResourceException("SKU already exists");
		}
		Product productEntity = modelMapper.map(productDTO, Product.class);
		Product savedProduct = productRepository.save(productEntity);
		log.info("Product created successfully. Id={}", savedProduct.getId());
		return modelMapper.map(savedProduct, ProductDTO.class);
	}
	
	public ProductDTO getProductById(Long id) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		return modelMapper.map(product, ProductDTO.class);
		
	}

	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub
		productRepository.deleteById(id);
		
	}


	public List<ProductDTO> getAllProducts() {
		return productRepository.findAll().stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
	}
	

	public ProductDTO updateProduct(Long id, @Valid ProductDTO productDTO) {
		// TODO Auto-generated method stub
		Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
		Product productEntity = modelMapper.map(productDTO, Product.class);
		productEntity.setId(existingProduct.getId());
		Product savedProduct = productRepository.save(productEntity);
		return modelMapper.map(savedProduct, ProductDTO.class);
	}

}
