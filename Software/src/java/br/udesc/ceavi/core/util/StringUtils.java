package br.udesc.ceavi.core.util;

/**
 * Miscellaneous utils methods for Strings.
 *
 * @author Samuel Felício Adriano <felicio.samuel@gmail.com>
 * @since  08/05/2016
 */
public class StringUtils {

    /**
     * Checks if a string is empty
     *
     * @param aString - string to be checked
     *
     * @return true if the String is null or has no value
     */
    public final static boolean isEmpty(final String aString) {
        boolean nullValue  = aString == null,
                emptyValue = aString.trim().equals("");

        return nullValue || emptyValue;
    }

    public final static String toFormat(final String aString, StringFormat format) {
        switch(format) {
            case CAMEL_CASE:
                return toCamelCase(aString);
            case BEAN:
                return toBeanFormat(aString);
        }

        throw new RuntimeException("Invalid Format");
    }

    /**
     * Converts the text to CamelCase
     *
     * @param text       - String to be formatted
     * @param firstUpper - boolean that indicates if the first Char must be in UpperCase
     *
     * @return String - formatted text
     */
    public final static String toCamelCase(String text, boolean firstUpper) {
        StringBuilder formatted = new StringBuilder();
        boolean       lower     = false;
        char          delimiter = '_';

        if(firstUpper) {
            text = Character.toUpperCase(text.charAt(0)) + text.substring(1);
        }

        for(int charIndex = 0; charIndex < text.length(); charIndex++) {
            final char currentChar = text.charAt(charIndex);

            if(currentChar == delimiter) {
                lower = false;
            } else if(lower) {
                formatted.append(Character.toLowerCase(currentChar));
            } else {
                formatted.append(Character.toUpperCase(currentChar));
                lower = true;
            }
        }

        return formatted.toString();
    }

    /**
     * Converts the text to CamelCase. The first char won't be in UpperCase
     *
     * @param text - String to be formatted
     *
     * @return String - formatted text
     */
    public final static String toCamelCase(String text) {
        return toCamelCase(text, false);
    }


    /**
     * Converts the text to CamelCase. The first char won't be in UpperCase
     *
     * @param text - String to be formatted
     *
     * @return String - formatted text
     */
    public final static String toBeanFormat(String text) {
        return text.replaceAll("_", ".");
    }

}