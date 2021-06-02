package com.hodvidar.cr.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyHolderTest {

    @Test
    void getNumberOfCompanyAndMoneyRaisedByCountry() {
        Company c1 = new Company();
        c1.setDomain("www.oodrive.com");
        c1.setMoneyRaisedRaw("€1B");
        Company c2 = new Company();
        c2.setDomain("digitalquarters.net");
        c2.setMoneyRaisedRaw("$1B");
        Company c3 = new Company();
        c3.setDomain("www.concordnow.com");
        c3.setMoneyRaisedRaw("C$1M");
        Company c4 = new Company();
        c4.setDomain("www.obi.de");
        c4.setMoneyRaisedRaw("£10k");
        Company c5 = new Company();
        c5.setDomain("www.obi.de");
        c5.setMoneyRaisedRaw("£100M");

        CompanyHolder holder = new CompanyHolder();
        holder.addCompany(c1);
        holder.addCompany(c2);
        holder.addCompany(c3);
        holder.addCompany(c4);
        holder.addCompany(c5);

        assertThat(holder.getNumberOfCompanyAndMoneyRaisedByCountry())
                .isEqualTo("Country,          # Companies,      $ Average funding\n" +
                        "United States,    2,                1000785500.00\n" +
                        "France,           1,                1213400000.00\n" +
                        "Germany,          2,                136743673.00\n");
    }
}
