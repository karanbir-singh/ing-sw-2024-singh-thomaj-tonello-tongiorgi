package it.polimi.ingsw.gc26.network;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;

public class ViewController {
    /**
     * References to the main client of which this object is controller
     */
    private MainClient mainClient;
    /**
     * ID of the client
     */
    private String clientID;

    /**
     * Nickname of the client
     */
    private String nickname;
    /**
     * Client's state
     */
    private ClientState clientState;
    /**
     * Attribute used for synchronize actions between server and client
     */
    private final Object lock;

    public ViewController(MainClient mainClient, String clientID, String nickname, ClientState clientState, Object lock) {
        this.mainClient = mainClient;
        this.clientID = clientID;
        this.nickname = nickname;
        this.clientState = clientState;
        this.lock = lock;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
        if (message.get("receiver").asText().equals(this.clientID) || message.get("receiver").asText().equals("")) {
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
        if (this.clientID.equals(clientID)) {
            System.out.println(STR."[ERROR]: \{errorMessage}");
        }
    }

    /**
     * Updates client's state
     *
     * @param clientState
     */
    public void updateClientState(ClientState clientState) {
        synchronized (lock) {
            this.clientState = clientState;
            lock.notifyAll();
        }
    }

    public ClientState getClientState() {
        return this.clientState;
    }


    public void setGameController(VirtualGameController virtualGameController) {
        synchronized (lock) {
            this.mainClient.setVirtualGameController(virtualGameController);
            lock.notifyAll();
        }
    }

    public void updateChosenPawn(String pawnColor, String clientID) {
        System.out.println(STR."\{clientID} chose  pawn color: \{pawnColor}");
    }

    public void updateSelectedMission(String clientID) {
        System.out.println(STR."Selected mission successfully!");
    }

    public void updateSelectedCardFromHand(String clientID) {
        if (clientID == null || this.clientID.equals(clientID)) {
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
}
