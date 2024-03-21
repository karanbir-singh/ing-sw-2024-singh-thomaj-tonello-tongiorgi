package it.polimi.ingsw.gc26.model;
import java.time.LocalTime;
import java.util.*;
import it.polimi.ingsw.gc26.model.player.Player;

public class Message {
    private final String text;
    private final ArrayList<Player> receivers;
    private final Player sender;
    private final LocalTime time;

    public Message(String text, ArrayList<Player> receivers, Player sender, LocalTime time){
        this.text = text;
        this.receivers = new ArrayList<Player>(receivers);
        this.sender = sender;
        this.time = time;
    }


    public Player getSender(){
        return this.sender;
    }
    public String getText(){
        return this.text;
    }
    public ArrayList<Player> getReceivers(){
        return new ArrayList<Player>(this.receivers); //una copia
    }
   public LocalTime getTime(){
        return this.time;
   }
}
