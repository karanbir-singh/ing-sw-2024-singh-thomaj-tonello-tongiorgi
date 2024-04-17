package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.StateClient;
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
    private final ArrayList<VirtualView> clients;

    private final MainController mainController;

    public VirtualRMIMainController(MainController mainController) throws RemoteException{
            this.mainController = mainController;
            this.virtualGameControllers = new ArrayList<>();
            this.clients = new ArrayList<>();
            UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public String connect(VirtualView client, String nickname){
        String clientID = UUID.randomUUID().toString();
        try{
            mainController.addView(client)
            clients.add(client);
            if(this.existsWaitingGame()){
                this.joinWaitingList(clientID,nickname);
                System.out.println("Connected to waiting game");
                //client.updateState(StateClient.WAITING_GAME); //go to this state

                //TODO notificare il client che è entrato in partita
            }else{
                client.updateState(StateClient.CREATION_GAME);
                // TODO notificare il client che non esistono partite e quindi di chiamare la createWaitingList
                System.out.println("Creating waiting game");
            }
        }catch (RemoteException e){
            e.printStackTrace();
        }

        return clientID;
    }

   @Override
   public boolean existsWaitingGame() throws RemoteException{
        return this.mainController.existsWaitingGame();
   }

    @Override
    public void createWaitingList(int numPlayers, String playerID, String playerNickname) throws RemoteException {
        this.mainController.createWaitingList(numPlayers, playerID, playerNickname);//sta roba andra sincronizzata nel client
        clients.getFirst().updateState(StateClient.WAITING_GAME);
        //TODO notificare il client che ora è nello stato di waiting
    }

    @Override
    public void joinWaitingList(String playerID, String playerNickname) throws RemoteException {
        GameController gameController = this.mainController.joinWaitingList(playerID, playerNickname);
        if (gameController == null) {
            // Here clients are still waiting
            clients.getLast().updateState(StateClient.WAITING_GAME);
        } else {
            VirtualGameController virtualGameController = new VirtualRMIGameController(gameController);
            virtualGameControllers.add(virtualGameController);
            System.out.println("Virtual Game Controller created, client can use getVirtual game controller");
            for(VirtualView client : clients){
                client.updateState(StateClient.BEGIN);
            }
            clients.clear();
            // Here we have to give/notify clients about
            // For now we use a getVirtualGameController
            // TODO gestisci come fare con la lista dei client e client in waiting
        }
    }

    // This is for testing
    @Override
    public VirtualGameController getVirtualGameController() throws RemoteException {
        if (!virtualGameControllers.isEmpty())
            return virtualGameControllers.getLast();
        return null;
    }
}
