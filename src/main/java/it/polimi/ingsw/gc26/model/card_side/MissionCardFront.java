package it.polimi.ingsw.gc26.model.card_side;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;
public abstract class MissionCardFront extends Side {

    public MissionCardFront(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, permanentResources, requestedResources, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }
}
