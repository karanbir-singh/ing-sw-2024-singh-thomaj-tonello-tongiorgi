package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.utils.TextStyle;
import java.io.Serializable;

/**
 * This is an enumeration of the possible values for a pawn which represent the player in the board
 */
public enum Pawn implements Serializable {
    /**
     * This enumeration represents the red pawn.
     */
    RED(TextStyle.RED.getStyleCode()),
    /**
     * This enumeration represents the green pawn.
     */
    GREEN(TextStyle.GREEN.getStyleCode()),
    /**
     * This enumeration represents the blue pawn.
     */
    BLUE(TextStyle.BLUE.getStyleCode()),
    /**
     * This enumeration represents the yellow pawn.
     */
    YELLOW(TextStyle.YELLOW.getStyleCode());

    /**
     * This attribute represents the pawn's font color.
     */
    private final String fontColor;

    /**
     * Constructor for pawn enumeration.
     * @param fontColor
     */
    Pawn(String fontColor) { this.fontColor = fontColor; }

    /**
     * This method returm the pawn's font color.
     * @return
     */
    public String getFontColor(){ return this.fontColor; }
}
