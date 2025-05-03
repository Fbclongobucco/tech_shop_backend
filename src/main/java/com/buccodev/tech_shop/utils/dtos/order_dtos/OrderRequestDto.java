package com.buccodev.tech_shop.utils.dtos.order_dtos;

import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDto(Long customerId, List<OrderItemRequestDto> orderItems) {
}
