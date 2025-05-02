package com.buccodev.tech_shop.controllers;

import com.buccodev.tech_shop.services.OrderService;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderRequestDto;
import com.buccodev.tech_shop.utils.dtos.order_dtos.OrderResponseDto;
import com.buccodev.tech_shop.utils.dtos.order_items_dtos.OrderItemRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tech-shop/orders")
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

    @GetMapping("/all-orders/{customerId}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrdersByCustomerId(@PathVariable Long customerId,
                                                                           @RequestParam(required = false) Integer page,
                                                                           @RequestParam(required = false) Integer size) {
        var orders = orderService.findAllOrdersByCustomerId(customerId, page, size);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long id) {
        var order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateOrder(@PathVariable Long id, @RequestBody List<OrderItemRequestDto> requestOrderItemsDto) {
        orderService.updateOrder(id, requestOrderItemsDto);
        return ResponseEntity.noContent().build();
    }

}
