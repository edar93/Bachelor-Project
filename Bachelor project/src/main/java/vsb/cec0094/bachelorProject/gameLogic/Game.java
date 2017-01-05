package vsb.cec0094.bachelorProject.gameLogic;

import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private String owner;
    private List<Player> players;
    private DrawPile drawPile;
    private Table table;

    public Game(GameInQueue gameInQueue){
        table = new Table();
        drawPile = new DrawPile(true, gameInQueue.getPlayersList().size() == 5);
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

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
