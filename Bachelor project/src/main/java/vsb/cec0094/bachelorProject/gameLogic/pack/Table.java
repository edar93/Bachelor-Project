package vsb.cec0094.bachelorProject.gameLogic.pack;


import vsb.cec0094.bachelorProject.gameLogic.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Table {

    private List<Card> cards;

    public Table() {
        this.cards = new ArrayList<>();
    }

    public Card faceCard(DrawPile drawPile){
        Card card = drawPile.giveCard();
        cards.add(card);
        return card;
    };

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
