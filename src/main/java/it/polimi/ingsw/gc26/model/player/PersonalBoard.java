package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;

import java.util.*;

public class PersonalBoard {
    private int xMin, xMax, yMin, yMax;
    private int score;
    private final ArrayList<Point> occupiedPositions;
    private final ArrayList<Point> playablePositions;
    private final ArrayList<Point> blockedPositions;
    private Card secretMission;
    private final Map<Symbol, Integer> visibleResources;
    private int selectedX = 0;
    private int selectedY = 0;

    /**
     * The constructor initializes everything: the score, the resource, missions occupiedPositions , playablePositions, blockedPositions.
     * Here, the first move is made playing the initial card.
     */
    public PersonalBoard() {
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

        occupiedPositions = new ArrayList<>();
        playablePositions = new ArrayList<>();
        blockedPositions = new ArrayList<>();

        addPoint(0, 0, playablePositions);
    }

    /**
     * secretMission getter
     * @return the reference of the secretMission card
     */
    public Card getSecretMission() {
        return secretMission;
    }

    /**
     * secretMission setter
     * @param secretMission card that you want to set
     */
    public void setSecretMission(Card secretMission) {
        this.secretMission = secretMission;
    }

    /**
     * Check if a position is playable or not
     * @param x a coordinate x of the personal board
     * @param y a coordinate t of the personal board
     * @return true if the position is playable, false otherwise
     */
    public boolean checkIfPlayablePosition(int x, int y) {
        return ifPresent(x, y, playablePositions).isPresent();
    }

    /**
     * Check if the player has enough resources to play a particular card
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
     * @param commonMissions commonMissions that are present in CommonBoard
     */
    public void endGame(ArrayList<Card> commonMissions) {
        this.score = this.score + secretMission.getFront().checkPattern(this.getResources(), occupiedPositions);

        for (Card mission : commonMissions) {
            this.score = this.score + mission.getFront().checkPattern(this.getResources(), occupiedPositions);
        }
    }

    /**
     * set the position chosen by the player.
     * if valid, the position is set
     * otherwise we print NOT VALID POSITION
     * @param selectedX the X coordinate of the personalBoard that the player wants to choose
     * @param selectedY the Y coordinate of the personalBoard that the player wants to choose
     */

    public void setPosition(int selectedX, int selectedY) {
        //check if the position is valid
        if(!checkIfPlayablePosition(selectedX, selectedY)){
            //update view
            System.err.println("NOT VALID POSITION");
            return;
        }
        this.selectedX = selectedX;
        this.selectedY = selectedY;
    }

    /**
     * score player getter
     * @return score of a particular player
     */
    public int getScore() {
        return score;
    }

    /**
     * set score
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * check if there is a point with coordinate x and y in an arrayList l and return it, otherwise return Optional.empty()
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
     * move a point from list L2 to L1
     * @param x coordinate X
     * @param y coordinate Y
     * @param l1 arrayList where you want to add the point
     * @param l2 arrayList where you want to remove the point
     */
    private void movePoint(int x, int y, ArrayList<Point> l1, ArrayList<Point> l2){
        Point p;
        try{
            p = ifPresent(x, y, l2).orElseThrow(NullPointerException::new);
        }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            System.err.println("null pointer exception in movePoint");
            return;
        }

        removePoint(x, y, l2);
        l1.add(p);
    }

    /**
     * add point with coordinate x and y to an arrayList
     * @param x coordinate x of the point
     * @param y coordinate y of the point
     * @param l arrayList where you want to add the point
     */
    private void addPoint(int x, int y, ArrayList<Point> l) {
        Point p = new Point(x, y);
        l.add(p);
    }

    /**
     * remove point with coordinate x and y from an arrayList
     * @param x coordinate x of the point
     * @param y coordinate y of the point
     * @param l arrayList where you want to remove the point
     */
    private void removePoint(int x, int y, ArrayList<Point> l){
        try{
            l.remove(ifPresent(x, y, l).orElseThrow(NullPointerException::new));
        }catch(NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
            System.err.println("Null Pointer Exception in removePoint");
        }
    }

    /**
     * selectedX getter
     * @return the selected X
     */
    public int getSelectedX() {
        return this.selectedX;
    }

    /**
     * selectedY getter
     * @return the selected Y
     */
    public int getSelectedY() {
        return this.selectedY;
    }


    /**
     * return how many resources of a certain kind the player has
     * @param symbol symbol that you want to find
     * @return number of symbols "symbol" in your personalBoard
     */
    public Integer getResourceQuantity(Symbol symbol) {
        return visibleResources.get(symbol);
    }

    /**
     * increase by 1 the number of symbols of a given kind in the player's personalBoard
     * @param symbol symbol that you want to find
     */
    public void increaseResource(Optional<Symbol> symbol) {
        symbol.ifPresent(value -> visibleResources.put(value, visibleResources.get(value) + 1));
    }

    /**
     * decrease by 1 the number of symbols of a given kind in the player's personalBoard
     * @param symbol symbol that you want to find
     */
    public void decreaseResource(Optional<Symbol> symbol) {
        symbol.ifPresent(value -> visibleResources.put(value, visibleResources.get(value) - 1));
    }

    /**
     * getter of all the resource counters in the player's personalBoard
     * @return map which contains all the resources on the personal boards
     */
    public Map<Symbol, Integer> getResources() {
        return new HashMap<>(this.visibleResources);
    }

    public void showBoard() {
        for (int currY = yMax + 1; currY >= yMin; currY--) {
            for (int currX = xMin - 1; currX <= xMax; currX++) {
                if (currY == yMax + 1 && currX != xMin - 1) {
                    System.out.print(currX + "   ");
                } else if (currX == xMin - 1 && currY != yMax + 1) {//anche questo
                    System.out.print(currY + "   ");
                } else if (ifPresent(currX, currY, blockedPositions).isPresent()) {
                    System.out.print("X   ");
                } else if (ifPresent(currX, currY, playablePositions).isPresent()) {
                    System.out.print("o   ");
                } else if (ifPresent(currX, currY, occupiedPositions).isPresent()) {
                    if(ifPresent(currX, currY, occupiedPositions).orElseThrow(NullPointerException::new).getSide().getSideSymbol().isPresent()){
                        System.out.print(ifPresent(currX, currY, occupiedPositions).orElseThrow(NullPointerException::new).getSide().getSideSymbol().orElseThrow(NullPointerException::new).getAlias() + "   ");
                    } else {
                        System.out.print("S   ");
                    }

                } else {
                    System.out.print("    ");
                }
            }
            System.out.println("\n");

        }
    }

    /**
     * getter of all the occupied positions on the personalBoard
     * @return the List of occupied Positions
     */
    public ArrayList<Point> getOccupiedPositions() {
        return this.occupiedPositions;
    }


    /**
     * private methods that handles the effects that a certain corner of the last played card has on the adjacent points
     * if the point is in occupiedPositions, the eventual covered resource is removed from the counter
     * otherwise, the point is moved in blockedPosition or playablePositions, depending on the corner's nature
     * @param checkingCorner corner that you want to check
     * @param checkingX coordinate X of the point that you want to check
     * @param checkingY coordinate Y of the point that you want to check
     */
    private void analyzePoint(Corner checkingCorner, int checkingX, int checkingY) {
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
     * @param side side selected of the card chosen by the player
     */
    public void playSide(Side side){
        // you need to check if the board has enough resources for the side.
        if(!checkIfEnoughResources(side)){
            System.err.println("NOT ENOUGH RESOURCES");
            //update della view
            return;
        }

        Point playingPoint;
        try{
            playingPoint = ifPresent(selectedX, selectedY, playablePositions).orElseThrow(NullPointerException::new);
            playingPoint.setSide(side);
        }catch (NullPointerException nullEx){
            nullEx.printStackTrace();
            System.err.println("Null Pointer Exception");
            return;
        }



        movePoint(selectedX, selectedY, occupiedPositions, playablePositions);

        // Analyze point selectedX+1, selectedY+1
        analyzePoint(side.getUPRIGHT(), 1, 1);

        // Analyze point selectedX-1, selectedY+1
        analyzePoint(side.getUPLEFT(), -1, 1);

        // Analyze point selectedX+1, selectedY-1
        analyzePoint(side.getDOWNRIGHT(), 1, -1);

        // Analyze point selectedX-1, selectedY-1
        analyzePoint(side.getDOWNLEFT(), -1, -1);

        // Increase
        for (Symbol resource : side.getPermanentResources()) {
            this.increaseResource(Optional.ofNullable(resource));
        }

        // qua conviene usare una notazione funzionale con gli optional
        // cioè decrementa valore se c'è il simbolo, altrimenti niente
        this.increaseResource(side.getUPRIGHT().getSymbol());
        this.increaseResource(side.getUPLEFT().getSymbol());
        this.increaseResource(side.getDOWNLEFT().getSymbol());
        this.increaseResource(side.getDOWNRIGHT().getSymbol());

        // Add points of the played side
        this.score = this.score + side.getPoints();

        // Use card ability to add points if it has it
        this.score = this.score + side.useAbility(this.getResources(), occupiedPositions, playingPoint);
    }

}

