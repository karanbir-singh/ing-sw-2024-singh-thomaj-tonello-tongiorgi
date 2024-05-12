package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.ViewController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * This class represents the handler to decode json from the server.
 */
public class SocketServerHandler implements Runnable {
    /**
     * This attributes represents the input from the server.
     */
    private BufferedReader inputFromServer;

    /**
     * This attributes represents the output to the server.
     */
    private BufferedWriter outputToServer;

    /**
     * This attribute represents the clientController
     */
    private ViewController viewController;


    public SocketServerHandler(ViewController viewController, BufferedReader inputFromServer, BufferedWriter outputToServer) {
        this.viewController = viewController;
        this.inputFromServer = inputFromServer;
        this.outputToServer = outputToServer;

        new Thread(this).start();
    }

    /**
     * This method is launched from the SocketClient and listens to the server
     * When a new message is received, it is decoded and the correct method is executed
     */
    @Override
    public void run() {
        String line;
        try {
            while ((line = inputFromServer.readLine()) != null) {
                ObjectMapper JsonMapper = new ObjectMapper();
                JsonNode msg = JsonMapper.readTree(line);
                ObjectMapper valueMapper = new ObjectMapper();
                JsonNode value = valueMapper.readTree(msg.get("value").asText());

                switch (msg.get("function").asText()) {
                    case "setClientID":
                        this.viewController.setClientID(value.get("clientID").asText());
                        break;
                    case "setGameController":
                        this.viewController.setGameController(new VirtualSocketGameController(this.outputToServer));
                        break;
                    case "updateState":
                        this.viewController.updateClientState(ClientState.valueOf(value.get("clientState").asText()));
                        break;
                    case "showMessage":
                        this.viewController.showMessage(value.get("message").asText(), value.get("clientID").asText());
                        break;
                    case "showError":
                        this.viewController.showError(value.get("errorMessage").asText(), value.get("clientID").asText());
                        break;
                    case "showChat":
                        this.viewController.showChat(value.get("message").asText());
                        break;
                    case "updateChosenPawn":
                        this.viewController.updateChosenPawn(value.get("pawnColor").asText(), value.get("clientID").asText());
                        break;
                    case "updateSelectedMission":
                        this.viewController.updateSelectedMission(value.get("clientID").asText());
                        break;
                    case "updateSelectedCardFromHand":
                        this.viewController.updateSelectedCardFromHand(value.get("clientID").asText());
                        break;
                    case "updateSelectedSide":
                        this.viewController.updateSelectedSide(value.get("cardIndex").asText(), value.get("clientID").asText());
                        break;
                    case "updateSelectedPositionOnBoard":
                        this.viewController.updateSelectedPositionOnBoard(value.get("selectedX").asText(), value.get("selectedY").asText(), value.get("clientID").asText(), value.get("success").asText());
                        break;
                    case "updatePlayedCardFromHand":
                        this.viewController.updatePlayedCardFromHand(value.get("clientID").asText(), value.get("success").asText());
                        break;
                    case "updatePoints":
                        this.viewController.updatePoints(value.get("clientID").asText(), value.get("points").asText());
                        break;
                    case "updateSelectedCardFromCommonTable":
                        this.viewController.updateSelectedCardFromCommonTable(value.get("clientID").asText(), value.get("success").asText());
                        break;
                    case "showCard":
                        this.viewController.showCard(value.get("clientID").asText(), value.get("cardSerialization").asText());
                        break;
                    case "showPersonalBoard":
                        this.viewController.showPersonalBoard(value.get("clientID").asText(), value.get("ownerNickname").asText(), value.get("personalBoardSerialization").asText());
                        break;
                    case "updateFirstPlayer":
                        this.viewController.updateFirstPlayer(value.get("nickname").asText());
                        break;
                    case "updateGameState":
                        this.viewController.updateGameState(value.get("gameState").asText());
                        break;
                    case "updateIDGame":
                        this.viewController.setGameID(value.get("idGame").asInt());
                        break;
                    case "isClientAlive":
                        break;
                    case "killProcess":
                        this.viewController.killProcess();
                        break;
                    case null, default:
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Server down");
        }
    }
//
//    /**
//     * Shows a message in the chat
//     *
//     * @param line json encoded message
//     */
//    public void showChat(String line) {
//        this.clientController.showChat(line);
//    }
//
//    /**
//     * Reports a message from the server (for example error reports)
//     *
//     * @param message
//     */
//    public void showMessage(String message, String clientID) {
//        this.clientController.showMessage(message, clientID);
//    }
//
//    /**
//     * Reports an error message from the server
//     *
//     * @param errorMessage
//     */
//    public void showError(String errorMessage, String clientID) {
//        this.clientController.showError(errorMessage, clientID);
//    }
//
//    /**
//     * Updates client's state
//     *
//     * @param clientState
//     * @throws RemoteException
//     */
//    public void updateState(ClientState clientState) {
//        this.clientController.updateClientState(clientState);
//    }
//
//
//    public void setClientID(String clientID) {
//        this.clientController.setClientID(clientID);
//    }
//
//    public void setGameController() {
//        this.clientController.setGameController();
//    }
//
//    public void updateChosenPawn(String pawnColor, String clientID) {
//        this.clientController.updateChosenPawn(pawnColor, clientID);
//    }
//
//    public void updateSelectedMission(String clientID) {
//        this.clientController.updateSelectedMission(clientID);
//    }
//
//    public void updateSelectedCardFromHand(String clientID) {
//        this.clientController.updateSelectedCardFromHand(clientID);
//    }
//
//    public void updateSelectedSide(String cardIndex, String clientID) {
//        this.clientController.updateSelectedSide(cardIndex, clientID);
//    }
//
//    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String clientID, String success) {
//        this.clientController.updateSelectedPositionOnBoard(selectedX, selectedY, clientID, success);
//    }
//
//    public void updatePlayedCardFromHand(String clientID, String success) {
//        this.clientController.updatePlayedCardFromHand(clientID, success);
//    }
//
//    public void updatePoints(String clientID, String points) {
//        this.clientController.updatePoints(clientID, points);
//    }
//
//    public void updateSelectedCardFromCommonTable(String clientID, String success) {
//        this.clientController.updateSelectedCardFromCommonTable(clientID, success);
//    }
//
//    public void showCard(String clientID, String cardSerialization) {
//        this.clientController.showCard(clientID, cardSerialization);
//
//    }
//
//    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) {
//        this.clientController.showPersonalBoard(clientID, ownerNickname, personalBoardSerialization);
//    }
//
//    public void updateFirstPlayer(String nickname) {
//        this.clientController.updateFirstPlayer(nickname);
//    }
//
//    public void updateGameState(String gameState) {
//        this.clientController.updateGameState(gameState);
//    }
}
