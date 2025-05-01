package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.ProductService;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductUpdateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tech-shop/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) Integer page,
                                                                  @RequestParam(required = false)  Integer size) {
        return ResponseEntity.ok(productService.findAllProducts(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct( @PathVariable Long id, @ RequestBody ProductUpdateRequestDto productRequestDto) {
        productService.updateProduct(id, productRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.createProduct(productRequestDto));
    }
}
