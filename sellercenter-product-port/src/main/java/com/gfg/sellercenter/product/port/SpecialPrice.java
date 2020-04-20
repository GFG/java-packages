package com.gfg.sellercenter.product.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SpecialPrice {
    @NotNull
    private final Double amount;

    private final ZonedDateTime from;
    private final ZonedDateTime to;

    public boolean rangeCovers(ZonedDateTime timePoint) {
        if (from != null && to != null) {
            return from.isBefore(timePoint) && to.isAfter(timePoint);
        } else if (to != null && to.isAfter(timePoint)) {
            return true;
        }

        return from != null && from.isBefore(timePoint);
    }
}
