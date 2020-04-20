package com.gfg.sellercenter.product.port.service;

import java.util.*;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.gfg.sellercenter.product.port.entity.Product;
import com.gfg.sellercenter.product.port.infra.JsonHttpReader;

@AllArgsConstructor
public class ProductReaderService implements ProductReaderServiceInterface {
    @NotNull
    @NotEmpty
    private final String hostUrl;

    @NotNull
    private final JsonHttpReader jsonReader;

    private static final String API_PATH = "/api/products/v1";

    private static final ObjectMapper productMapper;

    static {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        mapper.registerModule(module);

        productMapper = mapper;
    }

    @Override
    public Product getById(int productId) throws IOException {
        String requestUrl = hostUrl + API_PATH + "/" + productId;

        return deserialize(jsonReader.readOne(requestUrl));
    }

    @Override
    public Map<Integer, Product> getProducts(int[] productIds) throws IOException {
        if (productIds.length == 0) {
            throw new IllegalArgumentException("Product ids can not be empty");
        }

        String requestUrl = hostUrl + API_PATH + "?product_ids=[" + joinIds(productIds) + "]";

        JSONArray products = jsonReader.readAll(requestUrl);

        Map<Integer, Product> result = new HashMap<>();

        products.forEach(rawProduct -> {
            Product product = null;

            try {
                product = deserialize((JSONObject) rawProduct);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Unable to retrieve products", e);
            }

            result.put(product.getId(), product);
        });

        return result;
    }

    private Product deserialize(JSONObject json) throws JsonProcessingException {
        return productMapper.readValue(json.toString(), Product.class);
    }

    private String joinIds(int[] productIds) {
        StringJoiner intStringJoiner = new StringJoiner(",");
        Arrays.stream(productIds)
                .mapToObj(String::valueOf)
                .forEach(intStringJoiner::add);
        return intStringJoiner.toString();
    }
}
