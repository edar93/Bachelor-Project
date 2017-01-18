package vsb.cec0094.bachelorProject.models;

import java.util.ArrayList;
import java.util.List;

public class ActionToShow {

    private Action action;
    private List<String> markedPlayers;
    private List<Integer> ids;

    public ActionToShow() {
        markedPlayers = new ArrayList<>();
        ids = new ArrayList<>();
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<String> getMarkedPlayers() {
        return markedPlayers;
    }

    public void setMarkedPlayers(List<String> markedPlayers) {
        this.markedPlayers = markedPlayers;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
