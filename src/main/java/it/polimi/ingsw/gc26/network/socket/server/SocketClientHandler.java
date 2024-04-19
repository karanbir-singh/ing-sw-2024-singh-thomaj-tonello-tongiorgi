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
        /*ArrayList<Player> players = new ArrayList<Player>();
        players.add(new Player("0", "j"));
        this.gameController = new GameController(new Game(players));*/
        this.connect();
        while ((line = inputFromClient.readLine()) != null) {
            if (this.gameController != null) {
                JsonNode msg;
                ObjectMapper JsonMapper = new ObjectMapper();
                msg = JsonMapper.readTree(line);
                switch (msg.get("function").textValue()) {
                    case "addMessage":
                        Message newMessage = new Message(msg.get("value").asText());
                        this.addMessage(newMessage.getText(), newMessage.getReceiver().getNickname(), newMessage.getSender().getNickname(), LocalTime.now().toString());
                        break;
                    case "selectCardFromHand":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.selectCardFromHand(msg.get("cardIndex").asInt(), msg.get("playerID").asText());
                        break;
                    case "turnSelectedCardSide":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.turnSelectedCardSide(msg.get("playerID").asText());
                        break;
                    case "selectPositionOnBoard":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.selectPositionOnBoard(msg.get("x").asInt(), msg.get("y").asInt(), msg.get("playerID").asText());
                        break;
                    case "playCardFromHand":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.playCardFromHand(msg.get("playerID").asText());
                        break;
                    case "selectCardFromCommonTable":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.selectCardFromCommonTable(msg.get("cardX").asInt(), msg.get("cardY").asInt(), msg.get("playerID").asText());
                        break;
                    case "drawSelectedCard":
                        JsonMapper = new ObjectMapper();
                        msg = JsonMapper.readTree(msg.get("value").asText());
                        this.drawSelectedCard(msg.get("playerID").asText());
                        break;
                    case null, default:
                        break;
                }
            } else {
                this.virtualClient.reportError("The game is being initialized! Please wait!");
            }

        }
    }

    @Override
    public void choosePawnColor(String color, String playerID) throws RemoteException {

    }

    @Override
    public void selectSecretMission(int cardIndex, String playerID) throws RemoteException {

    }

    @Override
    public void setSecretMission(String playerID) throws RemoteException {

    }

    @Override
    public void selectCardFromHand(int cardIndex, String playerID) throws RemoteException {
        this.gameController.selectCardFromHand(cardIndex, playerID);
    }

    @Override
    public void turnSelectedCardSide(String playerID) throws RemoteException {
        this.gameController.turnSelectedCardSide(playerID);
    }

    @Override
    public void selectPositionOnBoard(int x, int y, String playerID) throws RemoteException {
        this.gameController.selectPositionOnBoard(x, y, playerID);

    }

    @Override
    public void playCardFromHand(String playerID) throws RemoteException {
        this.gameController.playCardFromHand(playerID);

    }

    @Override
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID) throws RemoteException {
        this.gameController.selectCardFromCommonTable(cardX, cardY, playerID);
    }

    @Override
    public void drawSelectedCard(String playerID) throws RemoteException {
        this.gameController.drawSelectedCard(playerID);
    }

    @Override
    public void addMessage(String line, String nicknameReceiver,String nicknameSender, String time) throws RemoteException {
        //System.out.println(msg);
        this.gameController.addMessage(line, nicknameReceiver, nicknameSender, time);
        //this.server.broadCastUpdate(msg, this);
        //this.virtualClient.showMessage(message);

    }

    @Override
    public void printPersonalBoard(String nickname, String playerID) throws RemoteException {

    }

    public void connect() throws IOException {
        while (this.nickname == null) {
            this.virtualClient.reportMessage("Insert your nickname: ");
            String nick = this.inputFromClient.readLine();
            if (this.mainController.getWaitingPlayer().stream().noneMatch(n1 -> n1.getNickname().equals(nick))) {
                this.nickname = nick;
            }
        }
        if (this.mainController.existsWaitingGame())  {
            GameController controller ;
            if ((controller = this.mainController.joinWaitingList(UUID.randomUUID().toString(), this.nickname)) != null) {
                this.server.setGameController(controller);
                this.server.broadCastReport("The game is ready!", this);
            }

        } else {
            this.virtualClient.reportMessage("Insert the number of players: ");
            String numberOfPlayers = this.inputFromClient.readLine();
            this.mainController.createWaitingList(Integer.parseInt(numberOfPlayers),UUID.randomUUID().toString(), this.nickname );
        }
        if (this.gameController == null) {
            this.virtualClient.reportMessage("Waiting for other players ... ");

        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

}
