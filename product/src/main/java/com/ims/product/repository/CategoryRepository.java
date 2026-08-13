package com.ims.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ims.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
