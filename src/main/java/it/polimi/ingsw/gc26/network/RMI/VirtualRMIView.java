package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class VirtualRMIView implements VirtualView {
    private final VirtualMainController virtualMainController;
    private VirtualGameController virtualGameController;
    private String clientID;
    private String nickname;
    private Object lock;
    ClientState clientState;

    public VirtualRMIView(VirtualMainController virtualMainController) throws RemoteException {
        this.virtualMainController = virtualMainController;
        clientState = ClientState.CONNECTION;
        clientID = "";
        lock = new Object();

        UnicastRemoteObject.exportObject(this, 0);
    }

    // This are examples of view updating methods
    @Override
    public void notifyMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void setClientID(String clientID) throws RemoteException {
        this.clientID = clientID;
    }

    @Override
    public void setGameController() throws RemoteException {

    }

    @Override
    public ClientState getState() {
        return this.clientState;
    }

    @Override
    public void reportMessage(String message) throws RemoteException {

    }

    @Override
    public void reportError(String errorMessage) throws RemoteException {

    }

    @Override
    public void updateState(ClientState clientState) throws RemoteException {
        synchronized (lock) {
            this.clientState = clientState;
            this.lock.notifyAll();
        }
    }


    // Method for running Terminal UI
    public Pair<VirtualGameController, String> runTUI() throws RemoteException {
        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        System.out.println("YOU CONNECTED TO THE SERVER");
        Scanner scanner = new Scanner(System.in);

        System.out.print("INSERISCI IL NICKNAME\nNickname: ");
        this.nickname = scanner.nextLine();
        this.virtualMainController.connect(this, this.nickname);

        synchronized (this.lock) {
            while (this.clientState == ClientState.CONNECTION) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        // TODO sistema a reconnect
        while (clientState == ClientState.GAME_ON_CREATION) {
            this.virtualMainController.connect(this, this.nickname);

            synchronized (this.lock) {
                while (this.clientState == ClientState.GAME_ON_CREATION) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (this.clientState == ClientState.CREATOR) {
            System.out.print("THERE ARE NO GAME FREE, YOU MUST CREATE A NEW GAME:\nNumber of players (2/3/4): ");
            String decision = scanner.nextLine();
            this.virtualMainController.createWaitingList(this, this.nickname, Integer.parseInt(decision));

            synchronized (this.lock) {
                while (this.clientState == ClientState.CREATOR) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (this.clientState == ClientState.INVALID_NUMBER_OF_PLAYER) {
                this.clientState = ClientState.CREATOR;
                System.out.print("INVALID NUMBER OF PLAYERS\nNumber of players (2/3/4): ");
                decision = scanner.nextLine();
                this.virtualMainController.createWaitingList(this, this.nickname, Integer.parseInt(decision));

                synchronized (this.lock) {
                    while (this.clientState == ClientState.CREATOR) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (clientState.equals(ClientState.INVALID_NICKNAME)) {
            while (clientState == ClientState.INVALID_NICKNAME) {
                System.out.print("NICKNAME GIA' PRESO\nNickname:");
                this.nickname = scanner.nextLine();
                clientState = ClientState.INVALID_NICKNAME;

                this.virtualMainController.connect(this, this.nickname);

                synchronized (this.lock) {
                    while (this.clientState == ClientState.INVALID_NICKNAME) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println("WAITING ...");
        while (clientState == ClientState.WAITING) {
            System.out.flush();
        }

        virtualGameController = this.virtualMainController.getVirtualGameController();
        System.out.println("GAME BEGIN");
        return new Pair<>(this.virtualGameController, this.clientID);
    }

    // Method for running Graphic UI
    public void runGUI() {

    }
}
