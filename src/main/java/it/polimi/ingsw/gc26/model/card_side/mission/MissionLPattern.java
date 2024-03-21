package it.polimi.ingsw.gc26.model.card_side.mission;

import it.polimi.ingsw.gc26.model.card_side.Corner;
import it.polimi.ingsw.gc26.model.card_side.MissionCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.*;

public class MissionLPattern extends MissionCardFront {
    public MissionLPattern(int type) {
        setType(type);
        setPoints(0);

        setSideSymbol(null);
        setDOWNLEFT(null);
        setDOWNRIGHT(null);
        setUPLEFT(null);
        setUPRIGHT(null);
        setPermanentResources(new ArrayList<>());
        setRequestedResources(new HashMap<>());
    }

    public int checkPattern(Map<Symbol, Integer> visibleResources, ArrayList<Point> occupiedPositions) {
        int points = 0;
        Optional<Point> findFirst = Optional.empty();
        Optional<Point> findSecond = Optional.empty();
        if (getType() == 1) {
            for (Point p : occupiedPositions) {
                if (p.getSide().checkSideSymbol(Symbol.PLANT) &&
                        !p.getFlag(4)) {
                    for (Point p1 : occupiedPositions) {
                        if (p1.getSide().checkSideSymbol(Symbol.FUNGI) &&
                                p1.getX() == p.getX() - 1 &&
                                p1.getY() == p.getY() + 1 &&
                                !p1.getFlag(4)) {
                            findFirst = Optional.of(p1);

                        }
                    }
                    for (Point p2 : occupiedPositions) {
                        if (p2.getSide().checkSideSymbol(Symbol.FUNGI) &&
                                p2.getX() == p.getX() - 1 &&
                                p2.getY() == p.getY() + 2 &&
                                !p2.getFlag(4)) {
                            findSecond = Optional.of(p2);

                        }
                    }
                    if (findFirst.isPresent() && findSecond.isPresent()) {
                        points = points + 3;
                        findFirst.get().setFlag(4, true);
                        findSecond.get().setFlag(4, true);
                        p.setFlag(4, true);
                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }


        } else if (getType() == 2) {

            for (Point p : occupiedPositions) {
                if (p.getSide().checkSideSymbol(Symbol.INSECT) &&
                        !p.getFlag(5)) {
                    for (Point p1 : occupiedPositions) {
                        if (p1.getSide().checkSideSymbol(Symbol.PLANT) &&
                                p1.getX() == p.getX() + 1 &&
                                p1.getY() == p.getY() + 1 &&
                                !p1.getFlag(5)) {
                            findFirst = Optional.of(p1);

                        }
                    }
                    for (Point p2 : occupiedPositions) {
                        if (p2.getSide().checkSideSymbol(Symbol.PLANT) &&
                                p2.getX() == p.getX() + 1 &&
                                p2.getY() == p.getY() + 2 &&
                                !p2.getFlag(5)) {
                            findSecond = Optional.of(p2);

                        }
                    }
                    if (findFirst.isPresent() && findSecond.isPresent()) {
                        points = points + 3;
                        findFirst.get().setFlag(5, true);
                        findSecond.get().setFlag(5, true);
                        p.setFlag(5, true);
                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }


        } else if (getType() == 3) {

            for (Point p : occupiedPositions) {
                if (p.getSide().checkSideSymbol(Symbol.FUNGI) &&
                        !p.getFlag(6)) {
                    for (Point p1 : occupiedPositions) {
                        if (p1.getSide().checkSideSymbol(Symbol.ANIMAL) &&
                                p1.getX() == p.getX() - 1 &&
                                p1.getY() == p.getY() - 1 &&
                                !p1.getFlag(6)) {
                            findFirst = Optional.of(p1);

                        }
                    }
                    for (Point p2 : occupiedPositions) {
                        if (p2.getSide().checkSideSymbol(Symbol.ANIMAL) &&
                                p2.getX() == p.getX() - 1 &&
                                p2.getY() == p.getY() - 2 &&
                                !p2.getFlag(6)) {
                            findSecond = Optional.of(p2);

                        }
                    }
                    if (findFirst.isPresent() && findSecond.isPresent()) {
                        points = points + 3;
                        findFirst.get().setFlag(6, true);
                        findSecond.get().setFlag(6, true);
                        p.setFlag(6, true);
                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }

        } else if (getType() == 4) {
            for (Point p : occupiedPositions) {
                if (p.getSide().checkSideSymbol(Symbol.ANIMAL) &&
                        !p.getFlag(7)) {
                    for (Point p1 : occupiedPositions) {
                        if (p1.getSide().checkSideSymbol(Symbol.INSECT) &&
                                p1.getX() == p.getX() + 1 &&
                                p1.getY() == p.getY() - 1 &&
                                !p1.getFlag(7)) {
                            findFirst = Optional.of(p1);

                        }
                    }
                    for (Point p2 : occupiedPositions) {
                        if (p2.getSide().checkSideSymbol(Symbol.INSECT) &&
                                p2.getX() == p.getX() + 1 &&
                                p2.getY() == p.getY() - 2 &&
                                !p2.getFlag(7)) {
                            findSecond = Optional.of(p2);

                        }
                    }
                    if (findFirst.isPresent() && findSecond.isPresent()) {
                        points = points + 3;
                        findFirst.get().setFlag(7, true);
                        findSecond.get().setFlag(7, true);
                        p.setFlag(7, true);
                    }
                }
                findFirst = Optional.empty();
                findSecond = Optional.empty();
            }
        }

        return points;
    }
}
