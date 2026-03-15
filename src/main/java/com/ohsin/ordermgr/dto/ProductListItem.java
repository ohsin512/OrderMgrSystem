package com.ohsin.ordermgr.dto;

import com.ohsin.ordermgr.domain.Product;

import java.math.BigDecimal;

public record ProductListItem(
        Long productId,
        String name,
        BigDecimal price
) {
    public static ProductListItem from(Product product) {
        return new ProductListItem(product.getId(), product.getName(), product.getPrice());
    }
}
