package vsb.cec0094.bachelorProject.gameLogic.pack;

import vsb.cec0094.bachelorProject.gameLogic.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Expeditions {

    private List<Card> cards;

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
