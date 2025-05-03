package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.ResourceDuplicateException;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CategoryRepository;
import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryRequestDto;
import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryResponseDto;
import com.buccodev.tech_shop.utils.mappers.CategoryMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {

        var isExistisCategory = categoryRepository.findByName(categoryRequestDto.nameCategory()).isPresent();
        if(isExistisCategory) {
            throw new ResourceDuplicateException("Category with name " + categoryRequestDto.nameCategory() + " already exists");
        }

        var category = CategoryMapper.toCategory(categoryRequestDto);
        var categorySalved = categoryRepository.save(category);

        return CategoryMapper.toCategoryResponseDto(categorySalved);
    }

    public CategoryResponseDto getCategoryById(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return CategoryMapper.toCategoryResponseDto(category);
    }

    public void deleteCategory(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }

    public List<CategoryResponseDto> getAllCategories(Integer page, Integer size) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);

        return categoryRepository.findAll(pageRequest).stream().map(CategoryMapper::toCategoryResponseDto).toList();
    }

    public CategoryResponseDto getCategoryByName(String name) {
        var category = categoryRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return CategoryMapper.toCategoryResponseDto(category);
    }
}
