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
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderItemsService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    public OrderItemsService(OrderItemRepository orderItemRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderItem createOrderItem(OrderItemRequestDto orderItemRequestDto, Order order) {

        var product = productRepository.findById(orderItemRequestDto
                .productId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        var orderItem = new OrderItem(null, order, product, orderItemRequestDto.quantity());

        return orderItemRepository.save(orderItem);
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

    @Transactional
    public void updateOrderItem(Long orderId, List<OrderItemRequestDto> orderItemRequestDto) {

       var order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));

       orderItemRequestDto.forEach(orderItem -> {
           var product = productRepository.findById(orderItem.productId()).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
           orderItemRepository.save(new OrderItem(null, order, product, orderItem.quantity()));
       });
    }

}
