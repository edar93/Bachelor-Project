package vsb.cec0094.bachelorProject.dao;

import vsb.cec0094.bachelorProject.exceptions.NoEmptyPlaceInGame;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import java.util.List;

public interface GameDao {

    void createGameInQueue(GameInQueue gameInQueue);

    GameInQueue getGameInQueue(String owner);

    List<GameInQueue> getAllGames();

    void joinGame(Integer id, String player) throws NoEmptyPlaceInGame;

    GameInQueue getGameById(Integer id);

    GameInQueue getPlayersGame(String player);

    void leftGame(String login, GameInQueue gameInQueue);

    void setNewMaxPlayersCount(Integer newMaxPlayersCount, GameInQueue gameInQueue);
}
