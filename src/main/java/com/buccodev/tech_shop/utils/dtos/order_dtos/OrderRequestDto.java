package com.buccodev.tech_shop.utils.dtos.order_dtos;

import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequestDto(@NotNull Long customerId, List<OrderItemRequestDto> orderItems) {
}
