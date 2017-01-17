package vsb.cec0094.bachelorProject.gameLogic;

import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Player implements Cloneable {

    private String login;
    private int coins;
    private int influencePoints;
    private int swords;
    private List<Card> cards;

    private static final List<CardType> invalidTypes = Arrays.asList(CardType.EXPEDITION, CardType.TAX_INFLUENCE, CardType.TAX_SWORDS);
    //CardType.FLUTE, CardType.FRIGATE, CardType.GALLEON, CardType.PINACE, CardType.SKIFF

    public static List<Player> cloneList(List<Player> source) {
        return source.stream()
                .map(p -> {
                    try {
                        return (Player) p.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Player clone = (Player) super.clone();
        clone.setCards(Card.cloneList(cards));
        return clone;
    }

    public boolean getCardFromTable(Table table, int position) {
        Card card = table.getCards().get(position);
        if (!canCardBeTaken(card)) {
            return false;
        }
        cards.add(card);
        table.getCards().remove(position);
        return true;
    }

    private boolean canCardBeTaken(Card card) {
        if (invalidTypes.contains(card.getCardType())) {
            return false;
        }

        return true;
    }

    public Player(String login) {
        this.login = login;
        coins = 3;
        influencePoints = 0;
        swords = 0;
        cards = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getInfluencePoints() {
        return influencePoints;
    }

    public void setInfluencePoints(int influencePoints) {
        this.influencePoints = influencePoints;
    }

    public int getSwords() {
        return swords;
    }

    public void setSwords(int swords) {
        this.swords = swords;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
