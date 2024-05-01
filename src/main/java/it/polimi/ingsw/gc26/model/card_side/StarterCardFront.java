package it.polimi.ingsw.gc26.model.card_side;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a Starter Card
 */
public class StarterCardFront extends Side implements Serializable {
    /**
     * Creates a new instance of a ResourceCardFront
     * @param permanentResources Resources that cannot be covered by other cards
     * @param UPLEFT Symbol in the up left corner.
     * @param DOWNLEFT Symbol in the down left corner.
     * @param UPRIGHT Symbol in the up right corner.
     * @param DOWNRIGHT Symbol in the down right corner.
     */
    public StarterCardFront(ArrayList<Symbol> permanentResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        setPermanentResources(permanentResources);
        setDOWNLEFT(DOWNLEFT);
        setDOWNRIGHT(DOWNRIGHT);
        setUPLEFT(UPLEFT);
        setUPRIGHT(UPRIGHT);
        setRequestedResources(new HashMap<>());
        setType(0);
        setPoints(0);
        setSideSymbol(null);
    }
}
