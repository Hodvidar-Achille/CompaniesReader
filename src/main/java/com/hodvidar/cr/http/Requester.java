package com.hodvidar.cr.http;

import com.hodvidar.cr.utils.recources.ResourceCloser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Requester {

    private static Requester INSTANCE = null;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Requester() {
    }

    public static synchronized Requester getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Requester();
        }
        return INSTANCE;
    }

    public String performGet(final String fullURL) {
        HttpURLConnection conn = null;
        InputStreamReader in = null;
        BufferedReader br = null;
        try {
            final URL url = new URL(fullURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            in = new InputStreamReader(conn.getInputStream());
            br = new BufferedReader(in);
            final String output = br.readLine();
            br.close();
            in.close();
            conn.disconnect();
            return output;
        } catch (Exception e) {
            logger.warn("An exception occurred during the HTTP connection", e);
        } finally {
            ResourceCloser.closeResource(br);
            ResourceCloser.closeResource(in);
            ResourceCloser.disconnectConnection(conn);
        }
        return null;
    }

}
