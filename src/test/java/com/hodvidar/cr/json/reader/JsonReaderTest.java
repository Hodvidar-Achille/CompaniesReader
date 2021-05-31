package com.hodvidar.cr.json.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonReaderTest {

    public static final String RESOURCES = "src" + File.separator + "main" + File.separator + "resources";
    public static final String COMPANY_1_SIMPLE = "company1_simple.json";
    public static final String COMPANY_EXAMPLE = "company_example.json";
    public static final String COMPANY_1 = "company1.json";
    public static final String COMPANIES = "companies.json";

    @Test
    void jacksonJsonReading() throws IOException {
        final String filePath = RESOURCES + File.separator + COMPANY_1_SIMPLE;
        final File jsonFile = new File(filePath);
        JsonFactory jsonfactory = new JsonFactory();
        JsonParser jsonParser = jsonfactory.createParser(jsonFile);
        int numberOfRecords = 0;
        String companyName = "";
        JsonToken jsonToken = jsonParser.nextToken();
        while (jsonToken != null) {
            String fieldname = jsonParser.getCurrentName();
            if ("name".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                companyName = jsonParser.getText();
            }
            if (jsonToken == JsonToken.END_OBJECT) {
                //do some processing, Indexing, saving in DB etc..
                numberOfRecords++;
            }
            jsonToken = jsonParser.nextToken();
        }
        assertThat(numberOfRecords).isEqualTo(1);
        assertThat(companyName).isEqualTo("Wetpaint");
    }

    @Test
    void jacksonJsonReadingEmbeddedJson() throws IOException {
        final String filePath = RESOURCES + File.separator + COMPANY_EXAMPLE;
        final File jsonFile = new File(filePath);
        JsonFactory jsonfactory = new JsonFactory(); //init factory
        JsonParser jsonParser = jsonfactory.createParser(jsonFile);
        int numberOfRecords = 0;
        int jsonObjectCounter = 0;
        JsonToken jsonToken = jsonParser.nextToken();
        while (jsonToken != null) { //Iterate all elements of array
            if (jsonToken == JsonToken.START_OBJECT) {
                jsonObjectCounter += 1;
            }
            if (jsonToken == JsonToken.END_OBJECT) {
                jsonObjectCounter -= 1;
                if (jsonObjectCounter == 0) {
                    numberOfRecords += 1;
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        assertThat(numberOfRecords).isEqualTo(5);
    }

    @Test
    void jacksonJsonReadingLargeFile() throws IOException {
        final String filePath = RESOURCES + File.separator + COMPANIES;
        final File jsonFile = new File(filePath);
        JsonFactory jsonfactory = new JsonFactory(); //init factory
        JsonParser jsonParser = jsonfactory.createParser(jsonFile);
        int numberOfRecords = 0;
        int jsonObjectCounter = 0;
        JsonToken jsonToken = jsonParser.nextToken();
        while (jsonToken != null) {
            if (jsonToken == JsonToken.START_OBJECT) {
                jsonObjectCounter += 1;
            }
            if (jsonToken == JsonToken.END_OBJECT) {
                jsonObjectCounter -= 1;
                if (jsonObjectCounter == 0) {
                    numberOfRecords += 1;
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        assertThat(numberOfRecords).isEqualTo(5000);
    }

    @Test
    void jacksonJsonReadingLargeFile_totalMoneyRaised() throws IOException {
        final String filePath = RESOURCES + File.separator + COMPANIES;
        final File jsonFile = new File(filePath);
        JsonFactory jsonfactory = new JsonFactory(); //init factory
        JsonParser jsonParser = jsonfactory.createParser(jsonFile);
        Map<String, Integer> devises = new HashMap<>();
        JsonToken jsonToken = jsonParser.nextToken();
        while (jsonToken != null) {
            String fieldname = jsonParser.getCurrentName();
            if ("total_money_raised".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                String moneyRaised = jsonParser.getText();
                String deviseRaised = moneyRaised.substring(0, 1);
                if (devises.containsKey(deviseRaised)) {
                    devises.put(deviseRaised, devises.get(deviseRaised) + 1);
                } else {
                    devises.put(deviseRaised, 1);
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        assertThat(devises.get("$")).isEqualTo(4898);
        assertThat(devises.get("€")).isEqualTo(71);
        assertThat(devises.get("£")).isEqualTo(21);
        assertThat(devises.get("C$")).isEqualTo(10);
    }

}
