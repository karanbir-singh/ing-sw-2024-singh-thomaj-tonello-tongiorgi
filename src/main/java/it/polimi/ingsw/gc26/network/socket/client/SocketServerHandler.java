package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;


/**
 * This class represents the handler to decode json from the server.
 */
public class SocketServerHandler implements VirtualView, Runnable {
    /**
     * This attribute represent the socket client where the nickname and clientID are stored
     */
    private final SocketClient socketClient;
    /**
     * This attributes represents the input from the server.
     */
    private BufferedReader inputFromServer;

    /**
     * Socket server handler's constructor. It initializes the class in order to read json from the server.
     *
     * @param socketClient    Socket client
     * @param inputFromServer Buffered reader to read json to the server
     */
    public SocketServerHandler(SocketClient socketClient, BufferedReader inputFromServer) {
        this.socketClient = socketClient;
        this.inputFromServer = inputFromServer;
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
        JsonNode message = null;
        try {
            ObjectMapper JsonMapper = new ObjectMapper();
            message = JsonMapper.readTree(line);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(message.get("receiver").asText().equals(this.socketClient.getClientID()) || message.get("receiver").asText().equals("")) {
            System.out.println(STR."[\{message.get("sender").asText()}]: \{message.get("text").asText()}");
        }
    }

    /**
     * Reports a message from the server (for example error reports)
     *
     * @param message
     */

    public void showMessage(String message, String clientID) {
        System.out.println(STR."[SERVER]: \{message}");
    }

    /**
     * Reports an error message from the server
     *
     * @param errorMessage
     */
    public void showError(String errorMessage, String clientID) {
        if (this.socketClient.getClientID().equals(clientID)) {
            System.out.println(STR."[ERROR]: \{errorMessage}");
        }
    }

    /**
     * Updates client's state
     *
     * @param clientState
     * @throws RemoteException
     */
    public void updateState(ClientState clientState) throws RemoteException {
        synchronized (this.socketClient.lock) {
            this.socketClient.setState(clientState);
            this.socketClient.lock.notifyAll();
        }

    }


    public void setClientID(String clientID) throws RemoteException {
        synchronized (this.socketClient) {
            this.socketClient.setClientID(clientID);
            this.socketClient.notifyAll();
        }
    }


    public void setGameController() {
        synchronized (this.socketClient) {
            this.socketClient.setVirtualGameController();
            this.socketClient.notifyAll();
        }

    }

    public void updateChosenPawn(String pawnColor, String clientID) {
        System.out.println(STR."\{clientID} chose  pawn color: \{pawnColor}");
    }

    public void updateSelectedMission(String clientID) {
        System.out.println(STR."Selected mission successfully!");
    }

    public void updateSelectedCardFromHand(String clientID) {
        if (this.socketClient.getClientID() == null || this.socketClient.getClientID().equals(clientID)) {
            System.out.println("Card selected from hand!");
        }
    }

    public void updateSelectedSide(String cardIndex, String clientID) {
        System.out.println(STR."Updated selected side, card index: \{cardIndex}");
    }

    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String clientID, String success) {
        if (success.equals("1")) {
            System.out.println(STR."Position [\{selectedX}, \{selectedY}] selected on board");
        } else {
            System.out.println("Position not selected on board!");
        }
    }

    public void updatePlayedCardFromHand(String clientID, String success) {
        if (success.equals("1")) {
            System.out.println("Card played successfully!");
        } else {
            System.out.println("Card not played!");
        }
    }

    public void updatePoints(String clientID, String points) {
        System.out.println(STR."\{clientID} reached \{points} points");
    }

    public void updateSelectedCardFromCommonTable(String clientID, String success) {
        if (success.equals("1")) {
            System.out.println(STR."\{clientID} picked a card from the common table!");
        } else {
            System.out.println(STR."\{clientID} failed at picking a card from common table!");
        }
    }

    public void showCard(String clientID, String cardSerialization) {
        System.out.println("Card: " + cardSerialization);

    }

    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) {
        System.out.println(STR."\{ownerNickname}'s personal board:");
        System.out.println(personalBoardSerialization);
    }

    public void updateFirstPlayer(String nickname) {
        System.out.println(STR."First player: \{nickname}");
    }

    public void updateGameState(String gameState) {
        System.out.println(STR."Game state: \{gameState}");
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public String getClientID() throws RemoteException {
        return this.socketClient.getClientID();
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public ClientState getState() throws RemoteException {
        return null;
    }

}
