package vsb.cec0094.bachelorProject.models;

import vsb.cec0094.bachelorProject.gameLogic.Game;

public class SemiState {

    Game game;
    ActionToShow actionToShow;

    public SemiState() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ActionToShow getActionToShow() {
        return actionToShow;
    }

    public void setActionToShow(ActionToShow actionToShow) {
        this.actionToShow = actionToShow;
    }
}
