package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;
import java.util.Scanner;

public class VirtualRMIView implements VirtualView {
    private final VirtualMainController virtualMainController;
    private String clientID;
    private VirtualGameController virtualGameController;

    public VirtualRMIView(VirtualMainController virtualMainController) {
        this.virtualMainController = virtualMainController;
    }

    // This are examples of view updating methods
    @Override
    public void showMessage(String message) throws RemoteException {

    }

    @Override
    public void reportMessage(String message) throws RemoteException {

    }

    @Override
    public void reportError(String errorMessage) throws RemoteException {

    }

    // Method for running Terminal UI
    public void runTUI() throws RemoteException {
        // TODO gestire la Remote Exception

        this.clientID = this.virtualMainController.connect(this, "kevin");
        System.out.println("YOU CONNECTED TO THE SERVER");

        Scanner scanner = new Scanner(System.in);
        if(this.virtualMainController.existsWaitingGame()){
            this.virtualMainController.joinWaitingList(this.clientID, "kevin");
        }else{
            System.out.println("HOW MANY PLAYER MAX");
            String numOfPlayer = scanner.nextLine();
            this.virtualMainController.createWaitingList(Integer.parseInt(numOfPlayer), this.clientID, "kevin");
        }
        this.virtualGameController = virtualMainController.getVirtualGameController();
        while(this.virtualGameController == null){
            this.virtualGameController = virtualMainController.getVirtualGameController();
        }
        System.out.println("I M IN THE GAME, VAMOSS");

        System.out.println("SELECT THE CARD");
        String index = scanner.nextLine();
        this.virtualGameController.selectCardFromHand(Integer.parseInt(index),this.clientID);
        System.out.println("SELECTED STARTER CARD");

        System.out.println("DO YOU WANT TO CHANGE SIDE? yes/no");
        String decision = scanner.nextLine();
        if(decision.equals("yes")){
            this.virtualGameController.turnSelectedCardSide(this.clientID);
            System.out.println("TURNED STARTER CARD SIDE");
        }else{
            System.out.println("YOU DON T WANT TO CHANGE STARTER CARD SIDE");
        }
        String decisionCard = scanner.nextLine();
        if(decisionCard.equals("playStarterCard")){
            this.virtualGameController.playCardFromHand(this.clientID);
            System.out.println("PLAYED STARTER CARD");
        }
    }

    // Method for running Graphic UI
    public void runGUI() {

    }
}
