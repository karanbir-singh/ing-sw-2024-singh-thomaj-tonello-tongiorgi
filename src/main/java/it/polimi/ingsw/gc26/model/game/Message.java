package it.polimi.ingsw.gc26.model.game;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final Player receiver;
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
     * @param receiver Players that have to receive the message
     * @param sender Player that send the message
     * @param time Time the message was created
     */
    public Message(String text, String receiver, String sender, LocalTime time){
        this.text = text;
        //TODO get sender
        this.sender = new Player("0", sender);
        if (time == null) {
            this.time = LocalTime.now();
        } else {
            this.time = time;
        }
        this.receiver = new Player("2", receiver);
    }


    /**
     * Initialize Messages
     * @param json_text string containing information to create the instance encoded as a json string
     * @throws JsonProcessingException error in the string's formatting
     */
    public Message(String json_text) throws JsonProcessingException {
        ObjectMapper JsonMapper = new ObjectMapper();
        JsonNode root = JsonMapper.readTree(json_text);
        this.text = root.get("text").asText();
        this.receiver = new Player("0", root.get("receiver").asText());
        this.sender = new Player("0", root.get("sender").asText());
        this.time = null; //TODO LocalTime.parse(root.get("time").asText());
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
    public Player getReceiver(){
        return this.receiver; //a copy
    }

    /**
     * Returns the time the message was created
     * @return time
     */
   public LocalTime getTime(){
        return this.time;
   }


    /**
     * Returns a json serialized string
     * @return json message
     */
   public String toJson() {
       HashMap<String, String>  data = new HashMap<>();
       data.put("text", this.getText());
       data.put("receiver", this.getReceiver().getNickname());
       data.put("sender", this.getSender().getNickname());
       data.put("time", ""); //TODO this.getTime().toString()
       ObjectMapper objectMapper = new ObjectMapper();
       try {
           return objectMapper.writeValueAsString(data);
       }catch (JsonProcessingException e) {
           return "";
       }
   }


    /**
     * Overrides the method toString to have more readability
     * @return string
     */
   @Override
    public String toString() {
       if (this.receiver != null) {
           return "[" + this.getSender().getNickname() + " -> " + this.getReceiver().getNickname() + "]: " + this.getText();
       }
       return "[" + this.getSender().getNickname() + "]: " + this.getText();

   }
}
