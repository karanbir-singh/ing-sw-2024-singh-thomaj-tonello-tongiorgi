package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Point;

import java.util.ArrayList;
import java.util.Map;

public class SimplifiedPersonalBoard {
    private int xMin, xMax, yMin, yMax;
    private int score;
    private ArrayList<Point> occupiedPositions;
    private ArrayList<Point> playablePositions;
    private ArrayList<Point> blockedPositions;
    private Card secretMission;
    private Map<Symbol, Integer> visibleResources;
    private int selectedX = 0;
    private int selectedY = 0;

    public SimplifiedPersonalBoard(int xMin, int xMax, int yMax, int yMin, int score, ArrayList<Point> occupiedPositions, ArrayList<Point> playablePositions, ArrayList<Point> blockedPositions, Card secretMission, Map<Symbol, Integer> visibleResources, int selectedX, int selectedY) {
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
    }

    public SimplifiedPersonalBoard(PersonalBoard personalBoard) {
        this.xMin = personalBoard.getxMin();
        this.xMax = personalBoard.getxMax();
        this.yMax = personalBoard.getyMax();
        this.yMin = personalBoard.getyMin();
        this.score = personalBoard.getScore();
        this.occupiedPositions = personalBoard.getOccupiedPositions();
        this.playablePositions = personalBoard.getPlayablePositions();
        this.blockedPositions = personalBoard.getBlockedPositions();
        this.secretMission = personalBoard.getSecretMission();
        this.visibleResources = personalBoard.getResources();
        this.selectedX = personalBoard.getSelectedX();
        this.selectedY = personalBoard.getSelectedY();
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

}
