package it.polimi.ingsw.gc26.model;

import java.util.*;

public class MissionTripletPattern extends MissionCardFront {

    public MissionTripletPattern(int type){
        super(type);
    }
    private int min(int num1, int num2, int num3){
        if(num1 >= num2 && num1 >= num3) return num1;
        else if(num2 >= num1 && num2 >= num3) return num2;
        else return num3;
    }

    public int checkPattern(Map<Symbol,Integer> visibleResources, ArrayList<Point> occupiedPositions){
        int points = 0;
        if(type == 1){
            points = points + 3 * min(visibleResources.get(Symbol.INKWELL),
                                        visibleResources.get(Symbol.QUILL),
                                        visibleResources.get(Symbol.MANUSCRIPT));
        }else if(type == 2){
            points = points + 2 * (visibleResources.get(Symbol.MANUSCRIPT) / 2);

        }else if(type == 3){
            points = points + 2 * (visibleResources.get(Symbol.QUILL) / 2);
        }else if(type == 4){
            points = points + 2 * (visibleResources.get(Symbol.INKWELL) / 2);
        }

        return points;
    }
    public int getType(){
        return super.getType();
    }
}
