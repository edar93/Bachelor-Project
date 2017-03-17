package vsb.cec0094.bachelorProject.gameLogic.card;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "card")
public class Card implements Cloneable, Comparable<Card> {

    @Transient
    public static final List<CardType> noActionTypes = Arrays.asList(CardType.EXPEDITION, CardType.TAX_INFLUENCE, CardType.TAX_SWORDS);
    @Transient
    public static final List<CardType> shipTypes = Arrays.asList(CardType.FLUTE, CardType.FRIGATE,
            CardType.GALLEON, CardType.PINACE, CardType.SKIFF);
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "Card_type")
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "coin")
    private int coin;

    @Column(name = "influence")
    private int influence;

    public Card(CardType cardType, int coin, int influence) {
        this.cardType = cardType;
        this.coin = coin;
        this.influence = influence;
    }

    public Card() {
    }

    public Card(CardType cardType) {
        this.cardType = cardType;
    }

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

    public static List<CardType> getNoActionTypes() {
        return noActionTypes;
    }

    public static List<CardType> getShipTypes() {
        return shipTypes;
    }

    @Override
    public int compareTo(Card card) {
        int comparingCardValue = card.getCardType().ordinal();
        int localCardValue = this.cardType.ordinal();
        if (comparingCardValue < localCardValue) {
            return 1;
        } else if (comparingCardValue > localCardValue) {
            return -1;
        } else {
            int localInfluence = this.getInfluence();
            int comparingInfluence = card.getInfluence();
            if (comparingInfluence < localInfluence) {
                return 1;
            } else if (comparingInfluence > localInfluence) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
