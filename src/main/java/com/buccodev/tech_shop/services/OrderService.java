package com.buccodev.tech_shop.services;

import com.buccodev.tech_shop.entities.Order;
import com.buccodev.tech_shop.entities.OrderItem;
import com.buccodev.tech_shop.entities.Product;
import com.buccodev.tech_shop.exceptions.ResourceNotFoundException;
import com.buccodev.tech_shop.repository.CustomerRepository;
import com.buccodev.tech_shop.repository.OrderRepository;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderRequestDto;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderResponseDto;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemResponseDto;
import com.buccodev.tech_shop.utils.mappers.OrderMapper;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemsService orderItemService;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderItemsService orderItemService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemService = orderItemService;
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestOrderDto) {

        var customer = customerRepository.findById(requestOrderDto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        var order = new Order(null, customer, requestOrderDto.createdAt());

        var listOrderItems = requestOrderDto.orderItems().stream()
                .map(items -> orderItemService.createOrderItem(items, order))
                .toList();

        order.addOrderItem(listOrderItems);

        orderRepository.save(order);

        return OrderMapper.toOrderResponseDtoFromOrder(order);

    }

    public List<OrderResponseDto> findAllOrdersByCustomerId(Long customerId, Integer page, Integer size) {
        if (page == null || page < 0) {
            page = 0;
        }
        if (size == null || size < 1) {
            size = 10;
        }

        Pageable pageRequest = PageRequest.of(page, size);

        var customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return orderRepository.findByCustomerId(customer.getId(), pageRequest).stream()
                .map(OrderMapper::toOrderResponseDtoFromOrder).toList();

    }

    public OrderResponseDto getOrderById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return OrderMapper.toOrderResponseDtoFromOrder(order);
    }

    public void deleteOrderById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.deleteById(order.getId());
    }


    public List<OrderItemResponseDto> findAllOrderItemsByOrderId(Long id){
        return orderItemService.findOrderItemsByOrderId(id);
    }

    @Transactional
    public void updateOrder(Long id, List<OrderItemRequestDto> requestOrderDto) {
        var order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));


        var listOrderItems = requestOrderDto.stream()
                .map(items -> orderItemService.createOrderItem(items, order))
                .toList();

        order.getOrderItems().clear();

        order.addOrderItem(listOrderItems);

        orderRepository.save(order);
    }
    
}
