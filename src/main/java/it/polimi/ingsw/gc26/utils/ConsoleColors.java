package it.polimi.ingsw.gc26.utils;

/**
 * The ConsoleColors class provides ANSI escape codes for colored text in console output.
 * It includes constants for resetting colors and printing error messages in bright red.
 */
public class ConsoleColors {
    /**
     * ANSI escape code to reset text color to default.
     */
    public static final String RESET = "\033[0m";
    /**
     * ANSI escape code for bright red text.
     */
    public static final String RED_BRIGHT = "\033[0;91m";
    /**
     * Prints the specified error message in bright red color to the console.
     *
     * @param message the error message to be printed.
     */
    public static void printError(String message) {
        System.out.println(RED_BRIGHT + message + RESET);
    }
}
