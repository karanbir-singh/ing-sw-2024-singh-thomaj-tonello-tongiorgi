package it.polimi.ingsw.gc26.model.card_side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResourceCardFront extends Side {

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
