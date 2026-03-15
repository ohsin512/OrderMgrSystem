package com.ohsin.ordermgr.config;

import com.ohsin.ordermgr.domain.Product;
import com.ohsin.ordermgr.domain.Stock;
import com.ohsin.ordermgr.repository.ProductRepository;
import com.ohsin.ordermgr.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public DataInitializer(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        Product keyboard = productRepository.save(new Product("기계식 키보드", new BigDecimal("89000.00")));
        Product mouse = productRepository.save(new Product("무선 마우스", new BigDecimal("45000.00")));
        Product monitor = productRepository.save(new Product("27인치 모니터", new BigDecimal("289000.00")));

        stockRepository.save(new Stock(keyboard, 10));
        stockRepository.save(new Stock(mouse, 8));
        stockRepository.save(new Stock(monitor, 5));
    }
}
