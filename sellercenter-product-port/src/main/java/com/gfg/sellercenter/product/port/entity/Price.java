package com.gfg.sellercenter.product.port.entity;

import lombok.Data;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Price {
    @NotNull
    private final Money amount;

    private final SpecialPrice specialPrice;
}
