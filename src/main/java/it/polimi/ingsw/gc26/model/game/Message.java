package it.polimi.ingsw.gc26.model.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.model.utils.TextStyle;
import it.polimi.ingsw.gc26.utils.ConsoleColors;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashMap;

/**
 * This class represents a message sent by players
 */
public class Message implements Serializable {
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
     *
     * @param text     string containing the information
     * @param receiver Players that have to receive the message
     * @param sender   Player that send the message
     * @param time     Time the message was created
     */
    public Message(String text, Player receiver, Player sender, String time) {
        this.text = text;
        this.sender = sender;
        if (time == null) {
            this.time = LocalTime.now();
        } else {
            this.time = LocalTime.parse(time);
        }
        this.receiver = receiver;
    }

    /**
     * Initialize Messages
     *
     * @param json_text string containing information to create the instance encoded as a json string
     * @throws JsonProcessingException error in the string's formatting
     */
    public Message(String json_text) throws JsonProcessingException {
        ObjectMapper JsonMapper = new ObjectMapper();
        JsonNode root = JsonMapper.readTree(json_text);
        this.text = root.get("text").asText();
        this.receiver = new Player(null, root.get("receiver").asText());
        this.sender = new Player(null, root.get("sender").asText());
        try {
            receiver.setPawn(Pawn.valueOf(root.get("receiverPawn").asText()));
        } catch(NullPointerException e) {};
        try {
            sender.setPawn(Pawn.valueOf(root.get("senderPawn").asText()));
        } catch (NullPointerException e) {};
        this.time = LocalTime.parse(root.get("time").asText());
        //LocalTime.parse(msg.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))
    }

    /**
     * Returns the message's sender
     *
     * @return sender
     */
    public Player getSender() {
        return this.sender;
    }

    /**
     * Returns the text information
     *
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Returns the Players that received the message
     *
     * @return players
     */
    public Player getReceiver() {
        return this.receiver; //a copy
    }

    /**
     * Returns the time the message was created
     *
     * @return time
     */
    public LocalTime getTime() {
        return this.time;
    }

    /**
     * Returns a json serialized string
     *
     * @return json message
     */
    public String toJson() {
        HashMap<String, String> data = new HashMap<>();
        data.put("text", this.getText());
        try {
            data.put("receiver", this.getReceiver().getNickname());
            data.put("receiverPawn", this.getReceiver().getPawnColor().toString());
        } catch (NullPointerException e) {
            data.put("receiver", "");
        }
        data.put("sender", this.getSender().getNickname());
        try {
            data.put("senderPawn", this.getSender().getPawnColor().toString());
        } catch (NullPointerException e) {}
        data.put("time", this.getTime().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

    /**
     * Overrides the method toString to have more readability
     *
     * @return string
     */
    @Override
    public String toString() {
        if (this.getSender().getPawnColor() == null) {
            if (this.receiver != null && this.receiver.getNickname() != null && !this.getReceiver().getNickname().isEmpty()) {
                return "[ " + this.sender.getNickname() + " -> " + this.receiver.getNickname() + " | " + this.time.toString().substring(0, 8) + " ]: " + this.text;
            }
            return "[ " + this.sender.getNickname() + " | " + this.time.toString().substring(0, 8) + " ]: " + this.text;
        }
        if (this.receiver != null && this.receiver.getNickname() != null && !this.getReceiver().getNickname().isEmpty()) {
            return this.getSender().getPawnColor().getFontColor() + "[ " + this.sender.getNickname() + " -> " + this.receiver.getNickname() + " | " + this.time.toString().substring(0, 8) + " ]: " + ConsoleColors.RESET + this.text;
        }
        return this.getSender().getPawnColor().getFontColor() + "[ " + this.sender.getNickname() + " | " + this.time.toString().substring(0, 8) + " ]: " + ConsoleColors.RESET + this.text;

    }

}
