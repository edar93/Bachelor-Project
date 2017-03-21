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
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class GameDaoImpl implements GameDao {

    private static final String SELECT_ALL_GAMES = "SELECT g FROM GameInQueue g";
    private static final String JOIN_GAME = "UPDATE User SET gameInQueue = :game WHERE login = :player";
    private static final String GET_PLAYERS_GAME = "SELECT gameInQueue FROM User WHERE login = :player";
    private static final String DELETE_PLAYERS_GAME = "UPDATE User u SET u.gameInQueue = null WHERE u.gameInQueue = :game";
    private static final String DELETE_PLAYERS_GAME_FOR_PLAYER = "UPDATE User u SET u.gameInQueue = null WHERE u.login = :login";
    private static final String GET_HIGHEST_ID = "SELECT MAX(id) FROM GameInQueue";
    private static final String GET_GAME_BY_ID = "SELECT g FROM GameInQueue g WHERE g.id = :id";
    private static final String RELEASE_PLAYER = "UPDATE User u SET u.inEndedGame = null WHERE u.login = :login ";

    private static final Logger LOGGER = LoggerFactory.getLogger(GameDaoImpl.class);

    @PersistenceContext
    EntityManager em;

    private int getIdForNewGame() {
        try {
            return ((Integer) em.createQuery(GET_HIGHEST_ID)
                    .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                    .getSingleResult()
            )
                    + 1;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    @Override
    public void createGameInQueue(GameInQueue gameInQueue) {
        LOGGER.debug("createGameInQueue was called");
        gameInQueue.setId(getIdForNewGame());
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
    public void joinGame(Integer id, String player) throws NoEmptyPlaceInGame {
        LOGGER.debug("joinGame was called with:" + id + " - " + player);
        GameInQueue gameInQueue = em.find(GameInQueue.class, id);
        isEmptyPlaceInGame(gameInQueue);
        User user = em.find(User.class, player);
        user.setGameInQueue(gameInQueue);
        gameInQueue.getPlayersList().add(user);
    }

    @Override
    public GameInQueue getGameById(Integer id) {
        Object result;
        try {
            result = em.createQuery(GET_GAME_BY_ID)
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
        return (GameInQueue) result;
    }

    @Override
    public GameInQueue getPlayersGame(String player) {
        //LOGGER.debug("getPlayersGame was called");
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

    @Override
    public void leftGame(String login, GameInQueue gameInQueue) {
        LOGGER.debug("leftGame was called login={}, gameInQueue={}", login, gameInQueue);
        if (login.equals(gameInQueue.getOwner())) {
            gameInQueue = em.find(GameInQueue.class, gameInQueue.getId());
            em.createQuery(DELETE_PLAYERS_GAME)
                    .setParameter("game", gameInQueue)
                    .executeUpdate();
            em.remove(gameInQueue);
        } else {
            em.createQuery(DELETE_PLAYERS_GAME_FOR_PLAYER)
                    .setParameter("login", login)
                    .executeUpdate();
        }
    }

    @Override
    public void setNewMaxPlayersCount(Integer newMaxPlayersCount, GameInQueue gameInQueue) {
        gameInQueue = em.find(GameInQueue.class, gameInQueue.getId());
        gameInQueue.setMaxPlayersCount(newMaxPlayersCount);
    }

    @Override
    public void removeEndedGame(Integer gameInQueueId) {
        GameInQueue gameInQueue = em.find(GameInQueue.class, gameInQueueId);
        for (User user : gameInQueue.getPlayersList()) {
            user.setGameInQueue(null);
            user.setInEndedGame(1);
        }
        em.remove(gameInQueue);
    }

    @Override
    public void releasePlayer(String login) {
        em.createQuery(RELEASE_PLAYER)
                .setParameter("login", login)
                .executeUpdate();
    }

    private void isEmptyPlaceInGame(GameInQueue gameInQueue) throws NoEmptyPlaceInGame {
        if (gameInQueue.getPlayersList().size() == gameInQueue.getMaxPlayersCount()) {
            throw new NoEmptyPlaceInGame("No empty place in game");
        }
    }
}
