package com.hodvidar.cr.utils.numeric.currency;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyConvertertTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "$ | 100 | 100.00",
            "€ | 100 | 121.34",
            "£ | 100 | 136.73",
            "C$ | 100 | 78.55",
            "$ | 1000000000 | 1000000000.00",
            "€ | 1000000000 | 1213400000.00",
            "£ | 1000000000 | 1367300000.00",
            "C$ | 1000000000 | 785500000.00",
    })
    void getMoneyRaisedInDollar(final String currency, final BigDecimal amount, final String expectedResult) {
        assertThat(CurrencyConverter.toUSD(currency, amount).toPlainString()).isEqualTo(expectedResult);
    }
}
