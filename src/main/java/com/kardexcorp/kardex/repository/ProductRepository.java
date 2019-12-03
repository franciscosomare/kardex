package com.kardexcorp.kardex.repository;

import com.kardexcorp.kardex.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}