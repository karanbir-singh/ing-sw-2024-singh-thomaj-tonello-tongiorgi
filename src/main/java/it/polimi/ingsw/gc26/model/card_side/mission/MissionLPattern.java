package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

public class MissionLPattern extends MissionCardFront {
    public MissionLPattern(int type) {
        setType(type);
        setPoints(0);

        setSideSymbol(null);
        setDOWNLEFT(new Corner(true, null));
        setDOWNRIGHT(new Corner(true, null));
        setUPLEFT(new Corner(true, null));
        setUPRIGHT(new Corner(true, null));
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
    }

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
    @Override
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1){
            points = calculatePoints(-1, 1, -1, 3, occupiedPositions, Symbol.PLANT, Symbol.FUNGI, 4, 3);
        } else if (getType() == 2) {
            points = calculatePoints(1, 1, 1, 3, occupiedPositions, Symbol.INSECT, Symbol.PLANT, 5, 3);
        } else if (getType() == 3) {
            points = calculatePoints(-1, -1, -1, -3, occupiedPositions, Symbol.FUNGI, Symbol.ANIMAL, 6, 3);
        } else if (getType() == 4) {
            points = calculatePoints(1, -1, 1, -3, occupiedPositions, Symbol.ANIMAL, Symbol.INSECT, 7, 3);
        }
        return points;
    }
}
