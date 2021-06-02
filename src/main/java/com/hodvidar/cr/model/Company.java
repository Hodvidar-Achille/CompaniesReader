package com.hodvidar.cr.model;

import com.hodvidar.cr.http.iptrack.IpTrackRequester;
import com.hodvidar.cr.utils.numeric.NumericalConverter;
import com.hodvidar.cr.utils.numeric.currency.CurrencyConverter;
import com.hodvidar.cr.utils.text.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Company {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String name;
    private String domain;
    private String moneyRaisedRaw;

    private CompletableFuture<String> asyncCountryCaller;

    private String country;
    private BigDecimal moneyRaisedInDollar;

    public Company() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domainRaw) {
        this.domain = StringUtils.sanitizeDomain(domainRaw);
        this.asyncCountryCaller = CompletableFuture.supplyAsync(
                () -> IpTrackRequester.getInstance().getCountry(domain)
        );
    }

    public String getMoneyRaisedRaw() {
        return moneyRaisedRaw;
    }

    public void setMoneyRaisedRaw(String moneyRaisedRaw) {
        this.moneyRaisedRaw = moneyRaisedRaw;
    }

    public BigDecimal getMoneyRaisedInDollar() {
        if (moneyRaisedInDollar != null) {
            return moneyRaisedInDollar;
        }
        final String[] split = StringUtils.splitCurrencyAmountAndExponent(moneyRaisedRaw);
        final String currency = split[0];
        final String amountRaw = split[1];
        if (amountRaw.equals("0")) {
            return new BigDecimal(0);
        }
        final String exponent = split[2];
        final BigDecimal amount = NumericalConverter.parseNumericalValue(amountRaw, exponent);
        this.moneyRaisedInDollar = CurrencyConverter.toUSD(currency, amount);
        return moneyRaisedInDollar;
    }

    public String getCountry() {
        if (country != null) {
            return country;
        }
        try {
            country =  this.asyncCountryCaller.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.warn("An exception occurred", e);
        }
        return country;
    }
}
