package vsb.cec0094.bachelorProject.gameLogic.services;

import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TwoShipsOfSameTypeOnTableException;
import vsb.cec0094.bachelorProject.gameLogic.ActionAndSemiStateHolder;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Phase;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.gameLogic.pack.Expeditions;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;

public class FaceCardService {

    private GameUtilsService gameUtilsService;
    private PlayerSwittchingService playerSwittchingService;
    private Game game;

    public FaceCardService(Game game) {
        this.game = game;
        playerSwittchingService = new PlayerSwittchingService(game);
        gameUtilsService = new GameUtilsService(game);
    }

    public ActionAndSemiStateHolder faceCard() throws CloneNotSupportedException, InvalidActionException {
        if (!Phase.EXPLORING.equals(game.getPhase())) {
            throw new InvalidActionException("face card is not allowed now");
        }
        Card card = game.getDrawPile().giveCard();

        if (card.getCardType() == CardType.EXPEDITION) {
            return faceExpedition(card);
        } else if (card.getCardType() == CardType.TAX_INFLUENCE || card.getCardType() == CardType.TAX_SWORDS) {
            return faceTaxes(card);
        } else if (Card.shipTypes.contains(card.getCardType())) {
            //TODO repel ship
            ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
            actionAndSemiStateHolder.addState(game, game.getTable().faceCard(card));

            try {
                gameUtilsService.recalculateShips();
            } catch (TwoShipsOfSameTypeOnTableException e) {
                actionAndSemiStateHolder.emphasizeLastAction();
                playerSwittchingService.shiftPlayer(true);
                actionAndSemiStateHolder.addState(game, new ActionToShow(Action.PICK_CARD));
                return actionAndSemiStateHolder;
            }
            return actionAndSemiStateHolder;
        } else {
            ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
            actionAndSemiStateHolder.addState(game, game.getTable().faceCard(card));
            return actionAndSemiStateHolder;
        }
    }

    private ActionAndSemiStateHolder faceExpedition(Card card) throws CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
        //show card on table
        game.getTable().addCard(card);
        ActionToShow actionToShow = new ActionToShow(Action.SHOW_FACED_EXPEDITION_ON_TABLE, Table.TABLE, game.getTable().getCards().size() - 1);
        actionAndSemiStateHolder.addState(game, actionToShow);
        //show card between expeditions
        game.getExpeditions().addCard(game.getTable().removeLastCard());
        actionToShow = new ActionToShow(Action.SHOW_FACED_EXPEDITION_ON_EXPEDITIONS_PACK, Expeditions.EXPEDITIONS, game.getExpeditions().getCards().size() - 1);
        actionAndSemiStateHolder.addState(game, actionToShow);
        return actionAndSemiStateHolder;
    }

    private ActionAndSemiStateHolder faceTaxes(Card card) throws CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
        int comparingValue;

        actionAndSemiStateHolder.addState(game, new ActionToShow(Action.SHOW_FACED_TAXES_PRE, Table.TABLE, game.getTable().getCards().size() - 1));
        if (card.getCardType() == CardType.TAX_SWORDS) {
            comparingValue = 0;
        } else {
            comparingValue = 1000;
        }

        for (Player player : game.getPlayers()) {
            if (player.getCoins() > 11) {
                player.setCoins(player.getCoins() / 2);
            }

            if (card.getCardType() == CardType.TAX_SWORDS) {
                if (player.getSwords() > comparingValue) {
                    comparingValue = player.getSwords();
                }
            } else if (card.getCardType() == CardType.TAX_INFLUENCE) {
                if (player.getInfluencePoints() < comparingValue) {
                    comparingValue = player.getInfluencePoints();
                }
            }
        }
        for (Player player : game.getPlayers()) {
            if (card.getCardType() == CardType.TAX_SWORDS && player.getSwords() == comparingValue) {
                player.setCoins(player.getCoins() + 1);
            } else if (card.getCardType() == CardType.TAX_INFLUENCE && player.getInfluencePoints() == comparingValue) {
                player.setCoins(player.getCoins() + 1);
            }
        }

        actionAndSemiStateHolder.addState(game, game.getTable().faceCard(card));
        return actionAndSemiStateHolder;
    }
}

