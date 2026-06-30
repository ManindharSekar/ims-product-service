package com.ims.product.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.product.dto.ProductDTO;
import com.ims.product.service.ProductService;

@WebMvcTest(ProductApi.class)
class ProductApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductService productService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private ProductDTO productDTO;

	@BeforeEach
	void setUp() {

		productDTO = new ProductDTO();
		productDTO.setId(1L);
		productDTO.setName("Laptop");
		productDTO.setSku("L123456");
		productDTO.setPrice(50000);
	}

	@DisplayName("Create Product Successfully")
	@Test
	void createProduct_ShouldReturnCreated() throws Exception {

		when(productService.createProduct(any(ProductDTO.class))).thenReturn(productDTO);

		mockMvc.perform(post("/products/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDTO))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Laptop"));
	}

	@Test
	void getProductById_ShouldReturnProduct() throws Exception {

		when(productService.getProductById(1L)).thenReturn(productDTO);

		mockMvc.perform(get("/products/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Laptop"));
	}

	@Test
	void updateProduct_ShouldReturnUpdatedProduct() throws Exception {

		when(productService.updateProduct(any(Long.class), any(ProductDTO.class))).thenReturn(productDTO);

		mockMvc.perform(put("/products/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(productDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Laptop"));
	}

	@Test
	void deleteProduct_ShouldReturnNoContent() throws Exception {

		doNothing().when(productService).deleteProductById(1L);

		mockMvc.perform(delete("/products/1")).andExpect(status().isNoContent());
	}

	@Test
	void getAllProducts_ShouldReturnList() throws Exception {

		when(productService.getAllProducts()).thenReturn(List.of(productDTO));

		mockMvc.perform(get("/products/")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Laptop"));
	}
}