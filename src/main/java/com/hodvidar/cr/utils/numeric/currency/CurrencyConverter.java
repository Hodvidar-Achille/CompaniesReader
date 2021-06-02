package com.hodvidar.cr.utils.numeric.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * For the exercise the currencies different from the USD will be converted
 * using the exchange rate from the 1st january 2021.
 */
public class CurrencyConverter {

    private static final BigDecimal GBP_TO_USD_RATE = new BigDecimal(1.3673);
    private static final BigDecimal EUR_TO_USD_RATE = new BigDecimal(1.2134);
    private static final BigDecimal CAD_TO_USD_RATE = new BigDecimal(0.7855);

    private CurrencyConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal toUSD(final String currency, final BigDecimal amount) {
        switch (currency) {
            case "£":
                return GBPtoUSD(amount);
            case "€":
                return EURtoUSD(amount);
            case "C$":
                return CADtoUSD(amount);
            default:
                return amount.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public static BigDecimal GBPtoUSD(final BigDecimal amount) {
        return amount.multiply(GBP_TO_USD_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal EURtoUSD(final BigDecimal amount) {
        return amount.multiply(EUR_TO_USD_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal CADtoUSD(final BigDecimal amount) {
        return amount.multiply(CAD_TO_USD_RATE).setScale(2, RoundingMode.HALF_UP);
    }


}
