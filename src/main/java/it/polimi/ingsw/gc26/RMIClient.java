package it.polimi.ingsw.gc26;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RMIClient extends UnicastRemoteObject implements VirtualView {
    private final VirtualServer server;
    private String playerId;
    private GameControllerInterface gameController;
    public RMIClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }
    public static void main(String args[]) throws Exception{
        //Il registro è online
        //L’oggetto remoto è gia stato pubblicato dal server

        final String remoteObjectName = "addressServer";
        //connect to registry
        Registry registry = LocateRegistry.getRegistry(1099);
        //we instanciate the server stub, the remote object
        VirtualServer server = (VirtualServer) registry.lookup(remoteObjectName);
        new RMIClient(server).run();
    }


    //Un OGGETTO NON-REMOTO, passato come parametro, o restituito come risultato da un metodo remoto,
    // è sempre passato per copia.
    //Ovvero serializzato, scritto nello stream, e ricaricato all’altro
    //estremo dello stream, ma come un oggetto differente.
    //Modificare quindi un oggetto ricevuto mediante invocazione
    //remota NON HA ALCUN EFFETTO sull’istanza originale di chi l’ha
    //inviato.

    //Un OGGETTO REMOTO, già esportato, passato come
    //parametro, o restituito come risultato da un metodo
    //remoto è PASSATO MEDIANTE IL SUO STUB.
    //Un oggetto remoto passato come parametro può solo
    //implementare interfacce remote


    private void run() throws  Exception{
        this.playerId = this.server.connect(this); //TODO HOW TO ASSOCIATE A CLIENT WITH A PLAYER?
        System.out.println("YOU CONNECTED TO THE SERVER");
        Scanner scanner = new Scanner(System.in);
        if(this.server.existsWaitingGame()){
            this.server.joinWaitingList(this.playerId,"kevin");
        }else{
            System.out.println("HOW MANY PLAYER MAX");
            String numOfPlayer = scanner.nextLine();
            this.server.createWaitingList(Integer.parseInt(numOfPlayer), "12334", "kevin");
        }
        this.gameController = server.getG();
        System.out.println("IT FUNCTIONS");
        this.gameController.prepareCommonTable();


    }
}
