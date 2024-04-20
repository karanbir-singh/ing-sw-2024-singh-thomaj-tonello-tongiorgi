package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;

public class VirtualSocketMainController implements VirtualMainController {
    private final PrintWriter outputToServer;

    public VirtualSocketMainController(BufferedWriter output) {
        this.outputToServer = new PrintWriter(output);
    }

    @Override
    public String connect(VirtualView client, String nickName) throws RemoteException {
        HashMap<String, String> data = VirtualSocketMainController.getBasicMessage();
        data.replace("function", "connect");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("nickname", nickName);
        writeToServer(data, msg);
        return null;
    }

    @Override
    public void createWaitingList(VirtualView client, String clientID, String nickname, int numPlayers) throws RemoteException {
        HashMap<String, String> data = VirtualSocketMainController.getBasicMessage();
        data.replace("function", "createWaitingList");
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        msg.put("nickname", nickname);
        msg.put("numPlayers", String.valueOf(numPlayers));
        writeToServer(data, msg);
    }

    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        HashMap<String, String> data = VirtualSocketMainController.getBasicMessage();
        data.replace("function", "getVirtualGameController");
        writeToServer(data, new HashMap<>());
        return null;
    }

    private static HashMap<String, String> getBasicMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    private void writeToServer(HashMap<String, String> data, HashMap<String, String> msg) {
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(msg));
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


}


