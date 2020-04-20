package com.gfg.sellercenter.product.port;

import lombok.Data;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class Product {
    @NotNull
    private final Integer id;

    private final Integer sourceId;

    private final String sku;

    @NotNull
    private final Price price;

    @NotNull
    @NotEmpty
    private final String sellerSku;

    @NotNull
    @NotEmpty
    private final String name;

    private final Double volumetricWeight;

    public boolean hasSpecialPrice() {
        return price.getSpecialPrice() != null;
    }

    public Money getCurrentPrice() {
        if (hasSpecialPrice()) {
            try {
                return getSpecialPrice();
            } catch (IllegalAccessException e) {
                throw new IllegalStateException(
                        "Product price changed during runtime, which lead to invalid state",
                        e
                );
            }
        }

        return price.getAmount();
    }

    private Money getSpecialPrice() throws IllegalAccessException {
        if (!hasSpecialPrice()) {
            throw new IllegalAccessException(
                    "getSpecialPrice method call should be wrapped by hasSpecialPrice if condition"
            );
        }

        ZonedDateTime now = ZonedDateTime.now();
        SpecialPrice specialPrice = price.getSpecialPrice();
        String currency = price.getAmount().getCurrency();
        ZonedDateTime specialPriceFrom = specialPrice.getFrom();
        ZonedDateTime specialPriceTo = specialPrice.getTo();

        if (specialPriceFrom != null && specialPriceTo != null) {
            if (specialPriceFrom.isBefore(now) && specialPriceTo.isAfter(now)) {
                return new Money(
                        specialPrice.getAmount(),
                        currency
                );
            }

            return price.getAmount();
        } else if (specialPriceTo != null && specialPriceTo.isAfter(now)) {
            return new Money(
                    specialPrice.getAmount(),
                    currency
            );
        } else if (specialPriceFrom != null && specialPriceFrom.isBefore(now)) {
            return new Money(
                    specialPrice.getAmount(),
                    currency
            );
        }

        return price.getAmount();
    }
}
