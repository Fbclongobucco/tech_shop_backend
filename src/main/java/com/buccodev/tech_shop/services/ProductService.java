package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.ProductRepository;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductUpdateRequestDto;
import com.buccodev.tech_shop.utils.mappers.ProductMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.DecimalMax;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        var product = ProductMapper.productRequestDtoToProduct(productRequestDto);
        productRepository.save(product);
        return ProductMapper.productToProductResponseDto(product);
    }

    @Transactional
    public void updateProduct(Long id, ProductUpdateRequestDto productRequestDto) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setName(productRequestDto.name());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setImageUrl(productRequestDto.imageUrl());
        product.setQuantityStock(productRequestDto.quantityStock());
        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.deleteById(product.getId());
    }

    public ProductResponseDto getProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductMapper.productToProductResponseDto(product);
    }

    public List<ProductResponseDto> getAllProducts(Integer page, Integer size) {
        if(page == null || page < 0) {
            page = 0;
        }

        if(size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest).stream().map(ProductMapper::productToProductResponseDto).toList();
    }

    public List<ProductResponseDto> getProductsByName(String name, Integer page, Integer size) {
        if(page == null || page < 0) {
            page = 0;
        }

        if(size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findByName(name, pageRequest).stream().map(ProductMapper::productToProductResponseDto).toList();
    }
}
