package com.ohsin.ordermgr.service;

import com.ohsin.ordermgr.dto.ProductListItem;

import java.util.List;

public interface ProductQueryService {

    List<ProductListItem> getProductList();
}
