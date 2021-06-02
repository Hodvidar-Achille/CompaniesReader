package com.hodvidar.cr.utils.numeric;

import java.math.BigDecimal;

public class NumericalConverter {

    private static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
    private static final BigDecimal ONE_MILLION = new BigDecimal("1000000");
    private static final BigDecimal ONE_BILLION = new BigDecimal("1000000000");

    private NumericalConverter() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns the numerical double corresponding to the given string.<br/>
     * 2.86k --> 2860 <br/>
     * 3.14M --> 3140000 <br/>
     * 4.76B --> 4760000000 <br/>
     */
    public static BigDecimal parseNumericalValue(final String numericalString) {
        if (null == numericalString
                || numericalString.isEmpty()
                || numericalString.isBlank()) {
            return new BigDecimal(0);
        }
        try {
            return new BigDecimal(numericalString);
        } catch (final Exception e) {
            // empty, continue
        }
        final int lastIndex = numericalString.length() - 1;
        final String exponent = numericalString.substring(lastIndex);
        final String numericalPart = numericalString.substring(0, lastIndex);
        return parseNumericalValue(numericalPart, exponent);
    }

    public static BigDecimal parseNumericalValue(final String numericalString, final String exponentString) {
        final char exponent = exponentString.charAt(0);
        BigDecimal numericalValue = new BigDecimal(numericalString);
        try {
            numericalValue = multiplyByExponent(exponent, numericalValue);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cannot parse '" + numericalString + "'");
        }
        numericalValue = numericalValue.stripTrailingZeros();
        return numericalValue;
    }

    private static BigDecimal multiplyByExponent(char exponent, BigDecimal number) {
        switch (exponent) {
            case 'k', 'K':
                return number.multiply(ONE_THOUSAND);
            case 'M':
                return number.multiply(ONE_MILLION);
            case 'B':
                return number.multiply(ONE_BILLION);
            case 'u':
                return number;
            default:
                throw new IllegalArgumentException("Cannot parse '" + exponent + "'");
        }
    }

}
