package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.ModelObservable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the chat between players managed by the server
 */
public class Chat implements Serializable {
    /**
     * This attribute represents all the messages sent and received by every player.
     * All messages are saved in this array and then filtered by player.
     */
    private final ArrayList<Message> messages;

    /**
     * Observable to notify client
     */
    private final ModelObservable observable;

    /**
     * Initializes the chat with an empty ArrayList
     */
    public Chat(ModelObservable observable) {
        this.observable = observable;
        messages = new ArrayList<Message>();
    }

    /**
     * Returns all messages in the server
     *
     * @return messages
     */
    public ArrayList<Message> getMessages() {
        return this.messages;
    }

    /**
     * Adds a new message to the chat
     *
     * @param message new message
     */
    public void addMessage(Message message) {
        messages.add(message); //TODO chiamata di comunicazione con il client
        //this.observable.notifyChat(message);
    }

    /**
     * Filters all the messages present by a player
     *
     * @param receiverPlayer the player that receives the message
     * @return arrayList containing the messages for the player
     */
    public ArrayList<Message> filterMessages(Player receiverPlayer) {
        ArrayList<Message> copy = new ArrayList<>();
        for (Message m : this.messages) {
            if (m.getReceiver().equals(receiverPlayer)) {
                copy.add(m);
            }
            if (m.getSender().equals(receiverPlayer)) {
                copy.add(m);
            }
        }

        return copy;
    }
}
