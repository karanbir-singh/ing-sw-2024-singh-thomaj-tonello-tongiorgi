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
 * This class represents a mission card with an Item pattern.
 */
public class MissionItemPattern extends MissionCardFront implements Serializable {
    /**
     * Creates a new instance of MissionItemPattern
     *
     * @param type represent which item combination is needed to this card give points
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
}
