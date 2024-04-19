package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualRMIMainController implements VirtualMainController {
    private final MainController mainController;

    public VirtualRMIMainController(MainController mainController) throws RemoteException {
        this.mainController = mainController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String connect(VirtualView client, String nickname) throws RemoteException {
        System.out.println("New client from RMI!");
        return this.mainController.connect(client, nickname);
    }

    @Override
    public void createWaitingList(VirtualView client, String clientID, String nickname, int numPlayers) throws RemoteException {
        this.mainController.createWaitingList(client, clientID, nickname, numPlayers);
    }

    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        return new VirtualRMIGameController(this.mainController.getGameController());
    }
}
