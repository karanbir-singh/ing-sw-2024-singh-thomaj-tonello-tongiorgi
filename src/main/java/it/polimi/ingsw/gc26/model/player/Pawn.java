package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.*;

/**
 * This is an enumeration of the possible values for a pawn which represent the player in the board
 */
public enum Pawn implements Serializable {
    RED(TextStyle.RED.getStyleCode()),
    GREEN(TextStyle.GREEN.getStyleCode()),
    BLUE(TextStyle.BLUE.getStyleCode()),
    YELLOW(TextStyle.YELLOW.getStyleCode());

    private final String fontColor;

    Pawn(String fontColor) { this.fontColor = fontColor; }

    public String getFontColor(){ return this.fontColor; }
}
