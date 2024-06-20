package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualView;
import java.io.BufferedWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;


/**
 * This class represents the game controller for the socket implementation
 */
public class VirtualSocketGameController implements VirtualGameController {

    /**
     * This attribute represent the print writer to send json to the server
     */
    private final BufferedWriter outputToServer;

    /**
     * Virtual socket game controller's constructor. Initializes the print writer.
     *
     * @param output writer
     */
    public VirtualSocketGameController(BufferedWriter output) {
        this.outputToServer = new BufferedWriter(output);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param color    color chosen
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
     * Encodes the parameters to play this function in the real controller.
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
     *
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
     *
     * @param cardIndex card index in the hand
     * @param playerID  client's ID
     */
    @Override
    public void selectCardFromHand(int cardIndex, String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "selectCardFromHand");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("cardIndex", String.valueOf(cardIndex));
        msg.put("playerID", playerID);
        writeToServer(data, msg);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param playerID client's ID
     */
    @Override
    public void turnSelectedCardSide(String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "turnSelectedCardSide");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        writeToServer(data, value);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param x        coordinate X in the board
     * @param y        coordinate Y in the board
     * @param playerID client's ID
     */
    @Override
    public void selectPositionOnBoard(int x, int y, String playerID) throws RemoteException {
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
     *
     * @param playerID client's ID
     */
    @Override
    public void playCardFromHand(String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "playCardFromHand");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        writeToServer(data, value);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param cardIndex position of the selected card on common table
     * @param playerID client's ID
     */
    @Override
    public void selectCardFromCommonTable(int cardIndex, String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "selectCardFromCommonTable");
        HashMap<String, String> value = new HashMap<>();
        value.put("cardIndex", String.valueOf(cardIndex));
        value.put("playerID", playerID);
        writeToServer(data, value);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param playerID client's ID
     * @throws RemoteException
     */
    @Override
    public void drawSelectedCard(String playerID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "drawSelectedCard");
        HashMap<String, String> value = new HashMap<>();
        value.put("playerID", playerID);
        writeToServer(data, value);

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param line             text to be delivered
     * @param nicknameReceiver
     * @param senderID         client's ID
     * @param time             current time
     */
    @Override
    public void addMessage(String line, String nicknameReceiver, String senderID, String time) throws RemoteException {
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
     * This method sends writes the message encoded with the protocol in the print writer to the server.
     *
     * @param data     base message with the correct function name
     * @param valueMsg data associated to the value key
     */
    private void writeToServer(HashMap<String, String> data, HashMap<String, String> valueMsg) throws RemoteException{
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(valueMsg));
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToServer.write(mappedData.writeValueAsString(data));
            this.outputToServer.newLine();
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw  new RemoteException();
        }
    }

    /**
     * Readds the virtual view after the server has gone down, because the connection must be recreated
     *
     * @param view client's view
     * @param clientID client's original ID
     * @throws RemoteException if the remote object cannot be called
     */
    @Override
    public void reAddView(VirtualView view, String clientID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketGameController.getBaseMessage();
        data.replace("function", "reAddView");
        HashMap<String, String> value = new HashMap<>();
        value.put("clientID", clientID);
        writeToServer(data, value);
    }


}