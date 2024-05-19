package it.polimi.ingsw.gc26.client;

import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
import it.polimi.ingsw.gc26.network.socket.server.VirtualSocketView;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class GUIApplication extends Application {
    MainClient mainClient;

    public void init(MainClient.NetworkType networkType , String ip, int socketPort, int rmiPort, String remoteObjectName){
        launch(networkType.toString(), ip, String.valueOf(socketPort), String.valueOf(rmiPort), remoteObjectName);
    }

    @Override
    public void start(Stage stage) throws Exception {

    }



    private void startSocketClient(String socketServerAddress, int socketServerPort) throws IOException {
        // Create connection with the server
        Socket serverSocket = new Socket(socketServerAddress, socketServerPort);

        // Get input and out stream from the server
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        BufferedWriter socketOut = new BufferedWriter(socketTx);

        // Create socket client
        this.mainClient = new MainClient(MainClient.GraphicType.tui);
        this.mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        this.mainClient.setVirtualView(new VirtualSocketView(null));

        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.getViewController(), socketIn, socketOut);

    }

    private void startRMIClient(String RMIServerAddress, int RMIServerPort, String remoteObjectName) throws RemoteException, NotBoundException {
        // Finding the registry and getting the stub of virtualMainController in the registry
        Registry registry = LocateRegistry.getRegistry(RMIServerAddress, RMIServerPort);

        // Create RMI Client
        this.mainClient = new MainClient(MainClient.GraphicType.tui);
        Remote remoteObject = (Remote) registry.lookup(remoteObjectName);
        this.mainClient.setVirtualMainController((VirtualMainController) remoteObject);
        this.mainClient.setVirtualView(new VirtualRMIView(mainClient.getViewController()));
    }
}
