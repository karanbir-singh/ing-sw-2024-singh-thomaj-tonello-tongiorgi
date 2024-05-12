package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.main_request.ConnectionRequest;
import it.polimi.ingsw.gc26.request.main_request.GameCreationRequest;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VirtualRMIMainController implements VirtualMainController {
    private final MainController mainController;

    public VirtualRMIMainController(MainController mainController) throws RemoteException {
        this.mainController = mainController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void connect(VirtualView client, String nickname, ClientState clientState) throws RemoteException {
        System.out.println("New client from RMI!");
        if (clientState == ClientState.CONNECTION)
            this.mainController.addRequest(new ConnectionRequest(client, nickname, 0));
        else if (clientState.equals(ClientState.INVALID_NICKNAME)) {
            client.updateClientState(ClientState.CONNECTION);
            this.mainController.addRequest(new ConnectionRequest(client, nickname, 2));
        }
    }

    @Override
    public void createWaitingList(VirtualView client, String nickname, int numPlayers) throws RemoteException {
        this.mainController.addRequest(new GameCreationRequest(client, nickname, numPlayers, 1));
    }

    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        return new VirtualRMIGameController(this.mainController.getGameController());
    }

}
