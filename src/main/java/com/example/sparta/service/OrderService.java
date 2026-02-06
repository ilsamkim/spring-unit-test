package com.example.sparta.service;

import com.example.sparta.dto.OrderLineRequest;
import com.example.sparta.entity.Order;
import com.example.sparta.entity.OrderLine;
import com.example.sparta.entity.Product;
import com.example.sparta.repository.OrderLineRepository;
import com.example.sparta.repository.OrderRepository;
import com.example.sparta.repository.ProductRepository;
import com.example.sparta.dto.OrderCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderLineRepository orderLineRepository;


    @Transactional
    public Order create(OrderCreateRequest request) {
        // 주문 데이터 생성
        Order order = orderRepository.save(new Order(request.getTotalPrice()));

        List<OrderLine> orderLineList = new ArrayList<>();
        for (OrderLineRequest olr : request.getOrderLines()) {
            Product product = productRepository.findById(olr.getProductId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 상품은 주문할 수 없습니다 !"));

            // 상품 구매 처리
            product.purchased(olr.getAmount());

            // 주문 상세 데이터 생성
            orderLineList.add(new OrderLine(order, product, olr.getAmount()));
        }
        orderLineRepository.saveAll(orderLineList);
        return order;
    }

}