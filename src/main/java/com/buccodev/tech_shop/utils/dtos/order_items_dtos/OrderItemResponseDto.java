package com.buccodev.tech_shop.utils.dtos.order_items_dtos;

import com.buccodev.tech_shop.utils.dtos.product_dto.ProductResponseDto;

public record OrderItemResponseDto(Long id, ProductResponseDto product, Integer quantity) {
}
