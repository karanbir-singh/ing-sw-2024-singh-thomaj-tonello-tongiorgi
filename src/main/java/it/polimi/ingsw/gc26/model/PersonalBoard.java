package it.polimi.ingsw.gc26.model;
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
    public void endGame(){
        this.point = this.point + secretMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);
        this.point = this.point + firstCommonMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);
        this.point = this.point + secondCommonMission.getFront().checkPattern(personalBoardSymbols.getResources(), occupiedPositions);
    }
    public void playSide(int x, int y, Side side) throws NullPointerException {
        //suppose is valid position
        Point p = ifPresent(x, y, playablePositions).orElseThrow(() -> (new NullPointerException()));
        p.setSide(side);
        movePoint(x, y, occupiedPositions, playablePositions);


        //begin analyzing the point X+1,Y+1
        if (ifPresent(x + 1, y + 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x + 1, y + 1, occupiedPositions);
            personalBoardSymbols.decreaseResource(p.side.DOWNLEFT.resource);
            p.side.DOWNLEFT.isHidden = true;
        } else if (ifPresent(x + 1, y + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x + 1, y + 1, playablePositions).isPresent()) {
        } else {
            if (side.UPRIGHT.evil == true) {
                addPoint(x + 1, y + 1, blockedPositions);
            } else {
                addPoint(x + 1, y + 1, playablePositions);
            }
        }
        //end analyzing the point X+1, Y+1

        //begin analyzing the point X-1,Y+1
        if (ifPresent(x - 1, y + 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x - 1, y + 1, occupiedPositions).orElseThrow(() -> (new NullPointerException()));
            personalBoardSymbols.decreaseResource(p.side.DOWNRIGHT.resource);
            p.side.DOWNRIGHT.isHidden = true;
        } else if (ifPresent(x - 1, y + 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x - 1, y + 1, playablePositions).isPresent()) {
        } else {
            if (side.UPLEFT.evil == true) {
                addPoint(x - 1, y + 1, blockedPositions);
            } else {
                addPoint(x - 1, y + 1, playablePositions);
            }
        }
        //end analyzing the point X-1, Y+1


        //begin analyzing the point X-1,Y-1
        if (ifPresent(x - 1, y - 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x - 1, y - 1, occupiedPositions).orElseThrow(() -> (new NullPointerException()));
            personalBoardSymbols.decreaseResource(p.side.UPRIGHT.resource);
            p.side.UPRIGHT.isHidden = true;
        } else if (ifPresent(x - 1, y - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x - 1, y - 1, playablePositions).isPresent()) {
        } else {
            if (side.DOWNLEFT.evil == true) {
                addPoint(x - 1, y - 1, blockedPositions);
            } else {
                addPoint(x - 1, y - 1, playablePositions);
            }
        }
        //end analyzing the point X-1, Y-1

        //begin analyzing the point X+1,Y-1
        if (ifPresent(x + 1, y - 1, occupiedPositions).isPresent()) {
            Point p = ifPresent(x + 1, y - 1, occupiedPositions).orElseThrow(() -> (new NullPointerException()));
            personalBoardSymbols.decreaseResource(p.side.UPLEFT.resource);
            p.side.UPLEFT.isHidden = true;
        } else if (ifPresent(x + 1, y - 1, blockedPositions).isPresent()) {
        } else if (ifPresent(x + 1, y - 1, playablePositions).isPresent()) {
        } else {
            if (side.DOWNRIGHT.evil == true) {
                addPoint(x + 1, y - 1, blockedPositions);
            } else {
                addPoint(x + 1, y - 1, playablePositions);
            }
        }
        //end analyzing the point X+1, Y-1


        //addSymbol
        for (int i = 0; i < side.resource.length(); i++) {
            personalBoardSymbols.increaseResource(side.resource.get(i));
        }
        personalBoardSymbols.increaseResource(side.UPRIGHT.resource);
        personalBoardSymbols.increaseResource(side.UPLEFT.resource);
        personalBoardSymbols.increaseResource(side.DOWNLEFT.resource);
        personalBoardSymbols.increaseResource(side.DOWNRIGHT.resource);

        //adding points of the card played
        this.score = this.score + side.points;

        //adding points of the cart ability
        this.score = this.score + side.useAbility(occupiedPositions, personalBoardSymbols.getResources());

    }

    public void setPosition(int selectedX, int selectedY){
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
        Point p = ifPresent(x, y, l2).orElseThrow(() -> (new NullPointerException()));
        removePoint(x, y, l2);
        l1.add(p);
    }

    private void addPoint(int x, int y, ArrayList<Point> l) {
        Point p = new Point(x, y);
        l.add(p);
    }

    private void removePoint(int x, int y, ArrayList<Point> l) throws NullPointerException {

        l.remove(ifPresent(x, y, l).orElseThrow(() -> (new NullPointerException())));
    }


}
