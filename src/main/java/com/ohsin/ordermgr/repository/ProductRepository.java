package com.ohsin.ordermgr.repository;

import com.ohsin.ordermgr.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}