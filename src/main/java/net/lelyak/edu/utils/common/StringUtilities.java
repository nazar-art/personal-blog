package net.lelyak.edu.utils.common;

import java.util.Formatter;

/**
 * @author Nazar Lelyak.
 */
public final class StringUtilities {

    public static final String NEW_LINE = System.getProperty("line.separator");

    private StringUtilities() {
    }

    public static String appendStrings(String message, Object... params) {
        try (Formatter format = new Formatter()) {
            return format.format(message, params).toString();
        }
    }

    public static String buildString(Object... args) {
        StringBuilder blr = new StringBuilder();

        for (Object arg : args) {
            blr.append(arg);
        }

        return blr.toString();
    }


    public static boolean textFound(String searchedValue, String[] options) {
        boolean result = false;

        for (String option : options) {
            if (option.equals(searchedValue)) {
                result = true;
                break;
            }
        }
        return result;
    }


    public static String appendStringsWithSeparator(String root, String separator, String... options) {

        StringBuilder sb = new StringBuilder();
        int optionsCount = options.length;
        int processedOption = 1;

        for (String option : options) {
            try (Formatter format = new Formatter()) {
                sb.append(format.format(root, option).toString());

                if (processedOption < optionsCount)
                    sb.append(separator);
                processedOption++;
            }
        }
        return sb.toString();
    }
}
