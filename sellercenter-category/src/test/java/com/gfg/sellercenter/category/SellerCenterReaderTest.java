package com.gfg.sellercenter.category;

import com.gfg.sellercenter.category.entity.CategoryDetails;
import com.gfg.sellercenter.category.entity.CategoryFulfillmentSetting;
import com.gfg.sellercenter.category.entity.CategoryStatus;
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

    @Test
    public void getAllCategoriesDetailsWithData() throws IOException {
        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(readerMock.getAllCategoriesDetails())
                .thenReturn(
                        getFileContent("json/all-categories.json")
                );

        CategoryService service = new CategoryService(readerMock);

        Map<Integer, CategoryDetails> categories = service.getAllCategoriesDetails();

        // check root category
        assertEquals(1, categories.get(1).getId());
        assertEquals("Root Category", categories.get(1).getName());
        assertEquals("1", categories.get(1).getSrcId());
        assertTrue(categories.get(1).isFulfillmentVisible());

        // check some normal category
        assertEquals(CategoryStatus.ACTIVE, categories.get(1673).getStatus());
        assertEquals("1673", categories.get(1673).getSrcId());
        assertEquals(4021, categories.get(1673).getLft());
        assertEquals(4056, categories.get(1673).getRgt());
        assertFalse(categories.get(1673).isFulfillmentVisible());

        // check not fulfillment visible category
        assertEquals(CategoryStatus.ACTIVE, categories.get(1).getStatus());
        assertFalse(categories.get(1525).isFulfillmentVisible());

        // check not synced category
        assertEquals(CategoryStatus.DELETED, categories.get(1672).getStatus());
        assertEquals(4019, categories.get(1672).getLft());
        assertEquals(4020, categories.get(1672).getRgt());
        assertNull(categories.get(1672).getSrcId());
    }

    @Test
    public void getCategoriesFulfillmentSettingWithEmptyData() throws IOException {
        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(readerMock.getAllCategoriesDetails())
                .thenReturn(
                        getFileContent("json/httpReaderEmptyResponse.json")
                );

        CategoryService service = new CategoryService(readerMock);
        Map<Integer, CategoryFulfillmentSetting> result = service.getCategoriesFulfillmentSetting(123);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getCategoriesFulfillmentSettingWithData() throws IOException {
        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(readerMock.getCategoriesFulfillmentSetting(123))
                .thenReturn(
                        getFileContent("json/fulfilmentCategoriesSettings.json")
                );

        CategoryService service = new CategoryService(readerMock);
        Map<Integer, CategoryFulfillmentSetting> result = service.getCategoriesFulfillmentSetting(123);

        assertEquals(3, result.size());
        assertTrue(result.get(1771).isFulfillmentEnabled());
        assertFalse(result.get(567).isFulfillmentEnabled());
    }

    private JSONArray getFileContent(String filename) throws IOException {
        return new JSONArray(
            new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filename)))
        );
    }
}
