package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.gameLogic.pack.Expeditions;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Cloneable, Serializable {

    private String owner;
    private List<Player> players;
    private Table table;
    private int playersCount;
    private int playerOnTurn;
    private int activePlayer;
    private Expeditions expeditions;
    @JsonIgnore
    private DrawPile drawPile;

    public ActionAndSemiStateHolder faceCard(GameManipulator gameManipulator) throws CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();

        Card card = drawPile.giveCard();
        if (card.getCardType() == CardType.EXPEDITION) {
            //show card on table
            table.addCard(card);
            ActionToShow actionToShow = new ActionToShow(Action.SHOW_FACED_EXPEDITION_ON_TABLE, Table.TABLE, table.getCards().size() - 1);
            actionAndSemiStateHolder.addState(this, actionToShow);
            //show card between expeditions
            expeditions.addCard(table.removeLastCard());
            actionToShow = new ActionToShow(Action.SHOW_FACED_EXPEDITION_ON_EXPEDITIONS_PACK, Expeditions.EXPEDITIONS, expeditions.getCards().size() - 1);
            actionAndSemiStateHolder.addState(this, actionToShow);
        } else if (card.getCardType() == CardType.TAX_INFLUENCE || card.getCardType() == CardType.TAX_SWORDS) {
            // implement
        } else {
            ActionToShow cardAddedToTable = table.faceCard(card);
            actionAndSemiStateHolder.addState(this, cardAddedToTable);
        }
        return actionAndSemiStateHolder;
    }

    public ActionToShow playerGetCardFromTable(int cardPosition) throws InvalidActionException {
        return players.get(activePlayer).getCardFromTable(table, cardPosition);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        try {
            Game clone = (Game) super.clone();
            clone.setOwner(this.owner);
            clone.setPlayers(Player.cloneList(this.players));
            clone.setTable((Table) this.table.clone());
            clone.setExpeditions((Expeditions) this.expeditions.clone());
            clone.setDrawPile((DrawPile) this.drawPile.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
            e.printStackTrace();
            return null;
        }
    }

    public Game(GameInQueue gameInQueue) {
        expeditions = new Expeditions();
        playersCount = gameInQueue.getPlayersList().size();
        playerOnTurn = 0;
        activePlayer = 0;
        table = new Table();
        drawPile = new DrawPile(true, playersCount == 5);
        owner = gameInQueue.getOwner();
        players = new ArrayList<>(playersCount);
        players = gameInQueue.getPlayersList().stream()
                .map(p -> new Player(p.getLogin()))
                .collect(Collectors.toList());
    }

    public Expeditions getExpeditions() {
        return expeditions;
    }

    public void setExpeditions(Expeditions exceptions) {
        this.expeditions = exceptions;
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

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }
}
