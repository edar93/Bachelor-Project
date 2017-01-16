package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {

    private String owner;
    private List<Player> players;
    private Table table;
    private int playersCount;
    private int playerOnTurn;
    private Exception exceptions;
    @JsonIgnore
    private DrawPile drawPile;

    public Game(GameInQueue gameInQueue){
        exceptions = new Exception();
        playersCount = gameInQueue.getPlayersList().size();
        playerOnTurn = 0;
        table = new Table();
        drawPile = new DrawPile(true, playersCount == 5);
        owner = gameInQueue.getOwner();
        players = new ArrayList<>(playersCount);
        players = gameInQueue.getPlayersList().stream()
                .map (p -> new Player(p.getLogin()))
                .collect(Collectors.toList());
    }

    public void faceCard(){
        table.faceCard(drawPile);
    };

    public void playerGetCardFromTable(int cardPosition){
        players.get(playerOnTurn).getCardFromTable(table, cardPosition);
    }

    public Exception getExceptions() {
        return exceptions;
    }

    public void setExceptions(Exception exceptions) {
        this.exceptions = exceptions;
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

    public int getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(int playersCount) {
        this.playersCount = playersCount;
    }

    public int getPlayerOnTurn() {
        return playerOnTurn;
    }

    public void setPlayerOnTurn(int playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

}
