package com.example.sparta.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderCreateRequest {
    private Long totalPrice;
    private List<OrderLineRequest> orderLines;
}
