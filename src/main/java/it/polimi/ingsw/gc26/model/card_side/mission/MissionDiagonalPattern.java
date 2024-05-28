package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a mission card with a Diagonal pattern.
 */
public class MissionDiagonalPattern extends MissionCardFront implements Serializable {
    /**
     * Creates a new instance of MissionDiagonalPattern
     *
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
     *
     * @param visibleResources  Player's visible resources in the board
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
}
