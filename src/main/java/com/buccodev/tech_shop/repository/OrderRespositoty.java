package com.buccodev.tech_shop.repository;

import com.buccodev.tech_shop.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRespositoty extends JpaRepository<Order, Long> {
}
