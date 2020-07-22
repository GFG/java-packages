package com.gfg.sellercenter.product.port.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.HashMap;

import com.gfg.sellercenter.product.port.entity.Product;
import com.gfg.sellercenter.product.port.infra.JsonHttpReader;
import com.gfg.sellercenter.product.port.entity.Money;
import com.gfg.sellercenter.product.port.entity.Price;
import com.gfg.sellercenter.product.port.entity.SpecialPrice;

public class ProductReaderServiceTest {
    private static final String RESOURCES_PATH = "src/test/resources/json/";

    private static final Product product1;
    private static final Product product2;

    static {
        product1 = new Product(
                1,
                "714096",
                "NU449AA30AEF-714096",
                new Price(
                        new Money(2090.00, "AUD"),
                        new SpecialPrice(null, null, null)
                ),
                "KLIO9_DELETED_2018-01-31_01-53-19",
                "Dani super dress 2",
                null
        );
        product2 = new Product(
                2,
                null,
                null,
                new Price(
                        new Money(10.00, "USD"),
                        new SpecialPrice(
                                4.99,
                                ZonedDateTime.parse("2018-03-14T00:00:00Z"),
                                ZonedDateTime.parse("2018-03-18T00:00:00Z")
                        )
                ),
                "DSS1",
                "Dani Super Skirt",
                20.22
        );
    }

    @Test
    public void shouldReadProduct() throws IOException {
        JsonHttpReader jsonReaderMock = mock(JsonHttpReader.class);
        when(jsonReaderMock.readOne("http://localhost:8081/api/products/v1/1"))
                .thenReturn(
                        new JSONObject(readFile("single-product.json"))
                );

        ProductReaderService service = new ProductReaderService(
                "http://localhost:8081",
                jsonReaderMock
        );

        assertEquals(product1, service.getById(1));
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + fileName)));
    }

    @Test
    public void getInstance() {
        ProductReaderService productReaderService = ProductReaderService.getInstance("some host url");
        assertEquals(ProductReaderService.class, productReaderService.getClass());
    }
}
