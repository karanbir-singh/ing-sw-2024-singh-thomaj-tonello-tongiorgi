package it.polimi.ingsw.gc26.model;
import java.util.*;
public abstract class MissionCardFront {
    protected final int type;

    public MissionCardFront(int type){
        this.type = type;
    }
    public abstract int checkPattern(Map<Symbol,Integer> visibleResources,ArrayList<Point> occupiedPositions);
    public int getType(){
        return this.type;
    }
}
