package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveCharacterException;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;
import java.util.List;

public class GameManipulator {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameManipulator.class);

    private Integer id;
    private String owner;
    private List<ActionToShow> actionsToShows;
    private List<Game> semiStates;
    private ActionToShow currentAction;
    private Game currentGame;
    @JsonIgnore
    private Game backupGame;
    @JsonIgnore
    private ActionToShow backupAction;

    public GameManipulator(GameInQueue gameInQueue) {
        this.id = gameInQueue.getId();
        owner = gameInQueue.getOwner();
        currentGame = new Game(gameInQueue);
        semiStates = new ArrayList<>();
        actionsToShows = new ArrayList<>();
        currentAction = new ActionToShow(Action.PICK_CARD);
    }

    public GameManipulator() {
    }

    public Boolean gameOverStatus() {
        return currentGame.getGameOver();
    }

    public void skipAction() {
        currentGame.skipAction();
        actionsToShows.clear();
        semiStates.clear();
    }

    public void applyAdmiral() throws CloneNotSupportedException, InvalidActionException {
        prepareForAction();
        try {
            currentGame.applyAdmiral();
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
            throw new InvalidActionException("invalid action", e);
        }
    }

    public void faceCard() throws CloneNotSupportedException, InvalidActionException {
        prepareForAction();
        try {
            ProcessActionAndSemiStateHolder(currentGame.faceCard());
            currentAction = null;
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
            throw new InvalidActionException("invalid action", e);
        }

    }

    public void playerPickExpedition(int id) throws CloneNotSupportedException, InvalidActionException {
        prepareForAction();
        try {
            ProcessActionAndSemiStateHolder(currentGame.pickExpedition(id));
            currentAction = null;
        } catch (Exception e) {
            //TooExpensiveExpeditionException
            rollback();
            e.printStackTrace();
            throw new InvalidActionException("invalid action", e);
        }
    }

    public void playerGetCardFromTable(int id) throws CloneNotSupportedException, InvalidActionException {
        prepareForAction();
        try {
            //display picked card
            semiStates.add((Game) currentGame.clone());
            actionsToShows.add(new ActionToShow(Action.GET_CARD, new String[]{Table.TABLE}, new Integer[]{id}));
            ProcessActionAndSemiStateHolder(currentGame.playerGetCardFromTable(id));
            currentAction = null;
        } catch (InvalidActionException | TooExpensiveCharacterException | IndexOutOfBoundsException e) {
            LOGGER.debug("exeption > " + e.getMessage() + " was catched");
            // IndexOutOfBoundsException if player call getCard while he can not take card;
            rollback();
            e.printStackTrace();
            throw new InvalidActionException("invalid action", e);
        }
    }

    private void ProcessActionAndSemiStateHolder(ActionAndSemiStateHolder actionAndSemiStateHolder) {
        if (actionAndSemiStateHolder != null) {
            semiStates.addAll(actionAndSemiStateHolder.getGameList());
            actionsToShows.addAll(actionAndSemiStateHolder.getActionToShowList());
        }
    }

    private void rollback() {
        actionsToShows.clear();
        semiStates.clear();
        currentGame = backupGame;
        currentAction = backupAction;
    }

    private void prepareForAction() throws CloneNotSupportedException {
        actionsToShows.clear();
        semiStates.clear();
        backupGame = (Game) currentGame.clone();
        if (currentAction != null) {
            backupAction = (ActionToShow) currentAction.clone();
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("owner", owner)
                .append("actionsToShows", actionsToShows)
                .append("semiStates", semiStates)
                .append("currentAction", currentAction)
                .append("currentGame", currentGame)
                .append("backupGame", backupGame)
                .append("backupAction", backupAction)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        GameManipulator that = (GameManipulator) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(owner, that.owner)
                .append(actionsToShows, that.actionsToShows)
                .append(semiStates, that.semiStates)
                .append(currentAction, that.currentAction)
                .append(currentGame, that.currentGame)
                .append(backupGame, that.backupGame)
                .append(backupAction, that.backupAction)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(owner)
                .append(actionsToShows)
                .append(semiStates)
                .append(currentAction)
                .append(currentGame)
                .append(backupGame)
                .append(backupAction)
                .toHashCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Game getBackupGame() {
        return backupGame;
    }

    public void setBackupGame(Game backupGame) {
        this.backupGame = backupGame;
    }

    public ActionToShow getBackupAction() {
        return backupAction;
    }

    public void setBackupAction(ActionToShow backupAction) {
        this.backupAction = backupAction;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<ActionToShow> getActionsToShows() {
        return actionsToShows;
    }

    public void setActionsToShows(List<ActionToShow> actionsToShows) {
        this.actionsToShows = actionsToShows;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public List<Game> getSemiStates() {
        return semiStates;
    }

    public void setSemiStates(List<Game> semiStates) {
        this.semiStates = semiStates;
    }

    public ActionToShow getCurrentAction() {
        return currentAction;
    }

    public void setCurrentAction(ActionToShow currentAction) {
        this.currentAction = currentAction;
    }
}
