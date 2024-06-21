package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class SimplifiedPersonalBoard implements Serializable {
    private final int xMin, xMax, yMin, yMax;
    private final int score;
    private final ArrayList<Point> occupiedPositions;
    private final ArrayList<Point> playablePositions;
    private final ArrayList<Point> blockedPositions;
    private final Card secretMission;
    private final Map<Symbol, Integer> visibleResources;
    private int selectedX = 0;
    private int selectedY = 0;
    private String nickname;

    public SimplifiedPersonalBoard(int xMin, int xMax, int yMax, int yMin, int score, ArrayList<Point> occupiedPositions, ArrayList<Point> playablePositions, ArrayList<Point> blockedPositions, Card secretMission, Map<Symbol, Integer> visibleResources, int selectedX, int selectedY, String nickname) {
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

    public int getxMin() {
        return xMin;
    }

    public int getxMax() {
        return xMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getyMax() {
        return yMax;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Point> getOccupiedPositions() {
        return occupiedPositions;
    }

    public ArrayList<Point> getPlayablePositions() {
        return playablePositions;
    }

    public ArrayList<Point> getBlockedPositions() {
        return blockedPositions;
    }

    public Card getSecretMission() {
        return secretMission;
    }

    public Map<Symbol, Integer> getVisibleResources() {
        return visibleResources;
    }

    public int getSelectedX() {
        return selectedX;
    }

    public int getSelectedY() {
        return selectedY;
    }

    public String getNickname() {
        return nickname;
    }
}
