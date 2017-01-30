package vsb.cec0094.bachelorProject.gameLogic;

import vsb.cec0094.bachelorProject.models.ActionToShow;

import java.util.ArrayList;
import java.util.List;

public class ActionAndSemiStateHolder {

    private List<ActionToShow> actionToShowList;
    private List<Game> gameList;

    public void addState(Game game, ActionToShow actionToShow) throws CloneNotSupportedException {
        gameList.add((Game) game.clone());
        actionToShowList.add((ActionToShow) actionToShow.clone());
    }

    public ActionAndSemiStateHolder(Game game, ActionToShow actionToShow) throws CloneNotSupportedException {
        gameList = new ArrayList<>();
        actionToShowList = new ArrayList<>();
        gameList.add((Game) game.clone());
        actionToShowList.add((ActionToShow) actionToShow.clone());
    }

    public ActionAndSemiStateHolder() {
        gameList = new ArrayList<>();
        actionToShowList = new ArrayList<>();
    }

    public List<ActionToShow> getActionToShowList() {
        return actionToShowList;
    }

    public void setActionToShowList(List<ActionToShow> actionToShowList) {
        this.actionToShowList = actionToShowList;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public void setGameList(List<Game> gameList) {
        this.gameList = gameList;
    }
}
