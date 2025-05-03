package com.buccodev.tech_shop.utils.dtos.customers_dtos;


import com.fasterxml.jackson.annotation.JsonInclude;

public record CustomerResponseDto(Long id, String name, String email, String phone, @JsonInclude(JsonInclude.Include.NON_NULL) String createdAt) {
}
