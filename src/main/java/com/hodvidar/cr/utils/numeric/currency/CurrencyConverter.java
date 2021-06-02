package com.hodvidar.cr.utils.numeric.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * For the exercise the currencies different from the USD will be converted
 * using the exchange rate from the 1st january 2021.
 */
public class CurrencyConverter {

    private static final BigDecimal GBP_TO_USD_RATE = BigDecimal.valueOf(1.3673);
    private static final BigDecimal EUR_TO_USD_RATE = BigDecimal.valueOf(1.2134);
    private static final BigDecimal CAD_TO_USD_RATE = BigDecimal.valueOf(0.7855);

    private CurrencyConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static BigDecimal toUSD(final String currency, final BigDecimal amount) {
        switch (currency) {
            case "£":
                return fromGBPtoUSD(amount);
            case "€":
                return fromEURtoUSD(amount);
            case "C$":
                return fromCADtoUSD(amount);
            default:
                return amount.setScale(2, RoundingMode.HALF_UP);
        }
    }

    public static BigDecimal fromGBPtoUSD(final BigDecimal amount) {
        return amount.multiply(GBP_TO_USD_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal fromEURtoUSD(final BigDecimal amount) {
        return amount.multiply(EUR_TO_USD_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal fromCADtoUSD(final BigDecimal amount) {
        return amount.multiply(CAD_TO_USD_RATE).setScale(2, RoundingMode.HALF_UP);
    }


}
