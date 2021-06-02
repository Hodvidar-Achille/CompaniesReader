package com.hodvidar.cr.utils.numeric;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MillisecondFormatterTest {
    @Test
    public void checkTime() {
        String r = MillisecondFormatter.asTime(999);
        assertThat(r).isEqualTo("0 hour(s) 0 minute(s) 0 seconde(s) 999 ms");

        r = MillisecondFormatter.asTime(59999);
        assertThat(r).isEqualTo("0 hour(s) 0 minute(s) 59 seconde(s) 999 ms");

        r = MillisecondFormatter.asTime(3599999);
        assertThat(r).isEqualTo("0 hour(s) 59 minute(s) 59 seconde(s) 999 ms");

        r = MillisecondFormatter.asTime(3600000);
        assertThat(r).isEqualTo("1 hour(s) 0 minute(s) 0 seconde(s) 0 ms");

        r = MillisecondFormatter.asTime(10799999);
        assertThat(r).isEqualTo("2 hour(s) 59 minute(s) 59 seconde(s) 999 ms");
    }
}
