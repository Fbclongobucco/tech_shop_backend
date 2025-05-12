package com.buccodev.tech_shop.utils.dtos.product_dto;

import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryRequestDto;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequestDto(@NotBlank String name,
                                @NotBlank String description,
                                @NotBlank CategoryRequestDto category,
                                @NotBlank BigDecimal price,
                                String imageUrl,
                                Integer quantityStock) {
}
