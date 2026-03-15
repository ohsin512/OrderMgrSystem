package com.ohsin.ordermgr.controller;

import com.ohsin.ordermgr.service.ProductQueryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final ProductQueryService productQueryService;

    public ViewController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productQueryService.getProductList());
        return "index";
    }
}
