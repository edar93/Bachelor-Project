package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.card.Expedition;
import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.models.ActionToShow;
import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "Stats_player")
public class Player implements Cloneable {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "login")
    private String login;

    @Column(name = "coins")
    private int coins;

    @Column(name = "influencePoints")
    private int influencePoints;

    @Column(name = "swords")
    private int swords;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    private StatsRecord record;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_owner")
    private List<Card> cards;

    @JsonIgnore
    @Transient
    private int crossCount, anchorCount, hutCount, jackOfAllTradesCount, discount, jestersCount, admiralsCount, cardsToTake,
            traderPinaceCount, traderFluteCount, traderSkiffCount, traderFrigadeCount, traderGalleonCount;

    public Player(String login) {
        this.login = login;
        coins = 3;
        cards = new ArrayList<>();
        cleanVariables();
    }

    public Player() {
    }

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

    public void canTakeExpedition(Expedition expedition) throws TooExpensiveExpeditionException {
        expedition.canBeTaken(crossCount, anchorCount, hutCount, jackOfAllTradesCount);
    }

    public void addCoin(){
        coins++;
    }

    public void getCoinsFromShip(Card card) {
        coins += card.getCoin();

        CardType shipType = card.getCardType();
        if (CardType.FRIGATE.equals(shipType)) {
            coins += traderFrigadeCount;
        } else if (CardType.GALLEON.equals(shipType)) {
            coins += traderGalleonCount;
        } else if (CardType.PINACE.equals(shipType)) {
            coins += traderPinaceCount;
        } else if (CardType.FLUTE.equals(shipType)) {
            coins += traderFluteCount;
        } else if (CardType.SKIFF.equals(shipType)) {
            coins += traderSkiffCount;
        }
    }

    public void takeCharacterCard(Card card, Boolean isOnTurn) throws TooExpensiveExpeditionException {
        int price = card.getCoin() - discount;
        if (!isOnTurn) {
            price += 1;
        }
        if (price < 0) {
            price = 0;
        }
        coins -= price;
        if (coins < 0) {
            throw new TooExpensiveExpeditionException(card + " is too expensive");
        }
        cards.add(card);
    }

    public ActionToShow takeExpedition(Expedition expedition, DrawPile drawPile) {
        coins += expedition.getCoin();
        int anchors = expedition.getAnchor();
        int huts = expedition.getHut();
        int crosses = expedition.getCross();

        for (int i = cards.size() - 1; i >= 0; i--) {
            if (cards.get(i).getCardType().equals(CardType.CAPTAIN) && anchors > 0) {
                anchors--;
                drawPile.getUsedCard(cards.remove(i));
            } else if (cards.get(i).getCardType().equals(CardType.SETTLER) && huts > 0) {
                huts--;
                drawPile.getUsedCard(cards.remove(i));
            } else if (cards.get(i).getCardType().equals(CardType.PRIEST) && crosses > 0) {
                crosses--;
                drawPile.getUsedCard(cards.remove(i));
            }
        }

        for (int i = cards.size() - 1; i >= 0; i--) {
            if (cards.get(i).getCardType().equals(CardType.JACK_OF_ALL_TRADES) && anchors > 0) {
                anchors--;
                drawPile.getUsedCard(cards.remove(i));
            } else if (cards.get(i).getCardType().equals(CardType.JACK_OF_ALL_TRADES) && huts > 0) {
                huts--;
                drawPile.getUsedCard(cards.remove(i));
            } else if (cards.get(i).getCardType().equals(CardType.JACK_OF_ALL_TRADES) && crosses > 0) {
                crosses--;
                drawPile.getUsedCard(cards.remove(i));
            }
        }

        cards.add(expedition);
        updateVariables();

        return new ActionToShow(null, this.login, cards.indexOf(expedition));
    }

    public void updateVariables() {
        cleanVariables();

        for (Card card : cards) {
            switch (card.getCardType()) {
                case ADMIRAL:
                    admiralsCount++;
                    break;
                case CAPTAIN:
                    anchorCount++;
                    break;
                case GOVERNOR:
                    cardsToTake++;
                    break;
                case JACK_OF_ALL_TRADES:
                    jackOfAllTradesCount++;
                    break;
                case JESTER:
                    jestersCount++;
                    break;
                case MADEMOISELLE:
                    discount++;
                    break;
                case PIRATE:
                    swords = swords + 2;
                    break;
                case PRIEST:
                    crossCount++;
                    break;
                case SAILOR:
                    swords++;
                    break;
                case SETTLER:
                    hutCount++;
                    break;
                case TRADER_FLUTE:
                    traderFluteCount++;
                    break;
                case TRADER_FRIGADE:
                    traderFrigadeCount++;
                    break;
                case TRADER_GALLEON:
                    traderGalleonCount++;
                    break;
                case TRADER_PINACE:
                    traderPinaceCount++;
                    break;
                case TRADER_SKIFF:
                    traderSkiffCount++;
                    break;
            }
            influencePoints += card.getInfluence();
        }
        Collections.sort(cards);
    }

    private void cleanVariables() {
        this.cardsToTake = 1;
        this.influencePoints = 0;
        this.swords = 0;
        this.crossCount = 0;
        this.anchorCount = 0;
        this.hutCount = 0;
        this.jackOfAllTradesCount = 0;
        this.discount = 0;
        this.jestersCount = 0;
        this.admiralsCount = 0;
        this.traderPinaceCount = 0;
        this.traderFluteCount = 0;
        this.traderSkiffCount = 0;
        this.traderFrigadeCount = 0;
        this.traderGalleonCount = 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Player clone = (Player) super.clone();
        clone.setCards(Card.cloneList(cards));
        return clone;
    }

    public StatsRecord getRecord() {
        return record;
    }

    public void setRecord(StatsRecord record) {
        this.record = record;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getCrossCount() {
        return crossCount;
    }

    public void setCrossCount(int crossCount) {
        this.crossCount = crossCount;
    }

    public int getAnchorCount() {
        return anchorCount;
    }

    public void setAnchorCount(int anchorCount) {
        this.anchorCount = anchorCount;
    }

    public int getHutCount() {
        return hutCount;
    }

    public void setHutCount(int hutCount) {
        this.hutCount = hutCount;
    }

    public int getJackOfAllTradesCount() {
        return jackOfAllTradesCount;
    }

    public void setJackOfAllTradesCount(int jackOfAllTradesCount) {
        this.jackOfAllTradesCount = jackOfAllTradesCount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getJestersCount() {
        return jestersCount;
    }

    public void setJestersCount(int jestersCount) {
        this.jestersCount = jestersCount;
    }

    public int getAdmiralsCount() {
        return admiralsCount;
    }

    public void setAdmiralsCount(int admiralsCount) {
        this.admiralsCount = admiralsCount;
    }

    public int getCardsToTake() {
        return cardsToTake;
    }

    public void setCardsToTake(int cardsToTake) {
        this.cardsToTake = cardsToTake;
    }

    public int getTraderPinaceCount() {
        return traderPinaceCount;
    }

    public void setTraderPinaceCount(int traderPinaceCount) {
        this.traderPinaceCount = traderPinaceCount;
    }

    public int getTraderFluteCount() {
        return traderFluteCount;
    }

    public void setTraderFluteCount(int traderFluteCount) {
        this.traderFluteCount = traderFluteCount;
    }

    public int getTraderSkiffCount() {
        return traderSkiffCount;
    }

    public void setTraderSkiffCount(int traderSkiffCount) {
        this.traderSkiffCount = traderSkiffCount;
    }

    public int getTraderFrigadeCount() {
        return traderFrigadeCount;
    }

    public void setTraderFrigadeCount(int traderFrigadeCount) {
        this.traderFrigadeCount = traderFrigadeCount;
    }

    public int getTraderGalleonCount() {
        return traderGalleonCount;
    }

    public void setTraderGalleonCount(int traderGalleonCount) {
        this.traderGalleonCount = traderGalleonCount;
    }
}
