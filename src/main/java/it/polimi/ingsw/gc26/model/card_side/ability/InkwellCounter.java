package it.polimi.ingsw.gc26.model.card_side.ability;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.GoldCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;
import java.util.Map;

public class InkwellCounter extends GoldCardFront {
    public InkwellCounter(Symbol sideSymbol, Map<Symbol, Integer> requestedResources, int points, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, requestedResources, 1, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }

    @Override
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point p) {
        return resources.get(Symbol.INKWELL);
    }
}
