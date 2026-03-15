package com.ohsin.ordermgr.service;

import com.ohsin.ordermgr.domain.Order;
import com.ohsin.ordermgr.domain.Product;
import com.ohsin.ordermgr.domain.Stock;
import com.ohsin.ordermgr.dto.CreateOrderRequest;
import com.ohsin.ordermgr.dto.OrderResponse;
import com.ohsin.ordermgr.exception.OutOfStockException;
import com.ohsin.ordermgr.exception.ResourceNotFoundException;
import com.ohsin.ordermgr.repository.OrderRepository;
import com.ohsin.ordermgr.repository.ProductRepository;
import com.ohsin.ordermgr.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public OrderResponse placeOrder(CreateOrderRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("존재하지 않는 상품입니다. id=" + request.productId()));

        Stock stock = stockRepository.findByProductIdForUpdate(request.productId())
                .orElseThrow(() -> new ResourceNotFoundException("재고 정보가 존재하지 않습니다. productId=" + request.productId()));

        if (stock.getQuantity() < request.quantity()) {
            throw new OutOfStockException("재고가 부족합니다. 현재 재고=" + stock.getQuantity());
        }

        stock.decrease(request.quantity());
        Order savedOrder = orderRepository.save(Order.create(product, request.quantity()));
        return OrderResponse.from(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderResponse getOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("주문이 존재하지 않습니다. id=" + orderId));
        return OrderResponse.from(order);
    }
}