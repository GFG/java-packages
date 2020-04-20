package com.gfg.sellercenter.product.port;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class ProductTest {
    @Test
    public void shouldNotHaveSpecialPrice() {
        Product product = createProduct(
                new Price(new Money(10.00, "EUR"), null)
        );

        assertFalse(product.hasSpecialPrice(ZonedDateTime.now()));
    }

    @Test
    public void shouldNotHaveSpecialPriceWithEmptyDates() {
        Product product = createProduct(
                new Price(
                        new Money(10.00, "EUR"),
                        new SpecialPrice(8.00, null, null)
                )
        );

        assertFalse(product.hasSpecialPrice(ZonedDateTime.now()));
    }

    @Test
    public void specialPriceWithoutDates() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                null,
                                null
                        )
                )
        );

        assertEquals(amount, product.getCurrentPrice());
    }

    @Test
    public void specialPriceExpired() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                ZonedDateTime.parse("2019-01-01T10:15:30+01:00"),
                                ZonedDateTime.parse("2019-01-02T10:15:30+01:00")
                        )
                )
        );

        assertEquals(amount, product.getCurrentPrice());
    }

    @Test
    public void specialPriceValid() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                ZonedDateTime.parse("2019-01-01T10:15:30+01:00"),
                                ZonedDateTime.parse("2222-02-02T10:15:30+01:00")
                        )
                )
        );

        assertEquals(new Money(8.00, "EUR"), product.getCurrentPrice());
    }

    @Test
    public void specialPriceSetOnlyFromInThePast() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                ZonedDateTime.parse("2019-01-01T10:15:30+01:00"),
                                null
                        )
                )
        );

        assertEquals(new Money(8.00, "EUR"), product.getCurrentPrice());
    }

    @Test
    public void specialPriceSetOnlyToInThePast() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                null,
                                ZonedDateTime.parse("2019-01-01T10:15:30+01:00")
                        )
                )
        );

        assertEquals(amount, product.getCurrentPrice());
    }

    @Test
    public void specialPriceSetOnlyFromInTheFuture() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                ZonedDateTime.parse("2222-01-01T10:15:30+01:00"),
                                null
                        )
                )
        );

        assertEquals(amount, product.getCurrentPrice());
    }

    @Test
    public void specialPriceSetOnlyToInTheFuture() {
        Money amount = new Money(10.00, "EUR");

        Product product = createProduct(
                new Price(
                        amount,
                        new SpecialPrice(
                                8.00,
                                null,
                                ZonedDateTime.parse("2222-01-01T10:15:30+01:00")
                        )
                )
        );

        assertEquals(new Money(8.00, "EUR"), product.getCurrentPrice());
    }

    private Product createProduct(Price price) {
        return new Product(
                1,
                null,
                null,
                price,
                "Test",
                "Jeans",
                10.00
        );
    }
}
