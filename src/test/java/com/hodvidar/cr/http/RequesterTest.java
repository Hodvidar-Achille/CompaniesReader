package com.hodvidar.cr.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RequesterTest {

    private static final String TEST_URL = "http://api.ipstack.com/digitalquarters.net?access_key=712afde4e4da8fb6a016be61a8051822&fields=country_name";

    @Test
    void httpRequest() throws IOException {
        URL url = new URL(TEST_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + conn.getResponseCode());
        }
        InputStreamReader in = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(in);
        String output = br.readLine();
        conn.disconnect();
        //Using the JSON simple library parse the string into a json object
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(output);
        String result = json.get("country_name").asText();
        assertThat(result).isEqualTo("United States");
    }

    @Test
    void performGet() throws IOException {
        final String urlComplet = "http://api.ipstack.com/62.212.109.180?access_key=712afde4e4da8fb6a016be61a8051822&format=1";
        final Map<String, String> map = new HashMap<>();
        Requester.performGet(urlComplet, map);
    }
}
