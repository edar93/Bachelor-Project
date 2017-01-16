package vsb.cec0094.bachelorProject.dao;

import org.springframework.stereotype.Repository;
import vsb.cec0094.bachelorProject.gameLogic.Game;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Repository
public class GamesHolder {

    private HashMap<String, Game> games;

    @PostConstruct
    public void init() {
        games = new HashMap<>();
    }

    public void addGame(Game game) {
        games.put(game.getOwner(), game);
    }

    public Game getGame(String owner) {
        return games.get(owner);
    }
}
