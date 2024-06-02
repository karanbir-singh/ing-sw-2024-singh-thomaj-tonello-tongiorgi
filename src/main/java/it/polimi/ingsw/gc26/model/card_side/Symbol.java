package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol implements Serializable {
    FUNGI(SpecialCharacters.MUSHROOM.getCharacter(),SpecialCharacters.BACKGROUND_RED.getCharacter(),TextStyle.RED.getStyleCode(), TextStyle.BACKGROUND_RED.getStyleCode()),
    ANIMAL(SpecialCharacters.WOLF.getCharacter(), SpecialCharacters.BACKGROUND_BLUE.getCharacter(), TextStyle.BLUE.getStyleCode(), TextStyle.BACKGROUND_BLUE.getStyleCode()),
    PLANT(SpecialCharacters.LEAF.getCharacter(), SpecialCharacters.BACKGROUND_GREEN.getCharacter(), TextStyle.GREEN.getStyleCode(), TextStyle.BACKGROUND_GREEN.getStyleCode()),
    INSECT(SpecialCharacters.BUTTERFLY.getCharacter(), SpecialCharacters.BACKGROUND_PURPLE.getCharacter(),TextStyle.PURPLE.getStyleCode(),TextStyle.BACKGROUND_PURPLE.getStyleCode()),
    INKWELL(SpecialCharacters.AMPHORA.getCharacter(), "", "",TextStyle.BACKGROUND_BEIGE.getStyleCode()),
    QUILL(SpecialCharacters.FEATHER.getCharacter(), "", "",TextStyle.BACKGROUND_BEIGE.getStyleCode()),
    MANUSCRIPT(SpecialCharacters.SHEET.getCharacter(), "", "",TextStyle.BACKGROUND_BEIGE.getStyleCode());
    private final String alias;
    private final String filler;
    private final String fontColor;
    private final String background;

    Symbol(String alias, String filler, String fontColor, String background) {
        this.alias = alias;
        this.filler = filler;
        this.fontColor = fontColor;
        this.background = background;
    }

    public String getAlias() {
        return alias;
    }

    public String getFiller() {return filler;}

    public String getFontColor() {return fontColor;}

    public String getBackground(){
        return background;
    }
}
