package com.ims.product.api;

import java.util.List;

import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.product.dto.ProductDTO;
import com.ims.product.entity.Product;
import com.ims.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {
	
	private final ProductService productService;
	
	private final ModelMapper modelMapper;
	
	
	@PostMapping("/create")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		Product productEntity = modelMapper.map(productDTO, Product.class);
		Product createdProduct = productService.createProduct(productEntity);
		ProductDTO createdProductDTO = modelMapper.map(createdProduct, ProductDTO.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDTO);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
		Product product = productService.getProductById(id);
		ProductDTO dto = modelMapper.map(product, ProductDTO.class);
		return ResponseEntity.ok(dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
		productService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,@Valid @RequestBody ProductDTO productDTO) {
		Product existingProduct = productService.getProductById(id);
		modelMapper.map(productDTO, existingProduct);
		existingProduct.setId(id);
		Product updatedProduct = productService.createProduct(existingProduct);
		ProductDTO updatedDTO = modelMapper.map(updatedProduct, ProductDTO.class);
		return ResponseEntity.ok(updatedDTO);
		
	}
	
	@GetMapping() 
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

}
