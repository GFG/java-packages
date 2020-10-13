package com.gfg.sellercenter.category.service;

import com.gfg.sellercenter.category.entity.CategoryDetails;
import com.gfg.sellercenter.category.entity.CategoryFulfillmentSetting;
import com.gfg.sellercenter.category.reader.HttpReader;
import org.json.JSONArray;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class CategoryFulfillmentVisibilityServiceTest {
    private static final String RESOURCES_PATH = "src/test/resources/";

    @Test
    public void getListOfFulfilmentEnabledCategoriesBySeller() throws IOException {

        HttpReader readerMock = Mockito.mock(HttpReader.class);
        Mockito.when(readerMock.getAllCategoriesDetails())
                .thenReturn(
                        getFileContent("json/all-categories.json")
                );
        Mockito.when(readerMock.getCategoriesFulfillmentSetting(1))
                .thenReturn(
                        getFileContent("json/fulfilmentCategoriesSettings.json")
                );

        CategoryService service = new CategoryService(readerMock);
        Map<Integer, CategoryFulfillmentSetting> sellerSpecificSettings = service.getCategoriesFulfillmentSetting(1);
        Map<Integer, CategoryDetails> allCategoriesDetails = service.getAllCategoriesDetails();
        CategoryFulfillmentVisibilityService categoryFulfillmentVisibilityService = new CategoryFulfillmentVisibilityService();

        List<Integer> enabledCategories = categoryFulfillmentVisibilityService.getListOfFulfilmentEnabledCategoriesBySeller(allCategoriesDetails, sellerSpecificSettings);

        // check that specifically enabled to seller category is allowed for fulfillment
        assertTrue(enabledCategories.contains(1771)); // specifically enabled category
        assertTrue(enabledCategories.contains(1772)); // child of 1771
        assertTrue(enabledCategories.contains(1773)); // child of 1771
        assertTrue(enabledCategories.contains(1774)); // child of 1771
        assertTrue(enabledCategories.contains(1775)); // child of 1771

        // check that specifically disabled to seller category is not allowed for fulfillment
        assertFalse(enabledCategories.contains(1672));
        assertFalse(enabledCategories.contains(2378)); // child of 1672
        assertFalse(enabledCategories.contains(2379)); // child of 1672

        // check globally enabled categories
        assertTrue(enabledCategories.contains(2));
        assertTrue(enabledCategories.contains(16)); // child of 2

        // check globally disabled categories
        assertFalse(enabledCategories.contains(1670));
        assertFalse(enabledCategories.contains(1671)); // child of 1670
        assertFalse(enabledCategories.contains(2380)); // child of 1671
    }

    private JSONArray getFileContent(String filename) throws IOException {
        return new JSONArray(
                new String(Files.readAllBytes(Paths.get(RESOURCES_PATH + filename)))
        );
    }
}
