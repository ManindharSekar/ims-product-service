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
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ims.product.dto.CategoryDTO;
import com.ims.product.service.CategoryService;

@WebMvcTest(CategoryApi.class)
class CategoryApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CategoryService categoryService;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private CategoryDTO categoryDTO;
	
	@MockitoBean
	private ModelMapper modelMapper;

	@BeforeEach
	void setUp() {

		categoryDTO = new CategoryDTO();
		categoryDTO.setId(1L);
		categoryDTO.setName("Electronics");
	}

	@Test
	void createCategory_ShouldReturnCreatedCategory() throws Exception {

		when(categoryService.createCategory(any(CategoryDTO.class))).thenReturn(categoryDTO);

		mockMvc.perform(post("/category/create").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Electronics"));
	}

	@Test
	void getCategoryById_ShouldReturnCategory() throws Exception {

		when(categoryService.getCategoryById(1L)).thenReturn(categoryDTO);

		mockMvc.perform(get("/category/1")).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
				.andExpect(jsonPath("$.name").value("Electronics"));
	}

	@Test
	void updateCategory_ShouldReturnUpdatedCategory() throws Exception {

		when(categoryService.updateCategory(any(Long.class), any(CategoryDTO.class))).thenReturn(categoryDTO);

		mockMvc.perform(put("/category/update/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(categoryDTO))).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1)).andExpect(jsonPath("$.name").value("Electronics"));
	}

	@Test
	void deleteCategory_ShouldReturnNoContent() throws Exception {

		doNothing().when(categoryService).deleteCategoryById(1L);

		mockMvc.perform(delete("/category/delete/1")).andExpect(status().isNoContent());
	}

	@Test
	void getAllCategories_ShouldReturnCategoryList() throws Exception {

		when(categoryService.getAllCategories()).thenReturn(List.of(categoryDTO));

		mockMvc.perform(get("/category/all")).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1))
				.andExpect(jsonPath("$[0].name").value("Electronics"));
	}
}