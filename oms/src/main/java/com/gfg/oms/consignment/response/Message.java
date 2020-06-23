package com.gfg.oms.consignment.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfg.oms.response.GeneralMessage;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Message extends GeneralMessage {
    @JsonProperty("purchase_order_id")
    private String purchaseOrderId;

    @JsonProperty("purchase_order_number")
    private String purchaseOrderNumber;

    @JsonProperty("items")
    private List<Item> items;
}
