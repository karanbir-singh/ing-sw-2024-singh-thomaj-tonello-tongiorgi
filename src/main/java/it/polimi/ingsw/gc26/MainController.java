package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainController {
    private Map<String, GameController> gamesControllers;

    public MainController() {
        this.gamesControllers = new HashMap<>();
    }

    public void createGame(int numPlayers) {
        if (numPlayers > 1 && numPlayers <= Game.MAX_NUM_PLAYERS) {
            String gameID = UUID.randomUUID().toString();
            gamesControllers.put(gameID, new GameController(new Game(numPlayers)));
        }
        // TODO gestire cosa fare in cui il numero di giocatori è negativo o maggiori di Game.MAX_NUM_PLAYERS
    }

    public GameController joinGame(String gameID) {
        Game game = gamesControllers.get(gameID).getGame();
        if (game.getPlayers().size() < gamesControllers.get(gameID).getGame().getNumberOfPlayers())
            return gamesControllers.get(gameID);
        else
            // TODO gestire in altro modo questo ramo
            return null;
    }

    public ArrayList<String> getGamesIDs() {
        return new ArrayList<>(gamesControllers.keySet());
    }

    public void deleteGame(String gameID){
        gamesControllers.remove(gameID);
    }
}
