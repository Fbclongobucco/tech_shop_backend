package com.buccodev.tech_shop.utils.dtos.category_dtos;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDto(@NotBlank String nameCategory) {
}
