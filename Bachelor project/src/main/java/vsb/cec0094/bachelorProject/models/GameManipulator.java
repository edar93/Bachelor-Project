package vsb.cec0094.bachelorProject.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;

import java.util.ArrayList;
import java.util.List;

public class GameManipulator {

    private String owner;
    private List<ActionToShow> actionsToShows;
    private List<Game> semiStates;
    private ActionToShow currentAction;
    private Game currentGame;
    @JsonIgnore
    private Game backupGame;
    @JsonIgnore
    private ActionToShow backupAction;

    public void faceCard() throws CloneNotSupportedException {
        prepaireForAction();

        actionsToShows.add(currentGame.faceCard());
        semiStates.add((Game) currentGame.clone());
        currentAction = null;
    }

    public void playerGetCardFromTable(int id) throws CloneNotSupportedException {
        prepaireForAction();

        try {
            //display picked card
            semiStates.add((Game) currentGame.clone());
            actionsToShows.add(new ActionToShow(Action.GET_CARD, new String[]{Table.TABLE}, new Integer[]{id}));
            actionsToShows.add(currentGame.playerGetCardFromTable(id));
            semiStates.add((Game) currentGame.clone());
            currentAction = null;
        } catch (InvalidActionException e) {
            rollback();
            e.printStackTrace();
        }
    }

    private void rollback() {
        currentGame = backupGame;
        currentAction = backupAction;
    }

    private void prepaireForAction() throws CloneNotSupportedException {
        actionsToShows.clear();
        semiStates.clear();
        backupGame = (Game) currentGame.clone();
        backupAction = (ActionToShow) currentAction.clone();
    }

    public GameManipulator(GameInQueue gameInQueue) throws CloneNotSupportedException {
        owner = gameInQueue.getOwner();
        currentGame = new Game(gameInQueue);
        semiStates = new ArrayList<>();
        actionsToShows = new ArrayList<>();
        currentAction = new ActionToShow(Action.PICK_CARD);
    }

    public GameManipulator() {
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
