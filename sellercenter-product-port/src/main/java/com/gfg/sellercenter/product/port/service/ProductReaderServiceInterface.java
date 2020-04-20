package com.gfg.sellercenter.product.port.service;

import com.gfg.sellercenter.product.port.entity.Product;

import java.util.List;
import java.util.Map;
import java.io.IOException;

interface ProductReaderServiceInterface {
    public Product getById(int productId) throws IOException;

    public Map<Integer, Product> getProducts(int[] productIds) throws IOException;
}
