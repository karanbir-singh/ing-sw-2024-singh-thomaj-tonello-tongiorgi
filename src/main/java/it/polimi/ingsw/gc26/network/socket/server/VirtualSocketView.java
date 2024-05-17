package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card_side.StarterCardFront;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.view_request.OtherPersonalBoardUpdateRequest;
import it.polimi.ingsw.gc26.view_model.*;
import javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanProperty;

import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.*;

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
        HashMap<String, String> msg = new HashMap<>();
        msg.put("message", message);
        sendToClient("showMessage", msg);
    }

    /**
     * Notifies the clients about an error.
     *
     * @param message
     * @throws RemoteException
     */
    @Override
    public void showError(String message) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("errorMessage", message);
        sendToClient("showError", msg);
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

        //message
        root.put("message", message);

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
            goldCards.set(String.valueOf(i), createGoldCardNode(simplifiedCommonTable.getGoldCards().get(i)));
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
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        //message
        root.put("message", message);

        //cards
        ArrayNode cards = om.createArrayNode();
        root.set("cards", cards);
        for (Card card : simplifiedHand.getCards()) {
            ObjectNode genericCard = om.createObjectNode();
            switch(card.getClass().getSimpleName()){
                case "GoldCard":
                    genericCard.put("type", "goldCard");
                    genericCard.set("card", createGoldCardNode(card));
                    break;
                case "ResourceCard":
                    genericCard.put("type", "resourceCard");
                    genericCard.set("card", createResourceCardNode(card));
                    break;
                case "StarterCard":
                    genericCard.put("type", "starterCard");
                    genericCard.set("card", createStarterCardNode(card));
                    break;
                case null, default:
                    throw new RemoteException();
            }
            cards.add(genericCard);
        }
        root.put("selectedCard", simplifiedHand.getCards().indexOf(simplifiedHand.getSelectedCard()));
        if (simplifiedHand.getSelectedCard() != null) {
            root.put("selectedSide", simplifiedHand.getSelectedSide().equals(simplifiedHand.getCards().get(simplifiedHand.getCards().indexOf(simplifiedHand.getSelectedCard())).getFront()) ? "0" : "1" ); // TODO
        } else {
            root.put("selectedSide", -1);
        }
        try {
            sendToClient("updateHand", om.writeValueAsString(root));
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
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        //message
        root.put("message", message);

        //cards
        ArrayNode cards = om.createArrayNode();
        root.set("cards", cards);
        for (Card card : simplifiedSecretHand.getCards()) {
            ObjectNode genericCard = om.createObjectNode();
            switch(card.getClass().getSimpleName()){
                case "MissionCard":
                    genericCard.put("type", "MissionCard");
                    genericCard.set("card", createMissionCardNode(card));
                    break;
                case null, default:
                    throw new RemoteException();
            }
            cards.add(genericCard);
        }
        root.put("selectedCard", simplifiedSecretHand.getCards().indexOf(simplifiedSecretHand.getSelectedCard()));
        if (simplifiedSecretHand.getSelectedCard() != null) {
            root.put("selectedSide", simplifiedSecretHand.getSelectedSide().equals(simplifiedSecretHand.getCards().get(simplifiedSecretHand.getCards().indexOf(simplifiedSecretHand.getSelectedCard())).getFront()) ? "0" : "1" ); // TODO
        } else {
            root.put("selectedSide", -1);
        }
        try {
            sendToClient("updateHand", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param personalBoard
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updatePersonalBoard(SimplifiedPersonalBoard personalBoard, String message) throws RemoteException {
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        // message
        root.put("message", message);

        // int fields
        root.put("xMin", personalBoard.getxMin());
        root.put("xMax", personalBoard.getxMax());
        root.put("yMax", personalBoard.getyMax());
        root.put("yMin", personalBoard.getyMin());
        root.put("score", personalBoard.getScore());
        root.put("selectedX", personalBoard.getSelectedX());
        root.put("selectedY", personalBoard.getSelectedY());

        // occupied positions
        ArrayNode occupiedPositions = om.createArrayNode();
        root.set("occupiedPositions", occupiedPositions);
        for (Point point : personalBoard.getOccupiedPositions()) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            occupiedPositions.add(genericPoint);
        }

        // playable positions
        ArrayNode playablePositions = om.createArrayNode();
        root.set("playablePositions", playablePositions);
        for (Point point : personalBoard.getPlayablePositions()) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            playablePositions.add(genericPoint);
        }

        // blocked positions
        ArrayNode blockedPositions = om.createArrayNode();
        root.set("blockedPositions", blockedPositions);
        for (Point point : personalBoard.getBlockedPositions()) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            blockedPositions.add(genericPoint);
        }

        // secret mission
        root.set("secretMission", createMissionCardNode(personalBoard.getSecretMission()));

        // visibleResources
        ObjectNode visibleResources = om.createObjectNode();
        root.set("visibleResources", visibleResources);
        for (Map.Entry<Symbol, Integer> resource : personalBoard.getVisibleResources().entrySet()) {
            visibleResources.put(resource.getKey().toString(), resource.getValue().toString());
        }

        try {
            sendToClient("updatePersonalBoard", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param otherPersonalBoard
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) throws RemoteException {
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        // message
        root.put("message", message);

        // int fields
        root.put("xMin", otherPersonalBoard.getxMin());
        root.put("xMax", otherPersonalBoard.getxMax());
        root.put("yMax", otherPersonalBoard.getyMax());
        root.put("yMin", otherPersonalBoard.getyMin());
        root.put("score", otherPersonalBoard.getScore());
        root.put("selectedX", otherPersonalBoard.getSelectedX());
        root.put("selectedY", otherPersonalBoard.getSelectedY());

        // occupied positions
        ArrayNode occupiedPositions = om.createArrayNode();
        root.set("occupiedPositions", occupiedPositions);
        for (Point point : otherPersonalBoard.getOccupiedPositions()) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            occupiedPositions.add(genericPoint);
        }

        // playable positions
        ArrayNode playablePositions = om.createArrayNode();
        root.set("playablePositions", playablePositions);
        for (Point point : otherPersonalBoard.getPlayablePositions()) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            playablePositions.add(genericPoint);
        }

        // blocked positions
        ArrayNode blockedPositions = om.createArrayNode();
        root.set("blockedPositions", blockedPositions);
        for (Point point : otherPersonalBoard.getBlockedPositions()) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            blockedPositions.add(genericPoint);
        }

        // secret mission
        root.set("secretMission", createMissionCardNode(otherPersonalBoard.getSecretMission()));

        // visibleResources
        ObjectNode visibleResources = om.createObjectNode();
        root.set("visibleResources", visibleResources);
        for (Map.Entry<Symbol, Integer> resource : otherPersonalBoard.getVisibleResources().entrySet()) {
            visibleResources.put(resource.getKey().toString(), resource.getValue().toString());
        }

        try {
            sendToClient("updateOtherPersonalBoard", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
        cardNode.put("sideSymbol", resourceCard.getFront().getSideSymbol().isPresent() ? resourceCard.getFront().getSideSymbol().get().toString() : "" );
        cardNode.put("points", String.valueOf(resourceCard.getFront().getPoints()));
        // corners
        return createCornerNodes(resourceCard, om, cardNode);
    }

    private ObjectNode createGoldCardNode(Card goldCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", goldCard.getClass().getSimpleName());
        cardNode.put("sideSymbol", goldCard.getFront().getSideSymbol().isPresent() ? goldCard.getFront().getSideSymbol().get().toString() : "");

        // resources
        ObjectNode resourcesNode = om.createObjectNode();
        for (Map.Entry<Symbol, Integer> resource : goldCard.getFront().getRequestedResources().entrySet()) {
            resourcesNode.put(resource.getKey().toString(), resource.getValue().toString());
        }
        cardNode.set("requestedResources", resourcesNode);

        // corners
        return createCornerNodes(goldCard, om, cardNode);
    }

    private ObjectNode createMissionCardNode(Card missionCard) {
        if (missionCard == null) {
            return new ObjectMapper().createObjectNode();
        }
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", missionCard.getClass().getSimpleName());
        cardNode.put("type", missionCard.getFront().getType());
        return cardNode;
    }

    private ObjectNode createStarterCardNode(Card starterCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();

        // permanent resources
        ArrayNode permanentResources = om.createArrayNode();
        for (Symbol symbol : starterCard.getFront().getPermanentResources()) {
            permanentResources.add(symbol.toString());
        }
        cardNode.set("permanentResources", permanentResources);

        // corners
        return createCornerNodes(starterCard, om, cardNode);
    }


    private ObjectNode createCornerNodes(Card starterCard, ObjectMapper om, ObjectNode cardNode) {
        ObjectNode UPLEFT = om.createObjectNode();
        cardNode.set("UPLEFT", UPLEFT);
        UPLEFT.put("isEvil", starterCard.getFront().getUPLEFT().isEvil());
        UPLEFT.put("symbol", starterCard.getFront().getUPLEFT().getSymbol().isPresent() ? starterCard.getFront().getUPLEFT().getSymbol().get().toString() : "" );
        ObjectNode DOWNLEFT = om.createObjectNode();
        cardNode.set("DOWNLEFT", DOWNLEFT);
        DOWNLEFT.put("isEvil", starterCard.getFront().getDOWNLEFT().isEvil());
        DOWNLEFT.put("symbol", starterCard.getFront().getDOWNLEFT().getSymbol().isPresent() ? starterCard.getFront().getDOWNLEFT().getSymbol().get().toString() : "" ) ;
        ObjectNode UPRIGHT = om.createObjectNode();
        cardNode.set("UPRIGHT", UPRIGHT);
        UPRIGHT.put("isEvil", starterCard.getFront().getUPRIGHT().isEvil());
        UPRIGHT.put("symbol", starterCard.getFront().getUPRIGHT().getSymbol().isPresent() ? starterCard.getFront().getUPRIGHT().getSymbol().get().toString() : "" ) ;
        ObjectNode DOWNRIGHT = om.createObjectNode();
        cardNode.set("DOWNRIGHT", DOWNRIGHT);
        DOWNRIGHT.put("isEvil", starterCard.getFront().getDOWNRIGHT().isEvil());
        DOWNRIGHT.put("symbol", starterCard.getFront().getDOWNRIGHT().getSymbol().isPresent() ? starterCard.getFront().getDOWNRIGHT().getSymbol().get().toString() : "" ) ;
        return cardNode;
    }
}

