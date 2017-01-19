package vsb.cec0094.bachelorProject.models;

import vsb.cec0094.bachelorProject.gameLogic.Game;

public class GameManipulator {

    private String owner;
    private ActionToShow lastAction;
    private Game currentGame;
    private Game semiState;


    public void faceCard() throws CloneNotSupportedException {
        semiState = (Game) currentGame.clone();
        currentGame.faceCard();
    }

    public void playerGetCardFromTable(int id) {
        currentGame.playerGetCardFromTable(id);
    }

    public GameManipulator(GameInQueue gameInQueue) throws CloneNotSupportedException {
        owner = gameInQueue.getOwner();
        currentGame = new Game(gameInQueue);
        semiState = (Game) currentGame.clone();
    }

    public GameManipulator() {

    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public ActionToShow getLastAction() {
        return lastAction;
    }

    public void setLastAction(ActionToShow lastAction) {
        this.lastAction = lastAction;
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }

    public Game getSemiState() {
        return semiState;
    }

    public void setSemiState(Game semiState) {
        this.semiState = semiState;
    }
}
