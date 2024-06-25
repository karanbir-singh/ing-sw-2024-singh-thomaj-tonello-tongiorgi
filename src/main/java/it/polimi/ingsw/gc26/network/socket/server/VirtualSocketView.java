package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.view_model.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the methods that can be called from the server to be executed on the client.
 * Implementation for socket network connection.
 */
public class VirtualSocketView implements VirtualView {

    /**
     * This represents the print writer to write output to the client
     */
    private final BufferedWriter outputToClient;

    /**
     * Virtual Socket view constructor. It initializes the print writer to the client
     *
     * @param output print writer to client
     */
    public VirtualSocketView(BufferedWriter output) {
        this.outputToClient = output;
    }

    /**
     * This method creates the basic structure for this protocol.
     *
     * @return base structure
     */
    private HashMap<String, String> getBaseMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    /**
     * This method writes the message in the output stream to the client.
     *
     * @param functionName name of the function to be called client side
     * @param valueMsg     data to send
     */
    private void sendToClient(String functionName, HashMap<String, String> valueMsg) throws RemoteException {
        HashMap<String, String> data = getBaseMessage();
        data.replace("function", functionName);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(valueMsg));
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.write(mappedData.writeValueAsString(data));
            this.outputToClient.newLine();
            this.outputToClient.flush();
        } catch (IOException ex) {
            throw new RemoteException();
        }
    }

    /**
     * This method writes the message in the output stream to the client.
     *
     * @param functionName name of the function to be called client side
     * @param valueMsg     data to send
     */
    private void sendToClient(String functionName, String valueMsg) throws RemoteException {
        HashMap<String, String> data = getBaseMessage();
        data.replace("function", functionName);
        try {
            data.replace("value", valueMsg);
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.write(mappedData.writeValueAsString(data));
            this.outputToClient.newLine();
            this.outputToClient.flush();
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

    /**
     * Print messages from the server to the client.
     *
     * @param message string to be displayed as information
     * @throws RemoteException if the remote method cannot be called
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
     * @param message string to be displayed as an error
     * @throws RemoteException if the remote method cannot be called
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
     * @param clientState new client state
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateClientState(ClientState clientState) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientState", clientState.toString());
        sendToClient("updateClientState", msg);
    }

    /**
     * Sets the client's id. Used only with socket implementation.
     *
     * @param clientID new client's id
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        sendToClient("setClientID", msg);
    }

    /**
     * Sets the game controller. Used only with socket implementation.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void setGameController() throws RemoteException {
        sendToClient("setGameController", (String) null);
    }

    /**
     * Updates the client's common table by giving a simplified version of model's common table.
     * It also displays a message with information about the update.
     *
     * @param simplifiedCommonTable updated common table
     * @param message               updated message
     * @throws RemoteException if the remote method cannot be called
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

        // selected index
        root.put("selectedIndex", simplifiedCommonTable.getSelectedIndex());

        try {
            sendToClient("updateCommonTable", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the client's hand by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedHand updated hand
     * @param message        updated message
     * @throws RemoteException if the remote method cannot be called
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
            switch (card.getClass().getSimpleName()) {
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
            root.put("selectedSide", simplifiedHand.getSelectedSide().equals(simplifiedHand.getCards().get(simplifiedHand.getCards().indexOf(simplifiedHand.getSelectedCard())).getFront()) ? "0" : "1"); // T
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
     * Updates the client's secret hand by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedSecretHand updated hand
     * @param message              updated message
     * @throws RemoteException if the remote method cannot be called
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
            switch (card.getClass().getSimpleName()) {
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
            root.put("selectedSide", simplifiedSecretHand.getSelectedSide().equals(simplifiedSecretHand.getCards().get(simplifiedSecretHand.getCards().indexOf(simplifiedSecretHand.getSelectedCard())).getFront()) ? "0" : "1");
        } else {
            root.put("selectedSide", -1);
        }
        try {
            sendToClient("updateSecretHand", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the client's personal board by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param personalBoard updated personal board
     * @param message       updated message
     * @throws RemoteException if the remote method cannot be called
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
        root.set("occupiedPositions", createArrayPositionsNode(personalBoard.getOccupiedPositions()));

        // playable positions
        root.set("playablePositions", createArrayPositionsNode(personalBoard.getPlayablePositions()));

        // blocked positions
        root.set("blockedPositions", createArrayPositionsNode(personalBoard.getBlockedPositions()));

        // secret mission
        root.set("secretMission", createMissionCardNode(personalBoard.getSecretMission()));

        // visibleResources
        ObjectNode visibleResources = om.createObjectNode();
        root.set("visibleResources", visibleResources);
        for (Map.Entry<Symbol, Integer> resource : personalBoard.getVisibleResources().entrySet()) {
            visibleResources.put(resource.getKey().toString(), resource.getValue().toString());
        }

        //nickname
        root.put("nickname", personalBoard.getNickname());

        try {
            sendToClient("updatePersonalBoard", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the other players' personal boards by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param otherPersonalBoard updated personal board
     * @param message            updated message
     * @throws RemoteException if the remote method cannot be called
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
        root.set("occupiedPositions", createArrayPositionsNode(otherPersonalBoard.getOccupiedPositions()));

        // playable positions
        root.set("playablePositions", createArrayPositionsNode(otherPersonalBoard.getPlayablePositions()));

        // blocked positions
        root.set("blockedPositions", createArrayPositionsNode(otherPersonalBoard.getBlockedPositions()));

        // secret mission
        root.set("secretMission", createMissionCardNode(otherPersonalBoard.getSecretMission()));

        // secret mission
        root.set("secretMission", createMissionCardNode(otherPersonalBoard.getSecretMission()));

        // visibleResources
        ObjectNode visibleResources = om.createObjectNode();
        root.set("visibleResources", visibleResources);
        for (Map.Entry<Symbol, Integer> resource : otherPersonalBoard.getVisibleResources().entrySet()) {
            visibleResources.put(resource.getKey().toString(), resource.getValue().toString());
        }

        //nickname
        root.put("nickname", otherPersonalBoard.getNickname());

        try {
            sendToClient("updateOtherPersonalBoard", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the client's representation of himself by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedPlayer updated player
     * @param message          updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updatePlayer(SimplifiedPlayer simplifiedPlayer, String message) throws RemoteException {
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        // message
        root.put("message", message);

        // ID
        root.put("ID", simplifiedPlayer.getID());
        // nickname
        root.put("nickname", simplifiedPlayer.getNickname());
        // pawn color
        root.put("pawnColor", simplifiedPlayer.getPawnColor().toString());
        // amIFirstPlayer
        root.put("amIFirstPlayer", simplifiedPlayer.isAmIFirstPlayer());
        // player's state
        root.put("state", simplifiedPlayer.getState().toString());

        try {
            sendToClient("updatePlayer", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the game by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedGame updated game
     * @param message        updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateGame(SimplifiedGame simplifiedGame, String message) throws RemoteException {
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        // message
        root.put("message", message);

        // game state
        root.put("gameState", simplifiedGame.getGameState().toString());

        // current player
        root.put("currentPlayer", simplifiedGame.getCurrentPlayer());

        // scores
        ObjectNode scores = om.createObjectNode();
        root.set("scores", scores);
        for (Map.Entry<String, Integer> score : simplifiedGame.getScores().entrySet()) {
            scores.put(score.getKey(), score.getValue().toString());
        }

        // winners
        ArrayNode winners = om.createArrayNode();
        for (String winner : simplifiedGame.getWinners()) {
            winners.add(winner);
        }
        root.set("winners", winners);

        // available pawns
        ArrayNode availablePawn = om.createArrayNode();
        for (Pawn pawn : simplifiedGame.getAvailablePawns()) {
            availablePawn.add(pawn.toString());
        }
        root.set("availablePawns", availablePawn);

        // selected pawns
        ObjectNode selectedPawns = om.createObjectNode();
        root.set("selectedPawns", selectedPawns);
        for (Map.Entry<String, Pawn> pawn : simplifiedGame.getPawnsSelected().entrySet()) {
            selectedPawns.put(pawn.getKey(), pawn.getValue() != null ? pawn.getValue().toString() : null);
        }

        try {
            sendToClient("updateGame", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the client's chat by giving a simplified version of model's one.
     * It also displays a message with the information about the update.
     *
     * @param simplifiedChat updated chat
     * @param message        updated message
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateChat(SimplifiedChat simplifiedChat, String message) throws RemoteException {
        ObjectMapper om = new ObjectMapper();
        ObjectNode root = om.createObjectNode();

        // message
        root.put("message", message);

        // chat's messages array
        ArrayNode messagesArray = om.createArrayNode();
        root.set("messages", messagesArray);
        for (Message messageInChat : simplifiedChat.getMessages()) {
            messagesArray.add(messageInChat.toJson());
        }
        try {
            sendToClient("updateChat", om.writeValueAsString(root));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Updates the client's game id.
     * It also displays a message with the information about the update.
     *
     * @param idGame updated game ID
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void updateIDGame(int idGame) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("idGame", String.valueOf(idGame));
        sendToClient("updateIDGame", msg);
    }

    /**
     * Resets the timer in the client so the client knows the server is up.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void ping() throws RemoteException {
        sendToClient("ping", new HashMap<>());
    }

    /**
     * Kills the client ending the game.
     *
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void killProcess() throws RemoteException {
        sendToClient("killProcess", new HashMap<>());
    }

    /**
     * Encodes a resource card to a json object
     *
     * @param resourceCard card to be encoded
     * @return encoded card
     */
    private ObjectNode createResourceCardNode(Card resourceCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("sideSymbol", resourceCard.getFront().getSideSymbol().isPresent() ? resourceCard.getFront().getSideSymbol().get().toString() : "");
        cardNode.put("points", String.valueOf(resourceCard.getFront().getPoints()));
        // images
        cardNode.put("imagePathFront", resourceCard.getFront().getImagePath());
        cardNode.put("imagePathBack", resourceCard.getBack().getImagePath());
        // corners
        return createCornerNodes(resourceCard.getFront(), om, cardNode);
    }

    /**
     * Encodes a resource side to a json object
     *
     * @param resourceSide resource side to be encoded
     * @return encoded side
     */
    private ObjectNode createResourceCardNode(Side resourceSide) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("sideSymbol", resourceSide.getSideSymbol().isPresent() ? resourceSide.getSideSymbol().get().toString() : "");
        cardNode.put("points", String.valueOf(resourceSide.getPoints()));
        // images
        cardNode.put("imagePathFront", resourceSide.getImagePath());
        cardNode.put("imagePathBack", resourceSide.getImagePath());
        // corners
        return createCornerNodes(resourceSide, om, cardNode);
    }

    /**
     * Encodes a gold card to a json object
     *
     * @param goldCard card to be encoded
     * @return encoded card
     */
    private ObjectNode createGoldCardNode(Card goldCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", goldCard.getFront().getClass().getSimpleName());
        cardNode.put("sideSymbol", goldCard.getFront().getSideSymbol().isPresent() ? goldCard.getFront().getSideSymbol().get().toString() : "");

        // points
        cardNode.put("points", goldCard.getFront().getPoints());

        // resources
        ObjectNode resourcesNode = om.createObjectNode();
        for (Map.Entry<Symbol, Integer> resource : goldCard.getFront().getRequestedResources().entrySet()) {
            resourcesNode.put(resource.getKey().toString(), resource.getValue().toString());
        }
        cardNode.set("requestedResources", resourcesNode);

        // images
        cardNode.put("imagePathFront", goldCard.getFront().getImagePath());
        cardNode.put("imagePathBack", goldCard.getBack().getImagePath());

        // corners
        return createCornerNodes(goldCard.getFront(), om, cardNode);
    }

    /**
     * Encodes a gold side to a json object
     *
     * @param goldSide side card to be encoded
     * @return encoded side
     */
    private ObjectNode createGoldCardNode(Side goldSide) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", goldSide.getClass().getSimpleName());
        cardNode.put("sideSymbol", goldSide.getSideSymbol().isPresent() ? goldSide.getSideSymbol().get().toString() : "");

        // points
        cardNode.put("points", goldSide.getPoints());

        // resources
        ObjectNode resourcesNode = om.createObjectNode();
        for (Map.Entry<Symbol, Integer> resource : goldSide.getRequestedResources().entrySet()) {
            resourcesNode.put(resource.getKey().toString(), resource.getValue().toString());
        }
        cardNode.set("requestedResources", resourcesNode);

        // images
        cardNode.put("imagePathFront", goldSide.getImagePath());
        cardNode.put("imagePathBack", goldSide.getImagePath());

        // corners
        return createCornerNodes(goldSide, om, cardNode);
    }

    /**
     * Encodes a mission card to a json object
     *
     * @param missionCard card to be encoded
     * @return encoded card
     */
    private ObjectNode createMissionCardNode(Card missionCard) {
        if (missionCard == null) {
            return new ObjectMapper().createObjectNode();
        }
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();
        cardNode.put("cardType", missionCard.getFront().getClass().getSimpleName());
        cardNode.put("type", missionCard.getFront().getType());
        // images
        cardNode.put("imagePathFront", missionCard.getFront().getImagePath());
        cardNode.put("imagePathBack", missionCard.getBack().getImagePath());
        return cardNode;
    }

    /**
     * Encodes a starter card to a json object
     *
     * @param starterCard card to be encoded
     * @return encoded card
     */
    private ObjectNode createStarterCardNode(Card starterCard) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();

        // front
        ObjectNode front = om.createObjectNode();
        cardNode.set("front", front);
        // permanent resources
        ArrayNode permanentResources = om.createArrayNode();
        for (Symbol symbol : starterCard.getFront().getPermanentResources()) {
            permanentResources.add(symbol.toString());
        }
        front.set("permanentResources", permanentResources);

        // image
        front.put("imagePathFront", starterCard.getFront().getImagePath());

        // front corners
        createCornerNodes(starterCard.getFront(), om, front);

        // back
        ObjectNode back = om.createObjectNode();
        cardNode.set("back", back);
        // permanent resources
        ArrayNode permanentResourcesBack = om.createArrayNode();
        for (Symbol symbol : starterCard.getFront().getPermanentResources()) {
            permanentResourcesBack.add(symbol.toString());
        }
        back.set("permanentResources", permanentResources);

        // image
        back.put("imagePathBack", starterCard.getBack().getImagePath());

        // back corners
        createCornerNodes(starterCard.getBack(), om, back);
        return cardNode;
    }

    /**
     * Encodes a starter side to a json object
     *
     * @param starterSide side to be encoded
     * @return encoded side
     */
    private ObjectNode createStarterCardNode(Side starterSide) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode cardNode = om.createObjectNode();

        // permanent resources
        ArrayNode permanentResources = om.createArrayNode();
        for (Symbol symbol : starterSide.getPermanentResources()) {
            permanentResources.add(symbol.toString());
        }
        cardNode.set("permanentResources", permanentResources);

        // image
        cardNode.put("imagePathFront", starterSide.getImagePath());

        return createCornerNodes(starterSide, om, cardNode);
    }


    /**
     * Encodes all the corners of a card to a json object
     *
     * @param side     corner's side to be encoded
     * @param om       mapper
     * @param cardNode card side encoded where to add the corners encode
     * @return card node with corners encoded
     */
    private ObjectNode createCornerNodes(Side side, ObjectMapper om, ObjectNode cardNode) {
        ObjectNode UPLEFT = om.createObjectNode();
        cardNode.set("UPLEFT", UPLEFT);
        UPLEFT.put("isEvil", side.getUPLEFT().isEvil());
        UPLEFT.put("symbol", side.getUPLEFT().getSymbol().isPresent() ? side.getUPLEFT().getSymbol().get().toString() : "");
        UPLEFT.put("isHidden", side.getUPLEFT().isHidden());
        ObjectNode DOWNLEFT = om.createObjectNode();
        cardNode.set("DOWNLEFT", DOWNLEFT);
        DOWNLEFT.put("isEvil", side.getDOWNLEFT().isEvil());
        DOWNLEFT.put("symbol", side.getDOWNLEFT().getSymbol().isPresent() ? side.getDOWNLEFT().getSymbol().get().toString() : "");
        DOWNLEFT.put("isHidden", side.getDOWNLEFT().isHidden());
        ObjectNode UPRIGHT = om.createObjectNode();
        cardNode.set("UPRIGHT", UPRIGHT);
        UPRIGHT.put("isEvil", side.getUPRIGHT().isEvil());
        UPRIGHT.put("symbol", side.getUPRIGHT().getSymbol().isPresent() ? side.getUPRIGHT().getSymbol().get().toString() : "");
        UPRIGHT.put("isHidden", side.getUPRIGHT().isHidden());
        ObjectNode DOWNRIGHT = om.createObjectNode();
        cardNode.set("DOWNRIGHT", DOWNRIGHT);
        DOWNRIGHT.put("isEvil", side.getDOWNRIGHT().isEvil());
        DOWNRIGHT.put("symbol", side.getDOWNRIGHT().getSymbol().isPresent() ? side.getDOWNRIGHT().getSymbol().get().toString() : "");
        DOWNRIGHT.put("isHidden", side.getDOWNRIGHT().isHidden());
        return cardNode;
    }

    /**
     * Encodes a back card to a json object
     *
     * @param side back side
     * @return encoded back side
     */
    private ObjectNode createCardBack(Side side) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode sideNode = om.createObjectNode();
        // side symbol
        sideNode.put("sideSymbol", side.getSideSymbol().isPresent() ? side.getSideSymbol().get().toString() : "");
        // permanent resource
        if (!side.getPermanentResources().isEmpty()) {
            sideNode.put("permanentResources", side.getPermanentResources().getFirst().toString());
        } else {
            sideNode.set("permanentResources", null);
        }

        // image
        sideNode.put("imagePathBack", side.getImagePath());
        // corners
        return createCornerNodes(side, om, sideNode);
    }

    /**
     * Encodes an array of position containing side in it to a json object
     *
     * @param positions position in personal board
     * @return encoded positions
     */
    private ArrayNode createArrayPositionsNode(ArrayList<Point> positions) {
        ObjectMapper om = new ObjectMapper();
        ArrayNode arrayPositions = om.createArrayNode();
        for (Point point : positions) {
            ObjectNode genericPoint = om.createObjectNode();
            genericPoint.put("X", point.getX());
            genericPoint.put("Y", point.getY());
            // flags
            ObjectNode flags = om.createObjectNode();
            for (Map.Entry<Integer, Boolean> flag : point.getFlags().entrySet()) {
                flags.put(flag.getKey().toString(), flag.getValue());
            }
            //image

            genericPoint.set("flags", flags);
            // side
            if (point.getSide() != null) {
                genericPoint.put("type", point.getSide().getClass().getSimpleName());
                switch (point.getSide().getClass().getSimpleName()) {
                    case "CardBack":
                        genericPoint.set("side", createCardBack(point.getSide()));
                        break;
                    case "StarterCardFront":
                        genericPoint.set("side", createStarterCardNode(point.getSide()));
                        break;
                    case "ResourceCardFront":
                        genericPoint.set("side", createResourceCardNode(point.getSide()));
                        break;
                    case "CornerCounter", "QuillCounter", "InkwellCounter", "ManuscriptCounter", "GoldCardFront":
                        genericPoint.set("side", createGoldCardNode(point.getSide()));
                        break;
                    case null, default:
                        genericPoint.set("side", null);
                        break;
                }
            } else {
                genericPoint.set("side", null);
            }
            arrayPositions.add(genericPoint);
        }
        return arrayPositions;
    }
}

