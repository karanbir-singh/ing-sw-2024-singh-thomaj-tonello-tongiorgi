package it.polimi.ingsw.gc26.model.card_side;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class represent a Resource Card
 */
public class ResourceCardFront extends Side implements Serializable {
    /**
     * Creates a new instance of a ResourceCardFront
     * @param sideSymbol Symbol that represents the card's color.
     * @param points Points given by this card
     * @param UPLEFT Symbol in the up left corner.
     * @param DOWNLEFT Symbol in the down left corner.
     * @param UPRIGHT Symbol in the up right corner.
     * @param DOWNRIGHT Symbol in the down right corner.
     */
    public ResourceCardFront(Symbol sideSymbol, int points, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        setSideSymbol(sideSymbol);
        setPoints(points);
        setDOWNLEFT(DOWNLEFT);
        setDOWNRIGHT(DOWNRIGHT);
        setUPLEFT(UPLEFT);
        setUPRIGHT(UPRIGHT);
        setRequestedResources(new HashMap<>());
        setPermanentResources(new ArrayList<>());
        setType(0);
    }
}
