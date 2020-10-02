package com.gfg.sellercenter.category.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CategoryWithProductInformation
{
    @NotNull
    private final int idCatalogCategory;

    @NotNull
    private final int idCatalogProduct;

    @NotNull
    private final String name;

    @NotNull
    private final String nameEn;

    @NotNull
    private final boolean isSerialNumberRequired;
}
