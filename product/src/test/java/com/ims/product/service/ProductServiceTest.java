package com.ims.product.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ims.product.dto.ProductDTO;
import com.ims.product.entity.Category;
import com.ims.product.entity.Product;
import com.ims.product.exception.DuplicateResourceException;
import com.ims.product.exception.ResourceNotFoundException;
import com.ims.product.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private ProductService productService;

	private Product product;
	private ProductDTO productDTO;

	@BeforeEach
	void setUp() {

		product = new Product();
		product.setId(1L);
		product.setName("Laptop");
		product.setSku("L1234567");
		product.setPrice(50000);
		Category category = new Category();
		category.setId(1L);
		product.setCategoryId(category);
		product.setSupplierId(1L);

		productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Laptop");
		productDTO.setSku("L1234567");
		productDTO.setPrice(50000);
		productDTO.setCategoryId(1L);
		productDTO.setSupplierId(1L);
	}

	@Test
	void createProduct_ShouldReturnSavedProduct() {

		// Arrange
		when(productRepository.existsBySku(productDTO.getSku())).thenReturn(false);

		when(modelMapper.map(productDTO, Product.class)).thenReturn(product);

		when(productRepository.save(product)).thenReturn(product);

		when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

		// Act
		ProductDTO result = productService.createProduct(productDTO);

		// Assert
		assertNotNull(result);
		assertEquals("Laptop", result.getName());
		assertEquals("L1234567", result.getSku());

		verify(productRepository).existsBySku(productDTO.getSku());
		verify(productRepository).save(product);
	}

	@Test
	void createProduct_ShouldThrowException_WhenSkuAlreadyExists() {

		// Arrange
		when(productRepository.existsBySku(productDTO.getSku())).thenReturn(true);

		// Act & Assert
		DuplicateResourceException exception = assertThrows(DuplicateResourceException.class,
				() -> productService.createProduct(productDTO));

		assertEquals("SKU already exists", exception.getMessage());

		verify(productRepository).existsBySku(productDTO.getSku());
		verify(productRepository, never()).save(any(Product.class));
	}

	@Test
	void getProductById_ShouldReturnProduct_WhenExists() {

		// Arrange
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

		// Act
		ProductDTO result = productService.getProductById(1L);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Laptop", result.getName());

		verify(productRepository).findById(1L);
	}

	@Test
	void getProductById_ShouldThrowException_WhenProductNotFound() {

		// Arrange
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> productService.getProductById(1L));

		assertEquals("Product not found with id: 1", exception.getMessage());

		verify(productRepository).findById(1L);
	}

	@Test
	void updateProduct_ShouldReturnUpdatedProduct() {

		// Arrange
		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		when(modelMapper.map(productDTO, Product.class)).thenReturn(product);

		when(productRepository.save(product)).thenReturn(product);

		when(modelMapper.map(product, ProductDTO.class)).thenReturn(productDTO);

		// Act
		ProductDTO result = productService.updateProduct(1L, productDTO);

		// Assert
		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Laptop", result.getName());

		verify(productRepository).findById(1L);
		verify(productRepository).save(product);
	}

	@Test
	void updateProduct_ShouldThrowException_WhenProductNotFound() {

		// Arrange
		when(productRepository.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
				() -> productService.updateProduct(1L, productDTO));

		assertEquals("Product not found with id: 1", exception.getMessage());

		verify(productRepository).findById(1L);
		verify(productRepository, never()).save(any(Product.class));
	}

	@Test
	void deleteProductById_ShouldDeleteProduct() {

		// Arrange
		doNothing().when(productRepository).deleteById(1L);

		// Act
		productService.deleteProductById(1L);

		// Assert
		verify(productRepository).deleteById(1L);
	}

	@Test
	void getAllProducts_ShouldReturnAllProducts() {

		// Arrange
		Product product2 = new Product();
		product2.setId(2L);
		product2.setName("Mouse");
		product2.setSku("M1234567");
		product2.setPrice(500);
		Category category = new Category();
		category.setId(1L);
		product2.setCategoryId(category);
		product2.setSupplierId(1L);

		List<Product> products = Arrays.asList(product, product2);

		when(productRepository.findAll()).thenReturn(products);

		// Act
		List<ProductDTO> result = productService.getAllProducts();

		// Assert
		assertEquals(2, result.size());

		verify(productRepository).findAll();
	}
}