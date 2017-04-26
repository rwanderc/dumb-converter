package com.wandercosta.dumbconverter;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test DumbConverter.
 *
 * @author Wander Costa (www.wandercosta.com)
 * @version 1.0
 */
public class DumbConverterTest {

    private static final DumbConverter CONVERTER = new DumbConverter();

    @Test
    public void shouldConvertUnits() {
        assertEquals("uom=\"B\">1010.0</", CONVERTER.convertUnits("uom=\"A\">1</", "A", "B", 1010f));
        assertEquals("uom=\"ft\">32.8084</", CONVERTER.convertUnits("uom=\"m\">10</", "m", "ft", 3.28084f));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertUnitsWithEmptyWitsml() {
        CONVERTER.convertUnits(null, "-", "-", 1010f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertUnitsWithEmptyFrom() {
        CONVERTER.convertUnits("-", "", "-", 1010f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertUnitsWithEmptyTo() {
        CONVERTER.convertUnits("-", "-", "", 0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToConvertUnitsWithFactorZero() {
        CONVERTER.convertUnits("-", "-", "-", 0f);
    }

}
