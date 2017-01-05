package vsb.cec0094.bachelorProject.gameLogic.card;

public class Card {

    private CardType cardType;
    private int coin;
    private int influence;

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
