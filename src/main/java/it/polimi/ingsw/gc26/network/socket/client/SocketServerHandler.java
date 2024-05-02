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
public class SocketServerHandler implements VirtualView, Runnable {
    /**
     * This attribute represent the socket client where the nickname and clientID are stored
     */
    private final SocketClient socketClient;
    /**
     * This attributes represents the input from the server.
     */
    private BufferedReader inputFromServer;

    /**
     * Socket server handler's constructor. It initializes the class in order to read json from the server.
     *
     * @param socketClient    Socket client
     * @param inputFromServer Buffered reader to read json to the server
     */
    public SocketServerHandler(SocketClient socketClient, BufferedReader inputFromServer) {
        this.socketClient = socketClient;
        this.inputFromServer = inputFromServer;
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

                switch (msg.get("function").asText()) {
                    case "setClientID":
                        this.setClientID(msg.get("value").asText());
                        break;
                    case "setGameController":
                        this.setGameController();
                        break;
                    case "updateState":
                        this.updateState(ClientState.valueOf(msg.get("value").asText()));
                        break;
                    case "notifyMessage":
                        this.notifyMessage(msg.get("value").asText());
                        break;
                    case "reportMessage":
                        this.reportMessage(msg.get("value").asText());
                        break;
                    case "reportError":
                        this.reportError(msg.get("value").asText());
                        break;
                    case null, default:
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows a message in the chat
     *
     * @param line json encoded message
     */
    @Override
    public void notifyMessage(String line) {
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
     *
     * @param message
     */
    @Override
    public void reportMessage(String message) {
        System.out.println(STR."[SERVER]: \{message}");
    }

    /**
     * Reports an error message from the server
     *
     * @param errorMessage
     */
    @Override
    public void reportError(String errorMessage) {
        System.out.println(STR."[ERROR]: \{errorMessage}");
    }

    /**
     * Updates client's state
     *
     * @param clientState
     * @throws RemoteException
     */
    public void updateState(ClientState clientState) throws RemoteException {
        synchronized (this.socketClient.lock) {
            this.socketClient.setState(clientState);
            this.socketClient.lock.notifyAll();
        }

    }

    /**
     * Sets the client ID returned by the connect method in the controller
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        synchronized (this.socketClient) {
            this.socketClient.setClientID(clientID);
            this.socketClient.notifyAll();
        }
    }

    /**
     * Sets the game controller when the game is ready to start playing.
     */
    @Override
    public void setGameController() {
        synchronized (this.socketClient) {
            this.socketClient.setVirtualGameController();
            this.socketClient.notifyAll();
        }

    }

    @Override
    public ClientState getState() throws RemoteException {
        return null;
    }
}
