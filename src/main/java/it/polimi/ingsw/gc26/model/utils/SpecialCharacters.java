package it.polimi.ingsw.gc26.model.utils;

/**
 * This enumeration represents the characters used to print the objects in the TUI.
 */
public enum SpecialCharacters {
    /**
     * This attribute represents the character to clear the terminal.
     */
    CLEAR_TERMINAL("\033[H\033[2J"),
    /**
     * This attribute represents the amphora character.
     */
    AMPHORA("\uD83C\uDFFA"), //🏺
    /**
     * This attribute represents the white background character.
     */
    BACKGROUND_BLANK_WIDE("   "),
    /**
     * This attribute represents the blue background character.
     */
    BACKGROUND_BLUE("\uD83D\uDD35"), //🔵
    /**
     * This attribute represents the green background character.
     */
    BACKGROUND_GREEN("\uD83D\uDFE2"), //🟢
    /**
     * This attribute represents the purple background character.
     */
    BACKGROUND_PURPLE("\uD83D\uDFE3"), //🟣
    /**
     * This attribute represents the red background character.
     */
    BACKGROUND_RED("❗"),
    /**
     * This attribute represents the yellow background character.
     */
    BACKGROUND_YELLOW("\uD83D\uDFE1"), //🟡
    /**
     * This attribute represents the blocked position character.
     */
    BLOCKED_POSITION("✖️"),
    /**
     * This attribute represents the butterfly character.
     */
    BUTTERFLY("\uD83E\uDD8B"), //🦋
    /**
     * This attribute represents the cup character.
     */
    CUP("\uD83C\uDFC6"), //🏆
    /**
     * This attribute represents the feather character.
     */
    FEATHER("\uD83E\uDEB6"), //🪶
    /**
     * This attribute represents the leaf character.
     */
    LEAF("\uD83C\uDF3F"), //🌿
    /**
     * This attribute represents the mushroom character.
     */
    MUSHROOM("🍄"),
    /**
     * This attribute represents the orange diamond character.
     */
    ORANGE_DIAMOND("🔸"),
    /**
     * This attribute represents the manuscript sheet character.
     */
    SHEET("\uD83D\uDCDC"), //📜
    /**
     * This attribute represents the black square character.
     */
    SQUARE_BLACK("\u001B[30m⬛\u001B[0m"),
    /**
     * This attribute represents the white square character.
     */
    SQUARE_WHITE("▫️"),
    /**
     * This attribute represents the white large square character, used to represent the corner in the card's details in the hand and common table.
     */
    SQUARE_WHITE_LARGE("▫️"),
    /**
     * This attribute represents the white vertical pipe character.
     */
    WHITE_VERTICAL_STRING("❕"),
    /**
     * This attribute represents the wolf character.
     */
    WOLF("\uD83D\uDC3A"); //🐺

    /**
     * This attribute represents the symbol's character.
     */
    private final String character;

    /**
     * Constructor for the enumeration characters.
     *
     * @param character
     */
    SpecialCharacters(String character) {
        this.character = character;
    }

    /**
     * This method returns the symbol's character.
     *
     * @return
     */
    public String getCharacter() {
        return this.character;
    }
}
