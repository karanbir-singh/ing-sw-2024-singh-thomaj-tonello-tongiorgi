package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.VirtualMainController;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServerPing implements Runnable {
    /**
     * This constant represents the max numbers of reconnection attempts
     */
    private static final int NUM_RECONNECTION_ATTEMPTS = 3;
    /**
     * This attribute represents the main client this thread for pinging RMI server is launched by
     */
    private final MainClient mainClient;

    /**
     * Constructor of this RMI server ping runnable
     *
     * @param mainClient
     */
    public RMIServerPing(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    @Override
    public void run() {
        //while (true) {
        // Set attempt num
        int numAttempt = 0;
        while (numAttempt < NUM_RECONNECTION_ATTEMPTS) {
            try {
                // Ping server
                mainClient.getVirtualMainController().amAlive();

                // Reset attempt num
                numAttempt = 0;
            } catch (RemoteException e) {
                // Something is wrong with the network
                System.out.println("Something went wrong, trying to reconnect...");

                // Increment attempt num
                numAttempt++;

                // Check if attempt num reached the max value of trials
                if (numAttempt == NUM_RECONNECTION_ATTEMPTS) {
                    System.out.println("Server is down, wait for reconnection...");

                    // Server is down
                    boolean isServerUp = false;

                    // Until server is down...
                    while (!isServerUp) {
                        // ...try to recreate connection with server
                        Registry registry;
                        try {
                            registry = LocateRegistry.getRegistry(MainClient.SERVER_IP, MainClient.RMI_SERVER_PORT);

                            // Reset virtual RMI main and game controller
                            mainClient.setVirtualMainController((VirtualMainController) registry.lookup(MainClient.remoteObjectName));
                            mainClient.setVirtualGameController(mainClient.getVirtualMainController().getVirtualGameController(mainClient.getViewController().getGameID()));

                            // Re-add view to the game controller
                            mainClient.getVirtualGameController().reAddView(mainClient.getVirtualView(), mainClient.getClientID());

                            // Server is up again
                            isServerUp = true;

                            // Reset attempt num
                            numAttempt = 0;
                        } catch (RemoteException | NotBoundException ex) {
                            // Sleep thread
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException ex1) {
                                System.out.println("Thread interrupted");
                            }
                            System.out.println("Trying to reconnect...");
                        }
                    }
                    System.out.println("Server is up, you can restart to play");
                }
            }

            // Sleep thread
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
        //}
    }
}
