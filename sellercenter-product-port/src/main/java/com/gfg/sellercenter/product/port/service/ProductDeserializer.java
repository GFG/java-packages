package com.gfg.sellercenter.product.port.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.gfg.sellercenter.product.port.entity.Money;
import com.gfg.sellercenter.product.port.entity.Price;
import com.gfg.sellercenter.product.port.entity.Product;
import com.gfg.sellercenter.product.port.entity.SpecialPrice;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.UUID;

public class ProductDeserializer extends StdDeserializer<Product> {

    public ProductDeserializer() {
        this(null);
    }

    public ProductDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Product deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext) throws IOException {
        JsonNode productNode = jsonParser.getCodec().readTree(jsonParser);
        JsonNode specialPrice = productNode.get("price").get("special");

        return new Product(
                productNode.get("id").intValue(),
                UUID.fromString(productNode.get("uuid").textValue()),
                productNode.hasNonNull("source_id")
                        ? productNode.get("source_id").textValue()
                        : null,
                productNode.get("sku").textValue(),
                new Price(
                        new Money(
                                productNode.get("price").get("amount").doubleValue(),
                                productNode.get("price").get("currency").textValue()
                        ),
                        new SpecialPrice(
                                specialPrice.hasNonNull("amount")
                                        ? specialPrice.get("amount").doubleValue()
                                        : null,
                                specialPrice.hasNonNull("from")
                                        ? ZonedDateTime.parse(specialPrice.get("from").textValue())
                                        : null,
                                specialPrice.hasNonNull("to")
                                        ? ZonedDateTime.parse(specialPrice.get("to").textValue())
                                        : null
                        )
                ),
                productNode.get("seller_sku").textValue(),
                productNode.get("name").textValue(),
                productNode.hasNonNull("volumetric_weight")
                        ? productNode.get("volumetric_weight").doubleValue()
                        : null,
                productNode.get("variation").textValue()
        );
    }
}
