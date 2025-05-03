package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.OrderItem;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemResponseDto;

public class OrderItemMapper {

    public static OrderItemResponseDto toOrderItem(OrderItem orderItem) {
        return new OrderItemResponseDto(orderItem.getId(), ProductMapper
                .toProductResponseToOrder(orderItem.getProduct()), orderItem.getQuantity());
    }
}
