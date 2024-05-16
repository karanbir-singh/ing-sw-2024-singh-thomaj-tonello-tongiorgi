package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.view_model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * This class represents the client for the server
 */
public class VirtualSocketView implements VirtualView {
    /**
     * This represents the print writer to write output to the client
     */
    private final PrintWriter outputToClient;

    /**
     * Virtual Socket view constructor. It initializes the print writer to the client
     *
     * @param output print writer to client
     */
    public VirtualSocketView(PrintWriter output) {
        this.outputToClient = output;
    }

    /**
     * This method creates the basic structure for this protocol.
     *
     * @return base structure
     */
    private static HashMap<String, String> getBaseMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    /**
     * Sends message in JSON format to client
     *
     * @param functionName represents the function to call client side
     * @param valueMsg     represents a value that is need to the called function
     */
    private void sendToClient(String functionName, HashMap<String, String> valueMsg) {
        HashMap<String, String> data = getBaseMessage();
        data.replace("function", functionName);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(valueMsg));
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private void sendToClient(String functionName, String valueMsg) {
        HashMap<String, String> data = getBaseMessage();
        data.replace("function", functionName);
        try {
            data.replace("value", valueMsg);
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Print messages from the server to the client.
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void showMessage(String message) throws RemoteException {

    }

    /**
     * Notifies the clients about an error.
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void showError(String message) throws RemoteException {

    }

    /**
     * Updates the client's state (used during the game's initialization)
     *
     * @param clientState
     * @throws RemoteException
     */
    @Override
    public void updateClientState(ClientState clientState) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientState", clientState.toString());
        sendToClient("updateClientState", msg);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param clientID new client IS
     * @throws RemoteException
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        sendToClient("setClientID", msg);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @throws RemoteException
     */
    @Override
    public void setGameController() throws RemoteException {
        sendToClient("setGameController", (String) null);
    }

    /**
     * @param simplifiedCommonTable
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException {
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        // resourceDeck
        root.set("resourceDeck", createResourceCardNode(simplifiedCommonTable.getResourceDeck()));

        // goldDeck
        root.set("goldDeck", createGoldCardNode(simplifiedCommonTable.getGoldDeck()));

        // commonMissions
        ObjectNode commonMissions = om.createObjectNode();
        root.set("commonMissions", commonMissions);
        for (int i = 0; i < simplifiedCommonTable.getCommonMissions().size(); i++) {
            commonMissions.set(String.valueOf(i), createMissionCardNode(simplifiedCommonTable.getCommonMissions().get(i)));
        }

        // resourceCards
        ObjectNode resourceCards = om.createObjectNode();
        root.set("resourceCards", resourceCards);
        for (int i = 0; i < simplifiedCommonTable.getResourceCards().size(); i++) {
            resourceCards.set(String.valueOf(i), createResourceCardNode(simplifiedCommonTable.getResourceCards().get(i)));
        }

        // resourceCards
        ObjectNode goldCards = om.createObjectNode();
        root.set("goldCards", goldCards);
        for (int i = 0; i < simplifiedCommonTable.getGoldCards().size(); i++) {
            goldCards.set(String.valueOf(i), createMissionCardNode(simplifiedCommonTable.getGoldCards().get(i)));
        }

        try {
            sendToClient("updateCommonTable", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param simplifiedHand
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException {
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            String a = mappedmsg.writeValueAsString(simplifiedHand);
            a = null;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param simplifiedSecretHand
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateSecretHand(SimplifiedHand simplifiedSecretHand, String message) throws RemoteException {

    }

    /**
     * @param personalBoard
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) throws RemoteException {

    }

    /**
     * @param otherPersonalBoard
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) throws RemoteException {

    }

    /**
     * @param simplifiedPlayer
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException {

    }

    /**
     * @param simplifiedChat
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException {

    }

    private ObjectNode createResourceCardNode(Card resourceCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("sideSymbol", resourceCard.getFront().getSideSymbol().toString());
        cardNode.put("points", String.valueOf(resourceCard.getFront().getPoints()));
        // corners
        ObjectNode UPLEFT = om.createObjectNode();
        cardNode.set("UPLEFT", UPLEFT);
        UPLEFT.put("isEvil", resourceCard.getFront().getUPLEFT().isEvil());
        UPLEFT.put("symbol", resourceCard.getFront().getUPLEFT().getSymbol().toString());
        ObjectNode DOWNLEFT = om.createObjectNode();
        cardNode.set("DOWNLEFT", DOWNLEFT);
        DOWNLEFT.put("isEvil", resourceCard.getFront().getDOWNLEFT().isEvil());
        DOWNLEFT.put("symbol", resourceCard.getFront().getDOWNLEFT().getSymbol().toString());
        ObjectNode UPRIGHT = om.createObjectNode();
        cardNode.set("UPRIGHT", UPRIGHT);
        UPRIGHT.put("isEvil", resourceCard.getFront().getUPRIGHT().isEvil());
        UPRIGHT.put("symbol", resourceCard.getFront().getUPRIGHT().getSymbol().toString());
        ObjectNode DOWNRIGHT = om.createObjectNode();
        cardNode.set("DOWNRIGHT", DOWNRIGHT);
        DOWNRIGHT.put("isEvil", resourceCard.getFront().getDOWNRIGHT().isEvil());
        DOWNRIGHT.put("symbol", resourceCard.getFront().getDOWNRIGHT().getSymbol().toString());

        return cardNode;
    }

    private ObjectNode createGoldCardNode(Card goldCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", goldCard.getClass().getSimpleName());
        cardNode.put("sideSymbol", goldCard.getFront().getSideSymbol().toString());

        // resources
        ObjectNode resourcesNode = om.createObjectNode();
        for (Map.Entry<Symbol, Integer> resource : goldCard.getFront().getRequestedResources().entrySet()) {
            resourcesNode.put(resource.getKey().toString(), resource.getValue().toString());
        }
        cardNode.set("requestedResources", resourcesNode);

        // corners
        ObjectNode UPLEFT = om.createObjectNode();
        cardNode.set("UPLEFT", UPLEFT);
        UPLEFT.put("isEvil", goldCard.getFront().getUPLEFT().isEvil());
        UPLEFT.put("symbol", goldCard.getFront().getUPLEFT().getSymbol().toString());
        ObjectNode DOWNLEFT = om.createObjectNode();
        cardNode.set("DOWNLEFT", DOWNLEFT);
        DOWNLEFT.put("isEvil", goldCard.getFront().getDOWNLEFT().isEvil());
        DOWNLEFT.put("symbol", goldCard.getFront().getDOWNLEFT().getSymbol().toString());
        ObjectNode UPRIGHT = om.createObjectNode();
        cardNode.set("UPRIGHT", UPRIGHT);
        UPRIGHT.put("isEvil", goldCard.getFront().getUPRIGHT().isEvil());
        UPRIGHT.put("symbol", goldCard.getFront().getUPRIGHT().getSymbol().toString());
        ObjectNode DOWNRIGHT = om.createObjectNode();
        cardNode.set("DOWNRIGHT", DOWNRIGHT);
        DOWNRIGHT.put("isEvil", goldCard.getFront().getDOWNRIGHT().isEvil());
        DOWNRIGHT.put("symbol", goldCard.getFront().getDOWNRIGHT().getSymbol().toString());

        return cardNode;
    }

    private ObjectNode createMissionCardNode(Card missionCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", missionCard.getClass().getSimpleName());
        cardNode.put("type", missionCard.getFront().getType());
        return cardNode;
    }
}

