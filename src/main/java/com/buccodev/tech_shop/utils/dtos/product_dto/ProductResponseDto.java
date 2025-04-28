package com.buccodev.tech_shop.utils.dtos.product_dto;

import java.math.BigDecimal;

public record ProductResponseDto(Long id, String name, String description,
                                 BigDecimal price, String imageUrl, Integer quantityStock) {
}
