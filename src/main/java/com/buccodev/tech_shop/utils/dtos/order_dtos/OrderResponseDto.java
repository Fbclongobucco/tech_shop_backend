package com.buccodev.tech_shop.utils.dtos.order_dtos;

import com.buccodev.tech_shop.utils.dtos.customers_dtos.CustomerResponseDto;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemResponseDto;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponseDto(Long id, CustomerResponseDto customer, String createdAt, List<OrderItemResponseDto> orderItems, BigDecimal totalValue) {
}
