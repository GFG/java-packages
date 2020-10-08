package com.gfg.sellercenter.category.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class CategoryFulfillmentSetting {
    @NotNull
    private int sellerId;

    @NotNull
    private int categoryId;

    @NotNull
    private boolean fulfillmentEnabled;
}
