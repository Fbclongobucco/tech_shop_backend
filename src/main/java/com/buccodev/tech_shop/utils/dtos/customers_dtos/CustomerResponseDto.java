package com.buccodev.tech_shop.utils.dtos.customers_dtos;

import java.time.LocalDateTime;

public record CustomerResponseDto(Long id, String name, String email, String phone, String createdAt) {
}
