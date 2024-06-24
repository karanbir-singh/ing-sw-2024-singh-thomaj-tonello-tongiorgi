package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.game.Message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a simplified version of a chat containing messages.
 * It provides methods to access and filter messages based on player nicknames.
 */
public class SimplifiedChat implements Serializable {
    /**
     * The list of messages stored in the chat.
     */
    private final ArrayList<Message> messages;

    /**
     * Constructs a SimplifiedChat object with the specified list of messages.
     *
     * @param messages the list of messages to initialize the chat.
     */
    public SimplifiedChat(ArrayList<Message> messages) {
        this.messages = messages;
    }

    /**
     * Returns the list of messages in the chat.
     *
     * @return the list of messages.
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }

    /**
     * Filters and returns messages from the chat that involve the specified player.
     * If the player is not involved in the chat, returns all messages.
     *
     * @param nickname the nickname of the player to filter messages for.
     * @param players  the list of players in the chat.
     * @return an ArrayList of messages involving the specified player.
     */
    public ArrayList<Message> filterMessagesByPlayer(String nickname, ArrayList<String> players) {
        ArrayList<Message> filteredMessages = new ArrayList<>();
        if (!players.contains(nickname)) {
            return messages;
        }
        if (!messages.isEmpty()) {
            for (Message message : messages) {
                if (message.getSender().getNickname().equals(nickname)) {
                    filteredMessages.add(message);
                } else if (message.getReceiver() != null && message.getReceiver().getNickname().equals(nickname)) {
                    filteredMessages.add(message);
                }
            }
        }
        return filteredMessages;
    }
}
