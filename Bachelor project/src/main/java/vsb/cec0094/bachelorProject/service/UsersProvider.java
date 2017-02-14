package vsb.cec0094.bachelorProject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsb.cec0094.bachelorProject.dao.GameDao;
import vsb.cec0094.bachelorProject.dao.GamesHolder;
import vsb.cec0094.bachelorProject.exceptions.NotPlayersTurnException;
import vsb.cec0094.bachelorProject.gameLogic.GameManipulator;
import vsb.cec0094.bachelorProject.models.GameInQueue;

@Service
public class UsersProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersProvider.class);
    @Autowired
    private GameDao gameDao;
    @Autowired
    private GamesHolder gamesHolder;
    private String login;
    private GameInQueue gameInQueue;
    private GameManipulator gameManipulator;

    public void prepareUser(String login) {
        this.login = login;
        if (login != null) {
            this.gameInQueue = gameDao.getPlayersGame(login);
        }
        if (gameInQueue != null) {
            this.gameManipulator = gamesHolder.getGame(gameInQueue.getOwner());
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public GameInQueue getGameInQueue() {
        return gameInQueue;
    }

    public void setGameInQueue(GameInQueue gameInQueue) {
        this.gameInQueue = gameInQueue;
    }

    public GameManipulator getGameManipulator() {
        return gameManipulator;
    }

    public void setGameManipulator(GameManipulator gameManipulator) {
        this.gameManipulator = gameManipulator;
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
