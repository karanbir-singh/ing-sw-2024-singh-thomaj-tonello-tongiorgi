package it.polimi.ingsw.gc26.model.player;
import it.polimi.ingsw.gc26.model.card_side.Symbol;

import java.util.*;
public class PersonalBoardSymbols {
    private Map<Symbol,Integer> visibleResources;
    public PersonalBoardSymbols(){
        visibleResources = new HashMap<Symbol, Integer>();
        visibleResources.put(Symbol.FUNGI,0);
        visibleResources.put(Symbol.ANIMAL,0);
        visibleResources.put(Symbol.PLANT,0);
        visibleResources.put(Symbol.INSECT,0);
        visibleResources.put(Symbol.INKWELL,0);
        visibleResources.put(Symbol.QUILL,0);
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

    public Map<Symbol,Integer> getResources(){
        Map<Symbol,Integer> resources = new HashMap(visibleResources);
        return resources;
    }
}
