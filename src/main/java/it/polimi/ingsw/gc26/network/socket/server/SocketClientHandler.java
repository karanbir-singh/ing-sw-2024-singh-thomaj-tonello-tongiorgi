package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.controller.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.UUID;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualView;


public class SocketClientHandler  {
    final MainController mainController;
    GameController gameController = null;
    final SocketServer server;
    final BufferedReader inputFromClient;
    final VirtualView virtualClient;
    String nickname = null;

    public SocketClientHandler(MainController controller, SocketServer server, BufferedReader input, PrintWriter output) {
        this.mainController = controller;
        this.server = server;
        this.inputFromClient = input;
        this.virtualClient = new VirtualSocketView(output);
        System.out.println("New client!");
    }

    public void runClientHandler() throws IOException {
        String line;
        while ((line = inputFromClient.readLine()) != null) {
            if (true) {
                JsonNode msg;
                ObjectMapper JsonMapper = new ObjectMapper();
                msg = JsonMapper.readTree(line);
                switch (msg.get("function").textValue()) {
                    case "connect" :
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.mainController.connect(this.virtualClient, msg.get("username").asText());
                        break;
                    case "addMessage":
                        Message newMessage = new Message(msg.get("value").asText());
                        this.gameController.addMessage(newMessage.getText(), newMessage.getReceiver().getNickname(), newMessage.getSender().getNickname(), LocalTime.now().toString());
                        break;
                    case "selectCardFromHand":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.gameController.selectCardFromHand(msg.get("cardIndex").asInt(), msg.get("playerID").asText());
                        break;
                    case "turnSelectedCardSide":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.gameController.turnSelectedCardSide(msg.get("playerID").asText());
                        break;
                    case "selectPositionOnBoard":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.gameController.selectPositionOnBoard(msg.get("x").asInt(), msg.get("y").asInt(), msg.get("playerID").asText());
                        break;
                    case "playCardFromHand":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.gameController.playCardFromHand(msg.get("playerID").asText());
                        break;
                    case "selectCardFromCommonTable":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.gameController.selectCardFromCommonTable(msg.get("cardX").asInt(), msg.get("cardY").asInt(), msg.get("playerID").asText());
                        break;
                    case "drawSelectedCard":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.gameController.drawSelectedCard(msg.get("playerID").asText());
                        break;
                    case null, default:
                        break;
                }
            } else {

                this.virtualClient.reportError("The game is being initialized! Please wait!");
            }

        }
    }

}
