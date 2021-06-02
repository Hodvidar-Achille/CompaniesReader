package com.hodvidar.cr.http.iptrack;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class IpTrackRequesterTest {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "www.oodrive.com = France",
            "digitalquarters.net = United States",
            "www.concordnow.com = United States",
            "www.obi.de = Germany"
    })
    void getCountry(final String domain, final String expectedCountry) {
        IpTrackRequester requester = IpTrackRequester.getInstance();
        assertThat(requester.getCountry(domain)).isEqualTo(expectedCountry);
    }
}
