package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class VirtualRMIView  implements VirtualView {
    private final VirtualMainController virtualMainController;
    private VirtualGameController virtualGameController;
    private String clientID;
    private String nickName;
    ClientState clientState;

    public VirtualRMIView(VirtualMainController virtualMainController) throws RemoteException {
        this.virtualMainController = virtualMainController;
        clientState = ClientState.CONNECTION;

        UnicastRemoteObject.exportObject(this, 0);
    }

    // This are examples of view updating methods
    @Override
    public void showMessage(String message) throws RemoteException {

    }

    public void updateState(ClientState clientState) {
        this.clientState = clientState;
    }

    @Override
    public void setClientID(String clientID) throws RemoteException {

    }

    @Override
    public void setGameController() throws RemoteException {

    }

    @Override
    public void reportMessage(String message) throws RemoteException {

    }

    @Override
    public void reportError(String errorMessage) throws RemoteException {

    }

    // Method for running Terminal UI
    public void runTUI() throws RemoteException {
        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        System.out.println("YOU CONNECTED TO THE SERVER");
        Scanner scanner = new Scanner(System.in);

        System.out.println("INSERISCI IL NICKNAME");
        this.nickName = scanner.nextLine();
        this.clientID = this.virtualMainController.connect(this, this.nickName);

        while (clientState == ClientState.INVALID_NICKNAME || clientState == ClientState.CONNECTION) {
            System.out.println("NICKNAME GIA' PRESO\nNickname:");
            this.nickName = scanner.nextLine();
            this.clientID = this.virtualMainController.connect(this, this.nickName);
        }

        if (clientState == ClientState.CREATOR) {
            System.out.println("THERE ARE NO GAME FREE, YOU MUST CREATE A NEW GAME, HOW MANY PLAYER?");
            String decision = scanner.nextLine();
            this.virtualMainController.createWaitingList(this, this.clientID, this.nickName, Integer.parseInt(decision));
        }
        System.out.println("WAITING PLAYERS...");
        while (clientState == ClientState.WAITING){
            System.out.flush();
        }

        virtualGameController = this.virtualMainController.getVirtualGameController();
        System.out.println("GAME BEGIN");

        while (true) {
            //game started
            boolean chat = false;
            String line = scanner.nextLine();
            String receiver = "";
            if (line.startsWith("/chat")) {
                chat = true;
                line = line.substring(line.indexOf(" ") + 1);
                if (line.startsWith("/")) {
                    receiver = line.substring(1, line.indexOf(" "));
                    line = line.substring(line.indexOf(" ") + 1);
                }
            }
            if (chat) {
                this.virtualGameController.addMessage(line, receiver, this.clientID, "");
            }
        }

    }

    // Method for running Graphic UI
    public void runGUI() {

    }
}
