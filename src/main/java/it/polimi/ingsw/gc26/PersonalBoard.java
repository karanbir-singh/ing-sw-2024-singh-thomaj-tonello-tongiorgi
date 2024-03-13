package it.polimi.ingsw.gc26;
import java.util.*;
public class PersonalBoard {
    private int xMin, xMax, yMin, yMax;
    private int score;
    private ArrayList<Point> occupiedPositions;
    private ArrayList<Point> playablePositions;
    private ArrayList<Point> blockedPositions;
    private MissionCard secretMission;
    private PersonalBoardSymbols personalBoardSymbols;

    public PersonalBoard(Side initialSide, MissionCard secretMission){
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
        addPoint(0,0,playablePositions);
        playSide(0,0,initialSide);


    }
    public boolean checkIfPlayable(int x, int y){
        return ifPresent(x,y,playablePositions) != null;
    }
    public void playSide(int x, int y, Side side){//supponiamo sia una posizione valida
        Point p = ifPresent(x,y, playablePositions);
        p.setSide(side);
        movePoint(x,y,occupiedPositions, playablePositions);




        //begin analyzing the point X+1,Y+1
        if(ifPresent(x+1,y+1,occupiedPositions) != null){
            Point p = ifPresent(x+1,y+1, occupiedPositions);
            personalBoardSymbols.decreaseResource(p.side.DOWNLEFT.resource);
            p.side.DOWNLEFT.isHidden = true;
        }else if(ifPresent(x+1,y+1,blockedPositions) != null){
        }else if(ifPresent(x+1,y+1,playablePositions) != null) {
        }else
        if(side.UPRIGHT.evil == true){
            addPoint(x+1,y+1,blockedPositions);
        }else{
            addPoint(x+1,y+1, playablePositions);
        }
    }
    //end analyzing the point X+1, Y+1

gyuqwggy
    //begin analyzing the point X-1,Y+1
    if(ifPresent(x-1,y+1,occupiedPositions) != null){
        Point p = ifPresent(x-1,y+1, occupiedPositions);
        personalBoardSymbols.decreaseResource(p.side.DOWNRIGHT.resource);
        p.side.DOWNRIGHT.isHidden = true;
    }else if(ifPresent(x-1,y+1,blockedPositions) != null){
    }else if(ifPresent(x-1,y+1,playablePositions) != null){
    }else{
        if(side.UPLEFT.evil == true){
            addPoint(x-1,y+1,blockedPositions);
        }else{
            addPoint(x-1,y+1, playablePositions);
        }
    }
    //end analyzing the point X-1, Y+1


    //begin analyzing the point X-1,Y-1
    if(ifPresent(x-1,y-1,occupiedPositions) != null){
        Point p = ifPresent(x-1,y-1, occupiedPositions);
        personalBoardSymbols.decreaseResource(p.side.UPRIGHT.resource);
        p.side.UPRIGHT.isHidden = true;
    }else if(ifPresent(x-1,y-1,blockedPositions) != null){
    }else if(ifPresent(x-1,y-1,playablePositions) != null){
    }else{
        if(side.DOWNLEFT.evil == true){
            addPoint(x-1,y-1,blockedPositions);
        }else{
            addPoint(x-1,y-1, playablePositions);
        }
    }
    //end analyzing the point X-1, Y-1

    //begin analyzing the point X+1,Y-1
    if(ifPresent(x+1,y-1,occupiedPositions) != null){
        Point p = ifPresent(x+1,y-1, occupiedPositions);
        personalBoardSymbols.decreaseResource(p.side.UPLEFT.resource);
        p.side.UPLEFT.isHidden = true;
    }else if(ifPresent(x+1,y-1,blockedPositions) != null){
    }else if(ifPresent(x+1,y-1,playablePositions) != null){
    }else{
        if(side.DOWNRIGHT.evil == true){
            addPoint(x+1,y-1,blockedPositions);
        }else{
            addPoint(x+1,y-1, playablePositions);
        }
    }
    //end analyzing the point X+1, Y-1




    //addSymbol
        for(int i = 0; i < side.resource.length(); i++){
        personalBoardSymbols.increaseResource(side.resource.get(i));
    }
        this.incrementResource(side.UPRIGHT.resource);
        this.incrementResource(side.UPLEFT.resource);
        this.incrementResource(side.DOWNLEFT.resource);
        this.incrementResource(side.DOWNRIGHT.resource);

    //adding points of the card played
    this.score = this.score + side.points;

    //adding points of the cart ability
    this.score = this.score + side.useAbility(occupiedPositions, personalBoardSymbols.visibleResouces);

}
private Point ifPresent(int x, int y, ArrayList<Point> l){
    for(Point p: l) {
        if(p.getX() == x && p.getY() == y) {
            return p;
        }
    }
    return null;
}


private void movePoint(int x, int y, ArrayList<Point> l1, ArrayList<Point> l2){
    Point p = ifPresent(x,y,l2);
    l2.removePoint(x,y,l2);
    l1.add(p);
}

private void addPoint(int x, int y, ArrayList<Point> l){
    Point p = new Point(x,y);
    l.add(p);
}
private void removePoint(int x, int y, ArrayList<Point> l){
    l.remove(ifPresent(x,y,l));
}
