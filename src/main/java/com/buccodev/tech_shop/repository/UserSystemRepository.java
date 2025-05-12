package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.UserSystem;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface UserSystemRepository extends JpaRepository<UserSystem, Long> {
    Optional<UserSystem> findByName(String name);

    Optional<UserSystem>findByEmail(String email);
}
