package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * This class represents the main controller for the socket implementation
 */
public class VirtualSocketMainController implements VirtualMainController {
    /**
     * This attribute represent the print writer to send json to the server
     */
    private final PrintWriter outputToServer;

    /**
     * Virtual socket main controller's constructor. Initializes the print writer.
     *
     * @param output
     */
    public VirtualSocketMainController(PrintWriter output) {
        this.outputToServer = new PrintWriter(output);
    }

    /**
     * This method creates the json encoding to call in the server's main controller the connect method
     *
     * @param client   virtualView
     * @param nickName client's nickname
     * @return clientID
     * @throws RemoteException
     */
    @Override
    public void connect(VirtualView client, String nickName, ClientState clientState) throws RemoteException {
        HashMap<String, String> data = VirtualSocketMainController.getBaseMessage();
        data.replace("function", "connect");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("nickname", nickName);
        msg.put("clientState", clientState.toString());
        writeToServer(data, msg);
    }

    /**
     * This method creates the json encoding to call in the server's main controller the createWaitingList method
     *
     * @param client
     * @param nickname
     * @param numPlayers number of players in a game
     * @throws RemoteException
     */
    @Override
    public void createWaitingList(VirtualView client, String nickname, int numPlayers) throws RemoteException {
        HashMap<String, String> data = VirtualSocketMainController.getBaseMessage();
        data.replace("function", "createWaitingList");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("nickname", nickname);
        msg.put("numPlayers", String.valueOf(numPlayers));
        writeToServer(data, msg);
    }

    /**
     * This method creates the json encoding to call in the server's main controller the getVirtualGameController method
     *
     * @return game controller
     * @throws RemoteException
     */
    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        HashMap<String, String> data = VirtualSocketMainController.getBaseMessage();
        data.replace("function", "getVirtualGameController");
        writeToServer(data, new HashMap<>());
        return null;
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

    @Override
    public String amAlive(){
        return "bho";
    }


}


