package com.ohsin.ordermgr.repository;

import com.ohsin.ordermgr.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}