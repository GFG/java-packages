package com.gfg.sellercenter.product.port.entity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class MoneyTest {
    @Test
    public void shouldBeEqualToItself() {
        Money money = new Money(15.55, "EUR");
        assertEquals(money, money);
    }

    @Test
    public void shouldBeEqualToOtherWithSameValues() {
        Money mine = new Money(15.55, "EUR");
        Money wives = new Money(15.55, "EUR");
        assertEquals(mine, wives);
    }

    @Test
    public void shouldNotBeEqualWithSameValueAndDifferentCurrency() {
        Money eur = new Money(15.55, "EUR");
        Money usd = new Money(15.55, "USD");
        assertNotEquals(eur, usd);
    }

    @Test
    public void shouldNotBeEqualWithSameCurrencyAndDifferentValue() {
        Money eur = new Money(15.55, "EUR");
        Money usd = new Money(55.15, "EUR");
        assertNotEquals(eur, usd);
    }
}
