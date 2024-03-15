package it.polimi.ingsw.gc26.model;

import java.util.*;

public class MissionDiagonalPattern extends MissionCardFront{

    public MissionDiagonalPatter(int type){
        super(type);
    }
    public int checkPattern(Map<Symbol,Integer> visibleResources, ArrayList<Point> occupiedPositions){
        int points = 0;
        Optional<Point> findFirst = Optional.empty();
        Optional<Point> findSecond = Optional.empty();
        if(type == 1){
            for(Point p: occupiedPositions){
                if(p.getSide().checkSideSymbol(Symbol.MUSHROOMS) &&
                    !p.getFlag(0)){
                    for(Point p1: occupiedPositions){
                        if(p1.getSide().checkSideSymbol(Symbol.MUSHROOMS) &&
                            p1.getX() == p.getX() + 1 &&
                            p1.getY() == p.getY() + 1 &&
                                !p1.getFlag(0)){
                            findFirst = Optional.of(p1);

                        }
                    }
                    for(Point p2: occupiedPositions){
                        if(p2.getSide().checkSideSymbol(Symbol.MUSHROOMS) &&
                                p2.getX() == p.getX() + 2 &&
                                p2.getY() == p.getY() + 2 &&
                                !p2.getFlag(0)){
                                findSecond = Optional.of(p2);

                        }
                    }
                    if(findFirst.isPresent() && findSecond.isPresent()){
                        points = points + 2;
                        findFirst.get().setFlag(0,true);
                        findSecond.get().setFlag(0,true);
                        p.setFlag(0,true);
                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }
        }else if(type == 2){

        }else if(type == 3){

        }else if(type == 4){

        }

        return points;
    }
    public int getType(){
        return super.getType();
    }

}
