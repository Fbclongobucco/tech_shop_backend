package com.buccodev.tech_shop.utils.dtos.product_dto;

import java.math.BigDecimal;

public record ProductRequestDto(String name, String description, BigDecimal price, String imageUrl, Integer quantityStock) {
}
