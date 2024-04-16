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
    public String connect(VirtualView client, String nickname) throws RemoteException {
        return "";
    }

    @Override
    public boolean existsWaitingGame() throws RemoteException {
        return false;
    }

    @Override
    public void createWaitingList(int numPlayers, String playerID, String playerNickname) throws RemoteException {

    }

    @Override
    public void joinWaitingList(String playerID, String playerNickname) throws RemoteException {

    }

    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        return null;
    }
}


