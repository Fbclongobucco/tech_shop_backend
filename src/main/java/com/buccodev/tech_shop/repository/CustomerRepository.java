package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);
    Optional<Customer> findByEmail(String email);
}
