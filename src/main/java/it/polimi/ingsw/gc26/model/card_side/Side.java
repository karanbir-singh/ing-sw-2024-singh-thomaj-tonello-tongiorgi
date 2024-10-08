package it.polimi.ingsw.gc26.model.card_side;

import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This abstract class represents a Side, two Sides form a Card
 */
abstract public class Side implements Serializable {
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
     * this attribute represents the path where the side's image is located
     */
    private String imagePath;

    /**
     * This method returns the points that a Mission cards gives based on the Player's board
     *
     * @param firstX            X coordinate of the first card
     * @param firstY            Y coordinate of the first card
     * @param secondX           X coordinate of the second card
     * @param secondY           Y coordinate of the second card
     * @param occupiedPositions Position occupied by Player's cards in the Player's Board
     * @param diagSymbol        Symbol that represent the cards to be checked
     * @param vertSymbol        Symbol that represent the cards to be checked
     * @param flag              flag that equals true if the card has already been count in a pattern
     * @param points            points given for every combination found
     * @return total points given by this card
     */
    protected int calculatePoints(int firstX, int firstY, int secondX, int secondY, ArrayList<Point> occupiedPositions, Symbol diagSymbol, Symbol vertSymbol, int flag, int points) {
        return 0;
    }

    /**
     * This method returns the points for abilities
     *
     * @param resources         Resources required to use the ability
     * @param occupiedPositions list of occupied position in the Player's board
     * @param p                 point where the card is placed
     * @return total point for the ability
     */
    public int useAbility(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions, Point p) {
        return 0;
    }

    /**
     * This method returns the extra points that are awarded considering the card position in the Player's board.
     *
     * @param resources         Player's visible resources in the board
     * @param occupiedPositions list of the position occupied in the Player's board
     * @return points given by this card
     */
    public int checkPattern(Map<Symbol, Integer> resources, ArrayList<Point> occupiedPositions) {
        return 0;
    }

    /**
     * Returns true if the sideSymbol (the card's kingdom) equals the parameter given
     *
     * @param sideSymbol symbol to check against the actual side's symbol
     * @return boolean
     */
    public boolean checkSideSymbol(Symbol sideSymbol) {
        return Optional.ofNullable(this.sideSymbol).map(symbol -> symbol.equals(sideSymbol)).orElse(false);
    }

    /**
     * Returns the points for this side
     *
     * @return points
     */
    public int getPoints() {
        return points;
    }

    /**
     * Sets the points for this side
     *
     * @param points new points for this side
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Return side's type
     *
     * @return type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets side's type
     *
     * @param type new side's type
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Returns the side's symbol (side's kingdom)
     *
     * @return an optional that contains the side's symbol
     */
    public Optional<Symbol> getSideSymbol() {
        return Optional.ofNullable(sideSymbol);
    }

    /**
     * Sets side's symbol
     *
     * @param sideSymbol new side symbol
     */
    public void setSideSymbol(Symbol sideSymbol) {
        this.sideSymbol = sideSymbol;
    }

    /**
     * Returns an arrayList with the side's permanent resources
     *
     * @return permanent resources
     */
    public ArrayList<Symbol> getPermanentResources() {
        return permanentResources;
    }

    /**
     * Sets the side's permanent resources
     *
     * @param permanentResources new permanent resources
     */
    public void setPermanentResources(ArrayList<Symbol> permanentResources) {
        this.permanentResources = new ArrayList<>(permanentResources);
    }

    /**
     * Returns the requested resources to be able to play this side
     *
     * @return requested resources
     */
    public Map<Symbol, Integer> getRequestedResources() {
        return new HashMap<>(this.requestedResources);
    }

    /**
     * Set's the requested resources to be able to play this side
     *
     * @param requestedResources new requested resources
     */
    public void setRequestedResources(Map<Symbol, Integer> requestedResources) {
        this.requestedResources = new HashMap<>(requestedResources);
    }

    /**
     * Returns a corner representing the corner up left
     *
     * @return UPLEFT
     */
    public Corner getUPLEFT() {
        return UPLEFT;
    }

    /**
     * Sets the corner representing the corner up left
     *
     * @param UPLEFT new corner up left
     */
    public void setUPLEFT(Corner UPLEFT) {
        this.UPLEFT = UPLEFT;
    }

    /**
     * Return a corner representing the corner down left
     *
     * @return DOWNLEFT
     */
    public Corner getDOWNLEFT() {
        return DOWNLEFT;
    }

    /**
     * Sets the corner representing the corner down left
     *
     * @param DOWNLEFT new corner down left
     */
    public void setDOWNLEFT(Corner DOWNLEFT) {
        this.DOWNLEFT = DOWNLEFT;
    }

    /**
     * Returns a corner represeting the corner up right
     *
     * @return UPRIGHT
     */
    public Corner getUPRIGHT() {
        return UPRIGHT;
    }

    /**
     * Sets the corner representing the corner up right
     *
     * @param UPRIGHT new corner up right
     */
    public void setUPRIGHT(Corner UPRIGHT) {
        this.UPRIGHT = UPRIGHT;
    }

    /**
     * Returns a corner representing the corner down right
     *
     * @return DOWNRIGHT
     */
    public Corner getDOWNRIGHT() {
        return DOWNRIGHT;
    }

    /**
     * Sets the corner representing the corner down right
     *
     * @param DOWNRIGHT new corner down right
     */
    public void setDOWNRIGHT(Corner DOWNRIGHT) {
        this.DOWNRIGHT = DOWNRIGHT;
    }

    /**
     * Sets the path representing the image
     *
     * @param imagePath path to corresponding image
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    /**
     * Returns the card's path
     *
     * @return
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Creates a String matrix with a printable representation of the side
     *
     * @return String[][] s
     */
    public String[][] printableSide() {
        String[][] s = new String[3][3];

        String empty = SpecialCharacters.SQUARE_WHITE.getCharacter();
        String blank = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String cornerBackground = TextStyle.BACKGROUND_WHITE.getStyleCode();

        //String fontColor;
        String filler;
        String kingdomColor;

        //fetch the special characters and color based on the side's kingdom
        if (sideSymbol != null) {
            filler = sideSymbol.getFiller();
            kingdomColor = sideSymbol.getBackground();
        } else {
            filler = SpecialCharacters.BACKGROUND_YELLOW.getCharacter();
            kingdomColor = TextStyle.BACKGROUND_YELLOW.getStyleCode();
        }


        if (UPLEFT.getSymbol().isPresent()) {
            s[0][0] = cornerBackground + UPLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;
        } else if (UPLEFT.isEvil()) {
            s[0][0] = filler;
        } else {
            s[0][0] = cornerBackground + empty + kingdomColor;
        }

        s[0][1] = blank + filler + blank;

        if (UPRIGHT.getSymbol().isPresent()) {
            s[0][2] = cornerBackground + UPRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;

        } else if (UPRIGHT.isEvil()) {
            s[0][2] = filler;
        } else {
            s[0][2] = cornerBackground + empty + kingdomColor;
        }

        s[1][0] = blank;

        if (permanentResources.isEmpty()) {
            s[1][1] = filler + filler + filler;
        } else {
            s[1][1] = "";
            if (permanentResources.size() == 1) {
                s[1][1] = filler + cornerBackground + permanentResources.get(0).getAlias() + kingdomColor + filler;
            } else if (permanentResources.size() == 2) {
                s[1][1] = cornerBackground + permanentResources.get(0).getAlias() + kingdomColor + filler + cornerBackground + permanentResources.get(1).getAlias() + kingdomColor;
            } else if (permanentResources.size() == 3) {
                s[1][1] = cornerBackground;
                for (Symbol permanentResource : permanentResources) {
                    s[1][1] = s[1][1] + permanentResource.getAlias();
                }
                s[1][1] = s[1][1] + kingdomColor;
            }
        }

        s[1][2] = blank;

        if (DOWNLEFT.getSymbol().isPresent()) {
            s[2][0] = cornerBackground + DOWNLEFT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;

        } else if (DOWNLEFT.isEvil()) {
            s[2][0] = filler;
        } else {
            s[2][0] = cornerBackground + empty + kingdomColor;
        }

        s[2][1] = blank + filler + blank;

        if (DOWNRIGHT.getSymbol().isPresent()) {
            s[2][2] = cornerBackground + DOWNRIGHT.getSymbol().orElseThrow(NullPointerException::new).getAlias() + kingdomColor;

        } else if (DOWNRIGHT.isEvil()) {
            s[2][2] = filler;
        } else {
            s[2][2] = cornerBackground + empty + kingdomColor;
        }

        //apply background color
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                s[i][j] = kingdomColor + s[i][j] + styleReset;
            }
        }

        return s;
    }

}
