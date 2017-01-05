package vsb.cec0094.bachelorProject.gameLogic.pack;


import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.card.Exptedition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile{

    private List<Card> cards;

    public DrawPile(boolean full, boolean fivePlayers) {
        this.cards = new ArrayList<>();

        if(full){
            cards.add(new Exptedition(CardType.EXPEDITION, 2, 4, 2, 0, 0));
            cards.add(new Exptedition(CardType.EXPEDITION, 2, 4, 0, 2, 0));
            cards.add(new Exptedition(CardType.EXPEDITION, 2, 4, 0, 0, 2));
            cards.add(new Exptedition(CardType.EXPEDITION, 3, 6, 0, 2, 1));
            cards.add(new Exptedition(CardType.EXPEDITION, 3, 6, 2, 0, 1));
            for(int i = 0; i < 3; i++){
                cards.add(new Card(CardType.JACK_OF_ALL_TRADES, 6, 1));
            }
            for(int i = 0; i < 4; i++){
                cards.add(new Card(CardType.GOVERNOR, 8, 0));
            }
            for(int i = 0; i < 5; i++){
                cards.add(new Card(CardType.SETTLER, 4, 1));
                cards.add(new Card(CardType.CAPTAIN, 4, 1));
                cards.add(new Card(CardType.PRIEST, 4, 1));
            }
        }
        if(fivePlayers){
            cards.add(new Exptedition(CardType.EXPEDITION, 3, 5, 1, 1, 1));
        }
        Collections.shuffle(cards);

    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
