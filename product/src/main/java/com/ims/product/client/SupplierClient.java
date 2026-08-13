package com.ims.product.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ims.product.dto.SupplierDTO;

@FeignClient(
	    name = "users-service",
	    url = "http://localhost:8081"
	)
	public interface SupplierClient {

	    @GetMapping("/api/suppliers/{id}")
	    SupplierDTO getSupplier(@PathVariable("id") Long id);
	}