package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.card.*;
import it.polimi.ingsw.gc26.model.card_side.*;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionDiagonalPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionItemPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionLPattern;
import it.polimi.ingsw.gc26.model.card_side.mission.MissionTripletPattern;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.request.view_request.*;
import it.polimi.ingsw.gc26.view_model.*;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.view_model.ViewController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;


/**
 * This class represents the handler to decode json from the server.
 */
public class SocketServerHandler implements Runnable {
    /**
     * This attributes represents the input from the server.
     */
    private final BufferedReader inputFromServer;

    /**
     * This attributes represents the output to the server.
     */
    private BufferedWriter outputToServer;

    /**
     * This attribute represents the clientController
     */
    private final ViewController viewController;

    /**
     *
     * @param viewController
     * @param inputFromServer
     * @param outputToServer
     */
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
                    case "updateClientState":
                        this.viewController.updateClientState(ClientState.valueOf(value.get("clientState").asText()));
                        break;
                    case "showMessage":
                        this.viewController.showMessage(value.get("message").asText());
                        break;
                    case "showError":
                        this.viewController.showError(value.get("errorMessage").asText());
                        break;
                    case "updateCommonTable":
                        SimplifiedCommonTable request = buildSimplifiedCommonTable(value);
                        this.viewController.addRequest(new CommonTableUpdateRequest(request, value.get("message").asText()));
                        break;
                    case "updateHand":
                        SimplifiedHand hand = buildSimplifiedHand(value);
                        viewController.addRequest(new HandUpdateRequest(hand, value.get("message").asText()));
                        break;
                    case "updateSecretHand":
                        SimplifiedHand secretHand = buildSimplifiedSecretHand(value);
                        this.viewController.addRequest(new SecretHandUpdateRequest(secretHand, value.get("message").asText()));
                        break;
                    case "updatePersonalBoard":
                        SimplifiedPersonalBoard personalBoard = buildPersonalBoard(value);
                        this.viewController.addRequest(new PersonalBoardUpdateRequest(personalBoard, value.get("message").asText()));
                        break;
                    case "updateOtherPersonalBoard":
                        SimplifiedPersonalBoard otherPersonalBoard = buildPersonalBoard(value);
                        this.viewController.addRequest(new OtherPersonalBoardUpdateRequest(otherPersonalBoard, value.get("message").asText()));
                        break;
                    case "updatePlayer":
                        SimplifiedPlayer simplifiedPlayer = buildSimplifiedPlayer(value);
                        this.viewController.addRequest(new PlayerUpdateRequest(simplifiedPlayer, value.get("message").asText()));
                        break;
                    case "updateChat":
                        SimplifiedChat simplifiedChat = buildSimplifiedChat(value);
                        this.viewController.addRequest(new ChatUpdateRequest(simplifiedChat, value.get("message").asText()));
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

    public SimplifiedCommonTable buildSimplifiedCommonTable(JsonNode encodedTable) {

        // resource card
        Card resourceCard = getResourceCard(encodedTable.get("resourceDeck"));

        // gold card
        Card goldCard = getGoldCard(encodedTable.get("goldDeck"));

        // mission cards
        ArrayList<Card> commonMission = new ArrayList<>();
        for (JsonNode mission : encodedTable.get("commonMissions")) {
            commonMission.add(getMissionCard(mission));
        }


        // resources Cards
        ArrayList<Card> resourceCards = new ArrayList<>();
        for (JsonNode resource : encodedTable.get("resourceCards")) {
            resourceCards.add(getResourceCard(resource));
        }

        //gold cards
        ArrayList<Card> goldCards = new ArrayList<>();
        for (JsonNode gold : encodedTable.get("goldCards")) {
            goldCards.add(getGoldCard(gold));
        }


        return new SimplifiedCommonTable(resourceCard, goldCard, commonMission, resourceCards, goldCards);
    }

    private GoldCard getGoldCard(JsonNode encodedCard) {
        if (encodedCard.findValue("card") != null) {
            encodedCard = encodedCard.get("card");
        }
        Map<Symbol, Integer> resources = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> resourceIterator = encodedCard.get("requestedResources").fields();
        while (resourceIterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = resourceIterator.next();
            resources.put(Symbol.valueOf(entry.getKey()), entry.getValue().asInt());
        }
        ArrayList<Corner> corners = getCorners(encodedCard);
        Side frontGold = switch (encodedCard.get("cardType").asText()) {
            case "CornerCounter" ->
                    new CornerCounter(Symbol.valueOf(encodedCard.get("sideSymbol").asText()),
                            resources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
            case "InkwellCounter" ->
                    new InkwellCounter(Symbol.valueOf(encodedCard.get("sideSymbol").asText()),
                            resources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
            case "ManuscriptCounter" ->
                    new ManuscriptCounter(Symbol.valueOf(encodedCard.get("sideSymbol").asText()),
                            resources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
            case "QuillCounter" ->
                    new QuillCounter(Symbol.valueOf(encodedCard.get("sideSymbol").asText()),
                            resources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
            default -> null;
        };
        Side backGold= new CardBack(Symbol.valueOf(encodedCard.get("sideSymbol").asText()));
        return  new GoldCard(frontGold, backGold);
    }

    private ResourceCard getResourceCard(JsonNode encodedCard) {
        if (encodedCard.findValue("card") != null) {
            encodedCard = encodedCard.get("card");
        }
        ArrayList<Corner> corners = getCorners(encodedCard);
        Side frontResource = new ResourceCardFront(Symbol.valueOf(encodedCard.get("sideSymbol").asText()),
                encodedCard.get("points").asInt(), corners.get(0), corners.get(1), corners.get(2), corners.get(3));
        Side backResource = new CardBack(!Objects.equals(encodedCard.get("sideSymbol").asText(), "") ? Symbol.valueOf(encodedCard.get("sideSymbol").asText()) : null);
        return new ResourceCard(frontResource, backResource);
    }

    private StarterCard getStarterCard(JsonNode encodedCard) {
        ArrayList<Symbol> resources = new ArrayList<>();
        if (encodedCard.findValue("card") != null) {
            encodedCard = encodedCard.get("card");
        }
        for ( JsonNode resource : encodedCard.get("front").get("permanentResources")) {
            resources.add(Symbol.valueOf(resource.asText()));
        }
        ArrayList<Corner> corners = getCorners(encodedCard.get("front"));
        Side front = new StarterCardFront(resources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
        ArrayList<Corner> cornersBack = getCorners(encodedCard.get("back"));
        Side back = new CardBack(cornersBack.get(0), cornersBack.get(1), cornersBack.get(2), cornersBack.get(3));
        return new StarterCard(front, back);
    }

    private MissionCard getMissionCard(JsonNode encodedCard) {
        if (encodedCard.isEmpty()) { return null;}
        MissionCardFront missionCardFront = switch (encodedCard.get("cardType").asText()) {
            case "missionLPatter" ->
                    new MissionLPattern(encodedCard.get("type").asInt());
            case "missionDiagonalPatter" ->
                    new MissionDiagonalPattern(encodedCard.get("type").asInt());
            case "missionTriplet" ->
                    new MissionTripletPattern(encodedCard.get("type").asInt());
            case "missionItem" ->
                    new MissionItemPattern(encodedCard.get("type").asInt());
            default -> null;
        };
        return new MissionCard(missionCardFront, new CardBack());
    }

    private SimplifiedHand buildSimplifiedHand(JsonNode encodedHand) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < encodedHand.get("cards").size(); i++) {
            Card newCard = null;
            switch (encodedHand.get("cards").get(i).get("type").asText()) {
                case "goldCard":
                    newCard = getGoldCard(encodedHand.get("cards").get(i));
                    break;
                case "resourceCard":
                    newCard = getResourceCard(encodedHand.get("cards").get(i));
                    break;
                case "starterCard":
                    newCard = getStarterCard(encodedHand.get("cards").get(i));
                    break;
                case null, default:
                    break;
            }
            cards.add(newCard);
        }

        Card selectedCard = encodedHand.get("selectedCard").asInt() != -1 ? cards.get(encodedHand.get("selectedCard").asInt()) : null;
        Side selectedSide = null;
        if (encodedHand.get("selectedSide").asInt() != -1) {
            selectedSide = encodedHand.get("selectedSide").asInt() == 0 ? selectedCard.getFront(): selectedCard.getBack() ;
        }
        return new SimplifiedHand(cards, selectedCard, selectedSide);
    }

    private SimplifiedHand buildSimplifiedSecretHand(JsonNode encodedHand) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < encodedHand.get("cards").size(); i++) {
            cards.add(getMissionCard(encodedHand.get("cards").get(i).get("card")));
        }
        Card selectedCard = encodedHand.get("selectedCard").asInt() != -1 ? cards.get(encodedHand.get("selectedCard").asInt()) : null;
        Side selectedSide = null;
        if (encodedHand.get("selectedSide").asInt() != -1) {
            selectedSide = encodedHand.get("selectedSide").asInt() == 0 ? selectedCard.getFront(): selectedCard.getBack() ;
        }
        return new SimplifiedHand(cards, selectedCard, selectedSide);
    }

    private SimplifiedPersonalBoard buildPersonalBoard(JsonNode encodedBoard) {
        ArrayList<Point> playablePositions = new ArrayList<>();
        ArrayList<Point> blockedPositions = new ArrayList<>();
        Card secretMission = getMissionCard(encodedBoard.get("secretMission"));

        ArrayList<Point> occupiedPositions = buildArrayPosition(encodedBoard.get("occupiedPositions"));
        for( JsonNode occupiedPosition : encodedBoard.get("occupiedPositions")){
            occupiedPositions.add(new Point(occupiedPosition.get("X").asInt(), occupiedPosition.get("Y").asInt()));
        }

        for( JsonNode playablePosition : encodedBoard.get("playablePositions")){
            playablePositions.add(new Point(playablePosition.get("X").asInt(), playablePosition.get("Y").asInt()));
        }

        for( JsonNode blockedPosition : encodedBoard.get("blockedPositions")){
            blockedPositions.add(new Point(blockedPosition.get("X").asInt(), blockedPosition.get("Y").asInt()));
        }

        // visible resources
        Map<Symbol, Integer> visibleResources = new HashMap<>();
        Iterator<Map.Entry<String, JsonNode>> resourceIterator = encodedBoard.get("visibleResources").fields();
        while (resourceIterator.hasNext()) {
            Map.Entry<String, JsonNode> entry = resourceIterator.next();
            visibleResources.put(Symbol.valueOf(entry.getKey()), entry.getValue().asInt());
        }

        return new SimplifiedPersonalBoard(encodedBoard.get("xMin").asInt(),
                encodedBoard.get("xMax").asInt(), encodedBoard.get("yMax").asInt(), encodedBoard.get("yMin").asInt(),
                encodedBoard.get("score").asInt(), occupiedPositions, playablePositions, blockedPositions, secretMission,
                visibleResources, encodedBoard.get("selectedX").asInt(), encodedBoard.get("selectedY").asInt());
    }

    private SimplifiedPlayer buildSimplifiedPlayer(JsonNode encodedPlayer) {

        return new SimplifiedPlayer(encodedPlayer.get("ID").asText(),
                encodedPlayer.get("nickname").asText(),
                Pawn.valueOf(encodedPlayer.get("pawnColor").asText()),
                encodedPlayer.get("amIFirstPlayer").asBoolean(),
                PlayerState.valueOf(encodedPlayer.get("state").asText()));
    }

    private SimplifiedChat buildSimplifiedChat(JsonNode encodedChat) throws JsonProcessingException {
        ArrayList<Message> messages = new ArrayList<>();
        for (JsonNode encodedMessage :  encodedChat.get("messages")) {
            messages.add(new Message(encodedMessage.asText()));
        }
        return new SimplifiedChat(messages);
    }

    private ArrayList<Point> buildArrayPosition(JsonNode arrayNode) {
        ArrayList<Point> positions = new ArrayList<>();
        // each point
        for(JsonNode position : arrayNode){
            // flags
            Map<Integer, Boolean> flags = new HashMap<>();
            Iterator<Map.Entry<String, JsonNode>> resourceIterator = position.get("flags").fields();
            while (resourceIterator.hasNext()) {
                Map.Entry<String, JsonNode> entry = resourceIterator.next();
                flags.put(Integer.parseInt(entry.getKey()), entry.getValue().asBoolean());
            }
            // side
            Side side = null;
            ArrayList<Symbol> permanentResources = new ArrayList<>();
            Map<Symbol, Integer> requestedResources = new HashMap<>();
            if(!position.get("type").isNull()) {
                ArrayList<Corner> corners = getCorners(position.get("side"));
                switch (position.get("type").asText()) {
                    case "CardBack":
                        if (!position.get("side").get("permanentResources").isNull()) {
                            permanentResources.add(Symbol.valueOf(position.get("side").get("permanentResources").toString()));
                        }
                        side = new CardBack(position.get("side").get("sideSymbol").asText().equals("") ? null : Symbol.valueOf(position.get("side").get("sideSymbol").asText()),
                                corners.get(0), corners.get(1), corners.get(2), corners.get(3),
                                permanentResources, new HashMap<>());
                        break;
                    case "StarterCardFront":
                        side = new StarterCardFront(permanentResources,
                                corners.get(0), corners.get(1), corners.get(2), corners.get(3));
                        break;
                    case "ResourceCardFront":
                        side = new ResourceCardFront(!Objects.equals(position.get("side").get("DOWNRIGHT").get("symbol").asText(), "") ?  Symbol.valueOf(position.get("side").get("DOWNRIGHT").get("symbol").asText()) : null,
                                position.get("side").get("points").asInt(),
                                corners.get(0), corners.get(1), corners.get(2), corners.get(3));
                        break;
                    case "CornerCounter":
                        side = new CornerCounter(!Objects.equals(position.get("side").get("DOWNRIGHT").get("symbol").asText(), "") ?  Symbol.valueOf(position.get("side").get("DOWNRIGHT").get("symbol").asText()) : null,
                                requestedResources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
                        break;
                    case "QuillCounter":
                        side = new QuillCounter(!Objects.equals(position.get("side").get("DOWNRIGHT").get("symbol").asText(), "") ?  Symbol.valueOf(position.get("side").get("DOWNRIGHT").get("symbol").asText()) : null,
                                requestedResources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
                        break;
                    case "InkwellCounter":
                        side = new InkwellCounter(!Objects.equals(position.get("side").get("DOWNRIGHT").get("symbol").asText(), "") ?  Symbol.valueOf(position.get("side").get("DOWNRIGHT").get("symbol").asText()) : null,
                                requestedResources, corners.get(0), corners.get(1), corners.get(2), corners.get(3));
                        break;
                    case "ManuscriptCounter":
                        side = new ManuscriptCounter(!Objects.equals(position.get("side").get("DOWNRIGHT").get("symbol").asText(), "") ?  Symbol.valueOf(position.get("side").get("DOWNRIGHT").get("symbol").asText()) : null,
                                requestedResources,
                                corners.get(0), corners.get(1), corners.get(2), corners.get(3));
                        break;
                    case null, default:
                        break;
                }
            }

            // x coordinate and Y coordinate
            positions.add(new Point(position.get("X").asInt(), position.get("Y").asInt(), flags, side));
        }
        return positions;
    }

    private ArrayList<Corner> getCorners(JsonNode side) {
        ArrayList<Corner> corners = new ArrayList<>();
        corners.add(new Corner(Boolean.parseBoolean(side.get("UPLEFT").get("isEvil").asText()),
                !Objects.equals(side.get("UPLEFT").get("symbol").asText(), "") ? Symbol.valueOf(side.get("UPLEFT").get("symbol").asText()) : null,
                side.get("UPLEFT").get("isHidden").asBoolean()));
        corners.add(new Corner(Boolean.parseBoolean(side.get("DOWNLEFT").get("isEvil").asText()),
                        !Objects.equals(side.get("DOWNLEFT").get("symbol").asText(), "") ? Symbol.valueOf(side.get("DOWNLEFT").get("symbol").asText()) : null,
                side.get("DOWNLEFT").get("isHidden").asBoolean()));
        corners.add(new Corner(Boolean.parseBoolean(side.get("UPRIGHT").get("isEvil").asText()),
                        !Objects.equals(side.get("UPRIGHT").get("symbol").asText(), "") ? Symbol.valueOf(side.get("UPRIGHT").get("symbol").asText()) : null,
                side.get("UPRIGHT").get("isHidden").asBoolean()));
        corners.add(new Corner(Boolean.parseBoolean(side.get("DOWNRIGHT").get("isEvil").asText()),
                        !Objects.equals(side.get("DOWNRIGHT").get("symbol").asText(), "") ?  Symbol.valueOf(side.get("DOWNRIGHT").get("symbol").asText()) : null,
                side.get("DOWNRIGHT").get("isHidden").asBoolean()));
        return corners;
    }
}
