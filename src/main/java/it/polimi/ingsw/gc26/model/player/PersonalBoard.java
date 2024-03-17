package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;

import java.util.*;

public class PersonalBoard {
    private int xMin, xMax, yMin, yMax;
    private int score;
    private final ArrayList<Point> occupiedPositions;
    private final ArrayList<Point> playablePositions;
    private final ArrayList<Point> blockedPositions;
    private final MissionCard secretMission;
    private final MissionCard firstCommonMission;
    private final MissionCard secondCommonMission;
    private final PersonalBoardSymbols personalBoardSymbols;

    private int selectedX;
    private int selectedY;

    public PersonalBoard(Side initialSide, MissionCard secretMission) {
        score = 0;
        xMin = -1;
        xMax = 1;
        yMin = -1;
        yMax = 1;
        this.secretMission = secretMission;
        personalBoardSymbols = new PersonalBoardSymbols();
        occupiedPositions = new ArrayList<Point>();
        playablePositions = new ArrayList<Point>();
        blockedPositions = new ArrayList<Point>();

        addPoint(0, 0, playablePositions);
        playSide(0, 0, initialSide);
    }

    public boolean checkIfPlayable(int x, int y) {
        return ifPresent(x, y, playablePositions).isPresent();
    }

    public void endGame() {
        this.score = this.score + secretMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);

        this.score = this.score + firstCommonMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);
        this.score = this.score + secondCommonMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);
    }

    public void playSide(int x, int y, Side side) throws NullPointerException {
        //suppose is valid position
        Point p = ifPresent(x, y, playablePositions).orElseThrow(NullPointerException::new);
        p.setSide(side);
        movePoint(x, y, occupiedPositions, playablePositions);

        //begin analyzing the point X+1,Y+1
        if (ifPresent(x + 1, y + 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x + 1, y + 1, occupiedPositions);

            // qua conviene usare una notazione funzionale con gli optional
            // cioè decrementa valore se c'è il simbolo, altrimenti niente
            personalBoardSymbols.decreaseResource(p.getSide().getDOWNLEFT().getSymbol().get());

            p.getSide().getDOWNLEFT().setHidden(true);
        } else if (ifPresent(x + 1, y + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x + 1, y + 1, playablePositions).isPresent()) {
        } else {
            if (side.getUPRIGHT().isEvil()) {
                addPoint(x + 1, y + 1, blockedPositions);
            } else {
                addPoint(x + 1, y + 1, playablePositions);
            }
        }
        //end analyzing the point X+1, Y+1

        //begin analyzing the point X-1,Y+1
        if (ifPresent(x - 1, y + 1, occupiedPositions).isPresent()) {
            p = ifPresent(x - 1, y + 1, occupiedPositions).orElseThrow(NullPointerException::new);

            // qua conviene usare una notazione funzionale con gli optional
            // cioè decrementa valore se c'è il simbolo, altrimenti niente
            personalBoardSymbols.decreaseResource(p.getSide().getDOWNRIGHT().getSymbol().get());

            p.getSide().getDOWNRIGHT().setHidden(true);
        } else if (ifPresent(x - 1, y + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x - 1, y + 1, playablePositions).isPresent()) {
        } else {
            if (side.getUPLEFT().isEvil()) {
                addPoint(x - 1, y + 1, blockedPositions);
            } else {
                addPoint(x - 1, y + 1, playablePositions);
            }
        }
        //end analyzing the point X-1, Y+1

        //begin analyzing the point X-1,Y-1
        if (ifPresent(x - 1, y - 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x - 1, y - 1, occupiedPositions).orElseThrow(NullPointerException::new);

            // qua conviene usare una notazione funzionale con gli optional
            // cioè decrementa valore se c'è il simbolo, altrimenti niente
            personalBoardSymbols.decreaseResource(p.getSide().getUPRIGHT().getSymbol().get());

            p.getSide().getUPRIGHT().setHidden(true);
        } else if (ifPresent(x - 1, y - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x - 1, y - 1, playablePositions).isPresent()) {
        } else {
            if (side.getDOWNLEFT().isEvil()) {
                addPoint(x - 1, y - 1, blockedPositions);
            } else {
                addPoint(x - 1, y - 1, playablePositions);
            }
        }
        //end analyzing the point X-1, Y-1

        //begin analyzing the point X+1,Y-1
        if (ifPresent(x + 1, y - 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x + 1, y - 1, occupiedPositions).orElseThrow(NullPointerException::new);

            // qua conviene usare una notazione funzionale con gli optional
            // cioè decrementa valore se c'è il simbolo, altrimenti niente
            personalBoardSymbols.decreaseResource(p.getSide().getUPLEFT().getSymbol().get());

            p.getSide().getUPLEFT().setHidden(true);
        } else if (ifPresent(x + 1, y - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x + 1, y - 1, playablePositions).isPresent()) {
        } else {
            if (side.getDOWNRIGHT().isEvil()) {
                addPoint(x + 1, y - 1, blockedPositions);
            } else {
                addPoint(x + 1, y - 1, playablePositions);
            }
        }
        //end analyzing the point X+1, Y-1


        //addSymbol
        for (Symbol s: side.getPermanentResources()) {
            personalBoardSymbols.increaseResource(s);
        }

        // qua conviene usare una notazione funzionale con gli optional
        // cioè decrementa valore se c'è il simbolo, altrimenti niente
        personalBoardSymbols.increaseResource(side.getUPRIGHT().getSymbol().get());
        personalBoardSymbols.increaseResource(side.getUPLEFT().getSymbol().get());
        personalBoardSymbols.increaseResource(side.getDOWNLEFT().getSymbol().get());
        personalBoardSymbols.increaseResource(side.getDOWNRIGHT().getSymbol().get());

        //adding points of the card played
        this.score = this.score + side.getPoints();

        //adding points of the cart ability
        // TODO controlla che cabbo è p
        this.score = this.score + side.useAbility(personalBoardSymbols.getResources(), occupiedPositions, p);

    }

    public void setPosition(int selectedX, int selectedY) {
        this.selectedX = selectedX;
        this.selectedY = selectedY;
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

    public int getScore() {
        return score;
    }
}
