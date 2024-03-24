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
    private final Card secretMission;
    private final Card firstCommonMission;
    private final Card secondCommonMission;
    private final Map<Symbol, Integer> visibleResources;
    private int selectedX = 0;
    private int selectedY = 0;

    public PersonalBoard(Side initialSide, Card secretMission) {
        score = 0;
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
        visibleResources = new HashMap<Symbol, Integer>();
        visibleResources.put(Symbol.FUNGI, 0);
        visibleResources.put(Symbol.ANIMAL, 0);
        visibleResources.put(Symbol.PLANT, 0);
        visibleResources.put(Symbol.INSECT, 0);
        visibleResources.put(Symbol.INKWELL, 0);
        visibleResources.put(Symbol.QUILL, 0);
        visibleResources.put(Symbol.MANUSCRIPT, 0);

        this.secretMission = secretMission;
        this.firstCommonMission = firstCommonMission;
        this.secondCommonMission = secondCommonMission;

        occupiedPositions = new ArrayList<>();
        playablePositions = new ArrayList<>();
        blockedPositions = new ArrayList<>();

        addPoint(0, 0, playablePositions);
        playSide(initialSide); // inizialmente selectedX and selectedY sono 0.
    }

    public boolean checkIfPlayablePosition(int x, int y) {
        return ifPresent(x, y, playablePositions).isPresent();
    }

    public boolean checkIfEnoughResources(Side side) {
        for (Symbol symbol : side.getRequestedResources().keySet()) {
            if (this.visibleResources.get(symbol) < side.getRequestedResources().get(symbol)) {
                return false;
            }
        }
        return true;
    }

    public void endGame() {
        this.score = this.score + secretMission.getFront().checkPattern(this.getResources(), occupiedPositions);

        this.score = this.score + firstCommonMission.getFront().checkPattern(this.getResources(), occupiedPositions);
        this.score = this.score + secondCommonMission.getFront().checkPattern(this.getResources(), occupiedPositions);
    }


    public void setPosition(int selectedX, int selectedY) {
        this.selectedX = selectedX;
        this.selectedY = selectedY;
    }
    public int getScore() {
        return score;
    }

    private Optional<Point> ifPresent(int x, int y, ArrayList<Point> l) {
        Optional<Point> o = Optional.empty();
        for (Point p : l) {
            if (p.getX() == x && p.getY() == y) {
                o = Optional.of(p);
            }
        }
        return o;
    }


    private void movePoint(int x, int y, ArrayList<Point> l1, ArrayList<Point> l2) throws NullPointerException {
        Point p = ifPresent(x, y, l2).orElseThrow(NullPointerException::new);
        removePoint(x, y, l2);
        l1.add(p);
    }

    private void addPoint(int x, int y, ArrayList<Point> l) {
        Point p = new Point(x, y);
        l.add(p);
    }

    private void removePoint(int x, int y, ArrayList<Point> l) throws NullPointerException {
        l.remove(ifPresent(x, y, l).orElseThrow(NullPointerException::new));
    }


    public int getSelectedX() {
        return this.selectedX;
    }

    public int getSelectedY() {
        return this.selectedY;
    }


    public Integer getResourceQuantity(Symbol symbol) {
        return visibleResources.get(symbol);
    }

    public void increaseResource(Optional<Symbol> symbol) {
        if (symbol.isPresent()) {
            visibleResources.put(symbol.get(), visibleResources.get(symbol.get()) + 1);
        }

    }

    public void decreaseResource(Optional<Symbol> symbol) {
        if (symbol.isPresent()) {
            visibleResources.put(symbol.get(), visibleResources.get(symbol.get()) - 1);
        }

    }

    public Map<Symbol, Integer> getResources() {
        return new HashMap<>(this.visibleResources);
    }

    public void showBoard() {
        for (int currY = yMax + 1; currY >= yMin; currY--) {
            for (int currX =  xMin - 1; currX <= xMax; currX++) {
                if(currY == yMax + 1 && currX != xMin - 1){
                    System.out.print(currX + "   ");
                }else if(currX == xMin - 1 && currY != yMax + 1){//anche questo
                    System.out.print(currY+ "   ");
                }else if (ifPresent(currX, currY, blockedPositions).isPresent()) {
                    System.out.print("X   ");
                } else if (ifPresent(currX, currY, playablePositions).isPresent()) {
                    System.out.print("o   ");
                } else if (ifPresent(currX, currY, occupiedPositions).isPresent()) {
                    System.out.print("C   ");
                } else {
                    System.out.print("    ");
                }
            }
            System.out.println("\n");

        }
    }

    public ArrayList<Point> getOccupiedPositions() {
        return this.occupiedPositions;
    }


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

        } else if (ifPresent(selectedX + checkingX, selectedY + checkingY, blockedPositions).isEmpty() &&
                ifPresent(selectedX + checkingX, selectedY + checkingY, playablePositions).isEmpty()) {

            if (checkingCorner.isEvil()) {
                addPoint(selectedX + checkingX, selectedY + checkingY, blockedPositions);
            } else {
                addPoint(selectedX + checkingX, selectedY + checkingY, playablePositions);
            }

            if(selectedX + checkingX >= xMax){
                xMax = selectedX + checkingX;
            }
            if(selectedX + checkingX <= xMin){
                xMin = selectedX + checkingX;
            }
            if(selectedY + checkingY >= yMax){
                yMax = selectedY + checkingY;
            }
            if(selectedY + checkingY <= yMin){
                yMin = selectedY + checkingY;
            }
        }
    }


    public void playSide(Side side) throws NullPointerException {
        // Suppose is valid position
        Point playingPoint = ifPresent(selectedX, selectedY, playablePositions).orElseThrow(NullPointerException::new);
        playingPoint.setSide(side);
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
            this.increaseResource(Optional.of(resource));
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

