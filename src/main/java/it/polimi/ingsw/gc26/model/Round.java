package it.polimi.ingsw.gc26.model;

public class Round {
    private int round;

    public Round(){
        this.round = 1;
    }

    public void increaseRound() {
        this.round++;
    }

    public int getRound() {
        return this.round;
    }
}
