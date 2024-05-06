package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.ClientController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;


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
    private PrintWriter outputToServer;

    /**
     * This attribute represents the clientController
     */
    private ClientController clientController;

    public SocketServerHandler(ClientController clientController, BufferedReader inputFromServer, PrintWriter outputToServer) {
        this.clientController = clientController;
        this.inputFromServer = inputFromServer;
        this.outputToServer = outputToServer;

        // Launch t
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
                        this.setClientID(value.get("clientID").asText());
                        break;
                    case "setGameController":
                        this.setGameController();
                        break;
                    case "updateState":
                        this.updateState(ClientState.valueOf(value.get("clientState").asText()));
                        break;
                    case "showMessage":
                        this.showMessage(value.get("message").asText(), value.get("clientID").asText());
                        break;
                    case "showError":
                        this.showError(value.get("errorMessage").asText(), value.get("clientID").asText());
                        break;
                    case "showChat":
                        this.showChat(value.get("message").asText());
                        break;
                    case "updateChosenPawn":
                        this.updateChosenPawn(value.get("pawnColor").asText(), value.get("clientID").asText());
                        break;
                    case "updateSelectedMission":
                        this.updateSelectedMission(value.get("clientID").asText());
                        break;
                    case "updateSelectedCardFromHand":
                        this.updateSelectedCardFromHand(value.get("clientID").asText());
                        break;
                    case "updateSelectedSide":
                        this.updateSelectedSide(value.get("cardIndex").asText(), value.get("clientID").asText());
                        break;
                    case "updateSelectedPositionOnBoard":
                        this.updateSelectedPositionOnBoard(value.get("selectedX").asText(), value.get("selectedY").asText(), value.get("clientID").asText(), value.get("success").asText());
                        break;
                    case "updatePlayedCardFromHand":
                        this.updatePlayedCardFromHand(value.get("clientID").asText(), value.get("success").asText());
                        break;
                    case "updatePoints":
                        this.updatePoints(value.get("clientID").asText(), value.get("points").asText());
                        break;
                    case "updateSelectedCardFromCommonTable":
                        this.updateSelectedCardFromCommonTable(value.get("clientID").asText(), value.get("success").asText());
                        break;
                    case "showCard":
                        this.showCard(value.get("clientID").asText(), value.get("cardSerialization").asText());
                        break;
                    case "showPersonalBoard":
                        this.showPersonalBoard(value.get("clientID").asText(), value.get("ownerNickname").asText(), value.get("personalBoardSerialization").asText());
                        break;
                    case "updateFirstPlayer":
                        this.updateFirstPlayer(value.get("nickname").asText());
                        break;
                    case "updateGameState":
                        this.updateGameState(value.get("gameState").asText());
                        break;
                    case null, default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a message in the chat
     *
     * @param line json encoded message
     */
    public void showChat(String line) {
        this.clientController.showChat(line);
    }

    /**
     * Reports a message from the server (for example error reports)
     *
     * @param message
     */
    public void showMessage(String message, String clientID) {
        this.clientController.showMessage(message, clientID);
    }

    /**
     * Reports an error message from the server
     *
     * @param errorMessage
     */
    public void showError(String errorMessage, String clientID) {
        this.clientController.showError(errorMessage, clientID);
    }

    /**
     * Updates client's state
     *
     * @param clientState
     * @throws RemoteException
     */
    public void updateState(ClientState clientState) {
        this.clientController.updateClientState(clientState);
    }


    public void setClientID(String clientID) {
        this.clientController.setClientID(clientID);
    }

    public void setGameController() {
        this.clientController.setGameController(new VirtualSocketGameController(this.outputToServer));
    }

    public void updateChosenPawn(String pawnColor, String clientID) {
        this.clientController.updateChosenPawn(pawnColor, clientID);
    }

    public void updateSelectedMission(String clientID) {
        this.clientController.updateSelectedMission(clientID);
    }

    public void updateSelectedCardFromHand(String clientID) {
        this.clientController.updateSelectedCardFromHand(clientID);
    }

    public void updateSelectedSide(String cardIndex, String clientID) {
        this.clientController.updateSelectedSide(cardIndex, clientID);
    }

    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String clientID, String success) {
        this.clientController.updateSelectedPositionOnBoard(selectedX, selectedY, clientID, success);
    }

    public void updatePlayedCardFromHand(String clientID, String success) {
        this.clientController.updatePlayedCardFromHand(clientID, success);
    }

    public void updatePoints(String clientID, String points) {
        this.clientController.updatePoints(clientID, points);
    }

    public void updateSelectedCardFromCommonTable(String clientID, String success) {
        this.clientController.updateSelectedCardFromCommonTable(clientID, success);
    }

    public void showCard(String clientID, String cardSerialization) {
        this.clientController.showCard(clientID, cardSerialization);

    }

    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) {
        this.clientController.showPersonalBoard(clientID, ownerNickname, personalBoardSerialization);
    }

    public void updateFirstPlayer(String nickname) {
        this.clientController.updateFirstPlayer(nickname);
    }

    public void updateGameState(String gameState) {
        this.clientController.updateGameState(gameState);
    }

    /**
     * @return
     * @throws RemoteException
     */
    public ClientState getState() {
        //return this.socketClient.serverHandler.getState();
        return this.clientController.getClientState();
    }

}
