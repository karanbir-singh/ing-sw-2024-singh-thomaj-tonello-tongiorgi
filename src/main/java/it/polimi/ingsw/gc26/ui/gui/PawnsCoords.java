package it.polimi.ingsw.gc26.ui.gui;


import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;

public class PawnsCoords {
    static ArrayList<Point> pointsCoord = new ArrayList<>() {{
        add(new Point(3, 10 ));
        add(new Point(5, 10));
        add(new Point(7, 10));
        add(new Point(8, 9));
        add(new Point(6, 9));
        add(new Point(4, 9));
        add(new Point(2, 9));
        add(new Point(2, 8));
        add(new Point(4, 8));
        add(new Point(6, 8));
        add(new Point(8, 8));
        add(new Point(8, 7));
        add(new Point(6, 7));
        add(new Point(4, 7));
        add(new Point(2, 7));
        add(new Point(2, 6));
        add(new Point(4, 6));
        add(new Point(6, 6));
        add(new Point(8, 6));
        add(new Point(8, 5));
        add(new Point(5, 4));
        add(new Point(2, 5));
        add(new Point(2, 4));
        add(new Point(2, 2));
        add(new Point(3, 1));
        add(new Point(5, 1));
        add(new Point(7, 1));
        add(new Point(8, 2));
        add(new Point(8, 4));
        add(new Point(5, 2));
    }};


    public static Point getCoords(int position) {
        return pointsCoord.get(position);
    }

    public static ArrayList<Point> getCoords() {
        return pointsCoord;
    }
}
