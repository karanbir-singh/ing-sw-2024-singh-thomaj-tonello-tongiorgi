package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;

/**
 * This is an enumeration of the possibles values of a symbol
 */
public enum Symbol implements Serializable {
    /**
     * Fungi character, color red .
     */
    FUNGI(SpecialCharacters.MUSHROOM.getCharacter(), SpecialCharacters.BACKGROUND_RED.getCharacter(), TextStyle.RED.getStyleCode(), TextStyle.BACKGROUND_RED.getStyleCode()),
    /**
     * Animal character, color blue.
     */
    ANIMAL(SpecialCharacters.WOLF.getCharacter(), SpecialCharacters.BACKGROUND_BLUE.getCharacter(), TextStyle.BLUE.getStyleCode(), TextStyle.BACKGROUND_BLUE.getStyleCode()),
    /**
     * Plant character, color green.
     */
    PLANT(SpecialCharacters.LEAF.getCharacter(), SpecialCharacters.BACKGROUND_GREEN.getCharacter(), TextStyle.GREEN.getStyleCode(), TextStyle.BACKGROUND_GREEN.getStyleCode()),
    /**
     * Insect character, color purple.
     */
    INSECT(SpecialCharacters.BUTTERFLY.getCharacter(), SpecialCharacters.BACKGROUND_PURPLE.getCharacter(), TextStyle.PURPLE.getStyleCode(), TextStyle.BACKGROUND_PURPLE.getStyleCode()),
    /**
     * Inkwell character, similar to a vase or an amphora.
     */
    INKWELL(SpecialCharacters.AMPHORA.getCharacter(), "", "", TextStyle.BACKGROUND_BEIGE.getStyleCode()),
    /**
     * Quill character, similar to a feather.
     */
    QUILL(SpecialCharacters.FEATHER.getCharacter(), "", "", TextStyle.BACKGROUND_BEIGE.getStyleCode()),
    /**
     * Manuscript character, similar to a manuscript sheet.
     */
    MANUSCRIPT(SpecialCharacters.SHEET.getCharacter(), "", "", TextStyle.BACKGROUND_BEIGE.getStyleCode());
    /**
     * Alias for the symbol.
     */
    private final String alias;
    /**
     * Filler for the symbol.
     */
    private final String filler;
    /**
     * Font color for the symbol.
     */
    private final String fontColor;
    /**
     * Background for the symbol.
     */
    private final String background;

    /**
     * Create the enumeration
     *
     * @param alias
     * @param filler
     * @param fontColor
     * @param background
     */
    Symbol(String alias, String filler, String fontColor, String background) {
        this.alias = alias;
        this.filler = filler;
        this.fontColor = fontColor;
        this.background = background;
    }

    /**
     * Returns symbol's alias.
     *
     * @return alias attribute
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Returns symbol's filler.
     *
     * @return filler attribute
     */
    public String getFiller() {
        return filler;
    }

    /**
     * Returns symbol's font color.
     *
     * @return font color attribute
     */
    public String getFontColor() {
        return fontColor;
    }

    /**
     * Returns symbol's background.
     *
     * @return background attribute
     */
    public String getBackground() {
        return background;
    }
}
