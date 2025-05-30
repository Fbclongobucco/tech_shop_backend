package com.buccodev.tech_shop.utils.dtos.product_dto;

import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequestDto(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull CategoryRequestDto category,
                                @NotNull BigDecimal price,
                                String imageUrl,
                                Integer quantityStock) {
}
