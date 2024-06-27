package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a mission card with an Item pattern.
 */
public class MissionItemPattern extends MissionCardFront implements Serializable {
    /**
     * Creates a new instance of MissionItemPattern
     *
     * @param type represent which item combination is needed to this card give points
     * @param imagePath path to corresponding image
     */
    public MissionItemPattern(int type, String imagePath) {
        setType(type);
        setPoints(0);
        setSideSymbol(null);
        setDOWNLEFT(new Corner(true, null));
        setDOWNRIGHT(new Corner(true, null));
        setUPLEFT(new Corner(true, null));
        setUPRIGHT(new Corner(true, null));
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
        setImagePath(imagePath);
    }

    /**
     * This method returns the extra points that are awarded considering the card position in the Player's board.
     *
     * @param visibleResources  Player's visible resources in the board
     * @param occupiedPositions list of the position occupied in the Player's board
     * @return points given by this card
     */
    @Override
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1) {
            points = points + 2 * (visibleResources.get(Symbol.FUNGI) / 3);
        } else if (getType() == 2) {
            points = points + 2 * (visibleResources.get(Symbol.PLANT) / 3);
        } else if (getType() == 3) {
            points = points + 2 * (visibleResources.get(Symbol.ANIMAL) / 3);
        } else if (getType() == 4) {
            points = points + 2 * (visibleResources.get(Symbol.INSECT) / 3);
        }
        return points;
    }

    /**
     * Creates a String matrix with a printable representation of the side
     *
     * @return String[][] s
     */
    @Override
    public String[][] printableSide() {
        String[][] s = new String[5][3];

        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String decoration = SpecialCharacters.CUP.getCharacter();
        String diamond = SpecialCharacters.ORANGE_DIAMOND.getCharacter();
        String alias = "";
        String background = "";
        String fontColor = TextStyle.YELLOW.getStyleCode();
        String whiteBackground = TextStyle.BACKGROUND_WHITE.getStyleCode();

        //fill borders
        s[0][0] = fontColor + " ╔";
        s[0][2] = "╗ ";
        s[0][1] = "═════" + decoration + diamond + decoration + "═════";
        for (int i = 1; i < 4; i++) {
            s[i][0] = diamond + " ";
            s[i][2] = " " + diamond;
        }
        s[4][0] = " ╚";
        s[4][1] = "═════" + diamond + diamond + diamond + "═════";
        s[4][2] = "╝ ";

        //fetch special characters and colors based on the card's type
        if (getType() == 1) {
            alias = Symbol.FUNGI.getAlias();
            background = Symbol.FUNGI.getBackground();
        } else if (getType() == 2) {
            alias = Symbol.PLANT.getAlias();
            background = Symbol.PLANT.getBackground();
        } else if (getType() == 3) {
            alias = Symbol.ANIMAL.getAlias();
            background = Symbol.ANIMAL.getBackground();
        } else if (getType() == 4) {
            alias = Symbol.INSECT.getAlias();
            background = Symbol.INSECT.getBackground();
        }

        //add font and background color
        for (int i = 0; i < 5; i++) {
            s[i][0] = fontColor + background + s[i][0];
            s[i][2] = s[i][2] + styleReset;
        }

        //fill center
        s[1][1] = "      " + whiteBackground + alias + background + "      ";
        s[2][1] = "      " + whiteBackground + alias + background + "      ";
        s[3][1] = "      " + whiteBackground + alias + background + "      ";

        return s;
    }
}
