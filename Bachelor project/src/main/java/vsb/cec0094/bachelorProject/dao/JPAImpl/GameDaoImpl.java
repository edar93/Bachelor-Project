package vsb.cec0094.bachelorProject.dao.JPAImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.exceptions.NoEmptyPlaceInGame;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class GameDaoImpl implements GameDao {

    private static final String SELECT_ALL_GAMES = "SELECT g FROM GameInQueue g";
    private static final String JOIN_GAME = "UPDATE User SET gameInQueue = :game WHERE login = :player";
    private static final String GET_PLAYERS_GAME = "SELECT gameInQueue FROM User WHERE login = :player";

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    @Override
    public void createGameInQueue(GameInQueue gameInQueue) {
        LOGGER.debug("createGameInQueue was called");
        em.persist(gameInQueue);
        String owner = gameInQueue.getOwner();
        User player = em.find(User.class, owner);
        player.setGameInQueue(gameInQueue);
        em.merge(player);
    }

    @Override
    public GameInQueue getGameInQueue(String owner) {
        LOGGER.debug("getGameInQueue was called");
        return em.find(GameInQueue.class, owner);
    }

    @Override
    public List<GameInQueue> getAllGames() {
        LOGGER.debug("getAllGames was called");
        return em.createQuery(SELECT_ALL_GAMES, GameInQueue.class).getResultList();
    }

    @Override
    public void joinGame(String owner, String player) throws NoEmptyPlaceInGame {
        LOGGER.debug("joinGame was called");
        GameInQueue game = em.find(GameInQueue.class, owner);
        isEmptyPlaceInGame(game);
        em.createQuery(JOIN_GAME)
                .setParameter("game", game)
                .setParameter("player", player)
                .executeUpdate();
    }

    @Override
    public GameInQueue getPlayersGame(String player) {
        LOGGER.debug("getPlayersGame was called");
        Object result;
        try {
            result = em.createQuery(GET_PLAYERS_GAME)
                    .setParameter("player", player)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return (GameInQueue) result;
    }

    private void isEmptyPlaceInGame(GameInQueue gameInQueue) throws NoEmptyPlaceInGame {
        if (gameInQueue.getPlayersList().size() == gameInQueue.getMaxPlayersCount()) {
            throw new NoEmptyPlaceInGame("No empty place in game");
        }
    }
}
