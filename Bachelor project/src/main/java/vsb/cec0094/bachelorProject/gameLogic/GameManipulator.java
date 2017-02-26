package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.ArrayList;
import java.util.List;

public class GameManipulator {

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

    public GameManipulator(GameInQueue gameInQueue) throws CloneNotSupportedException {
        this.id = gameInQueue.getId();
        owner = gameInQueue.getOwner();
        currentGame = new Game(gameInQueue);
        semiStates = new ArrayList<>();
        actionsToShows = new ArrayList<>();
        currentAction = new ActionToShow(Action.PICK_CARD);
    }

    public GameManipulator() {
    }

    public void skipAction() {
        currentGame.skipAction();
        actionsToShows.clear();
        semiStates.clear();
    }

    public void applyAdmiral() {
        currentGame.applyAdmiral();
    }

    public void faceCard() throws CloneNotSupportedException, InvalidActionException {
        prepareForAction();
        ProcessActionAndSemiStateHolder(currentGame.faceCard());
        currentAction = null;
    }

    public void playerPickExpedition(int id) throws CloneNotSupportedException {
        prepareForAction();
        try {
            ProcessActionAndSemiStateHolder(currentGame.pickExpedition(id));
            currentAction = null;
        } catch (TooExpensiveExpeditionException e) {
            rollback();
            e.printStackTrace();
        }
    }

    public void playerGetCardFromTable(int id) throws CloneNotSupportedException {
        prepareForAction();
        try {
            //display picked card
            semiStates.add((Game) currentGame.clone());
            actionsToShows.add(new ActionToShow(Action.GET_CARD, new String[]{Table.TABLE}, new Integer[]{id}));
            ProcessActionAndSemiStateHolder(currentGame.playerGetCardFromTable(id));
            currentAction = null;
        } catch (InvalidActionException | TooExpensiveExpeditionException | IndexOutOfBoundsException e) {
            // IndexOutOfBoundsException if player call getCard while he can not take card;
            rollback();
            e.printStackTrace();
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
                .append("owner", owner)
                .append("actionsToShows", actionsToShows)
                .append("semiStates", semiStates)
                .append("currentAction", currentAction)
                .append("currentGame", currentGame)
                .append("backupGame", backupGame)
                .append("backupAction", backupAction)
                .toString();
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
