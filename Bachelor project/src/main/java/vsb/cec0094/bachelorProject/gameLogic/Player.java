package vsb.cec0094.bachelorProject.gameLogic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.card.Expedition;
import vsb.cec0094.bachelorProject.gameLogic.pack.DrawPile;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Player implements Cloneable {

    private String login;
    private int coins;
    private int influencePoints;
    private int swords;
    private List<Card> cards;

    @JsonIgnore
    private int crossCount, anchorCount, hutCount, jackOfAllTradesCount, discount, jestersCount, admiralsCount, baseCardsToTake,
            traderPinaceCount, traderFluteCount, traderSkiffCount, traderFrigadeCount, traderGalleonCount;

    private static final List<CardType> invalidTypes = Arrays.asList(CardType.EXPEDITION, CardType.TAX_INFLUENCE, CardType.TAX_SWORDS);
    //CardType.FLUTE, CardType.FRIGATE, CardType.GALLEON, CardType.PINACE, CardType.SKIFF

    public void canTakeExpedition(Expedition expedition) throws TooExpensiveExpeditionException {
        expedition.canBeTaken(crossCount, anchorCount, hutCount, jackOfAllTradesCount);
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

    public ActionToShow getCardFromTable(Table table, int position) throws InvalidActionException {
        Card card = table.getCards().get(position);
        canBeCardTaken(card);
        cards.add(card);
        table.getCards().remove(position);
        updateVariables();
        return new ActionToShow(Action.GET_CARD, new String[]{this.login}, new Integer[]{cards.indexOf(card)});
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        Player clone = (Player) super.clone();
        clone.setCards(Card.cloneList(cards));
        return clone;
    }

    private void updateVariables() {
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
                    baseCardsToTake++;
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
        this.baseCardsToTake = 1;
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

    private void canBeCardTaken(Card card) throws InvalidActionException {
        if (invalidTypes.contains(card.getCardType())) {
            throw new InvalidActionException("invalid card type");
        }
    }

    public Player(String login) {
        this.login = login;
        coins = 3;
        cards = new ArrayList<>();
        cleanVariables();
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

    public int getBaseCardsToTake() {
        return baseCardsToTake;
    }

    public void setBaseCardsToTake(int baseCardsToTake) {
        this.baseCardsToTake = baseCardsToTake;
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

    public static List<CardType> getInvalidTypes() {
        return invalidTypes;
    }
}
