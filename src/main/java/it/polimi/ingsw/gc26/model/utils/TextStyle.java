package it.polimi.ingsw.gc26.model.utils;

public enum TextStyle {
    BACKGROUND_BEIGE("\u001B[48;2;242;233;175m"),
    BACKGROUND_BLUE("\u001B[48;2;0;116;186m"),
    BACKGROUND_GREEN("\u001B[48;2;0;210;106m"),
    BACKGROUND_PURPLE("\u001B[48;2;141;101;197m"),
    BACKGROUND_RED("\u001B[48;2;248;49;47m"),
    BACKGROUND_YELLOW("\u001B[48;2;255;209;56m"),
    BACKGROUND_WHITE("\u001B[48;2;255;255;255m"),
    BLUE("\u001B[34m"),
    GREEN("\u001B[32m"),
    PURPLE("\u001B[35m"),
    RED("\u001B[31m"),
    YELLOW("\33[93m"),
    STYLE_RESET("\u001B[0m");

    private final String styleCode;

    TextStyle(String styleCode){
        this.styleCode = styleCode;
    }

    public String getStyleCode(){
        return this.styleCode;
    }
}
