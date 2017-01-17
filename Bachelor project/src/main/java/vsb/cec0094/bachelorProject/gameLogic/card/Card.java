package vsb.cec0094.bachelorProject.gameLogic.card;

import java.util.List;
import java.util.stream.Collectors;

public class Card implements Cloneable {

    private CardType cardType;
    private int coin;
    private int influence;

    public static List<Card> cloneList(List<Card> source) {
        return source.stream()
                .map(c -> {
                    try {
                        return (Card) c.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Card(CardType cardType, int coin, int influence) {
        this.cardType = cardType;
        this.coin = coin;
        this.influence = influence;
    }

    public Card(CardType cardType) {
        this.cardType = cardType;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public int getInfluence() {
        return influence;
    }

    public void setInfluence(int influence) {
        this.influence = influence;
    }
}
