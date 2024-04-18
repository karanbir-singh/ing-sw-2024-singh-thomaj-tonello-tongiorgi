package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class VirtualRMIView implements VirtualView {
    private final VirtualMainController virtualMainController;
    private String clientID;
    private String nickName;
    private VirtualGameController virtualGameController;
    ClientState currState;

    public VirtualRMIView(VirtualMainController virtualMainController) throws RemoteException {
        this.virtualMainController = virtualMainController;

        currState = ClientState.CONNECTION;
        UnicastRemoteObject.exportObject(this, 0);
    }

    // This are examples of view updating methods
    @Override
    public void showMessage(String message) throws RemoteException {

    }

    public void updateState(ClientState clientState) {
        this.currState = clientState;
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

        while (currState == ClientState.INVALID_NICKNAME || currState == ClientState.CONNECTION) {
            System.out.println("NICKNAME GIA' PRESO\nNickname:");
            this.nickName = scanner.nextLine();
            this.clientID = this.virtualMainController.connect(this, this.nickName);
        }

        if (currState == ClientState.CREATOR) {
            System.out.println("THERE ARE NO GAME FREE, YOU MUST CREATE A NEW GAME, HOW MANY PLAYER?");
            String decision = scanner.nextLine();
            this.virtualMainController.createWaitingList(this, this.clientID, this.nickName, Integer.parseInt(decision));
        }
        System.out.println("WAITING PLAYERS...");
        while (currState == ClientState.WAITING){
            System.out.flush();
        }

        virtualGameController = this.virtualMainController.getVirtualGameController();
        System.out.println("GAME BEGIN");

    }

    // Method for running Graphic UI
    public void runGUI() {

    }
}
