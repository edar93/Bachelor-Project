package vsb.cec0094.bachelorProject.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActionToShow implements Cloneable {

    private Action action;
    private List<String> marked;
    private List<Integer> ids;

    @Override
    public Object clone() throws CloneNotSupportedException {
        ActionToShow clone = (ActionToShow) super.clone();
        clone.setMarked(
                marked.stream()
                        .map(c -> {
                            return c;
                        })
                        .collect(Collectors.toList())
        );
        clone.setIds(
                marked.stream()
                        .map(c -> {
                            return new Integer(c);
                        })
                        .collect(Collectors.toList())
        );
        return clone;
    }

    public ActionToShow(Action action) {
        this.action = action;
        marked = new ArrayList<>();
        ids = new ArrayList<>();
    }

    public ActionToShow(Action action, String[] marked, Integer[] ids) {
        this.action = action;
        this.marked = new ArrayList<>(Arrays.asList(marked));
        this.ids = new ArrayList<>(Arrays.asList(ids));
    }

    public ActionToShow(Action action, List<String> marked, List<Integer> ids) {
        this.action = action;
        this.marked = marked;
        this.ids = ids;
    }

    public ActionToShow() {
        marked = new ArrayList<>();
        ids = new ArrayList<>();
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
