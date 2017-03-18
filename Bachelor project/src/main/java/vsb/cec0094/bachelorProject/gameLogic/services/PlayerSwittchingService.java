package vsb.cec0094.bachelorProject.gameLogic.services;

import vsb.cec0094.bachelorProject.exceptions.TwoShipsOfSameTypeOnTableException;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Phase;
import vsb.cec0094.bachelorProject.gameLogic.Player;

public class PlayerSwittchingService {

    private static final int POINTS_TO_VICTORY = 1;

    private GameUtilsService gameUtilsService;
    private Game game;

    public PlayerSwittchingService(Game game) {
        this.game = game;
        gameUtilsService = new GameUtilsService(game);
    }

    /**
     * evaluate carrds
     *
     * @param twoShips
     */
    public void shiftPlayer(Boolean twoShips) {
        if (twoShips) {
            for (Player player : game.getPlayers()) {
                player.setCoins(player.getCoins() + player.getJestersCount());
            }
            nextActivePlayer(true);
            game.setPhase(Phase.EXPLORING);
        } else {
            gameUtilsService.applyJester();
            gameUtilsService.applyAdmiral();
            nextActivePlayer(false);
        }
        try {
            gameUtilsService.recalculateShips();
        } catch (TwoShipsOfSameTypeOnTableException e) {
            // can not occur
            e.printStackTrace();
        }
        if (getActivePlayerAsPlayer().getAdmiralsCount() > 1) {
            game.setAdmiralApplied(false);
        } else {
            game.setAdmiralApplied(true);
        }

        if (game.getTable().getCountOfCards() == 0 && !(Phase.EXPLORING.equals(game.getPhase()))) {
            shiftPlayer(false);
        }

        if (game.getTable().getCountOfCards() == 0 && (Phase.EXPLORING.equals(game.getPhase())) && (game.getCardsToTake() == 1)
                && (game.getActivePlayerAsPlayer().getAdmiralsCount() > 0)) {
            shiftPlayer(false);
        }
    }

    /**
     * switch active player
     *
     * @param twoShips
     */
    private void nextActivePlayer(boolean twoShips) {
        raiseActivePlayer();
        if (twoShips) {
            raisePlayerOnTurn();
            game.setPhase(Phase.EXPLORING);
            gameUtilsService.cleanTable();
        } else {
            if (game.getActivePlayer().equals(game.getPlayerOnTurn())) {
                gameUtilsService.cleanTable();
                game.setPhase(Phase.EXPLORING);
                raisePlayerOnTurn();
                raiseActivePlayer();
            } else {
                game.setPhase(Phase.OTHERS_PLAYERS_TRADING);
            }
        }
    }

    private void raiseActivePlayer() {
        game.setActivePlayer(game.getActivePlayer() + 1);
        if (game.getActivePlayer().equals(game.getPlayersCount())) {
            game.setActivePlayer(0);
        }
    }

    private void raisePlayerOnTurn() {
        game.setPlayerOnTurn(game.getPlayerOnTurn() + 1);
        if (game.getPlayerOnTurn().equals(game.getPlayersCount())) {
            game.setPlayerOnTurn(0);
            game.setGameOver(isGameInOverCheck());
        }
    }

    private boolean isGameInOverCheck() {
        for (Player player : game.getPlayers()) {
            if (player.getInfluencePoints() >= POINTS_TO_VICTORY) {
                return true;
            }
        }
        return false;
    }

    private Player getActivePlayerAsPlayer() {
        return game.getPlayers().get(game.getActivePlayer());
    }
}
