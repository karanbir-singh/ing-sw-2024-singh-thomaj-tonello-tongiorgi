package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.game.Message;

import java.io.Serializable;
import java.util.ArrayList;

public class SimplifiedChat implements Serializable {
    private final ArrayList<Message> messages;

    public SimplifiedChat(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }


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
