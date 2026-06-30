package com.ims.product.dto;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id;
	
    @Schema(description = "Product Name",
    		example = "Laptop")
	private String name;

	private String sku;
	
	@Schema( description = "Product Price",
		        example = "55000")
	private double price;
	
	private Long categoryId;
	
	private Long supplierId;

}
