package com.buccodev.tech_shop.utils.dtos.product_dto;

import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryRequestDto;

import java.math.BigDecimal;

public record ProductRequestDto(String name, String description, CategoryRequestDto category, BigDecimal price, String imageUrl, Integer quantityStock) {
}
