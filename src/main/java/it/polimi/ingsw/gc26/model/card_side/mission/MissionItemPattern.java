package it.polimi.ingsw.gc26.model.card_side.mission;
import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

public class MissionItemPattern extends MissionCardFront {

    public MissionItemPattern(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, permanentResources, requestedResources, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }

    public int checkPattern(Map<Symbol,Integer> visibleResources, ArrayList<Point> occupiedPositions){
        int points = 0;
        if(getType() == 1){
            points = points + 2*(visibleResources.get(Symbol.FUNGI) / 3);
        }else if(getType() == 2){
            points = points + 2*(visibleResources.get(Symbol.PLANT) / 3);
        }else if(getType() == 3){
            points = points + 2*(visibleResources.get(Symbol.ANIMAL) / 3);
        }else if(getType() == 4){
            points = points + 2*(visibleResources.get(Symbol.INSECT) / 3);
        }

        return points;
    }
}
