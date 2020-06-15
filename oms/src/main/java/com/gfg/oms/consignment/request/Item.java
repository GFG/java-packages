package com.gfg.oms.consignment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @JsonProperty("simple_sku")
    @NotNull
    private String simpleSku;

    @NotNull
    private Integer quantity;

    @JsonProperty("unit_price")
    @NotNull
    private String unitPrice;

    @JsonProperty("seller_charge")
    @NotNull
    private Double sellerCharge;

    @JsonProperty("label_code")
    private String labelCode;

    @JsonProperty("seller_comment")
    private String sellerComment;

    @JsonProperty("serial_number")
    private String serialNumber;

    @NotNull
    private List<Attribute> attributes;
}
