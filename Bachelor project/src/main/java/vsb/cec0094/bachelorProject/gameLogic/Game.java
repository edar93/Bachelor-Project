package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.exceptions.TwoShipsOfSameTypeOnTableException;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.card.Expedition;
import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.gameLogic.pack.Expeditions;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.gameLogic.services.FaceCardService;
import vsb.cec0094.bachelorProject.gameLogic.services.GetCardFromTableService;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Cloneable, Serializable {

    @JsonIgnore
    private final FaceCardService faceCardService = new FaceCardService(this);
    @JsonIgnore
    private final GetCardFromTableService getCardFromTableService = new GetCardFromTableService(this);
    private String owner;
    private List<Player> players;
    private Table table;
    private Integer playersCount;
    private Integer playerOnTurn;
    private Integer activePlayer;
    private Expeditions expeditions;
    private Phase phase;
    private Integer cardsToTake;
    private Boolean admiralApplied;
    private Boolean gameOver;
    @JsonIgnore
    private DrawPile drawPile;

    public Game(GameInQueue gameInQueue) {
        gameOver = false;
        expeditions = new Expeditions();
        playersCount = gameInQueue.getPlayersList().size();
        playerOnTurn = 0;
        activePlayer = 0;
        phase = Phase.EXPLORING;
        table = new Table();
        drawPile = new DrawPile(true, playersCount == 5);
        owner = gameInQueue.getOwner();
        players = new ArrayList<>(playersCount);
        admiralApplied = false;
        players = gameInQueue.getPlayersList().stream()
                .map(p -> new Player(p.getLogin()))
                .collect(Collectors.toList());
        try {
            recalculateShips();
        } catch (TwoShipsOfSameTypeOnTableException e) {
            //can not occur
            e.printStackTrace();
        }
    }

    public static List<Game> getClonedList(List<Game> gameList) throws CloneNotSupportedException {
        if (gameList == null) {
            return null;
        }
        List<Game> clonedList = new ArrayList<>();
        for (Game game : gameList) {
            clonedList.add((Game) game.clone());
        }
        return clonedList;
    }

    public void applyAdmiral() {
        if (table.getCountOfCards() > 4) {
            admiralApplied = true;
            phase = Phase.TRADING;
            Player activeP = players.get(activePlayer);
            activeP.setCoins(activeP.getCoins() * activeP.getAdmiralsCount() * 2);
        }
    }

    public void skipAction() {
        shiftPlayer(false);
    }

    public ActionAndSemiStateHolder playerGetCardFromTable(int cardPosition) throws InvalidActionException, TooExpensiveExpeditionException, CloneNotSupportedException {
        if (Phase.EXPLORING.equals(phase)) {
            phase = Phase.TRADING;
        }
        ActionToShow actionToShow = new ActionToShow(Action.GET_CARD, Table.TABLE, cardPosition);
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder(this, actionToShow);

        Card card = table.getCards().remove(cardPosition);
        cardTypeValidation(card);

        if (activePlayer != playerOnTurn) {
            players.get(playerOnTurn).addCoin();
        }

        if (Card.shipTypes.contains(card.getCardType())) {

            Player playerDoingAction = players.get(activePlayer);
            playerDoingAction.getCoinsFromShip(card);
            drawPile.getUsedCard(card);

            if (--cardsToTake == 0) {
                shiftPlayer(false);
            }
            return actionAndSemiStateHolder;
        } else {
            Player playerDoingAction = players.get(activePlayer);
            playerDoingAction.takeCharacterCard(card, true);
            playerDoingAction.updateVariables();

            if (--cardsToTake == 0) {
                shiftPlayer(false);
            }
            actionAndSemiStateHolder.addState(this, new ActionToShow(Action.GET_CARD, playerDoingAction.getLogin(), playerDoingAction.getCards().indexOf(card)));
            return actionAndSemiStateHolder;
        }

    }

    public ActionAndSemiStateHolder faceCard() throws CloneNotSupportedException, InvalidActionException {
        return faceCardService.faceCard();
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

    private boolean isGameInOverCheck() {
        for (Player player : players) {
            if (player.getInfluencePoints() >= 12) {
                return true;
            }
        }
        return false;
    }

    private void cleanTable() {
        drawPile.getUsedCards(table.removeCards());
    }

    private void shiftPlayer(Boolean twoShips) {
        if (twoShips) {
            for (Player player : players) {
                player.setCoins(player.getCoins() + player.getJestersCount());
            }
            nextActivePlayer(true);
            phase = Phase.EXPLORING;
        } else {
            applyJester();
            applyAdmiral();
            nextActivePlayer(false);
        }
        try {
            recalculateShips();
        } catch (TwoShipsOfSameTypeOnTableException e) {
            // can not occur
            e.printStackTrace();
        }
        if (getActivePlayerAsPlayer().getAdmiralsCount() > 1) {
            admiralApplied = false;
        } else {
            admiralApplied = true;
        }

        if (table.getCountOfCards() == 0 && !(Phase.EXPLORING.equals(phase))) {
            shiftPlayer(false);
        }
    }

    private void nextActivePlayer(boolean twoShips) {
        raiseActivePlayer();
        if (twoShips) {
            raisePlayerOnTurn();
            phase = Phase.EXPLORING;
            cleanTable();
        } else {
            if (activePlayer == playerOnTurn) {
                cleanTable();
                phase = Phase.EXPLORING;
                raisePlayerOnTurn();
                raiseActivePlayer();
            } else {
                phase = Phase.OTHERS_PLAYERS_TRADING;
            }
        }
    }

    private Player getActivePlayerAsPlayer() {
        return players.get(activePlayer);
    }

    private void raiseActivePlayer() {
        activePlayer++;
        if (activePlayer == playersCount) {
            activePlayer = 0;
        }
    }

    private void endGame() {
        //TODO
    }

    private void raisePlayerOnTurn() {
        if (isGameInOverCheck()) {
            playerOnTurn++;
            if (playerOnTurn == playersCount) {
                playerOnTurn = 0;
                gameOver = true;
                endGame();
            }
        } else {
            playerOnTurn++;
            if (playerOnTurn == playersCount) {
                playerOnTurn = 0;
            }
        }
    }

    private void recalculateShips() throws TwoShipsOfSameTypeOnTableException {
        cardsToTake = players.get(activePlayer).getCardsToTake();
        if (!Phase.OTHERS_PLAYERS_TRADING.equals(phase)) {
            int skiff = 0, flute = 0, pinace = 0, frigate = 0, galleon = 0;
            for (Card card : table.getCards()) {
                switch (card.getCardType()) {
                    case SKIFF:
                        skiff += 1;
                        break;
                    case FLUTE:
                        flute += 1;
                        break;
                    case PINACE:
                        pinace += 1;
                        break;
                    case FRIGATE:
                        frigate += 1;
                        break;
                    case GALLEON:
                        galleon += 1;
                        break;
                }
            }
            if (skiff == 2 || flute == 2 || pinace == 2 || frigate == 2 || galleon == 2) {
                throw new TwoShipsOfSameTypeOnTableException();
            }

            int typesCount = skiff + flute + pinace + frigate + galleon;
            if (typesCount == 4) {
                cardsToTake += 1;
            } else if (typesCount == 5) {
                cardsToTake += 2;
            }
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
            } else if (card.getCardType() == CardType.TAX_INFLUENCE && player.getInfluencePoints() == comparingValue) {
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

    private void applyJester() {
        if (table.getCountOfCards() == 0) {
            Player activeP = players.get(activePlayer);
            activeP.setCoins(activeP.getCoins() * activeP.getJestersCount());
        }
    }

    private void cardTypeValidation(Card card) throws InvalidActionException {
        if (Card.noActionTypes.contains(card.getCardType())) {
            throw new InvalidActionException("invalid card type");
        }
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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public Integer getPlayersCount() {
        return playersCount;
    }

    public void setPlayersCount(Integer playersCount) {
        this.playersCount = playersCount;
    }

    public Integer getPlayerOnTurn() {
        return playerOnTurn;
    }

    public void setPlayerOnTurn(Integer playerOnTurn) {
        this.playerOnTurn = playerOnTurn;
    }

    public Integer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Integer activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Expeditions getExpeditions() {
        return expeditions;
    }

    public void setExpeditions(Expeditions expeditions) {
        this.expeditions = expeditions;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public Integer getCardsToTake() {
        return cardsToTake;
    }

    public void setCardsToTake(Integer cardsToTake) {
        this.cardsToTake = cardsToTake;
    }

    public Boolean getAdmiralApplied() {
        return admiralApplied;
    }

    public void setAdmiralApplied(Boolean admiralApplied) {
        this.admiralApplied = admiralApplied;
    }

    public Boolean getGameOver() {
        return gameOver;
    }

    public void setGameOver(Boolean gameOver) {
        this.gameOver = gameOver;
    }

    public DrawPile getDrawPile() {
        return drawPile;
    }

    public void setDrawPile(DrawPile drawPile) {
        this.drawPile = drawPile;
    }
}
