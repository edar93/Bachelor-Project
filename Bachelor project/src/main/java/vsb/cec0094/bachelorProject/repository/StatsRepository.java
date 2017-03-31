package vsb.cec0094.bachelorProject.repository;

import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;

import javax.persistence.EntityManager;
import java.util.List;

public interface StatsRepository {

    Integer getPagesCount(String login);

    List<StatsRecord> getPlayersStats(String login);

    List<StatsRecord> getPlayersStats(String login, Integer page);

    StatsRecord getGame(Long gameId);

    void CreateNewRecoed(Game game);

    Long getLatesGameId(String login);

    void setEm(EntityManager em);
}
