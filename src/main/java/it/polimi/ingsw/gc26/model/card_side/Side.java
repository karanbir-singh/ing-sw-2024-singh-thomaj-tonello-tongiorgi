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

    public String[][] printableSide(){
        String[][] s = new String[3][3];
        String empty = Character.toString(0x2B1C);
        String evil = Character.toString(0x2B1B);
        Symbol kingdom;
        //String background = Character.toString(0x2B1B	);
        String background = " ";
        String filler;

        kingdom = sideSymbol;
        if (kingdom == Symbol.INSECT){
            filler = Character.toString(0x1F7E3);
        } else if(kingdom == Symbol.PLANT) {
            filler = Character.toString(0x1F7E2);
        } else if(kingdom == Symbol.ANIMAL) {
            filler = Character.toString(0x1F535);
        } else if(kingdom == Symbol.FUNGI) {
            filler = Character.toString(0x1F534);
        } else {
                filler = Character.toString(0x1F7E1);
        }

        if(UPLEFT.getSymbol().isPresent()){
            s[0][0] = UPLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() ;
        } else if(UPLEFT.isEvil()) {
            s[0][0] = evil ;
        } else {
            s[0][0] = empty  ;
        }

        s[0][1] = "‾" + background + filler + background + "‾";

        if(UPRIGHT.getSymbol().isPresent()){
            s[0][2] = UPRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias()  ;

        } else if(UPRIGHT.isEvil()) {
            s[0][2] = evil;
        } else {
            s[0][2] = empty  ;
        }

        s[1][0] = "|" + background;
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
        s[1][2] = background + "|" ;

        if(DOWNLEFT.getSymbol().isPresent()){
            s[2][0] = DOWNLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() ;

        } else if(DOWNLEFT.isEvil()) {
            s[2][0] =  evil ;
        } else {
            s[2][0] =  empty  ;
        }

        s[2][1] = "_" + background + filler + background + "_";

        if(DOWNRIGHT.getSymbol().isPresent()){
            s[2][2] = DOWNRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias();

        } else if(DOWNRIGHT.isEvil()) {
            s[2][2] =  evil  ;
        } else {
            s[2][2] =  empty ;
        }

        /*for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }*/
        return s;
    }

}
