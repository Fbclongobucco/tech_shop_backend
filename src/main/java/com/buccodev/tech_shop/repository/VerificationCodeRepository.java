package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {

    Optional<VerificationCode> findByUserId(Long userId);
    Optional<VerificationCode> findByCode(String code);
}
