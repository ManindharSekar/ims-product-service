package com.ims.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ims.product.entity.Product;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	private Product product;

	@BeforeEach
	void setUp() {
		product = new Product();
		product.setId(1L);
		product.setName("Laptop");
		product.setSku("L1234567");
		product.setPrice(50000);
		product.setSupplierId(1L);
	}

	@Test
	void createProduct_ShouldReturnSavedProduct() {

		when(productRepository.save(product)).thenReturn(product);
		Product savedProduct = productService.createProduct(product);

		assertNotNull(savedProduct);
		assertEquals("Laptop", savedProduct.getName());

		verify(productRepository, times(1)).save(product);
	}

	@Test
	void getProductById_ShouldReturnProduct_WhenProductExists() {

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		Product foundProduct = productService.getProductById(1L);

		assertNotNull(foundProduct);
		assertEquals(1L, foundProduct.getId());

		verify(productRepository).findById(1L);
	}

	@Test
	void getProductById_ShouldThrowException_WhenProductNotFound() {

		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> productService.getProductById(1L));

		assertEquals("Product not found with id: 1", exception.getMessage());

		verify(productRepository).findById(1L);
	}

	@Test
	void deleteProductById_ShouldDeleteProduct() {

		doNothing().when(productRepository).deleteById(1L);

		productService.deleteProductById(1L);

		verify(productRepository, times(1)).deleteById(1L);
	}

	@Test
	void getAllProducts_ShouldReturnAllProducts() {

		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Mouse");
		product2.setSku("M1234567");
		product2.setPrice(500);

		List<Product> products = Arrays.asList(product, product2);

		when(productRepository.findAll()).thenReturn(products);

		List<Product> result = productService.getAllProducts();

		assertEquals(2, result.size());

		verify(productRepository).findAll();
	}
}
