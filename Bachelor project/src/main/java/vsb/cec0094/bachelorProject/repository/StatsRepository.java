package vsb.cec0094.bachelorProject.repository;

import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;

import java.util.List;

public interface StatsRepository {

    void test();

    List<StatsRecord> getPlayersStats(String login);

    StatsRecord getGame(Long gameId);
}