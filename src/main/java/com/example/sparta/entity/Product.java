package com.example.sparta.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 상품 PK

    @Column(name = "name")
    private String name;   // 상품명

    @Column(name = "description")
    private String description;   // 상품 설명

    @Column(name = "price")
    private Long price;   // 상품 가격

    @Column(name = "amount")
    private Long amount;  // 잔여 상품 개수 (재고)

    @Column(name = "sale_count")
    private Long saleCount;  // 판매 횟수

    // 상품 구매 시 재고 Down, 판매 횟수 Up
    public void purchased(Long amount) {
        if (this.amount < amount) {
            throw new RuntimeException(this.name + "의 상품 재고가 부족합니다.");
        }
        this.amount -= amount;
        this.saleCount += amount;
    }
}