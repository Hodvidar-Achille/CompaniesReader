package com.hodvidar.cr.utils.numeric;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class NumericalConverterTest {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2k = 2000",
            "2M = 2000000",
            "2B = 2000000000",
            "0 = 0",
            "2.14 = 2.14",
            "2.14k = 2140",
            "2.14M = 2140000",
            "2.14B = 2140000000",
            "176.67899M = 176678990",
            " = 0"
    })
    void parseNumericalValue(final String input, final BigDecimal expectedValue) {
        assertThat(NumericalConverter.parseNumericalValue(input).toPlainString())
                .isEqualTo(expectedValue.toPlainString());
    }

}
