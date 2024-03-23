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
    private final PersonalBoardSymbols personalBoardSymbols;

    private int selectedX = 0;
    private int selectedY = 0;

    public PersonalBoard(Side initialSide, Card secretMission) {
        score = 0;
        xMin = -1;
        xMax = 1;
        yMin = -1;
        yMax = 1;
        this.secretMission = secretMission;
        personalBoardSymbols = new PersonalBoardSymbols();
        occupiedPositions = new ArrayList<>();
        playablePositions = new ArrayList<>();
        blockedPositions = new ArrayList<>();

        addPoint(0, 0, playablePositions);
        playSide(initialSide); // inizialmente selectedX and selectedY sono 0.
    }

    public boolean checkIfPlayable(int x, int y) {
        return ifPresent(x, y, playablePositions).isPresent();
    }

    public void endGame(ArrayList<Card> commonMissions) {
        this.score = this.score + secretMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);

        for (Card card : commonMissions){
            this.score = this.score + card.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);
        }
    }

    public void playSide(Side side) throws NullPointerException {

        //suppose is valid position
        Point p = ifPresent(selectedX, selectedY, playablePositions).orElseThrow(NullPointerException::new);
        p.setSide(side);
        movePoint(selectedX, selectedY, occupiedPositions, playablePositions);

        //begin analyzing the point X+1,Y+1
        if (ifPresent(selectedX + 1, selectedY + 1, occupiedPositions).isPresent()) {
            Point p1 = ifPresent(selectedX + 1, selectedY + 1, occupiedPositions).orElseThrow(NullPointerException::new);

            personalBoardSymbols.decreaseResource(p1.getSide().getDOWNLEFT().getSymbol());
            p1.getSide().getDOWNLEFT().setHidden(true);

        } else if (ifPresent(selectedX + 1, selectedY + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX + 1, selectedY + 1, playablePositions).isPresent()) {
        } else {
            if (side.getUPRIGHT().isEvil()) {
                addPoint(selectedX + 1, selectedY + 1, blockedPositions);
            } else {
                addPoint(selectedX + 1, selectedY + 1, playablePositions);
            }
        }
        //end analyzing the point X+1, Y+1

        //begin analyzing the point X-1,Y+1
        if (ifPresent(selectedX - 1, selectedY + 1, occupiedPositions).isPresent()) {
            Point p2 = ifPresent(selectedX - 1, selectedY + 1, occupiedPositions).orElseThrow(NullPointerException::new);

            personalBoardSymbols.decreaseResource(p2.getSide().getDOWNRIGHT().getSymbol());

            p2.getSide().getDOWNRIGHT().setHidden(true);
        } else if (ifPresent(selectedX - 1, selectedY + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX - 1, selectedY + 1, playablePositions).isPresent()) {
        } else {
            if (side.getUPLEFT().isEvil()) {
                addPoint(selectedX - 1, selectedY + 1, blockedPositions);
            } else {
                addPoint(selectedX - 1, selectedY + 1, playablePositions);
            }
        }
        //end analyzing the point X-1, Y+1

        //begin analyzing the point X-1,Y-1
        if (ifPresent(selectedX - 1, selectedY - 1, occupiedPositions).isPresent()) {
            Point p3 = ifPresent(selectedX - 1, selectedY - 1, occupiedPositions).orElseThrow(NullPointerException::new);

            personalBoardSymbols.decreaseResource(p3.getSide().getUPRIGHT().getSymbol());

            p3.getSide().getUPRIGHT().setHidden(true);
        } else if (ifPresent(selectedX - 1, selectedY - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX - 1, selectedY - 1, playablePositions).isPresent()) {
        } else {
            if (side.getDOWNLEFT().isEvil()) {
                addPoint(selectedX - 1, selectedY - 1, blockedPositions);
            } else {
                addPoint(selectedX - 1, selectedY - 1, playablePositions);
            }
        }
        //end analyzing the point X-1, Y-1

        //begin analyzing the point X+1,Y-1
        if (ifPresent(selectedX + 1, selectedY - 1, occupiedPositions).isPresent()) {
            Point p4 = ifPresent(selectedX + 1, selectedY - 1, occupiedPositions).orElseThrow(NullPointerException::new);

            personalBoardSymbols.decreaseResource(p4.getSide().getUPLEFT().getSymbol());

            p4.getSide().getUPLEFT().setHidden(true);
        } else if (ifPresent(selectedX + 1, selectedY - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX + 1, selectedY - 1, playablePositions).isPresent()) {
        } else {
            if (side.getDOWNRIGHT().isEvil()) {
                addPoint(selectedX + 1, selectedY - 1, blockedPositions);
            } else {
                addPoint(selectedX + 1, selectedY - 1, playablePositions);
            }
        }
        //end analyzing the point X+1, Y-1


        //addSymbol
        for (Symbol s: side.getPermanentResources()) {
            personalBoardSymbols.increaseResource(Optional.of(s));
        }

        // qua conviene usare una notazione funzionale con gli optional
        // cioè decrementa valore se c'è il simbolo, altrimenti niente
        personalBoardSymbols.increaseResource(side.getUPRIGHT().getSymbol());
        personalBoardSymbols.increaseResource(side.getUPLEFT().getSymbol());
        personalBoardSymbols.increaseResource(side.getDOWNLEFT().getSymbol());
        personalBoardSymbols.increaseResource(side.getDOWNRIGHT().getSymbol());

        //adding points of the card played
        this.score = this.score + side.getPoints();

        //adding points of the cart ability
        this.score = this.score + side.useAbility(personalBoardSymbols.getResources(), occupiedPositions, p);

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


    public int getSelectedX(){
        return this.selectedX;
    }

    public int getSelectedY(){
        return this.selectedY;
    }
}
