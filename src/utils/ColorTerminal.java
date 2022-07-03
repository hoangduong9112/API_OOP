package utils;
public class ColorTerminal {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";

    private ColorTerminal() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getAnsiReset() {
        return ANSI_RESET;
    }

    public static String getAnsiGreen() {
        return ANSI_GREEN;
    }

    public static String getAnsiBlue() {
        return ANSI_BLUE;
    }

    public static String getAnsiRed() {
        return ANSI_RED;
    }

}
