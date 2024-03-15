package it.polimi.ingsw.gc26.model;
import java.util.*;

public class MissionItemPattern extends MissionCardFront{


    public int checkPattern(Map<Symbol,Integer> visibleResources, ArrayList<Point> occupiedPositions){
        int points = 0;
        if(type == 1){
            points = points + 2*(visibleResources.get(Symbol.FUNGI) / 3);
        }else if(type == 2){
            points = points + 2*(visibleResources.get(Symbol.PLANT) / 3);
        }else if(type == 3){
            points = points + 2*(visibleResources.get(Symbol.ANIMAL) / 3);
        }else if(type == 4){
            points = points + 2*(visibleResources.get(Symbol.INSECT) / 3);
        }

        return points;
    }
    public int getType(){
        return super.getType();
    }
}
