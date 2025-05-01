package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Product;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;

public class ProductMapper {

    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
       var product = new Product(null, productRequestDto.name(), productRequestDto.description(), productRequestDto.price());
       if (productRequestDto.quantityStock() != null) {
           product.setQuantityStock(productRequestDto.quantityStock());
       }

       if(productRequestDto.imageUrl() != null) {
           product.setImageUrl(productRequestDto.imageUrl());
       }
        return product;
    }

    public static ProductResponseDto productToProductResponseDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl(), product.getQuantityStock());
    }
}
