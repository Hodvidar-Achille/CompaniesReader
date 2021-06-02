package com.hodvidar.cr.model;

import java.math.BigDecimal;
import java.util.List;

public class CompanyData {

    private final String country;
    private final int numberOfCompany;
    private final BigDecimal moneyRaisedInDollar;

    public CompanyData(String country, List<Company> companiesForCountry) {
        this.country = country;
        this.numberOfCompany = companiesForCountry.size();
        this.moneyRaisedInDollar = companiesForCountry.stream()
                .map(c -> c.getMoneyRaisedInDollar())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String getCountry() {
        return country;
    }

    public int getNumberOfCompany() {
        return numberOfCompany;
    }

    public BigDecimal getMoneyRaisedInDollar() {
        return moneyRaisedInDollar;
    }
}
