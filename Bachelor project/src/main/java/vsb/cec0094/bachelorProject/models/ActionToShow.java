package vsb.cec0094.bachelorProject.models;

import java.util.ArrayList;
import java.util.List;

public class ActionToShow {

    private Action action;
    private List<String> preMarked;
    private List<Integer> preIds;
    private List<String> marked;
    private List<Integer> ids;

    public ActionToShow() {
        preMarked = new ArrayList<>();
        marked = new ArrayList<>();
        preIds = new ArrayList<>();
        ids = new ArrayList<>();
    }

    public List<Integer> getPreIds() {
        return preIds;
    }

    public void setPreIds(List<Integer> preIds) {
        this.preIds = preIds;
    }

    public List<String> getPreMarked() {
        return preMarked;
    }

    public void setPreMarked(List<String> preMarked) {
        this.preMarked = preMarked;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<String> getMarked() {
        return marked;
    }

    public void setMarked(List<String> marked) {
        this.marked = marked;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
