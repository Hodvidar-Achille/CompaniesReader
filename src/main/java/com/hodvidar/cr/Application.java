package com.hodvidar.cr;

import com.hodvidar.cr.json.reader.JsonReader;
import com.hodvidar.cr.model.CompanyHolder;
import com.hodvidar.cr.utils.numeric.MillisecondFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Application {

    public static final String RESOURCES = "src" + File.separator + "main" + File.separator + "resources";

    private static final Logger logger = LoggerFactory.getLogger(Application.class.getSimpleName());

    private static final String filePath = RESOURCES + File.separator + "companies.json";

    public static void main(String[] args) {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
        final long before = System.currentTimeMillis();
        logger.info("\nCompany Reader App start... \n---------------------------------");
        // 1) Read the Json
        CompanyHolder companyHolder = new CompanyHolder();
        companyHolder.addCompanies(JsonReader.getInstance().getParserFromFile(filePath));
        // 2) Start async work to retrieve country for each company
        // 3) for each country keep track of total money raised in USD (convert if different)
        // 4) once all async work are done, perform computation to have average value
        // 5) Write down results
        logger.info("=== RESULT ===\n"+companyHolder.getNumberOfCompanyAndMoneyRaisedByCountry());
        final long after = System.currentTimeMillis();
        logger.info("\n--------------------------------- \nCompany Reader App end.");
        logger.info("Time taken : "+ MillisecondFormatter.asTime(after-before));
    }

}
