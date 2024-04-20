package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This abstract class represents a Side, two Sides form a Card
 */
abstract public class Side {
    /**
     * This attribute represents the points given by this side
     */
    private int points;
    /**
     * This attribute represents the type for Mission Cards
     */
    private int type;
    /**
     * This attribute represents the card's color (and the kingdom)
     */
    private Symbol sideSymbol;
    /**
     * This attribute represents the resources that cannot be covered by other cards, therefore these resources are permanent
     */
    private ArrayList<Symbol> permanentResources;
    /**
     * This attribute represent the resources that must be visible in the Player's board to be able to play this card
     */
    private Map<Symbol, Integer> requestedResources;
    /**
     * This attribute represent the corner in the up left
     */
    private Corner UPLEFT;
    /**
     * This attribute represent the corner in the down left
     */
    private Corner DOWNLEFT;
    /**
     * This attribute represents the corner in the up right
     */
    private Corner UPRIGHT;
    /**
     * This attribute represents the corner in the down right
     */
    private Corner DOWNRIGHT;

    /**
     * This method returns the points that a Mission cards gives based on the Player's board
     * @param firstX X coordinate of the first card
     * @param firstY Y coordinate of the first card
     * @param secondX X coordinate of the second card
     * @param secondY Y coordinate of the second card
     * @param occupiedPositions Position occupied by Player's cards in the Player's Board
     * @param diagSymbol Symbol that represent the cards to be checked
     * @param vertSymbol Symbol that represent the cards to be checked
     * @param flag flag that equals true if the card has already been count in a pattern
     * @param points points given for every combination found
     * @return total points given by this card
     */
    protected int calculatePoints(int firstX, int firstY, int secondX, int secondY, ArrayList<Point> occupiedPositions, Symbol diagSymbol, Symbol vertSymbol, int flag, int points) {
        return 0;
    }

    /**
     * This method returns the points for abilities
     * @param resources Resources required to use the ability
     * @param occupiedPositions list of occupied position in the Player's board
     * @param p point where the card is placed
     * @return total point for the ability
     */
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point p) {
        return 0;
    }

    /**
     * This method returns the extra points that are awarded considering the card position in the Player's board.
     * @param resources Player's visible resources in the board
     * @param occupiedPositions list of the position occupied in the Player's board
     * @return points given by this card
     */
    public int checkPattern(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions) {
        return 0;
    }

    /**
     * Returns true if the sideSymbol (the card's kingdom) equals the parameter given
     * @param sideSymbol symbol to check against the actual side's symbol
     * @return boolean
     */
    public boolean checkSideSymbol(Symbol sideSymbol) {
        return Optional.ofNullable(this.sideSymbol).map(symbol -> symbol.equals(sideSymbol)).orElse(false);
    }

    /**
     * Returns the points for this side
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points for this side
     * @param points new points for this side
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Return side's type
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets side's type
     * @param type new side's type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the side's symbol (side's kingdom)
     * @return an optional that contains the side's symbol
     */
    public Optional<Symbol> getSideSymbol() {
        return Optional.ofNullable(sideSymbol);
    }

    /**
     * Sets side's symbol
     * @param sideSymbol
     */
    public void setSideSymbol(Symbol sideSymbol) {
        this.sideSymbol = sideSymbol;
    }

    /**
     * Returns an arrayList with the side's permanent resources
     * @return permanent resources
     */
    public ArrayList<Symbol> getPermanentResources() {
        return permanentResources;
    }

    /**
     * Sets the side's permanent resources
     * @param permanentResources new permanent resources
     */
    public void setPermanentResources(ArrayList<Symbol> permanentResources) {
        this.permanentResources = new ArrayList<>(permanentResources);
    }

    /**
     * Returns the requested resources to be able to play this side
     * @return requested resources
     */
    public Map<Symbol, Integer> getRequestedResources() {
        return new HashMap<>(this.requestedResources);
    }

    /**
     * Set's the requested resources to be able to play this side
     * @param requestedResources new requested resources
     */
    public void setRequestedResources(Map<Symbol, Integer> requestedResources) {
        this.requestedResources = new HashMap<>(requestedResources);
    }

    /**
     * Returns a corner representing the corner up left
     * @return UPLEFT
     */
    public Corner getUPLEFT() {
        return UPLEFT;
    }

    /**
     * Sets the corner representing the corner up left
     * @param UPLEFT new corner up left
     */
    public void setUPLEFT(Corner UPLEFT) {
        this.UPLEFT = UPLEFT;
    }

    /**
     * Return a corner representing the corner down left
     * @return DOWNLEFT
     */
    public Corner getDOWNLEFT() {
        return DOWNLEFT;
    }

    /**
     * Sets the corner representing the corner down left
     * @param DOWNLEFT new corner down left
     */
    public void setDOWNLEFT(Corner DOWNLEFT) {
        this.DOWNLEFT = DOWNLEFT;
    }

    /**
     * Returns a corner represeting the corner up right
     * @return UPRIGHT
     */
    public Corner getUPRIGHT() {
        return UPRIGHT;
    }

    /**
     * Sets the corner representing the corner up right
     * @param UPRIGHT new corner up right
     */
    public void setUPRIGHT(Corner UPRIGHT) {
        this.UPRIGHT = UPRIGHT;
    }

    /**
     * Returns a corner representing the corner down right
     * @return DOWNRIGHT
     */
    public Corner getDOWNRIGHT() {
        return DOWNRIGHT;
    }

    /**
     * Sets the corner representing the corner down right
     * @param DOWNRIGHT new corner down right
     */
    public void setDOWNRIGHT(Corner DOWNRIGHT) {
        this.DOWNRIGHT = DOWNRIGHT;
    }

    /*public String[][] printableSide(){
        String[][] s = new String[3][3];
        String empty = Character.toString(0x2B1C);
        String evil = Character.toString(0x2B1B);
        String background = "  ";
        String fontColor;
        String styleReset = "\u001B[0m";
        String filler;

        /*if(sideSymbol!=null){
            background = sideSymbol.getBackground()+ "  " + styleReset;
            kingdomColor = sideSymbol.getBackground();
        }*/
/*
        if (sideSymbol != null){
            filler = sideSymbol.getFiller();
            fontColor = sideSymbol.getFontColor();
        } else {
            filler = Character.toString(0x1F7E1);
            fontColor = "\33[93m";
        }

        if(UPLEFT.getSymbol().isPresent()){
            s[0][0] = UPLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() ;
        } else if(UPLEFT.isEvil()) {
            s[0][0] = evil ;
        } else {
            s[0][0] = empty  ;
        }

        s[0][1] = fontColor + "‾‾‾"  + filler  + "‾‾‾" + styleReset;


        if(UPRIGHT.getSymbol().isPresent()){
            s[0][2] = UPRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias()  ;

        } else if(UPRIGHT.isEvil()) {
            s[0][2] = evil;
        } else {
            s[0][2] = empty  ;
        }

        s[1][0] = fontColor + "│" + background + styleReset;
        if(permanentResources.isEmpty()){
            s[1][1] = filler + filler + filler;
        } else {
            s[1][1] = "";
            if(permanentResources.size() == 1){
                s[1][1] = filler + permanentResources.get(0).getAlias() + filler;
            } else if(permanentResources.size() == 2){
                s[1][1] = permanentResources.get(0).getAlias() + filler + permanentResources.get(1).getAlias();
            } else if(permanentResources.size() == 3){
                for (Symbol permanentResource : permanentResources) {
                    s[1][1] = s[1][1] + permanentResource.getAlias();
                }
            }
        }
        s[1][2] = fontColor + background + "│" + styleReset;

        if(DOWNLEFT.getSymbol().isPresent()){
            s[2][0] = DOWNLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() ;

        } else if(DOWNLEFT.isEvil()) {
            s[2][0] =  evil ;
        } else {
            s[2][0] =  empty  ;
        }

        s[2][1] = fontColor + "___"  + filler  + "___" + styleReset;

        if(DOWNRIGHT.getSymbol().isPresent()){
            s[2][2] = DOWNRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias();

        } else if(DOWNRIGHT.isEvil()) {
            s[2][2] =  evil  ;
        } else {
            s[2][2] =  empty ;
        }

        /*for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                s[i][j] = "\33[40m" + s[i][j] + styleReset;
            }
        }*/
/*
        return s;
    }*/

    //with background
    public String[][] printableSide(){
        String[][] s = new String[3][3];
        String empty = "▫️";
        //String evil = Character.toString(0x2B1B);
        String background = "  ";
        String fontColor;
        String styleReset = "\u001B[0m";
        String filler;
        String kingdomColor = "\u001B[48;2;255;209;56m";
        String cornerBackground = "\u001B[48;2;255;255;255m";

        if (sideSymbol != null){
            filler = sideSymbol.getFiller();
            fontColor = sideSymbol.getFontColor();
            kingdomColor = sideSymbol.getBackground();
        } else {
            filler = Character.toString(0x1F7E1);
            fontColor = "\33[93m";
        }

        if(UPLEFT.getSymbol().isPresent()){
        s[0][0] = cornerBackground + UPLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;
        } else if(UPLEFT.isEvil()) {
        s[0][0] = filler ;
        } else {
        s[0][0] = cornerBackground + empty + kingdomColor ;
        }


        s[0][1] = fontColor + "   " + filler  + "   " + styleReset;


        if(UPRIGHT.getSymbol().isPresent()){
            s[0][2] = cornerBackground + UPRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;

        } else if(UPRIGHT.isEvil()) {
            s[0][2] = filler;
        } else {
            s[0][2] = cornerBackground + empty  + kingdomColor ;
        }

        s[1][0] = fontColor + " " + background + styleReset;

        if(permanentResources.isEmpty()){
            s[1][1] = filler + filler + filler;
        } else {
            s[1][1] = "";
            if(permanentResources.size() == 1){
                s[1][1] = filler + cornerBackground + permanentResources.get(0).getAlias() + kingdomColor + filler;
            } else if(permanentResources.size() == 2){
                s[1][1] = cornerBackground + permanentResources.get(0).getAlias() + kingdomColor + filler + cornerBackground + permanentResources.get(1).getAlias() + kingdomColor;
            } else if(permanentResources.size() == 3){
                s[1][1] = cornerBackground;
                for (Symbol permanentResource : permanentResources) {
                    s[1][1] = s[1][1] + permanentResource.getAlias();
                }
                s[1][1] = s[1][1] + kingdomColor;
            }
        }
        s[1][2] = fontColor + background + " " + styleReset;

        if(DOWNLEFT.getSymbol().isPresent()){
        s[2][0] = cornerBackground + DOWNLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;

        } else if(DOWNLEFT.isEvil()) {
        s[2][0] = filler ;
        } else {
        s[2][0] =  cornerBackground + empty + kingdomColor;
        }

        s[2][1] = fontColor + "   "  + filler  + "   " + styleReset;

        if(DOWNRIGHT.getSymbol().isPresent()){
        s[2][2] = cornerBackground + DOWNRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;

        } else if(DOWNRIGHT.isEvil()) {
        s[2][2] = filler;
        } else {
        s[2][2] = cornerBackground +  empty  + kingdomColor;
        }

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                s[i][j] = kingdomColor + s[i][j] + styleReset;
            }
        }

        return s;
        }

}
