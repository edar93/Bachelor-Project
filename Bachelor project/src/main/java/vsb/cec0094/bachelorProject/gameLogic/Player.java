package vsb.cec0094.bachelorProject.gameLogic;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String login;
    private int coins;
    private int influencePoints;
    private int swords;
    private List<Card> cards;

    public Player(String login){
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
