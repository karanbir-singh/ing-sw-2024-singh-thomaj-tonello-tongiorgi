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

/**
 * This class represents the virtual main controller object with the methods that can be called from the client.
 * This implementation is for RMI network connection.
 */
public class VirtualRMIMainController implements VirtualMainController {
    /**
     * This attribute represents the main controller used in the joining process.
     */
    private final MainController mainController;

    /**
     * Virtual controller's constructor. It exports the remote object.
     *
     * @param mainController unique main controller in the server.
     * @throws RemoteException if the remote object cannot be called
     */
    public VirtualRMIMainController(MainController mainController) throws RemoteException {
        this.mainController = mainController;
        UnicastRemoteObject.exportObject(this, 0);
    }

    /**
     * Joins the new client to an existing game if there's any game with available player,
     * otherwise it set the client's state to creator.
     *
     * @param client      Virtual view representing the client
     * @param nickname    client's nickname
     * @param clientState client's current state
     * @throws RemoteException if the remote method cannot be called
     */
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

    /**
     * Initializes the waiting list of players and updating max numbers of players for the next game
     *
     * @param client     Virtual view representing the client
     * @param nickname   nickname of the player who is initializing the waiting list
     * @param numPlayers number of players of the next game
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public void createWaitingList(VirtualView client, String nickname, int numPlayers) throws RemoteException {
        this.mainController.addRequest(new GameCreationRequest(client, nickname, numPlayers, 1));
    }

    /**
     * Returns the game controller if exists.
     *
     * @param id unique identifier for the game
     * @return virtual game controller
     * @throws RemoteException if the remote method cannot be called
     */
    @Override
    public VirtualGameController getVirtualGameController(int id) throws RemoteException {
        return new VirtualRMIGameController(this.mainController.getGameController(id));
    }

    @Override
    public void resetServerTimer(String clientID) {
        this.mainController.resetServerTimer(clientID);
    }
}
