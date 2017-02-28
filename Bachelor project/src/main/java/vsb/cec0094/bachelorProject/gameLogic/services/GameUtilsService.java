package vsb.cec0094.bachelorProject.gameLogic.services;


import vsb.cec0094.bachelorProject.exceptions.TwoShipsOfSameTypeOnTableException;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Phase;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;

public class GameUtilsService {

    private Game game;

    public GameUtilsService(Game game) {
        this.game = game;
    }

    public void applyAdmiral() {
        if (game.getTable().getCountOfCards() > 4) {
            game.setAdmiralApplied(true);
            game.setPhase(Phase.TRADING);
            Player activeP = game.getPlayers().get(game.getActivePlayer());
            activeP.setCoins(activeP.getCoins() + (activeP.getAdmiralsCount() * 2));
        }
    }

    public void applyJester() {
        if (game.getTable().getCountOfCards() == 0) {
            Player activeP = game.getPlayers().get(game.getActivePlayer());
            activeP.setCoins(activeP.getCoins() + activeP.getJestersCount());
        }
    }

    public void cleanTable() {
        game.getDrawPile().getUsedCards(game.getTable().removeCards());
    }

    public void recalculateShips() throws TwoShipsOfSameTypeOnTableException {
        game.setCardsToTake(game.getPlayers().get(game.getActivePlayer()).getCardsToTake());
        if (!Phase.OTHERS_PLAYERS_TRADING.equals(game.getPhase())) {
            int skiff = 0, flute = 0, pinace = 0, frigate = 0, galleon = 0;
            for (Card card : game.getTable().getCards()) {
                switch (card.getCardType()) {
                    case SKIFF:
                        skiff += 1;
                        break;
                    case FLUTE:
                        flute += 1;
                        break;
                    case PINACE:
                        pinace += 1;
                        break;
                    case FRIGATE:
                        frigate += 1;
                        break;
                    case GALLEON:
                        galleon += 1;
                        break;
                }
            }
            if (skiff == 2 || flute == 2 || pinace == 2 || frigate == 2 || galleon == 2) {
                throw new TwoShipsOfSameTypeOnTableException();
            }

            int typesCount = skiff + flute + pinace + frigate + galleon;
            if (typesCount == 4) {
                game.setCardsToTake(game.getCardsToTake() + 1);
            } else if (typesCount == 5) {
                game.setCardsToTake(game.getCardsToTake() + 2);
            }
        }
    }
}
