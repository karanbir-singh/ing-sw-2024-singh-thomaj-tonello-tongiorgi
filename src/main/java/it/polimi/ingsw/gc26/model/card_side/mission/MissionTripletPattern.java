package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

public class MissionTripletPattern extends MissionCardFront {

    public MissionTripletPattern(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        super(sideSymbol, permanentResources, requestedResources, UPLEFT, DOWNLEFT, UPRIGHT, DOWNRIGHT);
    }

    private int min(int num1, int num2, int num3){
        if(num1 >= num2 && num1 >= num3) return num1;
        else if(num2 >= num1 && num2 >= num3) return num2;
        else return num3;
    }

    public int checkPattern(Map<Symbol,Integer> visibleResources, ArrayList<Point> occupiedPositions){
        int points = 0;
        if(getType() == 1){
            points = points + 3 * min(visibleResources.get(Symbol.INKWELL),
                                        visibleResources.get(Symbol.QUILL),
                                        visibleResources.get(Symbol.MANUSCRIPT));
        }else if(getType() == 2){
            points = points + 2 * (visibleResources.get(Symbol.MANUSCRIPT) / 2);

        }else if(getType() == 3){
            points = points + 2 * (visibleResources.get(Symbol.QUILL) / 2);
        }else if(getType() == 4){
            points = points + 2 * (visibleResources.get(Symbol.INKWELL) / 2);
        }

        return points;
    }
}
