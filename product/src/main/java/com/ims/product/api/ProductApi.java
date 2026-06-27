package com.ims.product.api;

import java.util.List;

import org.jspecify.annotations.Nullable;
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

import com.ims.product.entity.Product;
import com.ims.product.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {
	
	private final ProductService productService;
	
	@PostMapping("/create")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product createdProduct = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(productService.getProductById(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
		productService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id, @RequestBody Product product) {
		Product existingProduct = productService.getProductById(id);
		existingProduct.setName(product.getName());
		existingProduct.setSku(product.getSku());
		existingProduct.setPrice(product.getPrice());
		existingProduct.setCategoryId(product.getCategoryId());
		existingProduct.setSupplierId(product.getSupplierId());
		Product updatedProduct = productService.createProduct(existingProduct);
		return ResponseEntity.ok(updatedProduct);
	}
	
	@GetMapping()
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		
		List allProducts = productService.getAllProducts();
		return ResponseEntity.ok(allProducts);
	}

}
