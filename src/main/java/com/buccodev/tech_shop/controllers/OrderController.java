package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.OrderService;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderRequestDto;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderResponseDto;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto requestOrderDto) {
        var order = orderService.createOrder(requestOrderDto);
        URI uri = URI.create("/orders/" + order.id() );
        return ResponseEntity.created(uri).body(order);
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC', 'CUSTOMER')")
    @GetMapping("/all-orders/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByCustomerId(@PathVariable Long customerId,
                                                                           @RequestParam(required = false) Integer page,
                                                                           @RequestParam(required = false) Integer size,
                                                                           Authentication authentication) {
        var orders = orderService.findAllOrdersByCustomerId(customerId, authentication, page, size);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasAnyRole('ADMIN','BASIC', 'CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id, Authentication authentication) {
        var order = orderService.getOrderById(id, authentication);
        return ResponseEntity.ok(order);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id, Authentication authentication) {
        orderService.deleteOrderById(id, authentication);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody List<OrderItemRequestDto> requestOrderItemsDto,
                                            Authentication authentication) {
        orderService.updateOrder(id, requestOrderItemsDto, authentication);
        return ResponseEntity.noContent().build();
    }

}
