package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

/**
 * This class represents a mission card with a triplet pattern.
 */
public class MissionTripletPattern extends MissionCardFront {
    /**
     * Creates a new instance of MissionTripletPattern
     * @param type represent which Triplet combination is needed to this card give points
     */
    public MissionTripletPattern(int type) {
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
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1) {
            points = points + 3 * Arrays.stream(new int[] {visibleResources.get(Symbol.INKWELL),
                    visibleResources.get(Symbol.QUILL),
                    visibleResources.get(Symbol.MANUSCRIPT)}).min().getAsInt();
        } else if (getType() == 2) {
            points = points + 2 * (visibleResources.get(Symbol.MANUSCRIPT) / 2);
        } else if (getType() == 3) {
            points = points + 2 * (visibleResources.get(Symbol.QUILL) / 2);
        } else if (getType() == 4) {
            points = points + 2 * (visibleResources.get(Symbol.INKWELL) / 2);
        }
        return points;
    }
}
