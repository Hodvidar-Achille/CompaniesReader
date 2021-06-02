package com.hodvidar.cr.json.reader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.hodvidar.cr.utils.numeric.NumericalConverter;
import com.hodvidar.cr.utils.text.StringUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
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
    void jacksonJsonReadingLargeFile_CountCurrencies() throws IOException {
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
                String[] split = StringUtils.splitLettersAndDigits(moneyRaised);
                String currencyRaised = split[0];
                if (devises.containsKey(currencyRaised)) {
                    devises.put(currencyRaised, devises.get(currencyRaised) + 1);
                } else {
                    devises.put(currencyRaised, 1);
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        assertThat(devises.get("$")).isEqualTo(4898);
        assertThat(devises.get("€")).isEqualTo(71);
        assertThat(devises.get("£")).isEqualTo(21);
        assertThat(devises.get("C$")).isEqualTo(10);
    }

    @Test
    void jacksonJsonReadingLargeFile_CountMoneyRaisedByCurrency() throws IOException {
        final String filePath = RESOURCES + File.separator + COMPANIES;
        final File jsonFile = new File(filePath);
        JsonFactory jsonfactory = new JsonFactory(); //init factory
        JsonParser jsonParser = jsonfactory.createParser(jsonFile);
        Map<String, BigDecimal> devises = new HashMap<>();
        JsonToken jsonToken = jsonParser.nextToken();
        while (jsonToken != null) {
            String fieldname = jsonParser.getCurrentName();
            if ("total_money_raised".equals(fieldname)) {
                jsonToken = jsonParser.nextToken();
                String moneyRaised = jsonParser.getText();
                String[] split = StringUtils.splitCurrencyAmountAndExponent(moneyRaised);
                String currencyRaised = split[0];
                String amountMoneyRaised = split[1];
                String exponentAmountMoneyRaised = split[2];
                BigDecimal realAmountMoney = NumericalConverter.parseNumericalValue(amountMoneyRaised, exponentAmountMoneyRaised);
                if (devises.containsKey(currencyRaised)) {
                    devises.put(currencyRaised, devises.get(currencyRaised).add(realAmountMoney));
                } else {
                    devises.put(currencyRaised, realAmountMoney);
                }
            }
            jsonToken = jsonParser.nextToken();
        }
        assertThat(devises.get("$").toPlainString()).isEqualTo("52069911000");
        assertThat(devises.get("€").toPlainString()).isEqualTo("643782100");
        assertThat(devises.get("£").toPlainString()).isEqualTo("59430000");
        assertThat(devises.get("C$").toPlainString()).isEqualTo("73350000");
    }

}
