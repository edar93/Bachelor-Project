package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.card.Expedition;
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

    public ActionToShow playerGetCardFromTable(int cardPosition) throws InvalidActionException {
        return players.get(activePlayer).getCardFromTable(table, cardPosition);
    }

    public ActionAndSemiStateHolder pickExpedition(int id) throws TooExpensiveExpeditionException, CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
        Expedition expedition = expeditions.getExpedition(id);
        players.get(activePlayer).canTakeExpedition(expedition); // if not throws exeption

        actionAndSemiStateHolder.addState(this, new ActionToShow(Action.SHOW_EXPEDITION_WHICH_WILL_BE_TAKEN, Expeditions.EXPEDITIONS, id));
        ActionToShow takeExpeditionAction = players.get(activePlayer).takeExpedition(expedition, drawPile);

        actionAndSemiStateHolder.addState(this, takeExpeditionAction);
        return actionAndSemiStateHolder;
    }

    public ActionAndSemiStateHolder faceCard(GameManipulator gameManipulator) throws CloneNotSupportedException {
        Card card = drawPile.giveCard();

        if (card.getCardType() == CardType.EXPEDITION) {
            return faceExpedition(card);
        } else if (card.getCardType() == CardType.TAX_INFLUENCE || card.getCardType() == CardType.TAX_SWORDS) {
            return faceTaxes(card);
        } else {
            ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
            actionAndSemiStateHolder.addState(this, table.faceCard(card));
            return actionAndSemiStateHolder;
        }
    }

    private ActionAndSemiStateHolder faceTaxes(Card card) throws CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
        int comparingValue;

        actionAndSemiStateHolder.addState(this, new ActionToShow(Action.SHOW_FACED_TAXES_PRE, Table.TABLE, table.getCards().size() - 1));
        if (card.getCardType() == CardType.TAX_SWORDS) {
            comparingValue = 0;
        } else {
            comparingValue = 1000;
        }

        for (Player player : players) {
            if (player.getCoins() > 11) {
                player.setCoins(player.getCoins() / 2);
            }

            if (card.getCardType() == CardType.TAX_SWORDS) {
                if (player.getSwords() > comparingValue) {
                    comparingValue = player.getSwords();
                }
            } else if (card.getCardType() == CardType.TAX_INFLUENCE) {
                if (player.getInfluencePoints() < comparingValue) {
                    comparingValue = player.getInfluencePoints();
                }
            }
        }

        for (Player player : players) {
            if (card.getCardType() == CardType.TAX_SWORDS && player.getSwords() == comparingValue) {
                player.setCoins(player.getCoins() + 1);
            }else if (card.getCardType() == CardType.TAX_INFLUENCE && player.getInfluencePoints() == comparingValue) {
                player.setCoins(player.getCoins() + 1);
            }
        }

        actionAndSemiStateHolder.addState(this, table.faceCard(card));
        return actionAndSemiStateHolder;
    }

    private ActionAndSemiStateHolder faceExpedition(Card card) throws CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
        //show card on table
        table.addCard(card);
        ActionToShow actionToShow = new ActionToShow(Action.SHOW_FACED_EXPEDITION_ON_TABLE, Table.TABLE, table.getCards().size() - 1);
        actionAndSemiStateHolder.addState(this, actionToShow);
        //show card between expeditions
        expeditions.addCard(table.removeLastCard());
        actionToShow = new ActionToShow(Action.SHOW_FACED_EXPEDITION_ON_EXPEDITIONS_PACK, Expeditions.EXPEDITIONS, expeditions.getCards().size() - 1);
        actionAndSemiStateHolder.addState(this, actionToShow);
        return actionAndSemiStateHolder;
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
