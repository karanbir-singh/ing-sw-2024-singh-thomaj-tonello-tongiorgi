package it.polimi.ingsw.gc26.model.card_side;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CardBack extends Side {
    // Mission Card Back constructor
    public CardBack(){
        setSideSymbol(null);
        setDOWNLEFT(null);
        setDOWNRIGHT(null);
        setUPLEFT(null);
        setUPRIGHT(null);
        setType(0);
        setPoints(0);
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
    }

    // Kingdom and Gold Card back constructor
    public CardBack(Symbol side){
        setSideSymbol(side);
        setDOWNLEFT(new Corner(false, null));
        setDOWNRIGHT(new Corner(false, null));
        setUPLEFT(new Corner(false, null));
        setUPRIGHT(new Corner(false, null));
        setType(0);
        setPoints(0);
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
    }

    // Starter Card back constructor
    public CardBack(Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT){
        setPermanentResources(new ArrayList<>());
        setSideSymbol(null);
        setDOWNLEFT(DOWNLEFT);
        setDOWNRIGHT(DOWNRIGHT);
        setUPLEFT(UPLEFT);
        setUPRIGHT(UPRIGHT);
        setType(0);
        setPoints(0);
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
    }
}
