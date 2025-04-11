package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRespository extends JpaRepository<Product, Long> {
}
