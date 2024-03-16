package it.polimi.ingsw.gc26.model.card_side;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class CardBack extends Side {

    public CardBack(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, permanentResources, requestedResources, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }
}
