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
}
