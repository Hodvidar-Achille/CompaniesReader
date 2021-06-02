package com.hodvidar.cr.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyTest {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "$100 = 100.00",
            "€100 = 121.34",
            "£100 = 136.73",
            "C$100 = 78.55",
            "$1000000000 = 1000000000.00",
            "€1000000000 = 1213400000.00",
            "£1000000000 = 1367300000.00",
            "C$1000000000 = 785500000.00",
            "$1B = 1000000000.00",
            "€1B = 1213400000.00",
            "£1B = 1367300000.00",
            "C$1B = 785500000.00",
            "$1M = 1000000.00",
            "€1M = 1213400.00",
            "£1M = 1367300.00",
            "C$1M = 785500.00",
            "$1k = 1000.00",
            "€1k = 1213.40",
            "£1k = 1367.30",
            "C$1k = 785.50",
    })
    void getMoneyRaisedInDollar(final String moneyRaisedRaw, final String expectedResult) {
        Company c = new Company();
        c.setMoneyRaisedRaw(moneyRaisedRaw);
        assertThat(c.getMoneyRaisedInDollar().toPlainString()).isEqualTo(expectedResult);
        assertThat(c.getMoneyRaisedInDollar().toPlainString()).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "www.oodrive.com = France",
            "digitalquarters.net = United States",
            "www.concordnow.com = United States",
            "www.obi.de = Germany"
    })
    void getCountry(final String domain, final String expectedCountry) {
        Company c = new Company();
        c.setDomain(domain);
        assertThat(c.getCountry()).isEqualTo(expectedCountry);
        assertThat(c.getCountry()).isEqualTo(expectedCountry);
    }
}
