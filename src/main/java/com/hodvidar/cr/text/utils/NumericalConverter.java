package com.hodvidar.cr.text.utils;

import java.math.BigDecimal;

public class NumericalConverter {

    private static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
    private static final BigDecimal ONE_MILLION = new BigDecimal("1000000");
    private static final BigDecimal ONE_BILLION = new BigDecimal("1000000000");

    /**
     * Returns the numerical double corresponding to the given string.<br/>
     * 2.86k --> 2860 <br/>
     * 3.14M --> 3140000 <br/>
     * 4.76B --> 4760000000 <br/>
     */
    public static BigDecimal parseNumericalValue(String numericalString) {
        if(null == numericalString
                || numericalString.isEmpty()
                || numericalString.isBlank()) {
            return new BigDecimal(0);
        }
        try {
            return new BigDecimal(numericalString);
        } catch (final Exception e) {
            // empty, continue
        }
        final int lastIndex = numericalString.length()-1;
        final char exponent = numericalString.charAt(lastIndex);
        final String numericalPart = numericalString.substring(0, lastIndex);
        BigDecimal numericalValue = new BigDecimal(numericalPart);
        try {
            numericalValue = multiplyByExponent(exponent, numericalValue);
        } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Cannot parse '"+numericalString+"'");
        }
        numericalValue = numericalValue.stripTrailingZeros();
        return numericalValue;
    }

    private static BigDecimal multiplyByExponent(char exponent, BigDecimal number) {
        switch(exponent) {
            case 'k':
            case 'K':
                return number.multiply(ONE_THOUSAND);
            case 'M':
                return number.multiply(ONE_MILLION);
            case 'B' :
                return number.multiply(ONE_BILLION);
            default:
                throw new IllegalArgumentException("Cannot parse '"+exponent+"'");
        }
    }

    public static String removeDollar(String input) {
        if(input == null) {
            return null;
        }
        return input.replaceAll("[$]", "");
    }
}
