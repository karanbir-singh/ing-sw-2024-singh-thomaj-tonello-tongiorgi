package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;
import java.util.HashMap;

public class ScoreBoard {
    private static final int TARGET = 20;
    private Player firstFinisher;
    private HashMap<Player, Integer> players;

    public ScoreBoard(ArrayList<Players> Players){
        this.players = new HashMap<>();
        for (Player player : Players ) {
            this.players.put(c, 0);
        }
    }
    public void setFirstFinisher(Player firstFinisher) {
        this.firstFinisher = firstFinisher;
    }

    public Player getFirstFinisher() {
        return  this.firstFinisher;
    }

    public getPoints(Player player) {
        return this.players.get(player);
    }

    public increasePoints(Player player, int increase) {
        int previousScore = this.players.get(player);
        this.players.put(player, previousScore + increase);
    }
}
