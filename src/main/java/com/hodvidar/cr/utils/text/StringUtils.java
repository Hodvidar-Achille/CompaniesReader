package com.hodvidar.cr.utils.text;

public class StringUtils {

    private static final String REGEX_TO_SPLIT_LETTERS_AND_DIGITS = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static String[] splitLettersAndDigits(final String input) {
        return input.split(REGEX_TO_SPLIT_LETTERS_AND_DIGITS);
    }

    public static String[] splitCurrencyAmountAndExponent(final String input) {
        final String[] split = splitLettersAndDigits(input);
        if (split.length == 3) {
            return split;
        }
        if (split.length == 2) {
            final String[] splitAdapted = new String[3];
            splitAdapted[0] = split[0];
            splitAdapted[1] = split[1];
            splitAdapted[2] = "u";
            return splitAdapted;
        }
        final String[] splitAdapted = new String[3];
        splitAdapted[0] = split[0];
        splitAdapted[2] = split[split.length - 1];
        String amount = "";
        for (int i = 1; i < split.length - 1; i++) {
            amount += split[i];
        }
        splitAdapted[1] = amount;
        return splitAdapted;
    }

    /**
     * http://www.lightsurf.com/solutions/mmsc.html
     * -->
     * www.lightsurf.com
     */
    public static String sanitizeDomain(final String domainRaw) {
        String domain = domainRaw.replaceAll("http://", "");
        domain = domain.replaceAll("https://", "");
        return domain.split("/")[0];
    }

    public static String alignLeft(final String string, final int size) {
        return alignLeft(string, size, ' ');
    }

    public static String alignLeft(final String string, final int size, final char pad) {
        if (string == null || size <= string.length()) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(size);
        sb.append(string);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }

}
