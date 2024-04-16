package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class VirtualRMIMainController implements VirtualMainController {
    // This attributes is only for testing now
    private final ArrayList<VirtualGameController> virtualGameControllers;

    private final MainController mainController;

    public VirtualRMIMainController(MainController mainController) throws RemoteException {
        this.mainController = mainController;
        this.virtualGameControllers = new ArrayList<>();
        UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String connect(VirtualView client, String nickname) throws RemoteException {
        String clientID = UUID.randomUUID().toString();

        if(this.existsWaitingGame()){
            this.joinWaitingList(clientID,nickname);
            System.out.println("Connected to waiting game");
        }else{
            // TODO notificare il client che non esistono partite e quindi di chiamare la createWaitingList
            System.out.println("Creating waiting game");
        }
        return clientID;
    }

    @Override
    public boolean existsWaitingGame() throws RemoteException {
        return this.mainController.existsWaitingGame();
    }

    @Override
    public void createWaitingList(int numPlayers, String playerID, String playerNickname) throws RemoteException {
        this.mainController.createWaitingList(numPlayers, playerID, playerNickname);
    }

    @Override
    public void joinWaitingList(String playerID, String playerNickname) throws RemoteException {
        GameController gameController = this.mainController.joinWaitingList(playerID, playerNickname);
        if (gameController == null) {
            // Here clients are still waiting
            System.out.printf("CLIENT JOINED WAITING LIST\n");
        } else {
            VirtualGameController virtualGameController = new VirtualRMIGameController(gameController);
            virtualGameControllers.add(virtualGameController);
            System.out.println("Virtual Game Controller created, client can use getVirtual game controller");

            // Here we have to give/notify clients about
            // For now we use a getVirtualGameController
            // TODO gestisci come fare con la lista dei client e client in waiting
        }
    }

    // This is for testing
    @Override
    public VirtualGameController getVirtualGameController() {
        if (!virtualGameControllers.isEmpty())
            return virtualGameControllers.getFirst();
        return null;
    }
}
