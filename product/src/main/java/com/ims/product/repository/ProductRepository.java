package com.ims.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	boolean existsBySku(String sku);

}
