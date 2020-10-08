package com.gfg.sellercenter.product.port.service;

import com.gfg.sellercenter.product.port.entity.Product;

import java.util.List;
import java.util.Map;
import java.io.IOException;

public interface ProductReaderServiceInterface {
    public Product getById(int productId) throws IOException;

    public Map<Integer, Product> getProducts(int[] productIds) throws IOException;

    public Map<Integer, Product> searchProducts(
            int sellerId,
            List<String> statuses,
            List<Integer> shipmentTypes,
            boolean onlySynced
    ) throws IOException;
}
