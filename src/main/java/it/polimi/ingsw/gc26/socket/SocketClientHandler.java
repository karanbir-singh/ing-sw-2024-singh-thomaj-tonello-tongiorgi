package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

import it.polimi.ingsw.gc26.MainController;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;

/**
 * logica con cui vado a leggere i messaggi, Fa parte del server
 */
public class SocketClientHandler implements VirtualServer {
    final MainController mainController;
    GameController gameController = null;
    final SocketServer server;
    final BufferedReader inputFromClient;
    final VirtualSocketClient virtualClient;
    String nickname = null;

    public SocketClientHandler(MainController controller, SocketServer server, BufferedReader input, PrintWriter output) {
        this.mainController = controller;
        this.server = server;
        this.inputFromClient = input;
        this.virtualClient = new VirtualSocketClient(output);
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
                        this.addMessage(msg.get("value").asText());
                        break;
                    case "":
                        //do something
                }
            } else {
                this.virtualClient.reportError("The game is being initialized! Please wait!");
            }

        }
    }

    @Override
    public void selectCardFromHand(Card card) {
        this.gameController.selectCardFromHand(0, "");
    }

    @Override
    public void turnSelectedCardSide() {

    }

    @Override
    public void selectPositionOnBoard(int x, int y) {

    }

    @Override
    public void playCardFromHand() {

    }

    @Override
    public void selectCardFromCommonTable(Card card) {

    }

    @Override
    public void drawSelectedCard() {

    }

    @Override
    public void addMessage(String message) {
        Message msg = null;
        try {
            msg = new Message(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(msg);
        this.gameController.addMessage(msg);
        this.server.broadCastUpdate(msg, this);
        //this.virtualClient.showMessage(message);

    }

    @Override
    public void sendText(String text) {

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
