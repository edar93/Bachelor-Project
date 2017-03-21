package vsb.cec0094.bachelorProject.repository.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.JPAImpl.IdGeneratorDao;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;
import vsb.cec0094.bachelorProject.repository.StatsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    private static final int PageSize = 3;

    private static final String GET_PLAYERS_GAME = "SELECT sr FROM StatsRecord sr JOIN Player p ON p.record.id = sr.id WHERE p.login = :login ORDER BY sr.createDate DESC";
    private static final String GET_LATEST_GAME_ID = "SELECT sr.id FROM StatsRecord sr JOIN Player p ON p.record.id = sr.id WHERE p.login = :login ORDER BY sr.createDate DESC ";
    private static final String RECORDS_COUNT = "SELECT count(sr) FROM StatsRecord sr JOIN Player p ON p.record.id = sr.id WHERE p.login = :login";

    @Inject
    IdGeneratorDao idGenerator;

    @PersistenceContext
    EntityManager em;

    @Override
    public List<StatsRecord> getPlayersStats(String login) {
        return em.createQuery(GET_PLAYERS_GAME, StatsRecord.class)
                .setParameter("login", login)
                .getResultList();
    }

    // pages starts from 1
    @Override
    public List<StatsRecord> getPlayersStats(String login, Integer page) {
        page--;
        return em.createQuery(GET_PLAYERS_GAME, StatsRecord.class)
                .setParameter("login", login)
                .setFirstResult(PageSize * page)
                .setMaxResults(PageSize)
                .getResultList();
    }

    @Override
    public Integer getPagesCount(String login) {
        int plus = 0;
        long resultsConut = em.createQuery(RECORDS_COUNT, Long.class)
                .setParameter("login", login)
                .getSingleResult();
        if (resultsConut % PageSize != 0) {
            plus = 1;
        }
        return (((int) resultsConut) / PageSize) + plus;
    }

    @Override
    public StatsRecord getGame(Long gameId) {
        return em.find(StatsRecord.class, gameId);
    }

    @Override
    public void CreateNewRecoed(Game game) {
        StatsRecord statsRecord = new StatsRecord();
        statsRecord.setCreateDate(new Date());

        statsRecord.setId(idGenerator.getIdForNewStatsRecord());
        Long playersId = idGenerator.getIdForNewPlayer();
        Integer cardId = idGenerator.getIdForNewCard();

        for (Player player : game.getPlayers()) {
            player.setId(playersId++);
            for (Card card : player.getCards()) {
                card.setId(cardId++);
                em.persist(card);
            }
            em.persist(player);
        }

        statsRecord.setPlayerList(new HashSet<>(game.getPlayers()));
        em.persist(statsRecord);
    }

    @Override
    public Long getLatesGameId(String login) {
        return em.createQuery(GET_LATEST_GAME_ID, Long.class)
                .setParameter("login", login)
                .setMaxResults(1)
                .getSingleResult();
    }
}
