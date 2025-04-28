package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
