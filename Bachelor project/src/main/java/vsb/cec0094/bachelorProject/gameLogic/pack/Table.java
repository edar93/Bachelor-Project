package vsb.cec0094.bachelorProject.gameLogic.pack;


import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;

import java.util.ArrayList;
import java.util.List;

public class Table implements Cloneable {

    public final static String TABLE = "TABLE";
    private List<Card> cards;

    public ActionToShow faceCard(DrawPile drawPile) {
        Card card = drawPile.giveCard();
        cards.add(card);
        return new ActionToShow(Action.FACE_CARD, new String[]{TABLE}, new Integer[]{cards.size() - 1});
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Table clone = (Table) super.clone();
        clone.setCards(Card.cloneList(cards));
        return clone;
    }

    public Table() {
        this.cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
