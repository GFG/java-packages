package com.gfg.sellercenter.product.port.service;

import java.net.URL;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.gfg.sellercenter.product.port.entity.Status;
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
        return this.getProductMap(result, requestUrl);
    }

    private Map<Integer, Product> getProductMap(Map<Integer, Product> result, String requestUrl) throws IOException {
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

    public Map<Integer, Product> getProducts(int[] productIds, int sellerId) throws IOException {
        if (productIds.length == 0) {
            throw new IllegalArgumentException("Product ids can not be empty");
        }

        Map<Integer, Product> result = new HashMap<>();
        String requestUrl =
                hostUrl +
                API_PATH_PRODUCTS_BY_IDS +
                "?product_ids=" + (new JSONArray(productIds)).toString() +
                "&seller_id=" + sellerId;
        return this.getProductMap(result, requestUrl);
    }

    @Override
    public Map<Integer, Product> searchProducts(
            int sellerId,
            List<Status> statuses,
            List<Integer> shipmentTypes,
            boolean onlySynced
    ) throws IOException {
        Map<Integer, Product> result = new HashMap<>();
        String statusesRequestParameter = (new JSONArray(statuses.stream().map(Status::getStatus).collect(Collectors.toList()))).toString();
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
        return this.getProductMap(result, requestUrl);
    }

    @Override
    public Map<Integer, Product> searchSyncedProducts(int sellerId, List<Status> statuses, List<Integer> shipmentTypes) throws IOException {
        return searchProducts(sellerId, statuses, shipmentTypes, true);
    }

    public static ProductReaderService getInstance(String hostUrl) {
        return new ProductReaderService(hostUrl, new JsonHttpReader());
    }

    private Product deserialize(JSONObject json) throws JsonProcessingException {
        return productMapper.readValue(json.toString(), Product.class);
    }
}
