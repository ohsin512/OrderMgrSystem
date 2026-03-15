package com.ohsin.ordermgr.service;

import com.ohsin.ordermgr.dto.CreateOrderRequest;
import com.ohsin.ordermgr.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(CreateOrderRequest request);

    OrderResponse getOrder(Long orderId);
}