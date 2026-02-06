package com.example.sparta.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 주문 PK

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();   // 주문 상세 리스트

    @Column(name = "total_price")
    private Long totalPrice;   // 총 주문 금액

    @Column
    private LocalDateTime createdAt = LocalDateTime.now();

    public Order(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

		// OrderLine 과 양방향 매핑
    public void addOrderline(OrderLine orderLine) {
        this.orderLines.add(orderLine);
    }
}