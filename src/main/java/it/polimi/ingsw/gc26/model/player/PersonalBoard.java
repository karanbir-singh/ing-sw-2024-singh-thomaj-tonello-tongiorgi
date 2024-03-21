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
    private final Map<Symbol,Integer> visibleResources;
    private int selectedX = 0;
    private int selectedY = 0;

    public PersonalBoard(Side initialSide, Card secretMission, Card firstCommonMission, Card secondCommonMission) {
        score = 0;
        xMin = 0;
        xMax = 0;
        yMin = 0;
        yMax = 0;
        visibleResources = new HashMap<Symbol, Integer>();
        visibleResources.put(Symbol.FUNGI,0);
        visibleResources.put(Symbol.ANIMAL,0);
        visibleResources.put(Symbol.PLANT,0);
        visibleResources.put(Symbol.INSECT,0);
        visibleResources.put(Symbol.INKWELL,0);
        visibleResources.put(Symbol.QUILL,0);
        visibleResources.put(Symbol.MANUSCRIPT,0);

        this.secretMission = secretMission;
        this.firstCommonMission = firstCommonMission;
        this.secondCommonMission = secondCommonMission;

        occupiedPositions = new ArrayList<Point>();
        playablePositions = new ArrayList<Point>();
        blockedPositions = new ArrayList<Point>();

        addPoint(0, 0, playablePositions);
        playSide(initialSide); // inizialmente selectedX and selectedY sono 0.
    }

    public boolean checkIfPlayablePosition(int x, int y) {
        return ifPresent(x, y, playablePositions).isPresent();
    }

    public boolean checkIfEnoughResources(Side side){
        for(Symbol symbol: side.getRequestedResources().keySet()){
            if(this.visibleResources.get(symbol) < side.getRequestedResources().get(symbol)){
                return false;
            }
        }
        return true;
    }
    public void endGame() {
        this.score = this.score + secretMission.getFront().checkPattern(getResources(), occupiedPositions);

        this.score = this.score + firstCommonMission.getFront().checkPattern(getResources(), occupiedPositions);
        this.score = this.score + secondCommonMission.getFront().checkPattern(getResources(), occupiedPositions);
    }

    public void playSide(Side side) throws NullPointerException {

        //suppose is valid position
        Point p = ifPresent(selectedX, selectedY, playablePositions).orElseThrow(NullPointerException::new);
        p.setSide(side);
        movePoint(selectedX, selectedY, occupiedPositions, playablePositions);

        //begin analyzing the point X+1,Y+1
        if (ifPresent(selectedX + 1, selectedY + 1, occupiedPositions).isPresent()) {
            Point p1 = ifPresent(selectedX + 1, selectedY + 1, occupiedPositions).orElseThrow(NullPointerException::new);

            this.decreaseResource(p1.getSide().getDOWNLEFT().getSymbol());
            p1.getSide().getDOWNLEFT().setHidden(true);

        } else if (ifPresent(selectedX + 1, selectedY + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX + 1, selectedY + 1, playablePositions).isPresent()) {
        } else {
            if (side.getUPRIGHT().isEvil()) {
                addPoint(selectedX + 1, selectedY + 1, blockedPositions);
            } else {
                addPoint(selectedX + 1, selectedY + 1, playablePositions);
            }


            if(selectedX + 1 > xMax){
                xMax = selectedX + 1;
            }
            if(selectedY + 1 > yMax){
                yMax = selectedY + 1;
            }
        }
        //end analyzing the point X+1, Y+1

        //begin analyzing the point X-1,Y+1
        if (ifPresent(selectedX - 1, selectedY + 1, occupiedPositions).isPresent()) {
            Point p2 = ifPresent(selectedX - 1, selectedY + 1, occupiedPositions).orElseThrow(NullPointerException::new);

            this.decreaseResource(p2.getSide().getDOWNRIGHT().getSymbol());

            p2.getSide().getDOWNRIGHT().setHidden(true);
        } else if (ifPresent(selectedX - 1, selectedY + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX - 1, selectedY + 1, playablePositions).isPresent()) {
        } else {
            if (side.getUPLEFT().isEvil()) {
                addPoint(selectedX - 1, selectedY + 1, blockedPositions);
            } else {
                addPoint(selectedX - 1, selectedY + 1, playablePositions);
            }


            if(selectedX - 1 < xMin){
                xMin = selectedX - 1;
            }
            if(selectedY + 1 > yMax){
                yMax = selectedY + 1;
            }
        }
        //end analyzing the point X-1, Y+1

        //begin analyzing the point X-1,Y-1
        if (ifPresent(selectedX - 1, selectedY - 1, occupiedPositions).isPresent()) {
            Point p3 = ifPresent(selectedX - 1, selectedY - 1, occupiedPositions).orElseThrow(NullPointerException::new);

            this.decreaseResource(p3.getSide().getUPRIGHT().getSymbol());

            p3.getSide().getUPRIGHT().setHidden(true);
        } else if (ifPresent(selectedX - 1, selectedY - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX - 1, selectedY - 1, playablePositions).isPresent()) {
        } else {
            if (side.getDOWNLEFT().isEvil()) {
                addPoint(selectedX - 1, selectedY - 1, blockedPositions);
            } else {
                addPoint(selectedX - 1, selectedY - 1, playablePositions);
            }



            if(selectedX - 1 < xMin){
                xMin = selectedX - 1;
            }
            if(selectedY - 1 < yMin){
                yMin = selectedY - 1;
            }
        }
        //end analyzing the point X-1, Y-1

        //begin analyzing the point X+1,Y-1
        if (ifPresent(selectedX + 1, selectedY - 1, occupiedPositions).isPresent()) {
            Point p4 = ifPresent(selectedX + 1, selectedY - 1, occupiedPositions).orElseThrow(NullPointerException::new);

            this.decreaseResource(p4.getSide().getUPLEFT().getSymbol());

            p4.getSide().getUPLEFT().setHidden(true);
        } else if (ifPresent(selectedX + 1, selectedY - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(selectedX + 1, selectedY - 1, playablePositions).isPresent()) {
        } else {
            if (side.getDOWNRIGHT().isEvil()) {
                addPoint(selectedX + 1, selectedY - 1, blockedPositions);
            } else {
                addPoint(selectedX + 1, selectedY - 1, playablePositions);
            }

            if(selectedX + 1 > xMax){
                xMax = selectedX + 1;
            }
            if(selectedY - 1 < yMin){
                yMin = selectedY - 1;
            }
        }
        //end analyzing the point X+1, Y-1



        //addSymbol
        for (Symbol s: side.getPermanentResources()) {
            this.increaseResource(Optional.of(s));
        }

        // qua conviene usare una notazione funzionale con gli optional
        // cioè decrementa valore se c'è il simbolo, altrimenti niente
        this.increaseResource(side.getUPRIGHT().getSymbol());
        this.increaseResource(side.getUPLEFT().getSymbol());
        this.increaseResource(side.getDOWNLEFT().getSymbol());
        this.increaseResource(side.getDOWNRIGHT().getSymbol());

        //adding points of the card played
        this.score = this.score + side.getPoints();

        //adding points of the cart ability
        this.score = this.score + side.useAbility(getResources(), occupiedPositions, p);

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


    public Integer getResourceQuantity(Symbol symbol){
        return visibleResources.get(symbol);
    }
    public void increaseResource(Optional<Symbol> symbol){
        if(symbol.isPresent()){
            visibleResources.put(symbol.get(), visibleResources.get(symbol.get()) + 1);
        }

    }

    public void decreaseResource(Optional<Symbol> symbol){
        if(symbol.isPresent()){
            visibleResources.put(symbol.get(), visibleResources.get(symbol.get()) - 1);
        }

    }

    public Map<Symbol,Integer> getResources(){
        Map<Symbol,Integer> resources = new HashMap(visibleResources);
        return resources;
    }

    public void showBoard(){
        for(int i = xMin -1; i <= xMax ; i++){
            for(int j = yMax + 1; j >= yMin; j--){

                if(ifPresent(i,j,blockedPositions).isPresent()){
                    System.out.print("X   ");
                }
                else if(ifPresent(i,j,playablePositions).isPresent()){
                    System.out.print("o   ");
                }
                else if(ifPresent(i,j,occupiedPositions).isPresent()){
                    System.out.print("C   ");
                }else{
                    System.out.print("    ");
                }
            }
            System.out.println("\n");

        }
    }
    public ArrayList<Point> getOccupiedPositions(){
        return  this.occupiedPositions;
    }




}
