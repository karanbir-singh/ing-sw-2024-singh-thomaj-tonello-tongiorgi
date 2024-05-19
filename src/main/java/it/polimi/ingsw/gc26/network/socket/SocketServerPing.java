package it.polimi.ingsw.gc26.network.socket;

import it.polimi.ingsw.gc26.client.MainClient;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketGameController;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;

import java.io.*;
import java.net.Socket;

public class SocketServerPing implements Runnable {
    /**
     * This constant represents the max numbers of reconnection attempts
     */
    private static final int NUM_RECONNECTION_ATTEMPTS = 3;
    /**
     * This attribute represents the main client this thread for pinging socket server is launched by
     */
    private final MainClient mainClient;

    /**
     * Constructor of this socket server ping runnable
     *
     * @param mainClient
     */
    public SocketServerPing(MainClient mainClient) {
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
                } catch (IOException e) {
                    // Something is wrong with the network...
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

                                // Reset num attempt
                                numAttempt = 0;
                            } catch (IOException ex) {
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
