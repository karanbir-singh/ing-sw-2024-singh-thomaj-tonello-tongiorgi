package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;
import java.io.BufferedReader;
import java.io.IOException;
import java.rmi.RemoteException;


/**
 * This class represents the handler to decode json from the server.
 */
public class SocketServerHandler implements VirtualView {
    /**
     * This attribute represent the socket client where the nickname and clientID are stored
     */
    private final SocketClient socketClient;
    /**
     * This attributes represents the input from the server.
     */
    private BufferedReader inputFromServer;
    /**
     * This enumeration represents the client's state.
     */
    protected ClientState clientState;
    /**
     * Flag to check if the server has updated the client's state.
     */
    protected boolean changeState;

    /**
     * Socket server handler's constructor. It initializes the class in order to read json from the server.
     * @param client Socket client
     * @param inputFromServer Buffered reader to read json to the server
     */
    public SocketServerHandler(SocketClient client, BufferedReader inputFromServer ) {
        this.socketClient = client;
        this.inputFromServer = inputFromServer;
        clientState = ClientState.CONNECTION;
        changeState = false;
    }

    /**
     * This method is launched from the SocketClient and listens to the server
     * When a new message is received, it is decoded and the correct method is executed
     * @throws IOException
     */
    public void onServerListening() throws IOException {
        String line;
        while((line = inputFromServer.readLine()) != null) {
            JsonNode root = null;
            try {
                ObjectMapper JsonMapper = new ObjectMapper();
                root = JsonMapper.readTree(line);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }

            switch (root.get("function").asText()) {
                case "setClientID":
                    this.setClientID(root.get("value").asText());
                    break;
                case "setGameController":
                    this.setGameController();
                    break;
                case "updateState":
                    this.updateState(ClientState.valueOf(root.get("value").asText()));
                    break;
                case "showMessage":
                    this.showMessage(root.get("value").asText());
                    break;
                case "reportMessage":
                    this.reportMessage(root.get("value").asText());
                    break;
                case "reportError":
                    this.reportError(root.get("value").asText());
                    break;
                case null, default:
                    break;
            }
        }
    }

    /**
     * Shows a message in the chat
     * @param line json encoded message
     */
    @Override
    public void showMessage(String line) {
        JsonNode message = null;
        try {
            ObjectMapper JsonMapper = new ObjectMapper();
            message = JsonMapper.readTree(line);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println(STR."[\{message.get("sender").asText()}]: \{message.get("text").asText()}");
    }

    /**
     * Reports a message from the server (for example error reports)
     * @param message
     */
    @Override
    public void reportMessage(String message) {
        System.out.println(STR."[SERVER]: \{message}");
    }

    /**
     * Reports an error message from the server
     * @param errorMessage
     */
    @Override
    public void reportError(String errorMessage) {
        System.out.println(STR."[ERROR]: \{errorMessage}");
    }

    /**
     * Updates client's state
     * @param clientState
     * @throws RemoteException
     */
    public void updateState(ClientState clientState) throws RemoteException {
        synchronized (this.clientState) {
            this.clientState = clientState;
            this.changeState = true;
        }
    }

    /**
     * Sets the client ID returned by the connect method in the controller
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        synchronized (this.socketClient.clientID) {
            this.socketClient.setClientID(clientID);
        }
    }

    /**
     * Sets the game controller when the game is ready to start playing.
     */
    @Override
    public void setGameController() {
        this.socketClient.setVirtualGameController();
    }


}
