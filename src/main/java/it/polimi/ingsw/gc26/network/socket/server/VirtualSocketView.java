package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;

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
        sendToClient("setGameController", null);
    }

    /**
     * @param simplifiedCommonTable
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) throws RemoteException {

    }

    /**
     * @param simplifiedHand
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateHand(SimplifiedHand simplifiedHand, String message) throws RemoteException {

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
    public void updatePersonalBoard(PersonalBoard personalBoard, String message) throws RemoteException {

    }

    /**
     * @param otherPersonalBoard
     * @param message
     * @throws RemoteException
     */
    @Override
    public void updateOtherPersonalBoard(PersonalBoard otherPersonalBoard, String message) throws RemoteException {

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

}
