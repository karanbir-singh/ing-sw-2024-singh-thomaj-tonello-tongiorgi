package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.VirtualMainController;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainServer {
    public static void main(String[] args) {

        // Start RMI Server
        final String serverName = "RMIMainController";

        try {
            //publication and binding of the virtualMainController in the registry
            System.out.println("Constructing server implementation...");
            MainController mainController = new MainController();
            System.out.println("Constructing server implementation...");

            VirtualMainController virtualMainController = new VirtualRMIMainController(mainController);
            System.out.println("Binding server implementation to registry...");
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serverName, virtualMainController);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("RMI SERVER IS ON");

        // Start Socket Server
    }
}
