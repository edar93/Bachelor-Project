package vsb.cec0094.bachelorProject.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.HashMap;

@Repository
public class GamesHolder implements Serializable{

    private static final Logger LOGGER = LoggerFactory.getLogger(GamesHolder.class);

    private HashMap<Integer, GameManipulator> games;

    @PostConstruct
    public void init() {
        games = new HashMap<>();
    }

    public void addGame(GameManipulator game) {
        games.put(game.getId(), game);
    }

    public GameManipulator getGame(Integer id) {
        return games.get(id);
    }

    public HashMap<Integer, GameManipulator> getGames() {
        return games;
    }

    public void setGames(HashMap<Integer, GameManipulator> games) {
        this.games = games;
    }
}
