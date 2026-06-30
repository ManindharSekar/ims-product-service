package com.ims.product.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ims.product.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Products")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductApi {
	
	private final ProductService productService;
	
	private static final Logger log = LoggerFactory.getLogger(ProductApi.class);
	
	@Operation(summary = "Create Product")
	@PostMapping("/create")
	public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		log.info("Received request to create product");		
		ProductDTO createdProduct = productService.createProduct(productDTO);	
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}
	 
	@Operation(summary = "Get Product by Id")
	@GetMapping("/{id}")
	@ApiResponses({
		    @ApiResponse(responseCode = "200",description = "Success"),
		    @ApiResponse(responseCode = "404",description = "Not Found")})
	public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") Long id) {
		ProductDTO productDTO = productService.getProductById(id);
		return ResponseEntity.ok(productDTO);
	}
	
	@Operation(summary = "Delete Product by Id")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
		productService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}
	
	@Operation(summary = "Update Product by Id")
	@PutMapping("/{id}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,@Valid @RequestBody ProductDTO productDTO) {
		ProductDTO updatedProduct = productService.updateProduct(id,productDTO);
		return ResponseEntity.ok(updatedProduct);
		
	}
	
	@Operation(summary = "Get All Products")
	@GetMapping("/") 
	public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<ProductDTO> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

}
