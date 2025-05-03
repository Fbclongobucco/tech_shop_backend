package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
