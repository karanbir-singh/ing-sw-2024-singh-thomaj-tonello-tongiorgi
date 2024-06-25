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
 * Implementation for RMI network connection.
 */
public class RMIPingManager implements PingManager {
    /**
     * This attribute represents the main client this thread for pinging RMI server is launched by
     */
    private final MainClient mainClient;

    /**
     * This attribute represents the server timeout seconds (10 seconds)
     */
    private static final int TIMEOUT = 10;

    /**
     * This attribute represents a flag indicating if the first ping from the server has arrived.
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
     * This attributes represents the max amount of time to wait after the server crush (60 seconds)
     */
    private static final int MAX_TIMEOUT_WHEN_SERVER_DOWN = 60;

    /**
     * Flag equals true if the server is reachable
     */
    private boolean isServerUp = true;

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
     * Resets timer in order to identify when the connection has been interrupted.
     */
    @Override
    public void reset() {
        synchronized (lock) {
            lastPingTime = System.currentTimeMillis();
            this.firstPingArrived = true;
        }
    }

    /**
     * Returns true if server is uo, false otherwise
     */
    @Override
    public boolean isServerUp() {
        return isServerUp;
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
                if (firstPingArrived) {
                    elapsed = (currentTime - lastPingTime) / 1000;
                } else {
                    elapsed = 0;

                }
            }

            // Manage when it's timeout
            if (elapsed >= TIMEOUT) {
                mainClient.getViewController().showError("Server is down, wait for reconnection...");
                // Server is down
                isServerUp = false;

                // Current time when the timer elapsed
                long timeWhenServerDown = System.currentTimeMillis() / 1000;

                // Until server is down...
                while (!isServerUp) {
                    //if server is down for more than 30 seconds, we close the client
                    if (System.currentTimeMillis() / 1000 - timeWhenServerDown >= MAX_TIMEOUT_WHEN_SERVER_DOWN) {
                        System.exit(0);
                    }

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
                            ex1.printStackTrace();
                        }
                    }
                }
                firstPingArrived = false;
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
