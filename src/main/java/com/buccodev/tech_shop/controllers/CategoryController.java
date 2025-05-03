package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.CategoryService;
import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryRequestDto;
import com.buccodev.tech_shop.utils.dtos.category_dtos.CategoryResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("tech-shop/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        var categoryResponseDto = categoryService.createCategory(categoryRequestDto);
        URI uri = URI.create("/tech-shop/category/" + categoryResponseDto.id());
        return ResponseEntity.created(uri).body(categoryResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        var categoryResponseDto = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@PathVariable String name) {
        var categoryResponseDto = categoryService.getCategoryByName(name);
        return ResponseEntity.ok(categoryResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories(@RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size) {
        var categories = categoryService.getAllCategories(page, size);
        return ResponseEntity.ok(categories);
    }
}
