package vsb.cec0094.bachelorProject.dao;

import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.models.User;

import java.util.List;

public interface GameDao {

    void createGameInQueue(GameInQueue gameInQueue);

    GameInQueue getGameInQueue(String owner);

    List<GameInQueue> getAllGames();

    void joinGame(String owner, String player);

    GameInQueue getPlayersGame(String player);
}
