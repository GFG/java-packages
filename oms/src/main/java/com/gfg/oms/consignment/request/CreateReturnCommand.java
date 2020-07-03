package com.gfg.oms.consignment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Getter
public class CreateReturnCommand extends CreateCommand {
    @JsonProperty("method")
    protected final String method = "createConsignmentReturnRequest";

    public CreateReturnCommand(
            @NotNull String shopSellerId,
            String purchaseOrderNumber,
            @NotNull ArrayList<Item> items,
            @NotNull String deliveryType,
            @NotNull ZonedDateTime shippingDate,
            String comment,
            String gisMtNumber,
            @NotNull ArrayList<Attribute> attributes
    ) {
        super(shopSellerId, purchaseOrderNumber, items, deliveryType, shippingDate, comment, gisMtNumber, attributes);
    }
}
