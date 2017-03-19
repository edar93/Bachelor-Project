package vsb.cec0094.bachelorProject.repository.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.JPAImpl.IdGeneratorDao;
import vsb.cec0094.bachelorProject.gameLogic.Game;
import vsb.cec0094.bachelorProject.gameLogic.Player;
import vsb.cec0094.bachelorProject.gameLogic.card.Card;
import vsb.cec0094.bachelorProject.gameLogic.card.CardType;
import vsb.cec0094.bachelorProject.models.statsModel.StatsRecord;
import vsb.cec0094.bachelorProject.repository.StatsRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
@Transactional
public class StatsRepositoryImpl implements StatsRepository {

    private static final String GET_PLAYERS_GAME = "SELECT sr FROM StatsRecord sr JOIN Player p ON p.record.id = sr.id WHERE p.login = :login ORDER BY sr.createDate DESC";
    private static final String GET_LATEST_GAME_ID = "SELECT sr.id FROM StatsRecord sr JOIN Player p ON p.record.id = sr.id WHERE p.login = :login ORDER BY sr.createDate DESC ";

    @Inject
    IdGeneratorDao idGenerator;

    @PersistenceContext
    EntityManager em;

    @Override
    public List<StatsRecord> getPlayersStats(String login) {
        StatsRecord test = em.find(StatsRecord.class, 0L);
        List<StatsRecord> statsRecordList = em.createQuery(GET_PLAYERS_GAME, StatsRecord.class)
                .setParameter("login", login)
                .getResultList();
        return statsRecordList;
    }

    @Override
    public StatsRecord getGame(Long gameId) {
        StatsRecord test = em.find(StatsRecord.class, 0L);
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

    @Override
    public void test() {
        Card card = new Card();
        card.setId(0);
        card.setCardType(CardType.ADMIRAL);
        card.setCoin(5);
        card.setInfluence(1);
        em.persist(card);

        Card card2 = new Card();
        card2.setId(1);
        card2.setCardType(CardType.PIRATE);
        card2.setCoin(7);
        card2.setInfluence(2);
        em.persist(card2);

        Player player = new Player();
        player.setLogin("adam");
        player.setId(0);
        player.setSwords(2);
        player.setInfluencePoints(3);
        List<Card> cardArrayList = new ArrayList<>();
        cardArrayList.add(card);
        cardArrayList.add(card2);
        player.setCards(cardArrayList);

        em.persist(player);

        StatsRecord statsRecord = new StatsRecord();
        statsRecord.setId(1);
        statsRecord.setCreateDate(new Date());
        Set<Player> playerList = new HashSet<>();
        playerList.add(player);
        statsRecord.setPlayerList(playerList);

        em.persist(statsRecord);
    }

}
