package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

/**
 * This class represents a mission card with a Diagonal pattern.
 */
public class MissionDiagonalPattern extends MissionCardFront {
    /**
     * Creates a new instance of MissionDiagonalPattern
     * @param type represent which diagonal combination is needed to this card give points
     */
    public MissionDiagonalPattern(int type) {
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
     * This method returns the extra points that are awarded considering its position in the Player's board.
     * @param visibleResources Player's visible resources in the board
     * @param occupiedPositions list of the position occupied in the Player's board
     * @return points given by this card
     */
    @Override
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1) {
            points = calculatePoints(1, 1, 2, 2, occupiedPositions, Symbol.FUNGI, Symbol.FUNGI, 0, 2);
        } else if (getType() == 2) {
            points = calculatePoints(1, -1, 2, -2, occupiedPositions, Symbol.PLANT, Symbol.PLANT, 1, 2);
        } else if (getType() == 3) {
            points = calculatePoints(1, 1, 2, 2, occupiedPositions, Symbol.ANIMAL, Symbol.ANIMAL, 2, 2);
        } else if (getType() == 4) {
            points = calculatePoints(1, -1, 2, -2, occupiedPositions, Symbol.INSECT, Symbol.INSECT, 3, 2);
        }
        return points;
    }


    @Override
    public String[][] printableSide(){
        String[][] s = new String[5][3];
        String styleReset = "\u001B[0m";
        String decoration = Character.toString(0x1F3C6);
        String diamond = "\uD83D\uDD38";
        String alias;
        String background;
        String fontColor = "\33[93m";
        String whiteFont = "\u001B[37m";
        String whiteBackground = "\u001B[48;2;255;255;255m";
        String filler;


        s[0][0] = fontColor + " ╔";
        s[0][2] = "╗ ";
        s[0][1] = "═════" + decoration + diamond + decoration + "═════";
        for(int i=1; i<4; i++){
            s[i][0] = diamond + " ";
            s[i][2] = " " + diamond ;
        }
        s[4][0] = " ╚";
        s[4][1] = "═════" + diamond + diamond + diamond + "═════";
        s[4][2] = "╝ ";

        if(getType() == 1 || getType() == 3){
            if(getType() == 1){
                alias = Symbol.FUNGI.getAlias();
                background = Symbol.FUNGI.getBackground();
                //filler = Symbol.FUNGI.getFiller();
            } else {
                alias = Symbol.ANIMAL.getAlias();
                background = Symbol.ANIMAL.getBackground();
            }
            for(int i=0; i<5; i++){
                s[i][0] = fontColor + background + s[i][0];
                s[i][2] = s[i][2] + styleReset;
            }
            s[1][1] =  "        " + whiteBackground + "[" + alias + "]" + background + "  ";
            s[2][1] =  "     " + whiteBackground + "[" + alias + "]" + background +"     ";
            s[3][1] = "  " + whiteBackground + "[" + alias + "]" + background + "        ";
        }

        if(getType() == 2 || getType() == 4){
            if(getType() == 2){
                alias = Symbol.PLANT.getAlias();
                background = Symbol.PLANT.getBackground();
                //filler = Symbol.FUNGI.getFiller();
            } else {
                alias = Symbol.INSECT.getAlias();
                background = Symbol.INSECT.getBackground();
            }
            for(int i=0; i<5; i++){
                s[i][0] = fontColor + background + s[i][0];
                s[i][2] = s[i][2] + styleReset;
            }
            s[3][1] =  "        " + whiteBackground + "[" + alias + "]" + background + "  ";
            s[2][1] =  "     " + whiteBackground + "[" + alias + "]" + background +"     ";
            s[1][1] = "  " + whiteBackground + "[" + alias + "]" + background + "        ";
        }

        return s;
    }
}
