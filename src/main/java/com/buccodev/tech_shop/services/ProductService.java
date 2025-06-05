package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Product;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CategoryRepository;
import com.buccodev.tech_shop.repository.ProductRepository;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductUpdateRequestDto;
import com.buccodev.tech_shop.utils.mappers.ProductMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final MinioService minioService;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, MinioService minioService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.minioService = minioService;
    }

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto, MultipartFile file) {

        String urlImage;

        if (file == null || file.isEmpty()) {
            urlImage = "https://placehold.co/600x400";
        } else {
            urlImage = minioService.uploadPhoto(file);
        }

        var category = categoryRepository.findByName(productRequestDto.category())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        var product = ProductMapper.productRequestDtoToProduct(productRequestDto);
        product.setImageUrl(urlImage);
        product.setCategory(category);
        category.getProducts().add(product);
        productRepository.save(product);
        return ProductMapper.toProductResponseDto(product);
    }

    @Transactional
    public void updateProduct(Long id, ProductUpdateRequestDto productRequestDto, MultipartFile file) {
        var category = categoryRepository.findByName(productRequestDto.category())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));


        if (file != null && !file.isEmpty()) {

            if (product.getImageUrl() != null) {
                minioService.deletePhoto(product.getImageUrl());
            }

            String newImageUrl = minioService.uploadPhoto(file);
            product.setImageUrl(newImageUrl);
        }

        product.setName(productRequestDto.name());
        product.setDescription(productRequestDto.description());
        product.setPrice(productRequestDto.price());
        product.setQuantityStock(productRequestDto.quantityStock());
        product.setCategory(category);

        productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        if (product.getImageUrl() != null) {
            minioService.deletePhoto(product.getImageUrl());
        }
        productRepository.deleteById(product.getId());
    }

    public ProductResponseDto findProductById(Long id) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return ProductMapper.toProductResponseDto(product);
    }

    public List<ProductResponseDto> findAllProducts(Integer page, Integer size) {
        if (page == null || page < 0) {
            page = 0;
        }

        if(size == null || size < 1) {
            size = 10;
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return productRepository.findAll(pageRequest).stream().map(ProductMapper::toProductResponseDto).toList();
    }

    public List<ProductResponseDto> findProductsByName(String name, Integer page, Integer size) {
        if (page == null || page < 0) {
            page = 0;
        }
        if(size == null || size < 1) {
            size = 10;
        }
        Pageable pages = PageRequest.of(page, size);


        Specification<Product> categoryEqual = (root, query, cb)
                -> cb.like( cb.upper( root.get("category").get("name")), "%" + name.toUpperCase() + "%");

        return  productRepository.findAll(categoryEqual, pages).stream().map(ProductMapper::toProductResponseDto).toList();

    }


    public List<ProductResponseDto> findProductsByCategory(String category, Integer page, Integer size) {
        if (page == null || page < 0) {
            page = 0;
        }
        if(size == null || size < 1) {
            size = 10;
        }
        Pageable pages = PageRequest.of(page, size);

        Specification<Product> categoryEqual = (root, query, cb)
                -> cb.like( cb.upper( root.get("category").get("name")), "%" + category.toUpperCase() + "%");

        return  productRepository.findAll(categoryEqual, pages).stream()
                .map(ProductMapper::toProductResponseDto).toList();

    }

    public void updateStock(Long id, Integer quantityStock) {
        var product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        product.setQuantityStock(product.getQuantityStock() + quantityStock);
        productRepository.save(product);
    }


}
