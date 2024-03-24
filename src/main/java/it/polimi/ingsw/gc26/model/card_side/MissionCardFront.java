package it.polimi.ingsw.gc26.model.card_side;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;
public abstract class MissionCardFront extends Side {
    @Override
    protected int calculatePoints(int firstX, int firstY, int secondX, int secondY, ArrayList<Point> occupiedPositions, Symbol diagSymbol, Symbol vertSymbol, int flag, int points) {
        int value = 0;
        Optional<Point> findFirst = Optional.empty();
        Optional<Point> findSecond = Optional.empty();
        for (Point p : occupiedPositions) {
            if (p.getSide().checkSideSymbol(diagSymbol) &&
                    !p.getFlag(flag)) {
                for (Point p1 : occupiedPositions) {
                    if (p1.getSide().checkSideSymbol(vertSymbol) &&
                            p1.getX() == p.getX() + firstX &&
                            p1.getY() == p.getY() + firstY &&
                            !p1.getFlag(flag)) {
                        findFirst = Optional.of(p1);
                    }
                }
                for (Point p2 : occupiedPositions) {
                    if (p2.getSide().checkSideSymbol(vertSymbol) &&
                            p2.getX() == p.getX() + secondX &&
                            p2.getY() == p.getY() + secondY &&
                            !p2.getFlag(flag)) {
                        findSecond = Optional.of(p2);

                    }
                }
                if (findFirst.isPresent() && findSecond.isPresent()) {
                    value = value + points;
                    findFirst.get().setFlag(flag, true);
                    findSecond.get().setFlag(flag, true);
                    p.setFlag(flag, true);
                }
            }
            findFirst = Optional.empty();
            findSecond = Optional.empty();
        }
        return value;
    }
}
