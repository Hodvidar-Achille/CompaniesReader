package com.hodvidar.cr.http.iptrack;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hodvidar.cr.http.Requester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpTrackRequester {

    // Too used : 712afde4e4da8fb6a016be61a8051822
    // b2c98d0769f2a909e5662fd0a63a95b7
    // Too used : ae2a812dd265822556997b9d49a2c195
    // Too used : e9206f50c83f2438a71c268825da585b
    // Too used : bda77b37324d70f7618c8ac0fdfbcd74
    private static final String IPTRACK_API_KEY = "b2c98d0769f2a909e5662fd0a63a95b7";
    private static final String START_URL = "http://api.ipstack.com/";
    private static final String PARAMETERS = "?access_key="
            + IPTRACK_API_KEY
            + "&fields=country_name";
    private static final String UNKNOWN_COUNTRY = "Unknown";
    private static IpTrackRequester INSTANCE = null;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IpTrackRequester() {
    }

    public static synchronized IpTrackRequester getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IpTrackRequester();
        }
        return INSTANCE;
    }

    /**
     * Returns the country associated with this domain name,
     * returns {@linkplain #UNKNOWN_COUNTRY} if the IPTrack_API doesn't found it
     * or any error occurs.
     */
    public String getCountry(String domainName) {
        if (domainName == null || domainName.isBlank()) {
            return UNKNOWN_COUNTRY;
        }
        try {
            final StringBuilder sb = new StringBuilder();
            sb.append(START_URL).append(domainName).append(PARAMETERS);
            final String apiOutput = Requester.getInstance().performGet(sb.toString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(apiOutput);
            if (json == null || json.get("country_name") == null) {
                logger.warn("Result is null for domaineName '" + domainName + "'");
                return UNKNOWN_COUNTRY;
            }
            return json.get("country_name").asText();
        } catch (Exception e) {
            logger.warn("An exception occurred for domainName '" + domainName + "'", e);
        }
        return UNKNOWN_COUNTRY;
    }


}
