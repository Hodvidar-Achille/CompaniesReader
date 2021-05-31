package com.hodvidar.cr.utils.text;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTest {

    @Test
    void splitLettersAndDigits() {
        String[] split = StringUtils.splitLettersAndDigits("$2M");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("2");
        assertThat(split[2]).isEqualTo("M");

        split =  StringUtils.splitLettersAndDigits("$200M");
        assertThat(split[0]).isEqualTo("$");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split =  StringUtils.splitLettersAndDigits("€200M");
        assertThat(split[0]).isEqualTo("€");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");

        split =  StringUtils.splitLettersAndDigits("C£200M");
        assertThat(split[0]).isEqualTo("C£");
        assertThat(split[1]).isEqualTo("200");
        assertThat(split[2]).isEqualTo("M");
    }
}
