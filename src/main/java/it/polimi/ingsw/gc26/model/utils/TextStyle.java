package it.polimi.ingsw.gc26.model.utils;

/**
 * This enumeration represents the text styles used for the TUI.
 */
public enum TextStyle {
    /**
     * This attribute represent the background beige character.
     */
    BACKGROUND_BEIGE("\u001B[48;2;242;233;175m"),
    /**
     * This attribute represent the background black character.
     */
    BACKGROUND_BLACK("\u001B[48;2;0;0;0m"),
    /**
     * This attribute represent the background blue character.
     */
    BACKGROUND_BLUE("\u001B[48;2;0;116;186m"),
    /**
     * This attribute represent the background green character.
     */
    BACKGROUND_GREEN("\u001B[48;2;0;210;106m"),
    /**
     * This attribute represent the background purple character.
     */
    BACKGROUND_PURPLE("\u001B[48;2;141;101;197m"),
    /**
     * This attribute represent the background red character.
     */
    BACKGROUND_RED("\u001B[48;2;248;49;47m"),
    /**
     * This attribute represent the background yellow character.
     */
    BACKGROUND_YELLOW("\u001B[48;2;255;209;56m"),
    /**
     * This attribute represent the background white character.
     */
    BACKGROUND_WHITE("\u001B[48;2;255;255;255m"),
    /**
     * This attribute represent the background black character.
     */
    BLACK("\u001B[30m"),
    /**
     * This attribute represent the blue character.
     */
    BLUE("\u001B[34m"),
    /**
     * This attribute represent the green character.
     */
    GREEN("\u001B[32m"),
    /**
     * This attribute represent the purple character.
     */
    PURPLE("\u001B[35m"),
    /**
     * This attribute represent the red character.
     */
    RED("\u001B[31m"),
    /**
     * This attribute represent the yellow character.
     */
    YELLOW("\33[93m"),
    /**
     * This attribute represent the character used to reset the style to the default one.
     */
    STYLE_RESET("\u001B[0m");

    /**
     * This attribute represents the style code.
     */
    private final String styleCode;

    /**
     * Constructor for text style enumeration.
     *
     * @param styleCode
     */
    TextStyle(String styleCode) {
        this.styleCode = styleCode;
    }

    /**
     * Return the style code
     *
     * @return ansi code character
     */
    public String getStyleCode() {
        return this.styleCode;
    }
}
