package com.gfg.sellercenter.category;

import com.gfg.sellercenter.category.entity.CategoryWithProductInformation;
import com.gfg.sellercenter.category.reader.HttpReader;
import com.gfg.sellercenter.category.service.CategoryService;
import org.json.JSONArray;
import static org.junit.Assert.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SellerCenterReaderTest {

    private static final String RESOURCES_PATH = "src/test/resources/";

    @Test
    public void responseWithData() throws Exception {

        Map<Integer,CategoryWithProductInformation> expectedCategories = new HashMap<>();

        expectedCategories.put(
            2,
            new CategoryWithProductInformation(
                1543,
                2,
                "Smart Home Theatre Systems",
                "Smart Home Theatre Systems en",
                    true
            )
        );
        expectedCategories.put(
            1,
            new CategoryWithProductInformation(
                2376,
                1,
                "ndchnenatc",
                "ndchnenatc en",
                false
            )
        );

        List<Integer> productIds = new ArrayList<>();

        productIds.add(1);
        productIds.add(2);
        productIds.add(3);
        productIds.add(3);

        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(
                readerMock.getProductCategoriesByProductIds(new ArrayList<>(Arrays.asList(1,2,3))))
            .thenReturn(
                getFileContent("json/httpReaderFullResponse.json")
            );

        CategoryService service = new CategoryService(readerMock);

        Map<Integer,CategoryWithProductInformation> categories = service.getProductCategoriesByProductsIds(productIds);

        assertEquals(expectedCategories, categories);
    }

    @Test
    public void emptyResponse() throws Exception {

        Map<Integer,CategoryWithProductInformation> expectedCategories = new HashMap<>();
        List<Integer> productIds = new ArrayList<>();

        productIds.add(1);
        productIds.add(2);
        productIds.add(3);

        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(readerMock.getProductCategoriesByProductIds(productIds))
                .thenReturn(
                        getFileContent("json/httpReaderEmptyResponse.json")
                );

        CategoryService service = new CategoryService(readerMock);

        Map<Integer,CategoryWithProductInformation> categories = service.getProductCategoriesByProductsIds(productIds);

        assertEquals(expectedCategories, categories);
    }

    private JSONArray getFileContent(String filename) throws IOException {
        return new JSONArray(
            new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filename)))
        );
    }
}
