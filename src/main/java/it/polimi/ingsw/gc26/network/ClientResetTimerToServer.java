package it.polimi.ingsw.gc26.network;

import it.polimi.ingsw.gc26.MainClient;

import java.rmi.RemoteException;

/**
 * This class resets the client's timer in the server so the server know the client is still connected.
 */
public class ClientResetTimerToServer implements Runnable {
    /**
     * Main client instnce, needed to get the main controller
     */
    private MainClient mainClient;
    /**
     * Frequency to send a new reset to the server
     */
    private final int TIMEFREQUENCY = 1000;

    /**
     * Class's constructor
     * @param mainClient
     */
    public ClientResetTimerToServer(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    /**
     * Creates an infinite loop and resets the time in the server every k- milliseconds.
     * If the server is down, it prints a messages while keeps attempting to reset its timer.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(TIMEFREQUENCY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                this.mainClient.getVirtualMainController().resetServerTimer(
                        this.mainClient.getClientID());
            } catch (RemoteException e) {
                System.out.println("Network failure!");
            }
        }
    }
}
