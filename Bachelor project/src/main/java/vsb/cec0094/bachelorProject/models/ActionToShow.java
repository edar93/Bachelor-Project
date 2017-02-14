package vsb.cec0094.bachelorProject.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ActionToShow implements Cloneable {

    private Action action;
    private List<String> marked;
    private List<Integer> ids;

    public ActionToShow(Action action) {
        this.action = action;
        marked = new ArrayList<>();
        ids = new ArrayList<>();
    }

    public ActionToShow(Action action, String marked, Integer id) {
        this.action = action;
        this.marked = new ArrayList<>();
        this.ids = new ArrayList<>();
        if (marked != null) {
            this.marked.add(marked);
        }
        if (id != null) {
            this.ids.add(id);
        }

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

    public static List<ActionToShow> getClonedList(List<ActionToShow> actionToShowList) throws CloneNotSupportedException {
        if (actionToShowList == null) {
            return null;
        }
        List<ActionToShow> clonedList = new ArrayList<>();
        for (ActionToShow action : actionToShowList) {
            clonedList.add((ActionToShow) action.clone());
        }
        return clonedList;
    }

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
                ids.stream()
                        .map(i -> {
                            return new Integer(i);
                        })
                        .collect(Collectors.toList())
        );
        return clone;
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
