package vsb.cec0094.bachelorProject.exceptions;

import vsb.cec0094.bachelorProject.gameLogic.Game;

public class GameOverExeption extends Exception {

    private Game game;

    public GameOverExeption(String message, Game game) {
        super(message);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
