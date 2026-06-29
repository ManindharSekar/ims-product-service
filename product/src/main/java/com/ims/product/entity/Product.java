package com.ims.product.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank(message = "Product name is required")
	private String name;
	
	@NotBlank(message = " SKU No is Required")
    @Size(max = 8, message = "maximum 8 character")
    @Column(unique = true)
	private String sku;
	
	@Positive(message = "Price must be greater than zero")
	private BigDecimal price;
	
	private Long categoryId;
	
	private Long supplierId;

}
