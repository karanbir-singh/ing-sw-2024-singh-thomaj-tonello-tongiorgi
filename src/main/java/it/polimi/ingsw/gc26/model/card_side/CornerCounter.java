package it.polimi.ingsw.gc26.model.card_side;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public class CornerCounter extends GoldCardFront {

    public CornerCounter(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, permanentResources, requestedResources, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
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
