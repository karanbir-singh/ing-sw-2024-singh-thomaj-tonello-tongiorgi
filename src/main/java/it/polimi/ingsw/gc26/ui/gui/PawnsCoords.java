package it.polimi.ingsw.gc26.ui.gui;

import it.polimi.ingsw.gc26.model.player.Point;

public enum PawnsCoords {
    ZERO(new Point(3, 10 )),
    ONE(new Point(5, 10)),
    TWO(new Point(7, 10)),
    THREE(new Point(8, 9)),
    FOUR(new Point(6, 9)),
    FIVE(new Point(4, 9)),
    SIX(new Point(2, 9)),
    SEVEN(new Point(2, 8)),
    EIGHT(new Point(4, 8)),
    NINE(new Point(6, 8)),
    TEN(new Point(8, 8)),
    ELEVEN(new Point(8, 7)),
    TWELVE(new Point(6, 7)),
    THIRTEEN(new Point(4, 7)),
    FOURTEEN(new Point(2, 7)),
    FIFTEEN(new Point(2, 6)),
    SIXTEEN(new Point(4, 6)),
    SEVENTEEN(new Point(6, 6)),
    EIGHTEEN(new Point(8, 6)),
    NINETEEN(new Point(8, 5)),
    TWENTY(new Point(5, 4)),
    TWENTYONE(new Point(2, 5)),
    TWENTYTWO(new Point(2, 4)),
    TWENTYTHREE(new Point(2, 2)),
    TWENTYFOUR(new Point(3, 1)),
    TWENTYFIVE(new Point(5, 1)),
    TWENTYSIX(new Point(7, 1)),
    TWENTYSEVEN(new Point(8, 2)),
    TWENTYEIGHT(new Point(8, 4)),
    TWENTYNINE(new Point(5, 2));


    private Point point;

    PawnsCoords(Point point) {
        this.point = point;
    }

    public Point getCoords() {
        return this.point;
    }
}
