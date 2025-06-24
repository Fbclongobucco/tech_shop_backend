package com.buccodev.tech_shop.utils.mappers;

import com.buccodev.tech_shop.entities.Order;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderResponseDto;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class OrderMapper {

    public static OrderResponseDto toOrderResponseDtoFromOrder(Order order) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return new OrderResponseDto(
            order.getId(),
            CustomerMapper.toResponseCustomerDtoToOrder(order.getCustomer()),
            order.getCreatedAt().format(formatter),
            order.getOrderItems().stream().map(OrderItemMapper::toOrderItem).toList(),
            order.getTotalValue(),
            AddressMapper.toAddressResponseDto(order.getAddress()),
            order.getStatus()
        ) ;
    }


}
