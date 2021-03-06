package vsb.cec0094.bachelorProject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.GameOverExeption;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.GameInQueue;
import vsb.cec0094.bachelorProject.repository.StatsRepository;

import javax.inject.Inject;

public class UsersProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersProvider.class);

    @Inject
    private GameDao gameDao;
    @Inject
    private GamesHolder gamesHolder;
    @Inject
    private StatsRepository statsRepository;

    private String login;
    private GameInQueue gameInQueue;
    private GameManipulator gameManipulator;

    public void prepareUser(String login) {
        this.login = login;
        if (login != null) {
            this.gameInQueue = gameDao.getPlayersGame(login);
        }
        if (gameInQueue == null) {
            this.gameManipulator = null;
        } else {
            try {
                this.gameManipulator = gamesHolder.getGame(gameInQueue.getId());
            } catch (GameOverExeption e) {
                gamesHolder.removeGame(gameInQueue.getId());
                gameDao.removeEndedGame(gameInQueue.getId());
                statsRepository.CreateNewRecoed(e.getGame());
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public GameInQueue getGameInQueue() {
        return gameInQueue;
    }

    public GameManipulator getGameManipulator() {
        return gameManipulator;
    }

    public GameManipulator getGameManipulatorWhenIsPlayerOnTurn() throws NotPlayersTurnException {
        int activePlayerId = gameManipulator.getCurrentGame().getActivePlayer();
        String loginOfActivePlayer = gameManipulator.getCurrentGame().getPlayers().get(activePlayerId).getLogin();
        if (!this.login.equals(loginOfActivePlayer)) {
            LOGGER.debug("Inactive player{" + login + "} try to do move");
            throw new NotPlayersTurnException(" not \"" + login + "\" turn");
        }
        return gameManipulator;
    }
}
