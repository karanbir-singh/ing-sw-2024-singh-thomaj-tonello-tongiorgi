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
            // Create virtual main controller, the remote object
            System.out.println("Constructing server implementation...");
            VirtualMainController virtualMainController = new VirtualRMIMainController(new MainController());
            System.out.println("Binding server implementation to registry...");

            // Create registry
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind main controller
            registry.rebind(serverName, virtualMainController);
            System.out.println("Server RMI on listening...");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("RMI SERVER IS ON");

        // Start Socket Server
    }
}
