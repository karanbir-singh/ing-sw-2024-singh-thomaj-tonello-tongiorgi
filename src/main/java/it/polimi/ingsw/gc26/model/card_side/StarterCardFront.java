package it.polimi.ingsw.gc26.model.card_side;

import java.util.ArrayList;
import java.util.HashMap;

public class StarterCardFront extends Side {

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
