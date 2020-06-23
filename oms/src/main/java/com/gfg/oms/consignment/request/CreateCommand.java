package com.gfg.oms.consignment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gfg.oms.Request;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateCommand extends Request implements Serializable {
    @JsonProperty("method")
    protected final String method = "createConsignmentRequest";

    @JsonProperty("shopSellerId")
    @NotNull
    private Integer shopSellerId;

    @JsonProperty("purchaseOrderNumber")
    private String purchaseOrderNumber;

    @JsonProperty("items")
    @NotNull
    private ArrayList<Item> items;

    @JsonProperty("deliveryType")
    @NotNull
    private String deliveryType;

    @NotNull
    private ZonedDateTime shippingDate;
    private String comment;
    private String gisMtNumber;

    @NotNull
    private ArrayList<Attribute> attributes;

    @JsonProperty
    public String getShippingDate() {
        return shippingDate.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss"));
    }
}
