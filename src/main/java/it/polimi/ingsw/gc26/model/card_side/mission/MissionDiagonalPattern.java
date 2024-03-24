package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

public class MissionDiagonalPattern extends MissionCardFront {
    public MissionDiagonalPattern(int type) {
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
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1) {
            points = calculatePoints(1, 1, 2, 2, occupiedPositions, Symbol.FUNGI, Symbol.FUNGI, 0, 2);
        } else if (getType() == 2) {
            points = calculatePoints(1, -1, 2, -2, occupiedPositions, Symbol.PLANT, Symbol.PLANT, 1, 2);
        } else if (getType() == 3) {
            points = calculatePoints(1, 1, 2, 2, occupiedPositions, Symbol.ANIMAL, Symbol.ANIMAL, 2, 2);
        } else if (getType() == 4) {
            points = calculatePoints(1, -1, 2, -2, occupiedPositions, Symbol.INSECT, Symbol.INSECT, 3, 2);
        }
        return points;
    }
}
