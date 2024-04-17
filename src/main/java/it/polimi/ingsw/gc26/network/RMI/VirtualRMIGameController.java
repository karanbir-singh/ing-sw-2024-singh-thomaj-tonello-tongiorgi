package it.polimi.ingsw.gc26.network.RMI;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.network.VirtualGameController;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class VirtualRMIGameController implements VirtualGameController  {

    private final GameController gameController;

    public VirtualRMIGameController(GameController gameController) throws RemoteException{
            this.gameController = gameController;
            UnicastRemoteObject.exportObject(this, 0);
    }

    @Override
    public void prepareCommonTable() {
        try {
            this.gameController.prepareCommonTable();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from prepareCommonBoard");
        }
    }

    @Override
    public void prepareStarterCards(){
        try {
            this.gameController.prepareStarterCards();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from prepareStarterCards");
        }
    }

    @Override
    public void preparePlayersHand(String playerID) {
        try {
            this.gameController.preparePlayersHand(playerID);
        }catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from preparePlayersHand");
        }
    }

    @Override
    public void prepareCommonMissions(){
        try {
            this.gameController.prepareCommonMissions();
        }catch(RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from prepareCommonMission");
        }
    }

    @Override
    public void prepareSecretMissions() {
        try {
            this.gameController.prepareSecretMissions();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from prepareSecretMission");
        }
    }

    @Override
    public void selectSecretMission(int cardIndex, String playerID){
        try {
            this.gameController.selectSecretMission(cardIndex, playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from selectSecretMission");
        }
    }

    @Override
    public void setSecretMission(String playerID){
        try {
            this.gameController.setSecretMission(playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from setSecretMission");
        }
    }

    @Override
    public void setFirstPlayer(String playerID){
        try {
            this.gameController.setFirstPlayer(playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from setFirstPlayer");
        }
    }

    @Override
    public void selectCardFromHand(int cardIndex, String playerID){
        try {
            this.gameController.selectCardFromHand(cardIndex, playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from selectCardFromHand");
        }
    }

    @Override
    public void turnSelectedCardSide(String playerID){
        try {
            this.gameController.turnSelectedCardSide(playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from turnSelectedCardSide");
        }
    }

    @Override
    public void selectPositionOnBoard(int selectedX, int selectedY, String playerID){
        try {
            this.gameController.selectPositionOnBoard(selectedX, selectedY, playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from selectPositionOnBoard");
        }
    }

    @Override
    public void playCardFromHand(String playerID) {
        try {
            this.gameController.playCardFromHand(playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from playCardFromHand");
        }
    }

    @Override
    public void selectCardFromCommonTable(int cardX, int cardY, String playerID){
        try {
            this.gameController.selectCardFromCommonTable(cardX, cardY, playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from selectCardFromCommonTable");
        }
    }

    @Override
    public void drawSelectedCard(String playerID){
        try {
            this.gameController.drawSelectedCard(playerID);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from drawSelectedCard");
        }
    }

    @Override
    public void changeTurn() {
        try {
            this.gameController.changeTurn();
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("REMOTE EXCEPTION CATCHED from changeTurn");
        }
    }
}
