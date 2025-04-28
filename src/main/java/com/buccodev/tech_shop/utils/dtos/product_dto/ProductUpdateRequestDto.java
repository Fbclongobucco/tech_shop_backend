package com.buccodev.tech_shop.utils.dtos.product_dto;

import java.math.BigDecimal;

public record ProductUpdateRequestDto(String name, String description, BigDecimal price, String imageUrl, Integer quantityStock) {}
