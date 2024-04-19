package it.polimi.ingsw.gc26.network.socket.client;

import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;

public class VirtualSocketMainController implements VirtualMainController {
    private final PrintWriter outputToServer;

    public VirtualSocketMainController(BufferedWriter output) {
        this.outputToServer = new PrintWriter(output);
    }

    @Override
    public String connect(VirtualView client, String nickName) throws RemoteException {
        return "";
    }

    @Override
    public void createWaitingList(VirtualView client, String clientID, String nickname, int numPlayers) throws RemoteException {

    }

    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        return null;
    }
}


