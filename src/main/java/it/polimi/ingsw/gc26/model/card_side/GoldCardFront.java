package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GoldCardFront extends Side {
    public GoldCardFront(Symbol sideSymbol, Map<Symbol, Integer> requestedResources, int points, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        setSideSymbol(sideSymbol);
        setPoints(points);
        setDOWNLEFT(DOWNLEFT);
        setDOWNRIGHT(DOWNRIGHT);
        setUPLEFT(UPLEFT);
        setUPRIGHT(UPRIGHT);
        setRequestedResources(new HashMap<>(requestedResources));

        setPermanentResources(new ArrayList<>());
        setType(0);
    }
}
