package it.polimi.ingsw.gc26.network.socket.client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.network.VirtualGameController;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;


/**
 * This class represents the game controller for the socket implementation
 */
public class VirtualSocketGameController implements VirtualGameController {

    /**
     * This attribute represent the print writer to send json to the server
     */
    private final PrintWriter outputToServer;

    /**
     * Virtual socket game controller's constructor. Initializes the print writer.
     * @param output writer
     */
    public VirtualSocketGameController(PrintWriter output) {
        this.outputToServer = new PrintWriter(output);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param color color chosen
     * @param playerID player's ID
     * @throws RemoteException
     */
    @Override
    public void choosePawnColor(String color, String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "choosePawnColor");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("color", color);
        msg.put("playerID", playerID);
        writeToServer(data, msg);

    }

    /**
     *
     * @param cardIndex
     * @param playerID
     * @throws RemoteException
     */
    @Override
    public void selectSecretMission(int cardIndex, String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "selectSecretMission");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("cardIndex", String.valueOf(cardIndex));
        msg.put("playerID", playerID);
        writeToServer(data, msg);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param playerID client's ID
     * @throws RemoteException
     */
    @Override
    public void setSecretMission(String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "setSecretMission");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("playerID", playerID);
        writeToServer(data, msg);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param cardIndex card index in the hand
     * @param playerID client's ID
     */
    @Override
    public void selectCardFromHand(int cardIndex, String playerID) {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "selectCardFromHand");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("cardIndex", String.valueOf(cardIndex));
        msg.put("playerID", playerID);
        writeToServer(data, msg);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param playerID client's ID
     */
    @Override
    public void turnSelectedCardSide(String playerID) {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "turnSelectedCardSide");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        writeToServer(data, value);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param x coordinate X in the board
     * @param y coordinate Y in the board
     * @param playerID client's ID
     */
    @Override
    public void selectPositionOnBoard(int x, int y, String playerID) {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "selectPositionOnBoard");
        HashMap<String, String> value = new HashMap<>();
        value.put("x", String.valueOf(x));
        value.put("y", String.valueOf(y));
        value.put("playerID", playerID);
        writeToServer(data, value);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param playerID client's ID
     */
    @Override
    public void playCardFromHand(String playerID) {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "playCardFromHand");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        writeToServer(data, value);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param cardX coordinate X on the common table
     * @param cardY coordinate Y on the common table
     * @param playerID client's ID
     */
    @Override
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID) {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "selectCardFromCommonTable");
        HashMap<String, String> value = new HashMap<>();
        value.put("cardX", String.valueOf(cardX));
        value.put("cardY", String.valueOf(cardY));
        value.put("playerID", playerID);
        writeToServer(data, value);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param playerID client's ID
     */
    @Override
    public void drawSelectedCard(String playerID) {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "drawSelectedCard");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        writeToServer(data, value);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param line text to be delivered
     * @param nicknameReceiver
     * @param senderID client's ID
     * @param time current time
     */
    @Override
    public void addMessage(String line, String nicknameReceiver,String senderID, String time)  {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "addMessage");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("text", line);
        msg.put("receiver", nicknameReceiver);
        msg.put("sender", senderID);
        msg.put("time", time);
        writeToServer(data, msg);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param nickname nickname of the player who owns the board
     * @param playerID client's ID
     * @throws RemoteException
     */
    @Override
    public void printPersonalBoard(String nickname, String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "printPersonalBoard");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("nickname", nickname);
        msg.put("playerID", playerID);
        writeToServer(data, msg);
    }

    /**
     * This method creates the basic structure for this protocol.
     * @return base structure
     */
    private static HashMap<String, String> getBaseMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    /**
     * This method sends writes the message encoded with the protocol in the print writer to the server.
     * @param data base message with the correct function name
     * @param valueMsg data associated to the value key
     */
    private void writeToServer(HashMap<String, String> data, HashMap<String, String> valueMsg) {
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(valueMsg));
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}