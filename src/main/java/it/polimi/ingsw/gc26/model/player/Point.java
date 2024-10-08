package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card_side.Side;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a point of the PersonalBoard
 */
public class Point implements Serializable {
    /**
     * These attributes represent the coordinates x and y of the Cartesian Plane
     */
    private final int x, y;
    /**
     * This attribute represents the side of the card played in a specific point
     */
    private Side side;
    /**
     * This attribute is a flag to mark when a card has already been used for a certain mission
     */
    private final Map<Integer, Boolean> flags;

    /**
     * Initializes the point with the passed coordinates
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.side = null;
        flags = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            flags.put(i, false);
        }
    }

    /**
     * Initializes the point with the coordinates, its flags and the side present in that point
     *
     * @param x     x coordinate
     * @param y     y coordinate
     * @param flags flags for this point
     * @param side  card's side present in the current point
     */
    public Point(int x, int y, Map<Integer, Boolean> flags, Side side) {
        this.x = x;
        this.y = y;
        this.flags = flags;
        this.side = side;
    }

    /**
     * Returns the map of flags
     *
     * @return flags
     */
    public Map<Integer, Boolean> getFlags() {
        return flags;
    }

    /**
     * Returns X coordinate
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Returns Y coordinate
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Returns a boolean for the flag in the given position
     *
     * @param flag flag's key
     * @return flag's value to know whether the side has already been counted for a pattern or not
     */
    public boolean getFlag(int flag) {
        return flags.get(flag);
    }

    /**
     * Sets a boolean for a flag
     *
     * @param flag  flag to be modified
     * @param value new flag's value
     */
    public void setFlag(int flag, boolean value) {
        flags.put(flag, value);
    }

    /**
     * Sets the card's side present in the point
     *
     * @param side new side
     */
    public void setSide(Side side) {
        this.side = side;
    }

    /**
     * Returns the side in the point
     *
     * @return side
     */
    public Side getSide() {
        return this.side;
    }
}
