package com.gfg.sellercenter.product.port.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.gfg.sellercenter.product.port.entity.Status;
import org.apache.http.client.utils.URIBuilder;
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
    @NotEmpty
    private final String scheme;

    @NotNull
    private final JsonHttpReader jsonReader;

    private static final String API_PATH_PRODUCTS_BY_IDS = "/api/products/v1";
    private static final String API_PATH_SEARCH_PRODUCTS_OF_SELLER = "/api/sellers/v1";
    private static final String API_PATH_BY_SKU_SELLER = "/api/products-by-seller-skus/v1";

    private static final ObjectMapper productMapper;

    static {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        mapper.registerModule(module);

        productMapper = mapper;
    }

    @Override
    public Product getById(int productId) throws IOException, URISyntaxException {
        URI uri = this.getURIBuilder(API_PATH_PRODUCTS_BY_IDS +"/" + productId).build();

        return deserialize(jsonReader.readOne(uri.toURL().toString()));
    }

    @Override
    public Map<Integer, Product> getProducts(int[] productIds) throws IOException, URISyntaxException {
        if (productIds.length == 0) {
            throw new IllegalArgumentException("Product ids can not be empty");
        }

        Map<Integer, Product> result = new HashMap<>();

        URIBuilder uriBuilder = this.getURIBuilder(API_PATH_PRODUCTS_BY_IDS);

        return this.getProductMap(
                result,
                uriBuilder
                        .addParameter("product_ids", (new JSONArray(productIds)).toString())
                        .build()
                        .toURL()
                        .toString()
        );
    }

    public Map<String, Product> getProducts(List<String> sellerSkus,int sellerId) throws URISyntaxException, IOException {
        if (sellerSkus.size() == 0) {
            throw new IllegalArgumentException("Product seller skus can´t be empty");
        }

        JSONArray jsArray = new JSONArray(sellerSkus);

        Map<String, Product> productMap = new HashMap<>();

        URI uri = this.getURIBuilder(API_PATH_BY_SKU_SELLER)
                .addParameter("product_seller_skus", jsArray.toString())
                .addParameter("seller_id", String.valueOf(sellerId)).build();

        return this.getProductSkuMap(productMap, uri.toURL().toString());
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

    private Map<String, Product> getProductSkuMap(Map<String, Product> result, String requestUrl) throws IOException {
        JsonParser parser = productMapper.getFactory().createParser(new URL(requestUrl));

        if (parser.nextToken() != JsonToken.START_ARRAY) {
            throw new IOException("Not a valid response, missing START_ARRAY");
        }
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            Product product = productMapper.readValue(parser, Product.class);
            result.put(product.getSellerSku(), product);
        }
        parser.close();

        return result;
    }

    public Map<Integer, Product> getProducts(int[] productIds, int sellerId) throws IOException, URISyntaxException {
        if (productIds.length == 0) {
            throw new IllegalArgumentException("Product ids can not be empty");
        }

        Map<Integer, Product> result = new HashMap<>();

        URIBuilder uriBuilder = this.getURIBuilder(API_PATH_PRODUCTS_BY_IDS);
        uriBuilder.addParameter("product_ids", (new JSONArray(productIds)).toString())
                .addParameter("seller_id", String.valueOf(sellerId));
        return this.getProductMap(result, uriBuilder.build().toURL().toString());
    }

    @Override
    public Map<Integer, Product> searchProducts(
            int sellerId,
            List<Status> statuses,
            List<Integer> shipmentTypes,
            boolean onlySynced
    ) throws IOException, URISyntaxException {
        Map<Integer, Product> result = new HashMap<>();
        String statusesRequestParameter = (new JSONArray(statuses.stream().map(Status::getStatus).collect(Collectors.toList()))).toString();
        String shipmentTypesParameter = (new JSONArray(shipmentTypes)).toString();
        int onlySyncedParameter = onlySynced ? 1 : 0;

        String path = String.format(
                "%s/%d/products",
                API_PATH_SEARCH_PRODUCTS_OF_SELLER,
                sellerId
        );
        URIBuilder uriBuilder = this.getURIBuilder(path);
        uriBuilder
                .addParameter("status", statusesRequestParameter)
                .addParameter("shipment_types", shipmentTypesParameter)
                .addParameter("only_synced", String.valueOf(onlySyncedParameter));
        return this.getProductMap(result, uriBuilder.build().toURL().toString());
    }

    @Override
    public Map<Integer, Product> searchSyncedProducts(int sellerId, List<Status> statuses, List<Integer> shipmentTypes) throws IOException, URISyntaxException {
        return searchProducts(sellerId, statuses, shipmentTypes, true);
    }

    public static ProductReaderService getInstance(String hostUrl, String urlSchema) {
        return new ProductReaderService(hostUrl, urlSchema, new JsonHttpReader());
    }

    private Product deserialize(JSONObject json) throws JsonProcessingException {
        return productMapper.readValue(json.toString(), Product.class);
    }

    private URIBuilder getURIBuilder(String path) {
        return new URIBuilder()
                .setScheme(this.scheme)
                .setHost(this.hostUrl)
                .setPath(path);
    }
}
