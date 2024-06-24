package it.polimi.ingsw.gc26.model.card_side;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a Gold Card Front
 */
public class GoldCardFront extends Side implements Serializable {
    /**
     * Creates a new instance of a GoldCardFront
     *
     * @param sideSymbol         Symbol that represents the card's color.
     * @param requestedResources Resources that must be available in the Player's board to be able to play this card.
     * @param points             Points given by this card
     * @param UPLEFT             Symbol in the up left corner.
     * @param DOWNLEFT           Symbol in the down left corner.
     * @param UPRIGHT            Symbol in the up right corner.
     * @param DOWNRIGHT          Symbol in the down right corner.
     * @param imagePath path to corresponding image
     */
    public GoldCardFront(Symbol sideSymbol, Map<Symbol, Integer> requestedResources, int points, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT, String imagePath) {
        setSideSymbol(sideSymbol);
        setPoints(points);
        setDOWNLEFT(DOWNLEFT);
        setDOWNRIGHT(DOWNRIGHT);
        setUPLEFT(UPLEFT);
        setUPRIGHT(UPRIGHT);
        setRequestedResources(new HashMap<>(requestedResources));
        setPermanentResources(new ArrayList<>());
        setType(0);
        setImagePath(imagePath);
    }
}
