package com.ohsin.ordermgr.service;

import com.ohsin.ordermgr.domain.Order;
import com.ohsin.ordermgr.domain.Product;
import com.ohsin.ordermgr.domain.Stock;
import com.ohsin.ordermgr.dto.CreateOrderRequest;
import com.ohsin.ordermgr.dto.OrderResponse;
import com.ohsin.ordermgr.exception.OutOfStockException;
import com.ohsin.ordermgr.repository.OrderRepository;
import com.ohsin.ordermgr.repository.ProductRepository;
import com.ohsin.ordermgr.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    @DisplayName("BVA: 재고가 1개이고 주문 수량이 1개면 주문에 성공한다")
    void placeOrder_success_whenStockIsOne() {
        Long productId = 1L;
        CreateOrderRequest request = new CreateOrderRequest(productId, 1);

        Product product = new Product("테스트 상품", new BigDecimal("10000.00"));
        ReflectionTestUtils.setField(product, "id", productId);
        Stock stock = new Stock(product, 1);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(stockRepository.findByProductIdForUpdate(productId)).thenReturn(Optional.of(stock));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            ReflectionTestUtils.setField(order, "id", 101L);
            return order;
        });

        OrderResponse response = orderService.placeOrder(request);

        assertEquals(0, stock.getQuantity());
        assertEquals(101L, response.orderId());
        assertEquals(productId, response.productId());
        assertEquals(1, response.quantity());
        assertEquals(0, response.totalPrice().compareTo(new BigDecimal("10000.00")));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    @DisplayName("BVA: 재고가 0개이고 주문 수량이 1개면 OutOfStockException을 던진다")
    void placeOrder_fail_whenStockIsZero() {
        Long productId = 1L;
        CreateOrderRequest request = new CreateOrderRequest(productId, 1);

        Product product = new Product("테스트 상품", new BigDecimal("10000.00"));
        ReflectionTestUtils.setField(product, "id", productId);
        Stock stock = new Stock(product, 0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(stockRepository.findByProductIdForUpdate(productId)).thenReturn(Optional.of(stock));

        assertThrows(OutOfStockException.class, () -> orderService.placeOrder(request));
        assertEquals(0, stock.getQuantity());
        verify(orderRepository, never()).save(any(Order.class));
    }
}
