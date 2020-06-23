package com.gfg.oms.consignment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Item {
    @JsonProperty("simple_sku")
    private String simpleSku;

    @JsonProperty("purchase_order_item_id")
    private String purchaseOrderItemId;
}
