package it.polimi.ingsw.gc26.model;
import java.util.*;
import it.polimi.ingsw.gc26.model.player.Player;
public class Chat {
    private final ArrayList<Message> messages;

    public Chat(){
        messages = new ArrayList<Message>();
    }

    public ArrayList<Message> getChat(){
        return this.messages;
    }

    public void addMessage(Message message){
        messages.add(message); //chiamata di comunicazione con il client
    }

    public ArrayList<Message> filterMessages(Player receiverPlayer){
        ArrayList<Message> copy = new ArrayList<Message>();
        for(Message m : this.messages){
            if(m.getReceivers().contains(receiverPlayer)){
                copy.add(m);
            }
            if(m.getSender().equals(receiverPlayer)){
                copy.add(m);
            }
        }

        return copy;
    }



}
