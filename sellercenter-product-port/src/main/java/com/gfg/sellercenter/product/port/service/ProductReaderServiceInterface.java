package com.gfg.sellercenter.product.port.service;

import com.gfg.sellercenter.product.port.entity.Product;
import com.gfg.sellercenter.product.port.entity.Status;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.io.IOException;

public interface ProductReaderServiceInterface {
    Product getById(int productId) throws IOException, URISyntaxException;

    Map<Integer, Product> getProducts(int[] productIds) throws IOException, URISyntaxException;

    Map<Integer, Product> getProducts(int[] productIds, int sellerId) throws IOException;

    Map<String, Product> getProducts(List<String> sellerSkus,int sellerId) throws URISyntaxException, IOException;

    Map<Integer, Product> searchProducts(
            int sellerId,
            List<Status> statuses,
            List<Integer> shipmentTypes,
            boolean onlySynced
    ) throws IOException, URISyntaxException;

    Map<Integer, Product> searchSyncedProducts(
            int sellerId,
            List<Status> statuses,
            List<Integer> shipmentTypes
    ) throws IOException, URISyntaxException;
}
