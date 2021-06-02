package com.hodvidar.cr.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.hodvidar.cr.utils.resources.ResourceCloser;
import com.hodvidar.cr.utils.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class CompanyHolder {

    private static final String DOMAIN_NAME = "homepage_url";
    private static final String MONEY_RAISED = "total_money_raised";

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<Company> companies;
    private Map<String, List<Company>> companiesByCountry;
    private List<CompanyData> companyDataByCountry;

    private String numberOfCompanyAndMoneyRaisedByCountry = null;

    public CompanyHolder() {
        this.companies = new ArrayList<>();
    }

    public void addCompanies(final JsonParser jsonParser) {
        try {
            int jsonObjectCounter = 0;
            JsonToken jsonToken = jsonParser.nextToken();
            Company company = new Company();
            while (jsonToken != null) {
                if (jsonToken == JsonToken.START_OBJECT) {
                    jsonObjectCounter += 1;
                }
                if (jsonToken == JsonToken.END_OBJECT) {
                    jsonObjectCounter -= 1;
                    if (jsonObjectCounter == 0) {
                        addCompany(company);
                        company = new Company();
                    }
                }
                String fieldname = jsonParser.getCurrentName();
                if (MONEY_RAISED.equals(fieldname)) {
                    jsonParser.nextToken();
                    String moneyRaised = jsonParser.getText();
                    company.setMoneyRaisedRaw(moneyRaised);
                }
                if (DOMAIN_NAME.equals(fieldname)) {
                    jsonParser.nextToken();
                    String domain = jsonParser.getText();
                    company.setDomain(domain);
                }
                jsonToken = jsonParser.nextToken();
            }
        } catch (IOException e) {
            logger.warn("An exception occurred", e);
        } finally {
            ResourceCloser.closeResource(jsonParser);
        }
    }

    public void addCompany(final Company c) {
        this.companies.add(c);
        this.companyDataByCountry = null;
        this.numberOfCompanyAndMoneyRaisedByCountry = null;
    }

    public String getNumberOfCompanyAndMoneyRaisedByCountry() {
        if (this.numberOfCompanyAndMoneyRaisedByCountry != null) {
            return this.numberOfCompanyAndMoneyRaisedByCountry;
        }
        if (companyDataByCountry == null) {
            companiesByCountry = companies.stream()
                    .collect(Collectors.groupingBy(Company::getCountry));
            companyDataByCountry = companiesByCountry.entrySet().stream()
                    .map(e -> new CompanyData(e.getKey(), e.getValue()))
                    .collect(Collectors.toList());
        }
        int sizeForAlign = maxLengthForAlign() + 5;
        StringBuilder sb = new StringBuilder();
        sb.append(StringUtils.alignLeft("Country, ", sizeForAlign));
        sb.append(StringUtils.alignLeft("# Companies, ", sizeForAlign));
        sb.append("$ Average funding\n");
        for (CompanyData data : companyDataByCountry) {
            sb.append(StringUtils.alignLeft(data.getCountry() + ", ", sizeForAlign));
            sb.append(StringUtils.alignLeft(data.getNumberOfCompany() + ", ", sizeForAlign));
            sb.append(data.getMoneyRaisedInDollar() + "\n");
        }
        this.numberOfCompanyAndMoneyRaisedByCountry = sb.toString();
        return this.numberOfCompanyAndMoneyRaisedByCountry;
    }

    private int maxLengthForAlign() {
        int max = 0;
        for (CompanyData data : companyDataByCountry) {
            int length = ("" + data.getCountry()).length();
            if (length > max) {
                max = length;
            }
            length = ("" + data.getNumberOfCompany()).length();
            if (length > max) {
                max = length;
            }
        }
        return max;
    }
}
