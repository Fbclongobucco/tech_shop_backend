package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Order;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderResponseDto;

public class OrderMapper {

    public static OrderResponseDto toOrderResponseDtoFromOrder(Order order) {

        return new OrderResponseDto(
            order.getId(),
            CustomerMapper.customerToResponseCustomerDto(order.getCustomer()),
            order.getCreatedAt(),
            order.getOrderItems().stream().map(OrderItemMapper::orderItemRequestDtoToOrderItem).toList()
        ) ;
    }


}
