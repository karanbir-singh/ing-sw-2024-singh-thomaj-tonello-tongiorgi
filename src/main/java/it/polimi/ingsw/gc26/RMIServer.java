package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class RMIServer implements VirtualServer{
    final MainController mainController;
    public static void main(String args[]) throws RemoteException { //TODO ALL REMOTE EXCEPTIONS MUST BE HANDLED BY A TRY AND CATCH
        final String serverName = "addressServer";
        VirtualServer server = new RMIServer(new MainController());

        //All’avvio il server pubblica sul registro RMI l’oggetto remoto
        //In questo modo il client potrà cercare gli oggetti remoti disponibili e ottenere un riferimento
        //Il registro RMI deve essere online prima di avviare il server
        //Di default il registro si trova in localhost sulla porta 1099


        //now we instantiate the stub or virtual server
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server,0);

        //now the registry
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind(serverName, stub);
        System.out.println("SERVER IS ON");
    }

    public RMIServer(MainController mainController){
        this.mainController = mainController;
    }

    //waiting phase
    public String connect(VirtualView client) throws RemoteException{
        //TODO I DON T KNOW WHAT TO PUT
        return UUID.randomUUID().toString();
    }

    public boolean existsWaitingGame() throws RemoteException{
        return this.mainController.existsWaitingGame();
    }

    public void createWaitingList(int numPlayers, String playerID, String playerNickname) throws RemoteException{
        this.mainController.createWaitingList(numPlayers,playerID,playerNickname);
    }

    public void joinWaitingList(String playerID, String playerNickname)throws RemoteException{
        this.mainController.joinWaitingList(playerID,playerNickname);
    }

    public GameControllerInterface getG() throws RemoteException{
        return this.mainController.getG();
    }



}
