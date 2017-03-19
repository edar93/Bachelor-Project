package vsb.cec0094.bachelorProject.gameLogic.services;

import vsb.cec0094.bachelorProject.exceptions.InvalidActionException;
import vsb.cec0094.bachelorProject.exceptions.TooExpensiveExpeditionException;
import vsb.cec0094.bachelorProject.gameLogic.ActionAndSemiStateHolder;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Phase;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.Expedition;
import vsb.cec0094.bachelorProject.gameLogic.pack.Expeditions;
import vsb.cec0094.bachelorProject.gameLogic.pack.Table;
import vsb.cec0094.bachelorProject.models.Action;
import vsb.cec0094.bachelorProject.models.ActionToShow;

public class GetCardFromTableService {

    private Game game;
    private PlayerSwittchingService playerSwittchingService;

    public GetCardFromTableService(Game game) {
        this.game = game;
        playerSwittchingService = new PlayerSwittchingService(game);
    }

    public ActionAndSemiStateHolder playerGetCardFromTable(int cardPosition) throws InvalidActionException, TooExpensiveExpeditionException, CloneNotSupportedException {
        if (Phase.EXPLORING.equals(game.getPhase())) {
            game.setPhase(Phase.TRADING);
        }
        ActionToShow actionToShow = new ActionToShow(Action.GET_CARD, Table.TABLE, cardPosition);
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder(game, actionToShow);

        Card card = game.getTable().getCards().remove(cardPosition);
        cardTypeValidation(card);

        if (!game.getActivePlayer().equals(game.getPlayerOnTurn())) {
            game.getPlayers().get(game.getPlayerOnTurn()).addCoin();
        }

        if (Card.shipTypes.contains(card.getCardType())) {

            Player playerDoingAction = game.getPlayers().get(game.getActivePlayer());
            playerDoingAction.getCoinsFromShip(card);
            game.getDrawPile().getUsedCard(card);

            game.setCardsToTake(game.getCardsToTake() - 1);
            if (game.getCardsToTake() == 0) {
                playerSwittchingService.shiftPlayer(false);
            }
            return actionAndSemiStateHolder;
        } else {
            Player playerDoingAction = game.getPlayers().get(game.getActivePlayer());
            Boolean isOnTurn = (game.getActivePlayer().equals(game.getPlayerOnTurn()));
            playerDoingAction.takeCharacterCard(card, isOnTurn);
            playerDoingAction.updateVariables();

            game.setCardsToTake(game.getCardsToTake() - 1);
            if (game.getCardsToTake() == 0) {
                playerSwittchingService.shiftPlayer(false);
            }
            actionAndSemiStateHolder.addState(game, new ActionToShow(Action.GET_CARD, playerDoingAction.getLogin(), playerDoingAction.getCards().indexOf(card)));
            return actionAndSemiStateHolder;
        }
    }

    public ActionAndSemiStateHolder pickExpedition(int id) throws TooExpensiveExpeditionException, CloneNotSupportedException {
        ActionAndSemiStateHolder actionAndSemiStateHolder = new ActionAndSemiStateHolder();
        Expedition expedition = game.getExpeditions().getExpedition(id);
        game.getPlayers().get(game.getActivePlayer()).canTakeExpedition(expedition); // if not throws exeption

        actionAndSemiStateHolder.addState(game, new ActionToShow(Action.SHOW_EXPEDITION_WHICH_WILL_BE_TAKEN, Expeditions.EXPEDITIONS, id));
        ActionToShow takeExpeditionAction = game.getPlayers().get(game.getActivePlayer()).takeExpedition(expedition, game.getDrawPile());

        actionAndSemiStateHolder.addState(game, takeExpeditionAction);
        return actionAndSemiStateHolder;
    }

    private void cardTypeValidation(Card card) throws InvalidActionException {
        if (Card.noActionTypes.contains(card.getCardType())) {
            throw new InvalidActionException("invalid card type");
        }
    }

}
