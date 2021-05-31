package com.hodvidar.cr.utils.text;

public class StringUtils {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    // You could split your string at the letter-digit marks, like so:
    private static final String REGEX_TO_SPLIT_LETTERS_AND_DIGITS = "(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)";

    public static String[] splitLettersAndDigits(final String input) {
        return input.split(REGEX_TO_SPLIT_LETTERS_AND_DIGITS);
    }

}
