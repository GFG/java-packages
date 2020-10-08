package com.gfg.sellercenter.product.port.service;

import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

import com.gfg.sellercenter.product.port.entity.Product;
import com.gfg.sellercenter.product.port.infra.JsonHttpReader;
import com.gfg.sellercenter.product.port.entity.Money;
import com.gfg.sellercenter.product.port.entity.Price;
import com.gfg.sellercenter.product.port.entity.SpecialPrice;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

public class ProductReaderServiceTest {
    private static final String RESOURCES_PATH = "src/test/resources/json/";
    private static final int MOCK_SERVER_POST = 1080;

    private static final Product product1;
    private static final Product product2;

    @BeforeClass
    public static void startServer() {
        ClientAndServer mockServer = startClientAndServer(MOCK_SERVER_POST);
    }

    static {
        product1 = new Product(
                1,
                UUID.fromString("979ab6ff-2f7f-11e9-a621-0afefbb27832"),
                "714096",
                "NU449AA30AEF-714096",
                new Price(
                        new Money(2090.00, "AUD"),
                        new SpecialPrice(null, null, null)
                ),
                "KLIO9_DELETED_2018-01-31_01-53-19",
                "Dani super dress 2",
                null,
                "3X"
        );
        product2 = new Product(
                2,
                UUID.fromString("979ab9cc-2f7f-11e9-a621-0afefbb27832"),
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
                20.22,
                "XXL/38"
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

    @Test
    public void searchProductsFormsCorrectUrl() throws IOException {
        new MockServerClient("localhost", MOCK_SERVER_POST)
                .when(
                        HttpRequest.request()
                                .withMethod("GET")
                                .withPath("/api/sellers/v1/1/products")
                                .withQueryStringParameter("status", "[\"active\",\"inactive\"]")
                                .withQueryStringParameter("shipment_types", "[1,5]")
                                .withQueryStringParameter("only_synced", "1")
                        ,
                        Times.exactly(1)
                )
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json")
                                )
                                .withBody(readFile("product-collection.json"))
                );

        JsonHttpReader jsonReaderMock = mock(JsonHttpReader.class);

        ProductReaderService service = new ProductReaderService(
                "http://localhost:" + MOCK_SERVER_POST,
                jsonReaderMock
        );

        Map<Integer, Product> result = service.searchProducts(
                1,
                Arrays.asList("active","inactive"),
                Arrays.asList(1, 5),
                true
        );
        assertEquals(2, result.size());
    }

    @Test
    public void searchProductsFormsCorrectUrlWhenParametersAreEmpty() throws IOException {
        new MockServerClient("localhost", MOCK_SERVER_POST)
                .when(
                        HttpRequest.request()
                                .withMethod("GET")
                                .withPath("/api/sellers/v1/123/products")
                                .withQueryStringParameter("status", "[]")
                                .withQueryStringParameter("shipment_types", "[]")
                                .withQueryStringParameter("only_synced", "0")
                        ,
                        Times.exactly(1)
                )
                .respond(
                        HttpResponse.response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json")
                                )
                                .withBody(readFile("product-collection.json"))
                );

        JsonHttpReader jsonReaderMock = mock(JsonHttpReader.class);

        ProductReaderService service = new ProductReaderService(
                "http://localhost:" + MOCK_SERVER_POST,
                jsonReaderMock
        );

        Map<Integer, Product> result = service.searchProducts(
                123,
                Collections.emptyList(),
                Collections.emptyList(),
                false
        );
        assertEquals(2, result.size());
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
