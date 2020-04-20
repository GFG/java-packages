package com.gfg.sellercenter.product.port;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductReaderService {
    public HashMap<Integer, Product> getProducts(List<Integer> productIds) throws IOException {
        HashMap<Integer, Product> result = new HashMap<Integer, Product>();
        ObjectMapper mapper = new ObjectMapper();
        URL url = new URL("http://localhost:8081/api/products/v1?product_ids=[" + productIds.stream().map(String::valueOf).collect(Collectors.joining(",")) + "]");
        Map<String, Object>[] map = mapper.readValue(url, Map[].class);

        for (Map<String, Object> elem : map) {

        }

        return result;
    }
}
