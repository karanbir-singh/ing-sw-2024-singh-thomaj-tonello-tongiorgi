package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.player.Player;

public class ScoreBoard {
    private final int TARGET = 20;
    private Player firstFinisher;

    public void setFirstFinisher(Player finisher){
        this.firstFinisher = finisher;
    }
}
