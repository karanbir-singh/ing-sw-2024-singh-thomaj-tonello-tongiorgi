package it.polimi.ingsw.gc26.ui;

import it.polimi.ingsw.gc26.MainClient;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface UIInterface {

    void init(MainClient.NetworkType networkType) throws IOException, NotBoundException;

    void runConnection() throws RemoteException;

    void runGame() throws RemoteException;
}
