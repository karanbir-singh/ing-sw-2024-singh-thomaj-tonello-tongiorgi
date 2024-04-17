package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualMainController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainClient {
    public static void main(String args[]) {

        // TODO la scelta di runnare un client socket o RMI avviene da user
        // TODO Per ora qui viene avviato il client con RMI e non quello con socket

        final String remoteObjectName = "RMIMainController";

        try {
            //finding the registry and getting the stub of virtualMainController in the registry
            Registry registry = LocateRegistry.getRegistry(1099);
            VirtualMainController virtualMainController = (VirtualMainController) registry.lookup(remoteObjectName);

            //running the RMI TUI
            new VirtualRMIView(virtualMainController).runTUI();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
