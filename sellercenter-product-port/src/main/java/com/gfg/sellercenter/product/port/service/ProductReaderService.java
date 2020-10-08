package com.gfg.sellercenter.product.port.service;

import java.net.URL;
import java.util.*;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
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

    private static final String API_PATH_PRODUCTS_BY_IDS = "/api/products/v1";
    private static final String API_PATH_SEARCH_PRODUCTS_OF_SELLER = "/api/sellers/v1";

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
        String requestUrl = hostUrl + API_PATH_PRODUCTS_BY_IDS + "/" + productId;

        return deserialize(jsonReader.readOne(requestUrl));
    }

    @Override
    public Map<Integer, Product> getProducts(int[] productIds) throws IOException {
        if (productIds.length == 0) {
            throw new IllegalArgumentException("Product ids can not be empty");
        }

        Map<Integer, Product> result = new HashMap<>();
        String requestUrl = hostUrl + API_PATH_PRODUCTS_BY_IDS + "?product_ids=" + (new JSONArray(productIds)).toString();
        JsonParser parser = productMapper.getFactory().createParser(new URL(requestUrl));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            throw new IOException("Not a valid response, missing START_ARRAY");
        }
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            Product product = productMapper.readValue(parser, Product.class);
            result.put(product.getId(), product);
        }
        parser.close();

        return result;
    }

    @Override
    public Map<Integer, Product> searchProducts(
            int sellerId,
            List<String> statuses,
            List<Integer> shipmentTypes,
            boolean onlySynced
    ) throws IOException {
        Map<Integer, Product> result = new HashMap<>();
        String statusesRequestParameter = (new JSONArray(statuses)).toString();
        String shipmentTypesParameter = (new JSONArray(shipmentTypes)).toString();
        int onlySyncedParameter = onlySynced ? 1 : 0;
        String query = String.format(
                "%d/products?status=%s&shipment_types=%s&only_synced=%d",
                sellerId,
                statusesRequestParameter,
                shipmentTypesParameter,
                onlySyncedParameter
        );
        String requestUrl = hostUrl + API_PATH_SEARCH_PRODUCTS_OF_SELLER + "/" + query;
        JsonParser parser = productMapper.getFactory().createParser(new URL(requestUrl));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            throw new IOException("Not a valid response, missing START_ARRAY");
        }
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            Product product = productMapper.readValue(parser, Product.class);
            result.put(product.getId(), product);
        }
        parser.close();

        return result;
    }

    public static ProductReaderService getInstance(String hostUrl) {
        return new ProductReaderService(hostUrl, new JsonHttpReader());
    }

    private Product deserialize(JSONObject json) throws JsonProcessingException {
        return productMapper.readValue(json.toString(), Product.class);
    }
}
