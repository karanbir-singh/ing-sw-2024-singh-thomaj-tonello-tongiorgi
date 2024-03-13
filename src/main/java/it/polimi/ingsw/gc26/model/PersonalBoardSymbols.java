package it.polimi.ingsw.gc26.model;
import java.util.*;
public class PersonalBoardSymbols {
    private Map<Symbol,Integer> visibleResources;
    public PersonalBoardSymbols(){
        visibleResources = new HashMap<Symbol, Integer>();
        visibleResources.put(Symbol.MUSHROOM,0);
        visibleResources.put(Symbol.WOLF,0);
        visibleResources.put(Symbol.LEAF,0);
        visibleResources.put(Symbol.BUTTERFLY,0);
        visibleResources.put(Symbol.FLASK,0);
        visibleResources.put(Symbol.FEATHER,0);
        visibleResources.put(Symbol.MANUSCRIPT,0);
    }

    public Integer getResourceQuantity(Symbol symbol){
        return visibleResources.get(symbol);
    }
    public void increaseResource(Symbol symbol){
        visibleResources.put(symbol, visibleResources.get(symbol) + 1);
    }

    public void decreaseResource(Symbol symbol){
        visibleResources.put(symbol, visibleResources.get(symbol) - 1);
    }

}
