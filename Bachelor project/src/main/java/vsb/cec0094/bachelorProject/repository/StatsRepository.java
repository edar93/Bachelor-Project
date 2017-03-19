package vsb.cec0094.bachelorProject.repository;

import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;

import java.util.List;

public interface StatsRepository {

    List<StatsRecord> getPlayersStats(String login);

    StatsRecord getGame(Long gameId);

    void CreateNewRecoed(Game game);

    Long getLatesGameId(String login);
}
