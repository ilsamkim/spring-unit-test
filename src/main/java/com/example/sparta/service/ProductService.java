package com.example.sparta.service;

import com.example.sparta.entity.Order;
import com.example.sparta.entity.Product;
import com.example.sparta.repository.OrderRepository;
import com.example.sparta.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    // 최근 일주일동안 가장 많이 팔린 상품 탑 쓰리 가져오기.
    public List<Product> findHit3Products() {
        // 조회 시작 기간 : 일주일 전
        LocalDateTime from = LocalDateTime.now().minusDays(7);

        // 최근 일주일 간 발생한 주문
        List<Order> orderList = orderRepository.findByCreatedAtIsAfter(from);

        // 상품 판매 횟수 맵 -> Key: 상품, Value: 해당 상품의 판매 횟수
        Map<Product, Long> saleCountMap = new HashMap<>();

        // order.OrderLine 을 확인하여 상품과 해당 상품의 주문량을 saleCountMap에 합산
        orderList.forEach(
                order -> order.getOrderLines().forEach(
                        ol -> saleCountMap.put(
                                ol.getProduct(),
                                saleCountMap.getOrDefault(ol.getProduct(), 0L) + ol.getAmount()
                        )
                )
        );

        return saleCountMap.entrySet().stream()  				// saleCountMap 에서
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))   // Value 로 내림차순
                .limit(3)    // 그중 1, 2, 3번째를 가져옴 (탑 쓰리)
                .map(Map.Entry::getKey)   // 해당하는 맵의 Key 만 가져옴 (Product)
                .toList();    // 리스트로 변환 (List<Product>)
    }
}