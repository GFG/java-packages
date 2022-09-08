package com.gfg.idencoder;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class IdEncoderTest {
    @Parameterized.Parameters(name = "{index}: Test with ID={0}, expected encoded result: {1}")
    public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1290L, "100011V"},
                {1291L, "100011W"},
                {5419L, "10004EU"},
                {1317L, "100012M"},
                {573437L, "100DD3X"},
                {100000062L, "11WMCOM"},
        });
    }

    private final Long id;
    private final String encodedId;

    public IdEncoderTest(Long id, String expectedEncodedId) {
        this.id = id;
        this.encodedId = expectedEncodedId;
    }

    @Test
    public void shouldEncodeMediumLength() {
        assertEquals(encodedId, IdEncoder.encodeMedium(id));
    }

    @Test
    public void shouldDecodeMediumLength() {
        assertEquals(id, IdEncoder.decodeMedium(encodedId));
    }
}
