package com.gfg.sellercenter.category.service;

import com.gfg.sellercenter.category.entity.CategoryDetails;
import com.gfg.sellercenter.category.entity.CategoryFulfillmentSetting;
import com.gfg.sellercenter.category.entity.CategoryStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CategoryFulfillmentVisibilityService {
    private static final boolean FULFILLMENT_ENABLED = true;
    private static final boolean FULFILLMENT_DISABLED = false;

    public List<Integer> getListOfFulfilmentEnabledCategoriesBySeller(Map<Integer, CategoryDetails> categories, Map<Integer, CategoryFulfillmentSetting> fulfillmentSettings) {
        enableSellerSpecificFulfillmentCategories(categories, fulfillmentSettings);
        disableSellerSpecificFulfillementCategories(categories, fulfillmentSettings);

        List<Integer> result = new ArrayList<>();
        for (CategoryDetails categoryDetails : categories.values()) {
            if (isCategoryEnabledForFulfillment(categoryDetails)) {
                result.add(categoryDetails.getId());
            }
        }

        return result;
    }

    private void disableSellerSpecificFulfillementCategories(Map<Integer, CategoryDetails> categories, Map<Integer, CategoryFulfillmentSetting> fulfillmentSettings) {
        for (CategoryFulfillmentSetting categoryFulfillmentSetting : fulfillmentSettings.values()) {
            if (!categoryFulfillmentSetting.isFulfillmentEnabled()) {
                markCategoryWithChildren(categories, categoryFulfillmentSetting.getCategoryId(), FULFILLMENT_DISABLED);
            }
        }
    }

    private void enableSellerSpecificFulfillmentCategories(Map<Integer, CategoryDetails> categories, Map<Integer, CategoryFulfillmentSetting> fulfillmentSettings) {
        for (CategoryFulfillmentSetting categoryFulfillmentSetting : fulfillmentSettings.values()) {
            if (categoryFulfillmentSetting.isFulfillmentEnabled()) {
                markCategoryWithChildren(categories, categoryFulfillmentSetting.getCategoryId(), FULFILLMENT_ENABLED);
            }
        }
    }

    private boolean isCategoryEnabledForFulfillment(CategoryDetails categoryDetails) {
        return categoryDetails.isFulfillmentVisible()
                && categoryDetails.isVisible()
                && categoryDetails.hasSrcId()
                && categoryDetails.getStatus() == CategoryStatus.ACTIVE;
    }

    private void markCategoryWithChildren(Map<Integer, CategoryDetails> categories, int categoryId, boolean fulfilmentVisible) {
        CategoryDetails parentCategoryDetails = categories.get(categoryId);
        int lft = parentCategoryDetails.getLft();
        int rgt = parentCategoryDetails.getRgt();
        for (CategoryDetails categoryDetails : categories.values()) {
            if (categoryDetails.getLft() >= lft && categoryDetails.getRgt() <= rgt) {
                if (fulfilmentVisible) {
                    categoryDetails.markAsFulfilmentVisible();
                } else {
                    categoryDetails.markAsNotFulfilmentVisible();
                }
            }
        }
    }
}
