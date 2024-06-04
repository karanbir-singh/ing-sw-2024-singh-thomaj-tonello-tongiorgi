package it.polimi.ingsw.gc26.ui;

import it.polimi.ingsw.gc26.MainClient;

import java.rmi.RemoteException;

public interface UIInterface {

    void init(MainClient.NetworkType networkType);

    void runConnection() throws RemoteException;

    void runGame() throws RemoteException;
}
