package vsb.cec0094.bachelorProject.gameLogic;

import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private String owner;
    private List<Player> players;

    public Game(GameInQueue gameInQueue){
        owner = gameInQueue.getOwner();
        players = new ArrayList<>(gameInQueue.getPlayersList().size());
        players = gameInQueue.getPlayersList().stream()
                .map (p -> new Player(p.getLogin()))
                .collect(Collectors.toList());
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
