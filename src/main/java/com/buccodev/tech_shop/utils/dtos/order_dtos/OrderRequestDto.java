package com.buccodev.tech_shop.utils.dtos.order_dtos;

import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDto(@NotBlank Long customerId, List<OrderItemRequestDto> orderItems) {
}
