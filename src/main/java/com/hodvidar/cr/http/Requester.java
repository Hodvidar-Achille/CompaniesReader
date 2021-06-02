package com.hodvidar.cr.http;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {

    private static Requester instance = null;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Requester() {
    }

    public static synchronized Requester getInstance() {
        if (instance == null) {
            instance = new Requester();
        }
        return instance;
    }

    public String performGet(final String fullURL) {
        try {
            final URL url = new URL(fullURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                logger.warn("Failed : HTTP Error code : "
                        + conn.getResponseCode()
                        + "for url '" + fullURL + "'");
                throw new IllegalStateException("HTTP error");
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);
            return br.readLine();
        } catch (Exception e) {
            logger.warn("An exception occurred during the HTTP connection", e);
            throw new IllegalStateException("HTTP error");
        }
    }

}
