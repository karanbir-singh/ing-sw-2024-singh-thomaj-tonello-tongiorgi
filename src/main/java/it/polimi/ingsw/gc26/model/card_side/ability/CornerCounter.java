package it.polimi.ingsw.gc26.model.card_side.ability;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.GoldCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;
import java.util.Map;

public class CornerCounter extends GoldCardFront {
    public CornerCounter(Symbol sideSymbol, Map<Symbol, Integer> requestedResources, int points, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, requestedResources, points, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }

    @Override
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point p) {
        int corners = 0;
        for (Point p1 : occupiedPositions) {
            if (p1.getX() == p.getX() + 1 && p1.getY() == p.getY() + 1) {
                corners++;
            } else if (p1.getX() == p.getX() - 1 && p1.getY() == p.getY() + 1) {
                corners++;
            } else if (p1.getX() == p.getX() - 1 && p1.getY() == p.getY() - 1) {
                corners++;
            } else if (p1.getX() == p.getX() + 1 && p1.getY() == p.getY() - 1) {
                corners++;
            }
        }
        return corners * 2;
    }
}
