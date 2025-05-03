package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Category;
import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryRequestDto;
import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryResponseDto;

public class CategoryMapper {

    public static CategoryResponseDto toCategoryResponseDto(Category category) {
        return new CategoryResponseDto(category.getId(), category.getName());
    }


    public static Category toCategory(CategoryRequestDto categoryRequestDto) {
        return new Category(null, categoryRequestDto.nameCategory());
    }
}
