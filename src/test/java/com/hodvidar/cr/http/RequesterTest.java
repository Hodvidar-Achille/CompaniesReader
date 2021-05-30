package com.hodvidar.cr.http;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequesterTest {

    @Test
    void performGet() throws IOException {
        final String urlComplet = "http://api.ipstack.com/62.212.109.180?access_key=712afde4e4da8fb6a016be61a8051822&format=1";
        final Map<String, String> map = new HashMap<>();
        Requester.performGet(urlComplet, map);
    }
}
