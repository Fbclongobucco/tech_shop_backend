package com.buccodev.tech_shop.utils.dtos.product_dto;

import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryResponseDto;

import java.math.BigDecimal;

public record ProductUpdateRequestDto(String name, String description, CategoryResponseDto category, BigDecimal price, String imageUrl, Integer quantityStock) {}
