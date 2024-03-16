package it.polimi.ingsw.gc26.Controller;

public class ScoreBoard {
    private static final int TARGET = 20;
    private Player firstFinisher;

    public void setFirstFinisher(Player firstFinisher) {
        this.firstFinisher = firstFinisher;
    }

    public Player getFirstFinisher() {
        return  this.firstFinisher;
    }
}
