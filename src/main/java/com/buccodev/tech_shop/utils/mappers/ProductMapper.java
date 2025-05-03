package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Product;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductRequestDto;
import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;

public class ProductMapper {

    public static Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
       var product = new Product(null, productRequestDto.name(), productRequestDto.description(),
               CategoryMapper.toCategory(productRequestDto.category()), productRequestDto.price());
       if (productRequestDto.quantityStock() != null) {
           product.setQuantityStock(productRequestDto.quantityStock());

       }

       if(productRequestDto.imageUrl() != null) {
           product.setImageUrl(productRequestDto.imageUrl());
       }
        return product;
    }

    public static ProductResponseDto toProductResponseDto(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getCategory().getName(), product.getPrice(), product.getImageUrl(), product.getQuantityStock());
    }

    public static ProductResponseDto toProductResponseToOrder(Product product) {
        return new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getCategory().getName(), product.getPrice(), product.getImageUrl(), null);
    }
}
