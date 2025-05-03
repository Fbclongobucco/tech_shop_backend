package com.buccodev.tech_shop.utils.dtos.product_dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

public record ProductResponseDto(Long id, String name, String description, String category,
                                 BigDecimal price, String imageUrl, @JsonInclude(JsonInclude.Include.NON_NULL) Integer quantityStock) {
}
