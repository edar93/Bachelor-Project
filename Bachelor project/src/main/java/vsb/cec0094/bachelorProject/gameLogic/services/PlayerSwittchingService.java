package vsb.cec0094.bachelorProject.gameLogic.services;

import vsb.cec0094.bachelorProject.exceptions.TwoShipsOfSameTypeOnTableException;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Phase;
import vsb.cec0094.bachelorProject.gameLogic.Player;

public class PlayerSwittchingService {

    private GameUtilsService gameUtilsService;
    private Game game;

    public PlayerSwittchingService(Game game) {
        this.game = game;
        gameUtilsService = new GameUtilsService(game);
    }

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
    }

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
        if (isGameInOverCheck()) {
            game.setPlayerOnTurn(game.getPlayerOnTurn() + 1);
            if (game.getPlayerOnTurn().equals(game.getPlayersCount())) {
                game.setPlayerOnTurn(0);
                game.setGameOver(true);
            }
        } else {
            game.setPlayerOnTurn(game.getPlayerOnTurn() + 1);
            if (game.getPlayerOnTurn().equals(game.getPlayersCount())) {
                game.setPlayerOnTurn(0);
            }
        }
    }

    private boolean isGameInOverCheck() {
        for (Player player : game.getPlayers()) {
            if (player.getInfluencePoints() >= 12) {
                return true;
            }
        }
        return false;
    }

    private void endGame() {
        //TODO
    }

    private Player getActivePlayerAsPlayer() {
        return game.getPlayers().get(game.getActivePlayer());
    }
}