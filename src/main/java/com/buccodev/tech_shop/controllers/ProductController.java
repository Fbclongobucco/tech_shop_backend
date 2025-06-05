package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.ProductService;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductUpdateRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.UpdateStockDto;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByName(@PathVariable String name,
                                                                      @RequestParam(required = false) Integer page,
                                                                      @RequestParam(required = false) Integer size) {
        return ResponseEntity.ok(productService.findProductsByName(name, page, size));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts(@RequestParam(required = false) Integer page,
                                                                  @RequestParam(required = false)  Integer size) {
        return ResponseEntity.ok(productService.findAllProducts(page, size));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'BASIC')")
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") @Valid ProductUpdateRequestDto productRequestDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        productService.updateProduct(id, productRequestDto, file);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponseDto> createProduct(
            @RequestPart("product") @Valid ProductRequestDto productRequestDto,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return ResponseEntity.ok(productService.createProduct(productRequestDto, file));
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable String category,
                                                                          @RequestParam(required = false) Integer page,
                                                                          @RequestParam(required = false) Integer size) {
        var products = productService.findProductsByCategory(category, page, size);
        return ResponseEntity.ok(products);
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC')")
    @PutMapping("/stock/{id}")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestBody UpdateStockDto updateStockDto) {
        productService.updateStock(id, updateStockDto.quantityStock());
        return ResponseEntity.noContent().build();
    }

}
