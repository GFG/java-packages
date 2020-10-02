package com.gfg.sellercenter.category.service;

import com.gfg.sellercenter.category.entity.CategoryWithProductInformation;
import com.gfg.sellercenter.category.reader.HttpReader;
import lombok.AllArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CategoryService {

    private final HttpReader httpReader;

    /**
     * This json array represent a list of products and categories information
     * @param productIds a list of product ids in order to fetch related categories
     * @return HashMap It returns a Map object where the key is the product id.
     */
    public Map<Integer, CategoryWithProductInformation> getProductCategoriesByProductsIds(List<Integer> productIds) throws Exception {
        productIds = productIds.stream().distinct().collect(Collectors.toList());
        return createCategoryEntity(
            this.httpReader.getProductCategoriesByProductIds(productIds)
        );
    }

    private Map<Integer, CategoryWithProductInformation> createCategoryEntity(JSONArray response) {
        Map<Integer, CategoryWithProductInformation> categoryToProductMap = new HashMap<>();

        response.forEach(
            resp -> {
                JSONObject rootResponse = (JSONObject) resp;
                JSONObject categoryResponse = rootResponse.getJSONObject("category");
                categoryToProductMap.put(
                    rootResponse.getInt("id"),
                    new CategoryWithProductInformation(
                        categoryResponse.getInt("id"),
                        rootResponse.getInt("id"),
                        categoryResponse.getString("name"),
                        categoryResponse.getString("nameEn"),
                        categoryResponse.getBoolean("serialNumberRequired"))
                );
            }
        );
        return categoryToProductMap;
    }
}
