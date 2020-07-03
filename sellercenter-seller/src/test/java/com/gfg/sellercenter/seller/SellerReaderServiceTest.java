package com.gfg.sellercenter.seller;

import com.gfg.sellercenter.seller.infra.HttpReader;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SellerReaderServiceTest {

    private static final String RESOURCES_PATH = "src/test/resources/";

    @Test
    public void getByIds() throws IOException {
        HttpReader jsonReaderMock = mock(HttpReader.class);
        when(jsonReaderMock.readRemote("http://somehost:8081/api/sellers/v1?seller_ids=[1,2]"))
                .thenReturn(
                        readFile("json/responseWithSomeSellers.json")
                );

        SellerReaderService service = new SellerReaderService(
                "http://somehost:8081",
                jsonReaderMock
        );

        Seller seller1 = getSeller1();
        Seller seller2 = getSeller2();

        Map<Integer, Seller> result = service.getByIds(new ArrayList<>(Arrays.asList(1,2)));
        assertEquals(seller1, result.get(1));
        assertEquals(seller2, result.get(2));
    }

    @Test
    public void getById() throws IOException {
        HttpReader jsonReaderMock = mock(HttpReader.class);
        when(jsonReaderMock.readRemote("http://somehost:8081/api/sellers/v1/1"))
                .thenReturn(
                        readFile("json/responseSingleSeller.json")
                );

        SellerReaderService service = new SellerReaderService(
                "http://somehost:8081",
                jsonReaderMock
        );

        Seller result = service.getById(1);
        Seller expectedResult = getSeller1();
        assertEquals(expectedResult, result);
    }

    private Seller getSeller1() {
        return new Seller(1, "seller-1@mailinator.com", "Midcom", "Midcom", "NG1003C", "117");
    }

    private Seller getSeller2() {
        return new Seller(2, null, "Comércio Digital", null, "NG1003X", "137");
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + fileName)));
    }
}
