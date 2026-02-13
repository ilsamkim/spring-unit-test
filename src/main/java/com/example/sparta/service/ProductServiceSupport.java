package com.example.sparta.service;

import com.example.sparta.entity.Order;
import com.example.sparta.entity.Product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceSupport {

    public static List<Product> extractFromOrderList(List<Order> orderList) {
        Map<Product, Long> saleCountMap = new HashMap<>();

        orderList.forEach(
                order -> order.getOrderLines().forEach(
                        ol -> saleCountMap.put(
                                ol.getProduct(),
                                saleCountMap.getOrDefault(ol.getProduct(), 0L) + ol.getAmount()
                        )
                )
        );

        return saleCountMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }
}
