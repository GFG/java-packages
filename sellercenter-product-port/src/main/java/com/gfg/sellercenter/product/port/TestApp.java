package com.gfg.sellercenter.product.port;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gfg.sellercenter.product.port.entity.Product;


import com.gfg.sellercenter.product.port.service.ProductDeserializer;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class TestApp {

    private static final ObjectMapper productMapper;

    static {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Product.class, new ProductDeserializer());
        mapper.registerModule(module);

        productMapper = mapper;
    }


    public static void main(String[] args) throws IOException {
        String requestUrl = "http://sc.devbox.local/product-collection.json";
        Map<Integer, Product> result = new HashMap<>();
        JsonParser parser = productMapper.getFactory().createParser(new URL(requestUrl));
        if (parser.nextToken() != JsonToken.START_ARRAY) {
            throw new IOException( "Not a valid response, missing START_ARRAY" );
        }
        while (parser.nextToken() == JsonToken.START_OBJECT) {
            Product product = productMapper.readValue(parser, Product.class);
            result.put(product.getId(), product);
        }


        System.out.println(result.entrySet().size());


    }
}
