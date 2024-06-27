package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.network.ModelObservable;
import it.polimi.ingsw.gc26.utils.ConsoleColors;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This class represents the personal board for a player. It contains the playable positions where a card can be placed,
 * the occupied positions where a card has been placed and the blocked positions where no card can be placed.
 * It also contains the methods to play a new card and check if an action is valid.
 * Finally, it can return the resources on the board and set the secret mission.
 */
public class PersonalBoard implements Serializable {
    /**
     * This attribute represents the personal board's dimension. It can be a negative number.
     */
    private int xMin, xMax, yMin, yMax;
    /**
     * This attribute represents the score for this personal board (that is associated to a player). From 0 to 30.
     */
    private int score;
    /**
     * This attribute represents the positions where a card has been placed.
     */
    private final ArrayList<Point> occupiedPositions;
    /**
     * This attribute represents the positions where a card car be placed.
     */
    private final ArrayList<Point> playablePositions;
    /**
     * This attribute represents the positions where no card can be placed.
     */
    private final ArrayList<Point> blockedPositions;
    /**
     * This attribute represents the secret mission chosen.
     */
    private Card secretMission;
    /**
     * This attribute represents the visible resources in the board.
     */
    private final Map<Symbol, Integer> visibleResources;
    /**
     * This attribute represents the coordinate X of the selected position.
     */
    private int selectedX = 0;
    /**
     * This attribute represents the coordinate Y of the selected position.
     */
    private int selectedY = 0;
    /**
     * This attribute represents the observable used to notify the client.
     */
    private final ModelObservable observable;
    /**
     * This attribute represents the player's nickname.
     */
    private String nickname;

    /**
     * The constructor initializes everything: the score, the resource, missions occupiedPositions , playablePositions, blockedPositions.
     *
     * @param observable observable to notify client
     * @param nickname   personal board owner
     */
    public PersonalBoard(ModelObservable observable, String nickname) {
        score = 0;
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;

        visibleResources = new HashMap<>();
        visibleResources.put(Symbol.FUNGI, 0);
        visibleResources.put(Symbol.ANIMAL, 0);
        visibleResources.put(Symbol.PLANT, 0);
        visibleResources.put(Symbol.INSECT, 0);
        visibleResources.put(Symbol.INKWELL, 0);
        visibleResources.put(Symbol.QUILL, 0);
        visibleResources.put(Symbol.MANUSCRIPT, 0);

        this.secretMission = null;
        this.nickname = nickname;

        occupiedPositions = new ArrayList<>();
        playablePositions = new ArrayList<>();
        blockedPositions = new ArrayList<>();

        addPoint(0, 0, playablePositions);
        this.observable = observable;
    }

    /**
     * Returns personal secret mission
     *
     * @return the reference of the secret mission card
     */
    public Card getSecretMission() {
        return secretMission;
    }

    /**
     * Sets the secret mission
     *
     * @param secretMission card that you want to set
     * @param clientID      unique client's id
     */
    public Card setSecretMission(Optional<Card> secretMission, String clientID) {
        if (secretMission.isPresent()) {
            this.secretMission = secretMission.get();
            this.observable.notifyUpdatePersonalBoard(new SimplifiedPersonalBoard(this, nickname), "Secret mission set", clientID);
            return this.secretMission;
        }
        this.observable.notifyError("Secret mission not present!", clientID);
        return null;
    }

    /**
     * Check if a position is playable or not
     *
     * @param x a coordinate x of the personal board
     * @param y a coordinate t of the personal board
     * @return true if the position is playable, false otherwise
     */
    public boolean checkIfPlayablePosition(int x, int y) {
        return ifPresent(x, y, playablePositions).isPresent();
    }

    /**
     * Check if the player has enough resources to play a particular card
     *
     * @param side side that the player wants to play
     * @return true if the board has enough resources, otherwise false
     */
    public boolean checkIfEnoughResources(Side side) {
        for (Symbol symbol : side.getRequestedResources().keySet()) {
            if (this.visibleResources.get(symbol) < side.getRequestedResources().get(symbol)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculate the points earned from commonMissions and secretMission at the end of the game
     *
     * @param commonMissions commonMissions that are present in CommonBoard
     */
    public void endGame(ArrayList<Card> commonMissions) {
        this.score = this.score + secretMission.getFront().checkPattern(this.getResources(), occupiedPositions);

        for (Card mission : commonMissions) {
            this.score = this.score + mission.getFront().checkPattern(this.getResources(), occupiedPositions);
        }

        if(this.score > 29){
            this.score = 29;
        }
    }

    /**
     * Sets the position chosen by the player.
     *
     * @param selectedX the X coordinate of the personalBoard that the player wants to choose
     * @param selectedY the Y coordinate of the personalBoard that the player wants to choose
     */

    public void setPosition(int selectedX, int selectedY, String clientID) {
        //check if the position is valid
        if (!checkIfPlayablePosition(selectedX, selectedY)) {
            this.observable.notifyError("It's not a playable position!", clientID);
            return;
        }
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.observable.notifyUpdatePersonalBoard(new SimplifiedPersonalBoard(this, nickname), "Position selected!", clientID);

    }

    /**
     * Returns player's score
     *
     * @return score of a particular player
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the min value for x-axis.
     *
     * @return real number
     */
    public int getXMin() {
        return xMin;
    }

    /**
     * Returns the max value for x-axis.
     *
     * @return real number
     */
    public int getXMax() {
        return xMax;
    }

    /**
     * Returns the min value for y-axis.
     *
     * @return real number
     */
    public int getYMin() {
        return yMin;
    }

    /**
     * Returns the max value for y-axis.
     *
     * @return real number
     */
    public int getYMax() {
        return yMax;
    }

    /**
     * @return the list of the playable positions
     */
    public ArrayList<Point> getPlayablePositions() {
        return playablePositions;
    }

    /**
     * @return the list of the blocked positions
     */
    public ArrayList<Point> getBlockedPositions() {
        return blockedPositions;
    }

    /**
     * Getter of all the occupied positions
     *
     * @return the List of occupied Positions
     */
    public ArrayList<Point> getOccupiedPositions() {
        return this.occupiedPositions;
    }

    /**
     * Sets manually the score point. Should only be for testing purposes.
     *
     * @param score value between 0 and 30
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Check if there is a point with coordinate x and y in an arrayList l and returns it, otherwise return Optional.empty()
     *
     * @param x coordinate x that you want to search
     * @param y coordinate y that you want to search
     * @param l arrayList where you need to search
     * @return the reference to the point if present, otherwise an Optional.empty()
     */
    private Optional<Point> ifPresent(int x, int y, ArrayList<Point> l) {
        Optional<Point> o = Optional.empty();
        for (Point p : l) {
            if (p.getX() == x && p.getY() == y) {
                o = Optional.of(p);
            }
        }
        return o;
    }

    /**
     * Moves a point from list l2 to l1
     *
     * @param x  coordinate X
     * @param y  coordinate Y
     * @param l1 arrayList where you want to add the point
     * @param l2 arrayList where you want to remove the point
     */
    private void movePoint(int x, int y, ArrayList<Point> l1, ArrayList<Point> l2) throws NullPointerException {
        Point p;
        p = ifPresent(x, y, l2).orElseThrow(NullPointerException::new);
        removePoint(x, y, l2);
        l1.add(p);
    }

    /**
     * Adds a point with coordinate x and y to the passed list
     *
     * @param x coordinate x of the point
     * @param y coordinate y of the point
     * @param l arrayList where you want to add the point
     */
    private void addPoint(int x, int y, ArrayList<Point> l) {
        Point p = new Point(x, y);
        l.add(p);
    }

    /**
     * Removes a point with coordinate x and y from the passed list
     *
     * @param x coordinate x of the point
     * @param y coordinate y of the point
     * @param l arrayList where you want to remove the point
     */
    private void removePoint(int x, int y, ArrayList<Point> l) {
        try {
            l.remove(ifPresent(x, y, l).orElseThrow(NullPointerException::new));
        } catch (NullPointerException nullPointerException) {
            nullPointerException.printStackTrace();
            ConsoleColors.printError("Null Pointer Exception in removePoint");
        }
    }

    /**
     * Returns selected X coordinate
     *
     * @return the selected X
     */
    public int getSelectedX() {
        return this.selectedX;
    }

    /**
     * Returns selected Y coordinate
     *
     * @return the selected Y
     */
    public int getSelectedY() {
        return this.selectedY;
    }


    /**
     * Returns how many resources of passed symbol are in the personal board
     *
     * @param symbol symbol that you want to find
     * @return number of symbols "symbol" in your personalBoard
     */
    public Integer getResourceQuantity(Symbol symbol) {
        return visibleResources.get(symbol);
    }

    /**
     * Increases by 1 the number of symbols of a given kind in the player's personalBoard
     *
     * @param symbol symbol that you want to find
     */
    public void increaseResource(Optional<Symbol> symbol) {
        symbol.ifPresent(value -> visibleResources.put(value, visibleResources.get(value) + 1));
    }

    /**
     * Decreases by 1 the number of symbols of a given kind in the player's personalBoard
     *
     * @param symbol symbol that you want to find
     */
    public void decreaseResource(Optional<Symbol> symbol) {
        symbol.ifPresent(value -> visibleResources.put(value, visibleResources.get(value) - 1));
    }

    /**
     * Getter of all the resource counters
     *
     * @return map which contains all the resources on the personal boards
     */
    public Map<Symbol, Integer> getResources() {
        return new HashMap<>(this.visibleResources);
    }

    /**
     * private methods that handles the effects that a certain corner of the last played card has on the adjacent points
     * if the point is in occupiedPositions, the eventual covered resource is removed from the counter
     * otherwise, the point is moved in blockedPosition or playablePositions, depending on the corner's nature
     *
     * @param checkingCorner corner that you want to check
     * @param checkingX      coordinate X of the point that you want to check
     * @param checkingY      coordinate Y of the point that you want to check
     * @throws NullPointerException throws null pointer if
     */
    private void analyzePoint(Corner checkingCorner, int checkingX, int checkingY) throws NullPointerException {
        Optional<Point> checkingPoint = ifPresent(selectedX + checkingX, selectedY + checkingY, occupiedPositions);
        if (checkingPoint.isPresent()) {
            Side checkingSide = checkingPoint.get().getSide();

            // // Decrease counter of symbols in personal board, based on the given coordinates
            if (checkingX == 1 && checkingY == 1) {
                this.decreaseResource(checkingSide.getDOWNLEFT().getSymbol());
                checkingSide.getDOWNLEFT().setHidden(true);

            } else if (checkingX == 1 && checkingY == -1) {
                this.decreaseResource(checkingSide.getUPLEFT().getSymbol());
                checkingSide.getUPLEFT().setHidden(true);

            } else if (checkingX == -1 && checkingY == 1) {
                this.decreaseResource(checkingSide.getDOWNRIGHT().getSymbol());
                checkingSide.getDOWNRIGHT().setHidden(true);
                checkingSide.getDOWNRIGHT().setHidden(true);

            } else if (checkingX == -1 && checkingY == -1) {
                this.decreaseResource(checkingSide.getUPRIGHT().getSymbol());
                checkingSide.getUPRIGHT().setHidden(true);
            }

        } else if (ifPresent(selectedX + checkingX, selectedY + checkingY, blockedPositions).isPresent()) {

        } else if (ifPresent(selectedX + checkingX, selectedY + checkingY, playablePositions).isPresent()) {
            if (checkingCorner.isEvil()) {
                movePoint(selectedX + checkingX, selectedY + checkingY, blockedPositions, playablePositions);
            }

        } else {


            if (checkingCorner.isEvil()) {
                addPoint(selectedX + checkingX, selectedY + checkingY, blockedPositions);
            } else {
                addPoint(selectedX + checkingX, selectedY + checkingY, playablePositions);
            }

            if (selectedX + checkingX >= xMax) {
                xMax = selectedX + checkingX;
            }
            if (selectedX + checkingX <= xMin) {
                xMin = selectedX + checkingX;
            }
            if (selectedY + checkingY >= yMax) {
                yMax = selectedY + checkingY;
            }
            if (selectedY + checkingY <= yMin) {
                yMin = selectedY + checkingY;
            }
        }
    }

    /**
     * permits to play the selected card and update all the resources and points
     *
     * @param side side selected of the card chosen by the player
     */
    public boolean playSide(Side side, String clientID) {
        // You need to check if the board has enough resources for the side.
        if (!checkIfEnoughResources(side)) {
            this.observable.notifyError("Not enough resources!", clientID);
            return false;
        }

        // Check if there is selected position
        Point playingPoint;
        try {
            playingPoint = ifPresent(selectedX, selectedY, playablePositions).orElseThrow(NullPointerException::new);
            playingPoint.setSide(side);
        } catch (NullPointerException e) {
            this.observable.notifyError("Select a position first!", clientID);
            return false;
        }

        try {
            movePoint(selectedX, selectedY, occupiedPositions, playablePositions);

            // Analyze point selectedX+1, selectedY+1
            analyzePoint(side.getUPRIGHT(), 1, 1);

            // Analyze point selectedX-1, selectedY+1
            analyzePoint(side.getUPLEFT(), -1, 1);

            // Analyze point selectedX+1, selectedY-1
            analyzePoint(side.getDOWNRIGHT(), 1, -1);

            // Analyze point selectedX-1, selectedY-1
            analyzePoint(side.getDOWNLEFT(), -1, -1);
        } catch (NullPointerException e) {
            this.observable.notifyError("Select a position first!", clientID);
            return false;
        }

        // Increase available resources from permanent resources of the cardss
        for (Symbol resource : side.getPermanentResources()) {
            this.increaseResource(Optional.ofNullable(resource));
        }

        // Increase available resources from corners
        this.increaseResource(side.getUPRIGHT().getSymbol());
        this.increaseResource(side.getUPLEFT().getSymbol());
        this.increaseResource(side.getDOWNLEFT().getSymbol());
        this.increaseResource(side.getDOWNRIGHT().getSymbol());

        // Update score
        this.score = this.score + side.getPoints();

        // Use card ability to add points if it has it
        this.score = this.score + side.useAbility(this.getResources(), occupiedPositions, playingPoint);
        if(this.score > 29){
            this.score = 29;
        }
        this.observable.notifyUpdatePersonalBoard(new SimplifiedPersonalBoard(this, nickname), "Card placed!", clientID);
        return true;
    }

}

