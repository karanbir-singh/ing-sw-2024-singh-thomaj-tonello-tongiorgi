package it.polimi.ingsw.gc26;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/*public class RMIClient extends UnicastRemoteObject implements VirtualView {
    private final VirtualServer server;
    private String playerId;
    public RMIClient(VirtualServer server) throws RemoteException {
        this.server = server;
    }
    public static void main(String args[]) throws  RemoteException{
        //Il registro è online
        //L’oggetto remoto è gia stato pubblicato dal server

        final String remoteObjectName = "addressServer";
        //connect to registry
        Registry registry = LocateRegistry.getRegistry(args[0]); //TODO we need to change the parameter
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
        while(true){
            Scanner scanner = new Scanner(System.in);
            String userInput;
            int decision;
            System.out.println("Choose a nickName");
            userInput = scanner.nextLine();

            decision =  this.server.login(userInput, this.playerId);
            while(decision == 0){
                System.out.println("Choose a new nickName because it's not new");
                userInput = scanner.nextLine();
                decision = this.server.login(userInput, this.playerId);
            }

            if(decision == 1){
                System.out.println("You are entering in a game");
                this.server.joinGame(this.playerId);
            }
            if(decision == 2){
                System.out.println("Choose how many player you want in your new game");
                int numPlayerGame = Integer.parseInt(scanner.nextLine());
                this.server.createGame(numPlayerGame,this.playerId);
            }

        }
    }
}*/
