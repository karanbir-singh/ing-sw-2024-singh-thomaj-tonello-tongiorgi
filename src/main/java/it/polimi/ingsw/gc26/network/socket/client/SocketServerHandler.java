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
import it.polimi.ingsw.gc26.ClientState;

public class SocketServerHandler implements VirtualView {
    private final SocketClient socketClient;
    private BufferedReader inputFromServer;
    protected ClientState clientState;
    protected boolean changeState;

    public SocketServerHandler(SocketClient client, BufferedReader inputFromServer ) {
        this.socketClient = client;
        this.inputFromServer = inputFromServer;
        clientState = ClientState.CONNECTION;
        changeState = false;
    }

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
            }
        }
    }

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

    @Override
    public void reportMessage(String message) {
        System.out.println(STR."[SERVER]: \{message}");
    }

    @Override
    public void reportError(String errorMessage) {
        System.out.println(STR."[ERROR]: \{errorMessage}");
    }

    public void updateState(ClientState clientState) throws RemoteException {
        synchronized (this.clientState) {
            this.clientState = clientState;
            this.changeState = true;
        }
    }

    @Override
    public void setClientID(String clientID) throws RemoteException {
        synchronized (this.socketClient.clientID) {
            this.socketClient.setClientID(clientID);
        }
    }

    @Override
    public void setGameController() {
        this.socketClient.setVirtualGameController();
    }


}
