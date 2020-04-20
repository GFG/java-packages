package com.gfg.sellercenter.product.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SpecialPrice {
    @NotNull
    private final Double amount;

    private final ZonedDateTime from;
    private final ZonedDateTime to;
}
