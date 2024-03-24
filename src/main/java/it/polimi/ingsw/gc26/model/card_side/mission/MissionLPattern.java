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
    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        if (getType() == 1) {
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
