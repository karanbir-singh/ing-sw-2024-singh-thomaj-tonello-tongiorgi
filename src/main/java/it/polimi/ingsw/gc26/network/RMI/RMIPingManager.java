package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.PingManager;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class implements a ping to the server in order to notice when the server has gone down.
 */
public class RMIPingManager implements PingManager {
    /**
     * This attribute represents the main client this thread for pinging RMI server is launched by
     */
    private final MainClient mainClient;

    /**
     * This attribute represents the server timeout seconds
     */
    private static final int TIMEOUT = 5;

    /**
     * This attribute represents a flag indicating if the
     */
    private boolean firstPingArrived;

    /**
     * This attributes represents the last ping time from the server
     */
    private long lastPingTime;

    /**
     * Used as a lock for synchronization
     */
    private final Object lock;

    /**
     * Constructor of this RMI server ping runnable
     *
     * @param mainClient main client reference
     */
    public RMIPingManager(MainClient mainClient) {
        this.mainClient = mainClient;
        this.lock = new Object();
        this.lastPingTime = System.currentTimeMillis();
        this.firstPingArrived = false;
    }

    /**
     * Resets the timer
     */
    @Override
    public void reset() {
        synchronized (lock) {
            lastPingTime = System.currentTimeMillis();
            this.firstPingArrived = true;
        }
    }

    /**
     * Runs RMI server ping manager
     */
    @Override
    public void run() {
        while (true) {
            // Get current time
            long currentTime = System.currentTimeMillis();

            // Check how much time has passed
            long elapsed;
            synchronized (lock) {
                if(firstPingArrived) {
                    elapsed = (currentTime - lastPingTime) / 1000;
                }else{
                    elapsed = 0;

                }
            }

            // Manage when it's timeout
            if (elapsed >= TIMEOUT) {
                System.out.println("Server is down, wait for reconnection...");
                mainClient.getViewController().showError("Server is down, wait for reconnection...");
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
                    } catch (RemoteException | NotBoundException ex) {
                        // Sleep thread
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex1) {
                            System.out.println("Thread interrupted");
                        }
                    }
                }
                System.out.println("Server is up, you can restart to play");
                mainClient.getViewController().closeErrorPopup();

                synchronized (lock) {
                    lastPingTime = System.currentTimeMillis();
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
