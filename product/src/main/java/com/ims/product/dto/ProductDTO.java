package com.ims.product.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
	
	private String name;

	private String sku;
	
	private BigDecimal price;
	
	private Long categoryId;
	
	private Long supplierId;

}
