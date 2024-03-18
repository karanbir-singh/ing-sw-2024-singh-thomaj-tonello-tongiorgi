package it.polimi.ingsw.gc26.model;
import java.util.*;
import it.polimi.ingsw.gc26.model.player.Player;

public class Message {
    private String text;
    private ArrayList<Player> receivers;
    private Player sender;

    public Message(String text, ArrayList<Player> receivers, Player sender){
        this.text = text;
        this.receivers = new ArrayList<Player>(receivers);
        this.sender = sender;
    }

    public ArrayList<Player> getReceivers(){
        return this.receivers;
    }

    public Player getSender(){
        return this.sender;
    }
}
