package it.polimi.ingsw.gc26.model.card_side;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class GoldCardFront extends Side {

    public GoldCardFront(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, permanentResources, requestedResources, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }

    @Override
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point p) {
        return 0;
    }
}
