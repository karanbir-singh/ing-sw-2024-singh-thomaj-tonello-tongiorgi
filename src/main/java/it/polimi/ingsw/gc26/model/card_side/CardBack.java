package it.polimi.ingsw.gc26.model.card_side;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represent a card's back side.
 */
public class CardBack extends Side implements Serializable {

    /**
     * Initializes a card's back empty for Mission Cards
     *
     * @param imagePath path to corresponding image
     */
    public CardBack(String imagePath){
        setSideSymbol(null);
        setDOWNLEFT(new Corner(true, null));
        setDOWNRIGHT(new Corner(true, null));
        setUPLEFT(new Corner(true, null));
        setUPRIGHT(new Corner(true, null));
        setType(0);
        setPoints(0);
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
        setImagePath(imagePath);

    }

    /**
     * Initializes a card's back for Resources cards and Gold Cards
     *
     * @param side Symbol that represent the card's color
     * @param imagePath path to corresponding image
     */
    public CardBack(Symbol side, String imagePath){
        setSideSymbol(side);
        setDOWNLEFT(new Corner(false, null));
        setDOWNRIGHT(new Corner(false, null));
        setUPLEFT(new Corner(false, null));
        setUPRIGHT(new Corner(false, null));
        setType(0);
        setPoints(0);
        ArrayList<Symbol> resources = new ArrayList<>();
        resources.add(side);
        setPermanentResources(resources);
        setRequestedResources(new HashMap<>());
        setImagePath(imagePath);
    }

    /**
     * Initializes a card's back for Starter Cards
     *
     * @param UPLEFT    up left corner
     * @param DOWNLEFT  down left corner
     * @param UPRIGHT   up right corner
     * @param DOWNRIGHT down right corner
     * @param imagePath path to corresponding image
     */
    public CardBack(Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT, String imagePath){
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
        setImagePath(imagePath);
    }

    /**
     * Initializes a general card's back, passing all attributes (used for socket stream)
     *
     * @param side               Symbol that represent the card's color
     * @param UPLEFT             up left corner
     * @param DOWNLEFT           down left corner
     * @param UPRIGHT            up right corner
     * @param DOWNRIGHT          down right corner
     * @param permanentResources resources that are always visible
     * @param requestedResources resources that are requested
     * @param imagePath path to corresponding image
     */
    public CardBack(Symbol side, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, String imagePath) {
        setSideSymbol(side);
        setDOWNLEFT(DOWNLEFT);
        setDOWNRIGHT(DOWNRIGHT);
        setUPLEFT(UPLEFT);
        setUPRIGHT(UPRIGHT);
        setType(0);
        setPoints(0);
        setPermanentResources(permanentResources);
        setRequestedResources(requestedResources);
        setImagePath(imagePath);
    }
}
