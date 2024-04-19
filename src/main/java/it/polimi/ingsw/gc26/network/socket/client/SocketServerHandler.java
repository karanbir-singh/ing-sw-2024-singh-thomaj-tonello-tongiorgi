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
    BufferedReader inputFromServer;
    ClientState clientState;

    public SocketServerHandler(BufferedReader inputFromServer ) {
        this.inputFromServer = inputFromServer;
        clientState = ClientState.CONNECTION;
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

        System.out.println("[" + message.get("sender").asText() + "]: " + message.get("text").asText());
    }

    @Override
    public void reportMessage(String message) {
        System.out.println("[SERVER]: " + message);
    }

    @Override
    public void reportError(String errorMessage) {
        System.out.println("[ERROR]: " + errorMessage);
    }

    public void updateState(ClientState clientState) throws RemoteException {
        this.clientState = clientState;
    }



}
