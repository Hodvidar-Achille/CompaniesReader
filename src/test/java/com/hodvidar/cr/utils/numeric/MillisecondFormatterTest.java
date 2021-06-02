package com.hodvidar.cr.utils.numeric;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class MillisecondFormatterTest {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "999 = 0 hour(s) 0 minute(s) 0 second(s) 999 ms",
            "59999 = 0 hour(s) 0 minute(s) 59 second(s) 999 ms",
            "3599999 = 0 hour(s) 59 minute(s) 59 second(s) 999 ms",
            "3600000 = 1 hour(s) 0 minute(s) 0 second(s) 0 ms",
            "10799999 = 2 hour(s) 59 minute(s) 59 second(s) 999 ms"
    })
    void checkTime(final long time, final String expectedResult) {
        assertThat(MillisecondFormatter.asTime(time)).isEqualTo(expectedResult);
    }
}
