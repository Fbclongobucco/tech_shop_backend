package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRespository extends JpaRepository<OrderItem, Long> {
}
