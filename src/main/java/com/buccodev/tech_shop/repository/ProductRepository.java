package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.Category;
import com.buccodev.tech_shop.entities.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Optional<Product> findByName(String name);

    List<Product> findByCategory(Category category, PageRequest pageRequest);

}
