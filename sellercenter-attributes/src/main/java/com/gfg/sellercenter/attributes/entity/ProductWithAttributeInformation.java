package com.gfg.sellercenter.attributes.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.json.JSONObject;
import java.util.Map;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProductWithAttributeInformation {

    private final int catalogProductId;

    private final String sellerSku;

    private final JSONObject attributes;

    private final Map<Integer, AttributeWithOption> attribute;
}
