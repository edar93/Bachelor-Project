package vsb.cec0094.bachelorProject.gameLogic.pack;

import vsb.cec0094.bachelorProject.gameLogic.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Expeditions implements Cloneable {

    private List<Card> cards;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Expeditions clone = (Expeditions) super.clone();
        clone.setCards(Card.cloneList(cards));
        return clone;
    }

    public Expeditions() {
        cards = new ArrayList<>();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
