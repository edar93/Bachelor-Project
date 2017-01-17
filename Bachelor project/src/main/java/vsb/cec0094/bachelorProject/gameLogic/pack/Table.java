package vsb.cec0094.bachelorProject.gameLogic.pack;


import vsb.cec0094.bachelorProject.gameLogic.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Table implements Cloneable {

    private List<Card> cards;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Table clone = (Table) super.clone();
        clone.setCards(Card.cloneList(cards));
        return clone;
    }

    public Card faceCard(DrawPile drawPile) {
        Card card = drawPile.giveCard();
        cards.add(card);
        return card;
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
