package it.polimi.ingsw.gc26.model.utils;

public enum SpecialCharacters {
    CLEAR_TERMINAL("\033[H\033[2J"),
    AMPHORA("\uD83C\uDFFA"), //🏺
    BACKGROUND_BLANK_MEDIUM("  "),
    BACKGROUND_BLANK_WIDE("   "),
    BACKGROUND_BLUE("\uD83D\uDD35"), //🔵
    BACKGROUND_GREEN("\uD83D\uDFE2"), //🟢
    BACKGROUND_PURPLE("\uD83D\uDFE3"), //🟣
    BACKGROUND_BROWN("\uD83D\uDFEB"), //🟫
    BACKGROUND_BROWN_DARK("\uD83D\uDFE4"), //🟤
    BACKGROUND_GREY("✖\uFE0F"), //✖️
    BACKGROUND_RED("❗"),
    BACKGROUND_YELLOW("\uD83D\uDFE1"), //🟡
    BLOCKED_POSITION("✖️"),
    BUTTERFLY("\uD83E\uDD8B"), //🦋
    CIRCLE_WHITE("⚪"),
    CUP("\uD83C\uDFC6"), //🏆
    FEATHER("\uD83E\uDEB6"), //🪶
    LEAF("\uD83C\uDF3F"), //🌿
    MUSHROOM("🍄"),
    ORANGE_DIAMOND("🔸"),
    SHEET("\uD83D\uDCDC"), //📜
    SQUARE_BLACK ("▪️"),
    SQUARE_BLACK_LARGE("⬛"),
    SQUARE_WHITE("▫️"),
    WHITE_VERTICAL_STRING("❕"),
    WOLF("\uD83D\uDC3A"); //🐺

    private final String character;

    SpecialCharacters(String character){
        this.character = character;
    }

    public String getCharacter(){
        return this.character;
    }
}
