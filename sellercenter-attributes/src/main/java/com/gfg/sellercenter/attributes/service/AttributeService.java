package com.gfg.sellercenter.attributes.service;

import com.gfg.sellercenter.attributes.entity.AttributeOption;
import com.gfg.sellercenter.attributes.entity.AttributeWithOption;
import com.gfg.sellercenter.attributes.entity.ProductWithAttributeInformation;
import com.gfg.sellercenter.attributes.reader.HttpReader;

import lombok.AllArgsConstructor;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AttributeService {

    private final HttpReader httpReader;

    public Map<Integer, ProductWithAttributeInformation>
            getSerialNumberRequiredAttributesByProductIds(List<Integer> productIds)
                    throws Exception {
        productIds = productIds.stream().distinct().collect(Collectors.toList());

        return createAttributeEntity(
                this.httpReader.getSerialNumberRequiredAttributesByProductIds(productIds));
    }

    private Map<Integer, ProductWithAttributeInformation> createAttributeEntity(
            JSONArray responseArray) {
        Map<Integer, ProductWithAttributeInformation> categoryToProductMap = new HashMap<>();

        for (Object response : responseArray) {
            JSONObject resp = (JSONObject) response;
            Map<Integer, AttributeWithOption> attributeWithOption = new HashMap<>();
            categoryToProductMap.putIfAbsent(
                    resp.getInt("id"),
                    new ProductWithAttributeInformation(
                            resp.getInt("id"),
                            resp.getString("skuSeller"),
                            resp.getJSONObject("attributes"),
                            attributeWithOption));
            this.addAttributeWithOption(
                    categoryToProductMap.get(resp.getInt("id")),
                    resp.getJSONObject("attributeWithOption"));
        }

        return categoryToProductMap;
    }

    private void addAttributeWithOption(
            ProductWithAttributeInformation product, JSONObject response) {

        Integer attributeId = response.getInt("id");
        product.getAttribute()
                .putIfAbsent(
                        attributeId,
                        new AttributeWithOption(
                                attributeId, response.getString("label"), new HashMap<>()));
        product.getAttribute()
                .get(attributeId)
                .getAttributeOptions()
                .put(
                        response.getJSONObject("attributeOption").getInt("id"),
                        new AttributeOption(
                                response.getJSONObject("attributeOption").getInt("id"),
                                response.getJSONObject("attributeOption").getString("value")));
    }
}
