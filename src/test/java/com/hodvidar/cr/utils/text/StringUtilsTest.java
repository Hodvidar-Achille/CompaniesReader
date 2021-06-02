package com.hodvidar.cr.utils.text;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    void splitLettersAndDigits() {
        String[] split = StringUtils.splitLettersAndDigits("$2M");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("2");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitLettersAndDigits("$200M");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitLettersAndDigits("€200M");
        assertThat(split[0]).isEqualTo("€");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitLettersAndDigits("C£200M");
        assertThat(split[0]).isEqualTo("C£");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitLettersAndDigits("C£200.14M");
        assertThat(split[0]).isEqualTo("C£");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo(".");
        assertThat(split[3]).isEqualTo("14");
        assertThat(split[4]).isEqualTo("M");

        split = StringUtils.splitLettersAndDigits("$0");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("0");
    }

    @Test
    void splitCurrencyAmountAndExponent() {
        String[] split = StringUtils.splitCurrencyAmountAndExponent("$2M");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("2");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitCurrencyAmountAndExponent("$200M");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitCurrencyAmountAndExponent("€200M");
        assertThat(split[0]).isEqualTo("€");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitCurrencyAmountAndExponent("C£200M");
        assertThat(split[0]).isEqualTo("C£");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitCurrencyAmountAndExponent("C£200.14M");
        assertThat(split[0]).isEqualTo("C£");
        assertThat(split[1]).isEqualTo("200.14");
        assertThat(split[2]).isEqualTo("M");

        split = StringUtils.splitCurrencyAmountAndExponent("$0");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("0");
        assertThat(split[2]).isEqualTo("u");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "http://www.lightsurf.com/solutions/mmsc.html = www.lightsurf.com",
            "http://www.mogreet.com/index.html = www.mogreet.com",
            "https://www.mogreet.com/index.html = www.mogreet.com"
    })
    void sanitizeDomain(final String domainRaw, final String expectedDomain) {
        assertThat(StringUtils.sanitizeDomain(domainRaw))
                .isEqualTo(expectedDomain);
    }
}
