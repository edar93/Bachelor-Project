package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.models.GameInQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class GameDaoImpl implements GameDao {

    private static final String SELECT_ALL_GAMES = "SELECT g FROM GameInQueue g";
    private static final String JOIN_GAME = "UPDATE User SET gameInQueue = :game WHERE login = :player";

    @PersistenceContext
    EntityManager em;

    @Override
    public void createGameInQueue(GameInQueue gameInQueue) {
        em.persist(gameInQueue);
    }

    @Override
    public GameInQueue getGameInQueue(String owner) {
        return em.find(GameInQueue.class, owner);
    }

    @Override
    public List<GameInQueue> getAllGames() {
        return em.createQuery(SELECT_ALL_GAMES, GameInQueue.class).getResultList();
    }

    @Override
    public void joinGame(String owner, String player) {
        GameInQueue game = em.find(GameInQueue.class, owner);
        em.createQuery(JOIN_GAME)
                .setParameter("game", game)
                .setParameter("player", player)
                .executeUpdate();
    }


}
