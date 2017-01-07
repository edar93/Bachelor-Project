package vsb.cec0094.bachelorProject.gameLogic.pack;


import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.card.Exptedition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPile {

    private List<Card> cards;

    public DrawPile(boolean full, boolean fivePlayers) {
        this.cards = new ArrayList<>();

        if (full) {
            cards.add(new Exptedition(CardType.EXPEDITION, 2, 4, 2, 0, 0));
            cards.add(new Exptedition(CardType.EXPEDITION, 2, 4, 0, 2, 0));
            cards.add(new Exptedition(CardType.EXPEDITION, 2, 4, 0, 0, 2));
            cards.add(new Exptedition(CardType.EXPEDITION, 3, 6, 0, 2, 1));
            cards.add(new Exptedition(CardType.EXPEDITION, 3, 6, 2, 0, 1));
            if (fivePlayers) {
                cards.add(new Exptedition(CardType.EXPEDITION, 3, 5, 1, 1, 1));
            }

            for (int i = 0; i < 2; i++) {
                cards.add(new Card(CardType.TAX_INFLUENCE));
                cards.add(new Card(CardType.TAX_SWORDS));
            }

            cards.add(new Card(CardType.SAILOR, 7, 3));
            cards.add(new Card(CardType.JESTER, 5, 1));
            cards.add(new Card(CardType.JESTER, 9, 3));
            cards.add(new Card(CardType.PIRATE, 5, 1));
            cards.add(new Card(CardType.PIRATE, 7, 2));
            cards.add(new Card(CardType.PIRATE, 9, 3));
            cards.add(new Card(CardType.ADMIRAL, 5, 1));
            for (int i = 0; i < 2; i++) {
                cards.add(new Card(CardType.SAILOR, 5, 2));
                cards.add(new Card(CardType.MADEMOISELLE, 7, 2));
                cards.add(new Card(CardType.MADEMOISELLE, 9, 3));
                cards.add(new Card(CardType.ADMIRAL, 9, 3));
                cards.add(new Card(CardType.TRADER_FLUTE, 3, 1));
                cards.add(new Card(CardType.TRADER_FRIGADE, 3, 1));
                cards.add(new Card(CardType.TRADER_GALLEON, 3, 1));
                cards.add(new Card(CardType.TRADER_PINACE, 3, 1));
                cards.add(new Card(CardType.TRADER_SKIFF, 3, 1));

            }
            for (int i = 0; i < 3; i++) {
                cards.add(new Card(CardType.JACK_OF_ALL_TRADES, 6, 1));
                cards.add(new Card(CardType.JESTER, 7, 2));
                cards.add(new Card(CardType.ADMIRAL, 7, 2));
            }
            for (int i = 0; i < 4; i++) {
                cards.add(new Card(CardType.GOVERNOR, 8, 0));
            }
            for (int i = 0; i < 5; i++) {
                cards.add(new Card(CardType.SETTLER, 4, 1));
                cards.add(new Card(CardType.CAPTAIN, 4, 1));
                cards.add(new Card(CardType.PRIEST, 4, 1));
            }
            for (int i = 0; i < 7; i++) {
                cards.add(new Card(CardType.SAILOR, 3, 1));
            }

            cards.add(new Card(CardType.SKIFF, 2, 1));
            cards.add(new Card(CardType.FLUTE, 2, 1));
            cards.add(new Card(CardType.FLUTE, 3, 2));
            cards.add(new Card(CardType.SKIFF, 3, 3));
            cards.add(new Card(CardType.SKIFF, 4, 5));
            cards.add(new Card(CardType.FLUTE, 4, 5));
            cards.add(new Card(CardType.FRIGATE, 3, 1000));
            cards.add(new Card(CardType.FRIGATE, 4, 1000));
            cards.add(new Card(CardType.GALLEON, 3, 1000));
            cards.add(new Card(CardType.GALLEON, 4, 1000));
            cards.add(new Card(CardType.PINACE, 2, 1));
            cards.add(new Card(CardType.PINACE, 3, 2));
            cards.add(new Card(CardType.PINACE, 4, 4));
            for (int i = 0; i < 2; i++) {
                cards.add(new Card(CardType.SKIFF, 2, 3));
                cards.add(new Card(CardType.FLUTE, 2, 2));
                cards.add(new Card(CardType.SKIFF, 3, 5));
                cards.add(new Card(CardType.FLUTE, 3, 5));
                cards.add(new Card(CardType.FRIGATE, 3, 6));
                cards.add(new Card(CardType.GALLEON, 3, 7));
                cards.add(new Card(CardType.PINACE, 2, 2));
                cards.add(new Card(CardType.PINACE, 3, 4));
            }
            for (int i = 0; i < 3; i++) {
                cards.add(new Card(CardType.SKIFF, 1, 1));
                cards.add(new Card(CardType.FLUTE, 1, 1));
                cards.add(new Card(CardType.FRIGATE, 1, 1));
                cards.add(new Card(CardType.FRIGATE, 2, 3));
                cards.add(new Card(CardType.GALLEON, 1, 2));
                cards.add(new Card(CardType.GALLEON, 2, 4));
                cards.add(new Card(CardType.PINACE, 1, 1));
            }
        }
        //TODO restore shuffle
        //Collections.shuffle(cards);

    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
