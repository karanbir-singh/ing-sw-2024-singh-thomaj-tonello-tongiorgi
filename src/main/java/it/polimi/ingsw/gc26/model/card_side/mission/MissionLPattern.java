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
 * This class represents a mission card with an L pattern.
 */
public class MissionLPattern extends MissionCardFront implements Serializable {
    /**
     * Creates a new instance of MissionLPattern
     * @param type represent which L combination is needed to this card give points
     */
    public MissionLPattern(int type) {
        setType(type);
        setPoints(0);
        setSideSymbol(null);
        setDOWNLEFT(new Corner(true, null));
        setDOWNRIGHT(new Corner(true, null));
        setUPLEFT(new Corner(true, null));
        setUPRIGHT(new Corner(true, null));
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
    }

    /**
     * This method returns the extra points that are awarded considering the card position in the Player's board.
     * @param visibleResources Player's visible resources in the board
     * @param occupiedPositions list of the position occupied in the Player's board
     * @return points given by this card
     */
    @Override
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1) {
            points = calculatePoints(-1, 1, -1, 3, occupiedPositions, Symbol.PLANT, Symbol.FUNGI, 4, 3);
        } else if (getType() == 2) {
            points = calculatePoints(1, 1, 1, 3, occupiedPositions, Symbol.INSECT, Symbol.PLANT, 5, 3);
        } else if (getType() == 3) {
            points = calculatePoints(-1, -1, -1, -3, occupiedPositions, Symbol.FUNGI, Symbol.ANIMAL, 6, 3);
        } else if (getType() == 4) {
            points = calculatePoints(1, -1, 1, -3, occupiedPositions, Symbol.ANIMAL, Symbol.INSECT, 7, 3);
        }
        return points;
    }

    /**
     * Creates a String matrix with a printable representation of the side
     * @return String[][] s
     */
    @Override
    public String[][] printableSide(){
        String[][] s = new String[5][3];

        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String decoration = SpecialCharacters.CUP.getCharacter();
        String diamond = SpecialCharacters.ORANGE_DIAMOND.getCharacter();
        String fontColor = TextStyle.YELLOW.getStyleCode();
        String whiteBackground = TextStyle.BACKGROUND_WHITE.getStyleCode();
        String alias;
        String secondAlias;
        String background;

        //fill borders
        s[0][0] = fontColor + " ╔";
        s[0][2] = "╗ ";
        s[0][1] = "═════" + decoration + decoration + decoration + "═════";
        for(int i=1; i<4; i++){
            s[i][0] = diamond + " ";
            s[i][2] = " " + diamond ;
        }
        s[4][0] = " ╚";
        s[4][1] = "═════" + diamond + diamond + diamond + "═════";
        s[4][2] = "╝ ";

        //fill center based on the card's type
        if(getType() == 1){
            alias = Symbol.FUNGI.getAlias();
            background = Symbol.FUNGI.getBackground();
            secondAlias = Symbol.PLANT.getAlias();

            for(int i=0; i<5; i++){
                s[i][0] = fontColor + background + s[i][0];
                s[i][2] = s[i][2] + styleReset;
            }
            s[1][1] = "   " + whiteBackground + "[" + alias + "]" + background + "       ";
            s[2][1] = "   " + whiteBackground + "[" + alias + "]" + background + "       ";
            s[3][1] = "      " + whiteBackground + "[" + secondAlias + "]" + background + "    ";
        }

        if(getType() == 2){
            alias = Symbol.PLANT.getAlias();
            background = Symbol.PLANT.getBackground();
            secondAlias = Symbol.INSECT.getAlias();

            for(int i=0; i<5; i++){
                s[i][0] = fontColor + background + s[i][0];
                s[i][2] = s[i][2] + styleReset;
            }
            s[1][1] = "       " + whiteBackground + "[" + alias + "]" + background + "   ";
            s[2][1] = "       " + whiteBackground + "[" + alias + "]" + background + "   ";
            s[3][1] = "    " + whiteBackground + "[" + secondAlias + "]" + background + "      ";
        }

        if(getType() == 3){
            alias = Symbol.ANIMAL.getAlias();
            background = Symbol.ANIMAL.getBackground();
            secondAlias = Symbol.FUNGI.getAlias();
            for(int i=0; i<5; i++){
                s[i][0] = fontColor + background + s[i][0];
                s[i][2] = s[i][2] + styleReset;
            }
            s[1][1] = "      " + whiteBackground + "[" + secondAlias + "]" + background + "    ";
            s[3][1] = "   " + whiteBackground + "[" + alias + "]" + background + "       ";
            s[2][1] = "   " + whiteBackground + "[" + alias + "]" + background + "       ";
        }

        if(getType() == 4){
            alias = Symbol.INSECT.getAlias();
            background = Symbol.INSECT.getBackground();
            secondAlias = Symbol.ANIMAL.getAlias();

            for(int i=0; i<5; i++){
                s[i][0] = fontColor + background + s[i][0];
                s[i][2] = s[i][2] + styleReset;
            }
            s[1][1] = "    " + whiteBackground + "[" + secondAlias + "]" + background + "      ";
            s[3][1] = "       " + whiteBackground + "[" + alias + "]" + background + "   ";
            s[2][1] = "       " + whiteBackground + "[" + alias + "]" + background + "   ";
        }

        return s;
    }
}
