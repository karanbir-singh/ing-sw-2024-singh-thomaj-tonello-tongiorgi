package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

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
    public void notifyMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void reportMessage(String message) throws RemoteException {

    }

    @Override
    public void reportError(String errorMessage) throws RemoteException {

    }

    public void updateState(ClientState clientState) {
        this.clientState = clientState;
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
        //System.out.println("WAITING PLAYERS...");
        while (clientState == ClientState.WAITING){
            System.out.flush();
        }

        virtualGameController = this.virtualMainController.getVirtualGameController();
        //System.out.println("GAME BEGIN");

        // PHASE 1: Game preparation
        System.out.println("Do you want to turn side of the starter card? (y/n)");
        String answer = scanner.nextLine();
        if (answer.equals("y")) {
            virtualGameController.turnSelectedCardSide(this.clientID);
            System.out.println("Side turned");
        }

        System.out.println("Play selected side? (y/n)");
        answer = scanner.nextLine();
        if (answer.equals("y")) {
            virtualGameController.playCardFromHand(this.clientID);
            System.out.println("Starter card placed");
        }

        System.out.println("Select pawn color from available colors");
        answer = scanner.nextLine();
        this.virtualGameController.choosePawnColor(answer, this.clientID);
        System.out.println("Pawn color selected");

        System.out.println("Select secret mission: (0/1)");
        answer = scanner.nextLine();
        this.virtualGameController.selectSecretMission(0, this.clientID);

        System.out.println("Set selected secret mission? (y/n)");
        answer = scanner.nextLine();
        if (answer.equals("y")) {
            this.virtualGameController.setSecretMission(this.clientID);
        }
    }

    // Method for running Graphic UI
    public void runGUI() {

    }
}
