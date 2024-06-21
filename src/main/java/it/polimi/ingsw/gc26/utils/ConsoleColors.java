package it.polimi.ingsw.gc26.utils;

public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String RED_BRIGHT = "\033[0;91m";    // RED

    // Bold High Intensity
    public static void printError(String message) {
        System.out.println(RED_BRIGHT + message + RESET);
    }
}