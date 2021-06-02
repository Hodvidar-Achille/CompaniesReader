package com.hodvidar.cr.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

class RequesterTest {

    private static final String TEST_URL = "http://api.ipstack.com/digitalquarters.net?access_key=b2c98d0769f2a909e5662fd0a63a95b7&fields=country_name";

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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(output);
        String result = json.get("country_name").asText();
        assertThat(result).isEqualTo("United States");
    }

    @Test
    void performGet() throws JsonProcessingException {
        final String apiOutput = Requester.getInstance().performGet(TEST_URL);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.readTree(apiOutput);
        String result = json.get("country_name").asText();
        assertThat(result).isEqualTo("United States");
    }

}
