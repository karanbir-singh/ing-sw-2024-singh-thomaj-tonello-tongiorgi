package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.Callable;

public class VirtualSocketServer implements VirtualServer {
    private final PrintWriter outputToServer;

    public VirtualSocketServer(BufferedWriter output) {
        this.outputToServer = new PrintWriter(output);
    }

    @Override
    public void selectCardFromHand(int cardIndex, String playerID) {
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "selectCardFromHand");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("cardIndex", String.valueOf(cardIndex));
        msg.put("playerID", playerID);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(msg));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void turnSelectedCardSide(String playerID) {

    }

    @Override
    public void selectPositionOnBoard(int x, int y) {

    }

    @Override
    public void playCardFromHand() {

    }

    @Override
    public void selectCardFromCommonTable(Card card) {

    }

    @Override
    public void drawSelectedCard() {

    }

    @Override
    public void addMessage(String line, String nicknameReceiver,String nicknameSender, String time)  {
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "addMessage");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("text", line);
        msg.put("receiver", nicknameReceiver);
        msg.put("sender", nicknameSender);
        msg.put("time", null);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(msg));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendText(String text) {
        this.outputToServer.println(text);
        this.outputToServer.flush();
    }

    private static HashMap<String, String> getBasicMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

}


