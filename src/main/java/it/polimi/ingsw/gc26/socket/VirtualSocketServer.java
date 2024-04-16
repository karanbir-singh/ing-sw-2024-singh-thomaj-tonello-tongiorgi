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
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "turnSelectedCardSide");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(value));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectPositionOnBoard(int x, int y, String playerID) {
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "selectPositionOnBoard");
        HashMap<String, String> value = new HashMap<>();
        value.put("x", String.valueOf(x));
        value.put("y", String.valueOf(y));
        value.put("playerID", playerID);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(value));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playCardFromHand(String playerID) {
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "playCardFromHand");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(value));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID) {
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "selectCardFromCommonTable");
        HashMap<String, String> value = new HashMap<>();
        value.put("cardX", String.valueOf(cardX));
        value.put("cardY", String.valueOf(cardY));
        value.put("playerID", playerID);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(value));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void drawSelectedCard(String playerID) {
        HashMap<String, String> data = VirtualSocketServer.getBasicMessage();
        data.replace("function", "drawSelectedCard");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(value));
            ObjectMapper mappedData = new ObjectMapper();

            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

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


