package it.polimi.ingsw.gc26.network.socket;

import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.PingManager;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketGameController;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;

import java.io.*;
import java.net.Socket;

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
     * Used as a lock for synchronization
     */
    private final Object lock;

    /**
     * Constructor of this socket server ping runnable
     *
     * @param mainClient main client reference
     */
    public SocketPingManager(MainClient mainClient, int timeout) {
        this.mainClient = mainClient;
        this.lock = new Object();
        this.lastPingTime = System.currentTimeMillis();
    }

    /**
     * Resets the timer
     */
    @Override
    public void reset() {
        synchronized (lock) {
            lastPingTime = System.currentTimeMillis();
        }
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
                elapsed = (currentTime - lastPingTime) / 1000;
            }

            // Manage when it's timeout
            if (elapsed >= TIMEOUT) {
                System.out.println("Server is down, wait for reconnection...");

                // Server is down
                boolean isServerUp = false;

                // Until server is down...
                while (!isServerUp) {
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

                        // TODO Gabi controlla sincronizzazione e vedi se togliere questa sleep
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }

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
                            System.out.println("Thread interrupted");
                        }
                    }
                }
                System.out.println("Server is up, you can restart to play");

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
