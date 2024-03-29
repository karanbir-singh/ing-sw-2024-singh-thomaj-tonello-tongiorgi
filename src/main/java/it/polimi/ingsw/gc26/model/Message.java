package it.polimi.ingsw.gc26.model;
import java.time.LocalTime;
import java.util.*;
import it.polimi.ingsw.gc26.model.player.Player;

/**
 * This class represents a message sent by players
 */
public class Message {
    /**
     * This attribute represents the string sent
     */
    private final String text;
    /**
     * This attribute represents the players that have to receive the message
     */
    private final ArrayList<Player> receivers;
    /**
     * This attribute represents the player that sent the message
     */
    private final Player sender;
    /**
     * This attribute represents the time the message was created
     */
    private final LocalTime time;

    /**
     * Initializes Message
     * @param text string containing the information
     * @param receivers Players that have to receive the message
     * @param sender Player that send the message
     * @param time Time the message was created
     */
    public Message(String text, ArrayList<Player> receivers, Player sender, LocalTime time){
        this.text = text;
        this.receivers = new ArrayList<Player>(receivers);
        this.sender = sender;
        this.time = time;
    }

    /**
     * Returns the message's sender
     * @return sender
     */
    public Player getSender(){
        return this.sender;
    }

    /**
     * Returns the text information
     * @return text
     */
    public String getText(){
        return this.text;
    }

    /**
     * Returns the Players that received the message
     * @return players
     */
    public ArrayList<Player> getReceivers(){
        return new ArrayList<Player>(this.receivers); //a copy
    }

    /**
     * Returns the time the message was created
     * @return time
     */
   public LocalTime getTime(){
        return this.time;
   }
}
