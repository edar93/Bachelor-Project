package vsb.cec0094.bachelorProject.models;

import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;

import java.util.ArrayList;
import java.util.List;

public class GameManipulator {

    private String owner;
    private List<ActionToShow> actionsToShows;
    private List<Game> semiStates;
    private ActionToShow currentMove;
    private Game currentGame;


    public void faceCard() throws CloneNotSupportedException {
        actionsToShows = new ArrayList<>();
        actionsToShows.add(currentGame.faceCard());

        //TODO test data - here
        List<Integer> ids = new ArrayList();
        ids.add(1);
        List<String> who = new ArrayList();
        who.add(Table.TABLE);
        currentMove.setIds(ids);
        currentMove.setMarked(who);

//        currentMove = new ActionToShow(Action.PICK_CARD);
    }

    public void playerGetCardFromTable(int id) {
        currentGame.playerGetCardFromTable(id);
    }

    public GameManipulator(GameInQueue gameInQueue) throws CloneNotSupportedException {
        owner = gameInQueue.getOwner();
        currentGame = new Game(gameInQueue);
        semiStates = new ArrayList<>();
        actionsToShows = new ArrayList<>();
        currentMove = new ActionToShow(Action.PICK_CARD);
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

    public ActionToShow getCurrentMove() {
        return currentMove;
    }

    public void setCurrentMove(ActionToShow currentMove) {
        this.currentMove = currentMove;
    }
}
