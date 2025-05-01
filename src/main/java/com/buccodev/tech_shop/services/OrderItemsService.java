package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Order;
import com.buccodev.tech_shop.entities.OrderItem;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.OrderItemRepository;
import com.buccodev.tech_shop.repository.OrderRepository;
import com.buccodev.tech_shop.repository.ProductRepository;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemResponseDto;
import com.buccodev.tech_shop.utils.mappers.OrderItemMapper;
import jakarta.transaction.Transactional;

import java.util.List;

public class OrderItemsService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public OrderItemsService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public OrderItem createOrderItem(OrderItemRequestDto orderItemRequestDto, Order order) {

        var product = productRepository.findById(orderItemRequestDto
                .productId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        return new OrderItem(null, order, product, orderItemRequestDto.quantity());
    }

    public OrderItemResponseDto findOrderItemById(Long id) {
        var orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order item not found"));
        return OrderItemMapper.orderItemRequestDtoToOrderItem(orderItem);
    }

    public List<OrderItemResponseDto> findOrderItemsByOrderId(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return order.getOrderItems().stream()
                .map(OrderItemMapper::orderItemRequestDtoToOrderItem)
                .toList();
    }

}
