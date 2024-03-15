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
                if(p.getSide().checkSideSymbol(Symbol.FUNGI) &&
                    !p.getFlag(0)){
                    for(Point p1: occupiedPositions){
                        if(p1.getSide().checkSideSymbol(Symbol.FUNGI) &&
                            p1.getX() == p.getX() + 1 &&
                            p1.getY() == p.getY() + 1 &&
                                !p1.getFlag(0)){
                            findFirst = Optional.of(p1);

                        }
                    }
                    for(Point p2: occupiedPositions){
                        if(p2.getSide().checkSideSymbol(Symbol.FUNGI) &&
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

            for(Point p: occupiedPositions){
                if(p.getSide().checkSideSymbol(Symbol.PLANT)
                    && !p.getFlag(1)){
                    for(Point p1: occupiedPositions){
                        if(p1.getSide().checkSideSymbol(Symbol.PLANT) &&
                            p1.getX() == p.getX() + 1 &&
                            p1.getY() == p.getY() - 1 &&
                            !p1.getFlag(1)){
                            findFirst = Optional.of(p1);
                        }
                    }
                    for(Point p2: occupiedPositions){
                        if(p2.getSide().checkSideSymbol(Symbol.PLANT) &&
                                p2.getX() == p.getX() + 2 &&
                                p2.getY() == p.getY() - 2 &&
                                !p2.getFlag(1)){
                            findSecond = Optional.of(p2);
                        }
                    }
                    if(findFirst.isPresent() && findSecond.isPresent()){
                        points = points + 2;
                        findFirst.get().setFlag(1,true);
                        findSecond.get().setFlag(1,true);
                        p.setFlag(1,true);

                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }

        }else if(type == 3){
            for(Point p: occupiedPositions) {
                if (p.getSide().checkSideSymbol(Symbol.ANIMAL) &&
                        !p.getFlag(2)) {
                    for (Point p1 : occupiedPositions) {
                        if (p1.getSide().checkSideSymbol(Symbol.ANIMAL) &&
                                p1.getX() == p.getX() + 1 &&
                                p1.getY() == p.getY() + 1 &&
                                !p1.getFlag(2)) {
                            findFirst = Optional.of(p1);

                        }
                    }
                    for (Point p2 : occupiedPositions) {
                        if (p2.getSide().checkSideSymbol(Symbol.ANIMAL) &&
                                p2.getX() == p.getX() + 2 &&
                                p2.getY() == p.getY() + 2 &&
                                !p2.getFlag(2)) {
                            findSecond = Optional.of(p2);

                        }
                    }
                    if (findFirst.isPresent() && findSecond.isPresent()) {
                        points = points + 2;
                        findFirst.get().setFlag(2, true);
                        findSecond.get().setFlag(2, true);
                        p.setFlag(2, true);
                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }
        }else if(type == 4){
            for(Point p: occupiedPositions) {
                if (p.getSide().checkSideSymbol(Symbol.INSECT)
                        && !p.getFlag(3)) {
                    for (Point p1 : occupiedPositions) {
                        if (p1.getSide().checkSideSymbol(Symbol.INSECT) &&
                                p1.getX() == p.getX() + 1 &&
                                p1.getY() == p.getY() - 1 &&
                                !p1.getFlag(3)) {
                            findFirst = Optional.of(p1);
                        }
                    }
                    for (Point p2 : occupiedPositions) {
                        if (p2.getSide().checkSideSymbol(Symbol.PLANT) &&
                                p2.getX() == p.getX() + 2 &&
                                p2.getY() == p.getY() - 2 &&
                                !p2.getFlag(3)) {
                            findSecond = Optional.of(p2);
                        }
                    }
                    if (findFirst.isPresent() && findSecond.isPresent()) {
                        points = points + 2;
                        findFirst.get().setFlag(3, true);
                        findSecond.get().setFlag(3, true);
                        p.setFlag(3, true);

                    }
                }

                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }
        }

        return points;
    }
    public int getType(){
        return super.getType();
    }

}
