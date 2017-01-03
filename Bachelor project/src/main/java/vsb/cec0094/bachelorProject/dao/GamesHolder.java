package vsb.cec0094.bachelorProject.dao;

import org.springframework.stereotype.Repository;
import vsb.cec0094.bachelorProject.gameLogic.Game;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class GamesHolder {

    private HashMap<String, Game> games;
    private int test;

    @PostConstruct
    public void init(){
        test = 10;
        games = new HashMap<>();
    }

    public void addGame(Game game){
        games.put(game.getOwner(), game);
    }

    public Game getGame(String owner){
        return games.get(owner);
    }

    public int getTestAndAddOne(){
        return test++;
    }
}
