package it.polimi.ingsw.gc26.model.card_side;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

abstract public class Side {
    private int points;
    private int type;
    private Optional<Symbol> sideSymbol;
    private ArrayList<Symbol> permanentResources;
    private Map<Symbol, Integer> requestedResources;

    private Corner UPLEFT;
    private Corner DOWNLEFT;
    private Corner UPRIGHT;
    private Corner DOWNRIGHT;

    public Side(Optional<Symbol> sideSymbol, ArrayList<Symbol> permanentResources, Map<Symbol, Integer> requestedResources, Corner UPLEFT, Corner DOWNLEFT, Corner UPRIGHT, Corner DOWNRIGHT) {
        this.sideSymbol = sideSymbol;
        this.permanentResources = permanentResources;
        this.points = 0;
        this.type = 0;
        this.requestedResources = requestedResources;
        this.UPLEFT = UPLEFT;
        this.DOWNLEFT = DOWNLEFT;
        this.UPRIGHT = UPRIGHT;
        this.DOWNRIGHT = DOWNRIGHT;
    }

    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point p) {
        return 0;
    }

    public int checkPattern(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions) {
        return 0;
    }

    public boolean checkSideSymbol(Symbol sideSymbol) {
        return this.sideSymbol.map(symbol -> symbol.equals(sideSymbol)).orElse(false);
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Optional<Symbol> getSideSymbol() {
        return sideSymbol;
    }

    public void setSideSymbol(Optional<Symbol> sideSymbol) {
        this.sideSymbol = sideSymbol;
    }

    public ArrayList<Symbol> getPermanentResources() {
        return permanentResources;
    }

    public void setPermanentResources(ArrayList<Symbol> permanentResources) {
        this.permanentResources = permanentResources;
    }

    public Map<Symbol, Integer> getRequestedResources() {
        return requestedResources;
    }

    public void setRequestedResources(Map<Symbol, Integer> requestedResources) {
        this.requestedResources = requestedResources;
    }

    public Corner getUPLEFT() {
        return UPLEFT;
    }

    public void setUPLEFT(Corner UPLEFT) {
        this.UPLEFT = UPLEFT;
    }

    public Corner getDOWNLEFT() {
        return DOWNLEFT;
    }

    public void setDOWNLEFT(Corner DOWNLEFT) {
        this.DOWNLEFT = DOWNLEFT;
    }

    public Corner getUPRIGHT() {
        return UPRIGHT;
    }

    public void setUPRIGHT(Corner UPRIGHT) {
        this.UPRIGHT = UPRIGHT;
    }

    public Corner getDOWNRIGHT() {
        return DOWNRIGHT;
    }

    public void setDOWNRIGHT(Corner DOWNRIGHT) {
        this.DOWNRIGHT = DOWNRIGHT;
    }
}
