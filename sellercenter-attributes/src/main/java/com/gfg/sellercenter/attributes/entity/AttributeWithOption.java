package com.gfg.sellercenter.attributes.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AttributeWithOption {

    private final int idCatalogAttribute;

    private final String label;

    private final Map<Integer, AttributeOption> attributeOptions;
}
