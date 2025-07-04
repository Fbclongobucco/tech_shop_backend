package com.buccodev.tech_shop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order_tb")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalValue;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(nullable = false)
    private LocalDateTime createdAt;

   @Enumerated(EnumType.STRING)
   @Column(nullable = false, columnDefinition = "VARCHAR(20)")
   private OrderStatus status;

    public Order() {}

    public Order(Long id, Customer customer, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.createdAt = createdAt;
        this.address = customer.getDefaultAddress();
        this.status = OrderStatus.PENDING;
        calculateTotalValue();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalValue() {
        calculateTotalValue();
        return totalValue;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(List<OrderItem> orderItems) {
        this.orderItems.addAll(orderItems);
        calculateTotalValue();
    }

    private void calculateTotalValue() {
        BigDecimal totalValue = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            totalValue = totalValue.add(orderItem.getTotalPrice());
        }
        this.totalValue = totalValue;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public String getStatus() {
        return status.getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
