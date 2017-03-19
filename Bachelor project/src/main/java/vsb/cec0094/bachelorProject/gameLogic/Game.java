package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.exceptions.TwoShipsOfSameTypeOnTableException;
import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.gameLogic.pack.Expeditions;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.gameLogic.services.FaceCardService;
import vsb.cec0094.bachelorProject.gameLogic.services.GameUtilsService;
import vsb.cec0094.bachelorProject.gameLogic.services.GetCardFromTableService;
import vsb.cec0094.bachelorProject.gameLogic.services.PlayerSwittchingService;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Cloneable, Serializable {

    @JsonIgnore
    private FaceCardService faceCardService = new FaceCardService(this);
    @JsonIgnore
    private GetCardFromTableService getCardFromTableService = new GetCardFromTableService(this);
    @JsonIgnore
    private GameUtilsService gameUtilsService = new GameUtilsService(this);
    @JsonIgnore
    private PlayerSwittchingService playerSwittchingService = new PlayerSwittchingService(this);

    private Integer id;
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

    // TODO remove comment
    //@JsonIgnore
    private DrawPile drawPile;


    public Game(GameInQueue gameInQueue) {
        initServices();
        this.id = gameInQueue.getId();
        gameOver = false;
        expeditions = new Expeditions();
        playersCount = gameInQueue.getPlayersList().size();
        playerOnTurn = 0;
        activePlayer = 0;
        phase = Phase.EXPLORING;
        table = new Table();
        drawPile = new DrawPile(playersCount == 5);
        owner = gameInQueue.getOwner();
        players = new ArrayList<>(playersCount);
        admiralApplied = false;
        players = gameInQueue.getPlayersList().stream()
                .map(p -> new Player(p.getLogin()))
                .collect(Collectors.toList());
        try {
            gameUtilsService.recalculateShips();
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

    public Player getActivePlayerAsPlayer() {
        return players.get(activePlayer);
    }

    public void initServices() {
        faceCardService = new FaceCardService(this);
        getCardFromTableService = new GetCardFromTableService(this);
        gameUtilsService = new GameUtilsService(this);
        playerSwittchingService = new PlayerSwittchingService(this);
    }

    public void applyAdmiral() {
        gameUtilsService.applyAdmiral();
    }

    public void skipAction() {
        playerSwittchingService.shiftPlayer(false);
    }

    public ActionAndSemiStateHolder playerGetCardFromTable(int cardPosition) throws InvalidActionException, TooExpensiveExpeditionException, CloneNotSupportedException {
        return getCardFromTableService.playerGetCardFromTable(cardPosition);
    }

    public ActionAndSemiStateHolder faceCard() throws CloneNotSupportedException, InvalidActionException {
        return faceCardService.faceCard();
    }

    public ActionAndSemiStateHolder pickExpedition(int id) throws TooExpensiveExpeditionException, CloneNotSupportedException {
        return getCardFromTableService.pickExpedition(id);
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
            clone.initServices();
            return clone;
        } catch (CloneNotSupportedException e) {
            System.out.println("Cloning not allowed.");
            e.printStackTrace();
            return null;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
