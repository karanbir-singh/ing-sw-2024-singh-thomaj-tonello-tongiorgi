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

/**
 * This class represents the handler to decode json from the client.
 */
public class SocketClientHandler  {
    /**
     * This attribute represents the main controller
     */
    final MainController mainController;
    /**
     * This attribute represents the game controller
     */
    GameController gameController = null;

    /**
     * This attribute represents the input from the client
     */
    final BufferedReader inputFromClient;
    /**
     * This attribute represents the virtual client
     */
    final VirtualView virtualClient;

    /**
     * Socket client handler constructor. Initialized the controllers and the virtual view.
     * @param controller main controller used during the first part of the game (to connect the clients)
     * @param input      buffered reader to read data from the client
     * @param output     print writer to write to the client
     */
    public SocketClientHandler(MainController controller, BufferedReader input, PrintWriter output) {
        this.mainController = controller;
        this.inputFromClient = input;
        this.virtualClient = new VirtualSocketView(output);
        System.out.println("New client from Socket!");
    }

    /**
     * Starts an infinite loop listening to clients data
     * @throws IOException
     */
    public void runClientHandler() throws IOException {
        String line;
        while ((line = inputFromClient.readLine()) != null) {
            if (true) {
                ObjectMapper jsonMapper = new ObjectMapper();
                JsonNode msg = jsonMapper.readTree(line);
                ObjectMapper valueMapper = new ObjectMapper();
                JsonNode value = valueMapper.readTree(msg.get("value").asText());
                switch (msg.get("function").textValue()) {
                    case "connect" :
                        String clientID = this.mainController.connect(this.virtualClient, value.get("nickname").asText());
                        this.virtualClient.setClientID(clientID);
                        break;
                    case "createWaitingList":
                        this.mainController.createWaitingList(this.virtualClient, value.get("clientID").asText(), value.get("nickname").asText(), value.get("numPlayers").asInt());
                        break;
                    case "getVirtualGameController":
                        this.gameController = this.mainController.getGameController();
                        this.virtualClient.setGameController();
                        break;
                    case "addMessage":
                        this.gameController.addMessage(value.get("text").asText(), value.get("receiver").asText(), value.get("sender").asText(), value.get("time").asText());
                        break;
                    case "selectCardFromHand":
                        this.gameController.selectCardFromHand(value.get("cardIndex").asInt(), value.get("playerID").asText());
                        break;
                    case "turnSelectedCardSide":
                        this.gameController.turnSelectedCardSide(value.get("playerID").asText());
                        break;
                    case "selectPositionOnBoard":
                        this.gameController.selectPositionOnBoard(value.get("x").asInt(), value.get("y").asInt(), value.get("playerID").asText());
                        break;
                    case "playCardFromHand":
                        this.gameController.playCardFromHand(value.get("playerID").asText());
                        break;
                    case "selectCardFromCommonTable":
                        this.gameController.selectCardFromCommonTable(value.get("cardX").asInt(), value.get("cardY").asInt(), value.get("playerID").asText());
                        break;
                    case "drawSelectedCard":
                        this.gameController.drawSelectedCard(value.get("playerID").asText());
                        break;
                    case "choosePawnColor":
                        this.gameController.choosePawnColor(value.get("color").asText(), value.get("playerID").asText());
                        break;
                    case "selectSecretMission":
                        this.gameController.selectSecretMission(value.get("cardIndex").asInt(), value.get("playerID").asText());
                        break;
                    case "setSecretMission":
                        this.gameController.setSecretMission(value.get("playerID").asText());
                    case "printPersonalBoard":
                        this.gameController.printPersonalBoard(value.get("nickname").asText(), value.get("playerID").asText());
                    case null, default:
                        break;
                }
            } else {

                this.virtualClient.reportError("The game is being initialized! Please wait!");
            }

        }
    }

}
