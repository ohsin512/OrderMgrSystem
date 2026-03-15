package com.ohsin.ordermgr.service;

import com.ohsin.ordermgr.dto.ProductListItem;
import com.ohsin.ordermgr.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductListItem> getProductList() {
        return productRepository.findAll()
                .stream()
                .map(ProductListItem::from)
                .toList();
    }
}
