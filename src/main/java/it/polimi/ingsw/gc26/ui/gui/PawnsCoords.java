package it.polimi.ingsw.gc26.ui.gui;


import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;

/**
 * The PawnsCoords class provides static methods to get matrix coordinates of each point.
 * The coordinates are stored as points in a list, which can be accessed by value.
 */
public class PawnsCoords {
    /**
     * A static list of predefined coordinates represented by Point objects.
     */
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

    /**
     * Returns the coordinates of a pawn at a specific position.
     *
     * @param position the position of the pawn in the list.
     * @return the Point representing the coordinates of the pawn.
     */
    public static Point getCoords(int position) {
        return pointsCoord.get(position);
    }

    /**
     * Returns the list of all predefined coordinates for pawns.
     *
     * @return the ArrayList of Point objects representing the coordinates.
     */
    public static ArrayList<Point> getCoords() {
        return pointsCoord;
    }
}
