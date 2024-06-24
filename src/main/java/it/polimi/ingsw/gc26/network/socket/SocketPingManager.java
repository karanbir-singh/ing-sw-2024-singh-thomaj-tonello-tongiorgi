package it.polimi.ingsw.gc26.network.socket;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.PingManager;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketGameController;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;

import java.io.*;
import java.net.Socket;

/**
 * This class implements a ping to the server in order to notice when the server has gone down.
 * Implementation for socket network connection.
 */
public class SocketPingManager implements PingManager {
    /**
     * This attribute represents the main client this thread for pinging RMI server is launched by
     */
    private final MainClient mainClient;

    /**
     * This attribute represents the server timeout seconds
     */
    private static final int TIMEOUT = 5;

    /**
     * This attributes represents the last ping time from the server
     */
    private long lastPingTime;

    /**
     * This attribute represents a flag indicating if the first ping from the server has arrived.
     */
    private boolean firstPingArrived;

    /**
     * Used as a lock for synchronization
     */
    private final Object lock;

    private final long maxTimeoutWhenServerDown = 30;

    private long timeWhenServerDown;

    /**
     * Flag equals true if the server is reachable
     */
    private boolean isServerUp = true;

    /**
     * Constructor of this socket server ping runnable
     *
     * @param mainClient main client reference
     */
    public SocketPingManager(MainClient mainClient) {
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
            firstPingArrived = true;
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
     * Runs Socket server ping manager
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

                this.timeWhenServerDown = System.currentTimeMillis() / 1000;

                // Until server is down...
                while (!isServerUp) {
                    //if the server is down for more than 30 seconds, we close the clients
                    if(System.currentTimeMillis()/ 1000 - this.timeWhenServerDown >= maxTimeoutWhenServerDown){
                        System.exit(0);
                    }


                    try {
                        // ...try to recreate connection with server
                        Socket serverSocket = new Socket(MainClient.SERVER_IP, MainClient.SERVER_SOCKET_PORT);

                        // Input and output stream with server
                        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
                        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

                        // Reader
                        BufferedReader socketIn = new BufferedReader(socketRx);

                        // Writer
                        BufferedWriter socketOut = new BufferedWriter(socketTx);

                        // Reset virtual socket main controller
                        mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));

                        // Get virtual socket game controller
                        mainClient.getVirtualMainController().getVirtualGameController(mainClient.getViewController().getGameID());

                        // Reset virtual socket game controller
                        mainClient.setVirtualGameController(new VirtualSocketGameController(socketOut));

                        // Relaunch server requests handler
                        new SocketServerHandler(mainClient.getViewController(), socketIn, socketOut);

                        // Re-add view to the game controller
                        mainClient.getVirtualGameController().reAddView(mainClient.getVirtualView(), mainClient.getClientID());

                        // Server is up again
                        isServerUp = true;
                    } catch (IOException ex) {
                        // Sleep thread
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex1) {
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
