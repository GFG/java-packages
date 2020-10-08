package com.gfg.sellercenter.attributes.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AttributeOption {

    private final int idCatalogAttributeOption;

    private final String value;
}
