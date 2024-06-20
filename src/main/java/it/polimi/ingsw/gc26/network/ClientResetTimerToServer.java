package it.polimi.ingsw.gc26.network;
import com.sun.tools.javac.Main;
import it.polimi.ingsw.gc26.MainClient;

import java.rmi.RemoteException;

public class ClientResetTimerToServer implements Runnable{
    private MainClient mainClient;
    private final int TIMEFREQUENCY = 1000;


    public ClientResetTimerToServer(MainClient mainClient){
        this.mainClient = mainClient;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(TIMEFREQUENCY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                this.mainClient.getVirtualMainController().resetServerTimer(
                        this.mainClient.getClientID());
            } catch (RemoteException e) {
                System.out.println("Problemi di rete");
            }
        }
    }
}
