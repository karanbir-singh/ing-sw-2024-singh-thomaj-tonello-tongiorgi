package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Player;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

/*public class RMIServer implements VirtualServer{
    final MainController mainController;
    public static void main(String args[]) throws RemoteException { //TODO ALL REMOTE EXCEPTIONS MUST BE HANDLED BY A TRY AND CATCH
        final String serverName = "addressServer";
        VirtualServer server = new RMIServer(new MainController()); //real server that has a static type Virtual Server and Dynamic type RMISERVER

        //All’avvio il server pubblica sul registro RMI l’oggetto remoto
        //In questo modo il client potrà cercare gli oggetti remoti disponibili e ottenere un riferimento
        //Il registro RMI deve essere online prima di avviare il server
        //Di default il registro si trova in localhost sulla porta 1099


        //now we instantiate the stub or virtual server
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(server,0);

        //now the registry
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(serverName, stub);
    }

    public RMIServer(MainController mainController){
        this.mainController = mainController;
    }

    //waiting phase
    public String connect(VirtualView client) throws RemoteException{
        //TODO I DON T KNOW WHAT TO PUT
        return UUID.randomUUID().toString();
    }

    public int login(String nickname, String id)throws RemoteException{

        boolean find = false;
        for(GameController gameController : MainController.getGameControllers()){
            if(gameController.getGame().getState() == GameState.WAITING){

                for(Player player: gameController.getGame().getPlayers()){
                    if(player.getNickname().equals(nickname)){
                        return 0; //deve ripetere il login, in quanto un player ha gia quel nickname
                    }
                }
                find = true;
                return 1; //qua fa join di un game
            }
        }

        if(!find){
            return 2; //crea un nuovo game
        }
        return 0;
    }
    public void createGame(int numPlayers, String id) throws Exception,RemoteException {
        mainController.createGame(numPlayers, id); //return

    }
    public void joinGame(String id) throws RemoteException{
        mainController.joinGame(id); //TODO i dont know what to pass to the function, i don t have the player, maybe i have the client
    }



    //Only when game is started
    public void selectCardFromHand(int cardPosition, String id ) throws RemoteException{
        mainController.getGameController(id).selectCardFromHand(cardPosition,id); //TODO i dont know how to select the correct game
    }
    public void turnSelectedCardSide(String id) throws RemoteException{
        mainController.getGameController(id).turnSelectedCardSide(id);
    }
    public void selectPositionOnBoard(int x, int y, String id) throws RemoteException{
        mainController.getGameController(id).selectPositionOnBoard(id);
    }

    public void playCardFromHand(String id) throws RemoteException{
        mainController.getGameController(id).playCardFromHand();
    }
    public void selectCardFromCommonTable(int x, int y, String id) throws RemoteException{
        mainController.getGameController(id).selectCardFromCommonTable(x,y,id);
    }

    public void drawSelectedCard(String id) throws RemoteException{
        mainController.getGameController(id).drawSelectedCard(id);
    }

    public void selectSecretMission(int x,String id) throws RemoteException{
        mainController.getGameController(id).selectSecretMission(x,id);
    }
    public void setSecretMission(String id) throws RemoteException{
        mainController.getGameController(id).setSecretMission(id);
    }

}*/