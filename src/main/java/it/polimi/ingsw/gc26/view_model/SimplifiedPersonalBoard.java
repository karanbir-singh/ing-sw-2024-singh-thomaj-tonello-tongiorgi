package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Represents a simplified version of a player's personal board in the game.
 */
public class SimplifiedPersonalBoard implements Serializable {
    /**
     * Minimum X coordinate of the personal board.
     */
    private final int xMin;

    /**
     * Maximum X coordinate of the personal board.
     */
    private final int xMax;

    /**
     * Maximum Y coordinate of the personal board.
     */
    private final int yMax;

    /**
     * Minimum Y coordinate of the personal board.
     */
    private final int yMin;

    /**
     * Score associated with the personal board.
     */
    private final int score;

    /**
     * List of occupied positions on the personal board.
     */
    private final ArrayList<Point> occupiedPositions;

    /**
     * List of playable positions on the personal board.
     */
    private final ArrayList<Point> playablePositions;

    /**
     * List of blocked positions on the personal board.
     */
    private final ArrayList<Point> blockedPositions;

    /**
     * Secret mission card associated with the player.
     */
    private final Card secretMission;

    /**
     * Map of visible resources (symbols and quantities) on the personal board.
     */
    private final Map<Symbol, Integer> visibleResources;

    /**
     * Selected X coordinate on the personal board.
     */
    private int selectedX;

    /**
     * Selected Y coordinate on the personal board.
     */
    private int selectedY;

    /**
     * Nickname of the player owning the personal board.
     */
    private String nickname;

    /**
     * Constructs a simplified personal board with specified attributes.
     *
     * @param xMin             Minimum X coordinate of the board.
     * @param xMax             Maximum X coordinate of the board.
     * @param yMax             Maximum Y coordinate of the board.
     * @param yMin             Minimum Y coordinate of the board.
     * @param score            Score associated with the personal board.
     * @param occupiedPositions List of positions occupied on the board.
     * @param playablePositions List of positions playable on the board.
     * @param blockedPositions List of blocked positions on the board.
     * @param secretMission    Secret mission card associated with the player.
     * @param visibleResources Map of visible resources (symbols and quantities) on the board.
     * @param selectedX        Selected X coordinate on the board.
     * @param selectedY        Selected Y coordinate on the board.
     * @param nickname         Nickname of the player owning the board.
     */
    public SimplifiedPersonalBoard(int xMin, int xMax, int yMax, int yMin, int score,
                                   ArrayList<Point> occupiedPositions, ArrayList<Point> playablePositions,
                                   ArrayList<Point> blockedPositions, Card secretMission,
                                   Map<Symbol, Integer> visibleResources, int selectedX, int selectedY,
                                   String nickname) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMax = yMax;
        this.yMin = yMin;
        this.score = score;
        this.occupiedPositions = occupiedPositions;
        this.playablePositions = playablePositions;
        this.blockedPositions = blockedPositions;
        this.secretMission = secretMission;
        this.visibleResources = visibleResources;
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.nickname = nickname;
    }

    /**
     * Constructs a simplified personal board based on the given `PersonalBoard` object and nickname.
     *
     * @param personalBoard PersonalBoard object to be simplified.
     * @param nickname      Nickname of the player owning the board.
     */
    public SimplifiedPersonalBoard(PersonalBoard personalBoard, String nickname) {
        this.xMin = personalBoard.getXMin();
        this.xMax = personalBoard.getXMax();
        this.yMax = personalBoard.getYMax();
        this.yMin = personalBoard.getYMin();
        this.score = personalBoard.getScore();
        this.occupiedPositions = personalBoard.getOccupiedPositions();
        this.playablePositions = personalBoard.getPlayablePositions();
        this.blockedPositions = personalBoard.getBlockedPositions();
        this.secretMission = personalBoard.getSecretMission();
        this.visibleResources = personalBoard.getResources();
        this.selectedX = personalBoard.getSelectedX();
        this.selectedY = personalBoard.getSelectedY();
        this.nickname = nickname;
    }

    /**
     * Returns the minimum X coordinate of the personal board.
     *
     * @return Minimum X coordinate.
     */
    public int getxMin() {
        return xMin;
    }

    /**
     * Returns the maximum X coordinate of the personal board.
     *
     * @return Maximum X coordinate.
     */
    public int getxMax() {
        return xMax;
    }

    /**
     * Returns the minimum Y coordinate of the personal board.
     *
     * @return Minimum Y coordinate.
     */
    public int getyMin() {
        return yMin;
    }

    /**
     * Returns the maximum Y coordinate of the personal board.
     *
     * @return Maximum Y coordinate.
     */
    public int getyMax() {
        return yMax;
    }

    /**
     * Returns the score associated with the personal board.
     *
     * @return Score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the list of occupied positions on the personal board.
     *
     * @return List of occupied positions.
     */
    public ArrayList<Point> getOccupiedPositions() {
        return occupiedPositions;
    }

    /**
     * Returns the list of playable positions on the personal board.
     *
     * @return List of playable positions.
     */
    public ArrayList<Point> getPlayablePositions() {
        return playablePositions;
    }

    /**
     * Returns the list of blocked positions on the personal board.
     *
     * @return List of blocked positions.
     */
    public ArrayList<Point> getBlockedPositions() {
        return blockedPositions;
    }

    /**
     * Returns the secret mission card associated with the personal board.
     *
     * @return Secret mission card.
     */
    public Card getSecretMission() {
        return secretMission;
    }

    /**
     * Returns the map of visible resources (symbols and quantities) on the personal board.
     *
     * @return Map of visible resources.
     */
    public Map<Symbol, Integer> getVisibleResources() {
        return visibleResources;
    }

    /**
     * Returns the selected X coordinate on the personal board.
     *
     * @return Selected X coordinate.
     */
    public int getSelectedX() {
        return selectedX;
    }

    /**
     * Returns the selected Y coordinate on the personal board.
     *
     * @return Selected Y coordinate.
     */
    public int getSelectedY() {
        return selectedY;
    }

    /**
     * Returns the nickname of the player owning the personal board.
     *
     * @return Player's nickname.
     */
    public String getNickname() {
        return nickname;
    }
}
